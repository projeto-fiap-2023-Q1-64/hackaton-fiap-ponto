package br.fiap.projeto.ponto.usecase;

import br.fiap.projeto.ponto.entity.*;
import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import br.fiap.projeto.ponto.entity.integration.ColaboradorPonto;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import br.fiap.projeto.ponto.usecase.port.IGestaoPontoUsecase;
import br.fiap.projeto.ponto.usecase.port.IPontoColaboradorIntegrationAdapterGateway;
import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import br.fiap.projeto.ponto.utils.EmailSender;

import javax.mail.MessagingException;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class GestaoPontoUseCase implements IGestaoPontoUsecase {
    public static final String COLABORADOR_AUSENTE = "Colaborador Não encontrado!";

    private final IPontoRepositoryAdapterGateway clienteRepositoryAdapterGateway;
    private final IPontoColaboradorIntegrationAdapterGateway pontoColaboradorIntegrationAdapterGateway;

    public GestaoPontoUseCase(IPontoRepositoryAdapterGateway pontoRepository, IPontoColaboradorIntegrationAdapterGateway pontoColaboradorIntegrationAdapterGateway) {
        this.clienteRepositoryAdapterGateway = pontoRepository;
        this.pontoColaboradorIntegrationAdapterGateway = pontoColaboradorIntegrationAdapterGateway;
    }

    @Override
    public Ponto insere(UUID usuarioId) throws EntradaInvalidaException, EntidadeNaoEncontradaException {
        PontoEventType pontoEventType = PontoEventType.ENTRADA;
        Optional<Ponto> optionalPonto = clienteRepositoryAdapterGateway.buscaUltimoRegistroCorrete(usuarioId);
        if(optionalPonto.isPresent() && optionalPonto.get().getTipoEvento().equals(pontoEventType)){
            pontoEventType = PontoEventType.SAIDA;
        }
        Ponto novoPonto = new Ponto(usuarioId, pontoEventType);
        return clienteRepositoryAdapterGateway.insere(novoPonto);
    }
    @Override
    public PontoReport getPontosPorMesAno(UUID usuarioId, int mes, int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException {
        if (usuarioId == null) {
            throw new EntradaInvalidaException(Ponto.CODIGO_AUSENTE);
        }
        if (mes < 1 || mes > 12) {
            throw new EntradaInvalidaException(Ponto.MES_INVALIDO);
        }
        if (ano < 2000) {
            throw new EntradaInvalidaException(Ponto.ANO_INVALIDO);
        }

        ColaboradorPonto colaborador = pontoColaboradorIntegrationAdapterGateway.buscaColaborador(usuarioId.toString());

        if (colaborador == null){
            throw new EntradaInvalidaException(COLABORADOR_AUSENTE);
        }

        MesAno mesAnoRef = new MesAno(mes,ano);
        // Recupera todos os pontos lançados do mês
        List<Ponto> pontos = clienteRepositoryAdapterGateway.findByUsuarioIdAndMesEAno(usuarioId, mes, ano);

        // Lista de pontos diários
        List<PontoDiario> pontosDiarios = new ArrayList<>();

        // Map de Listas de pontos separados por Data (LocalDate)
        Map<LocalDate, List<Ponto>> pontosPorDia = pontos.stream()
                .collect(Collectors.groupingBy(ponto -> ponto.getDataHoraEvento().toLocalDate()));

        // Para armazenar a duração do mês
        Duration tempoTrabalhadoMes = Duration.ZERO;

        // Loop para contabilizar valores de entradas diárias
        for (Map.Entry<LocalDate, List<Ponto>> entry : pontosPorDia.entrySet()) {
            // Data do registro corrente do mapa
            LocalDate data = entry.getKey();
            // Lista dos pontos do dia corrente
            List<Ponto> pontosDoDia = entry.getValue();

            //Recupera o trabalhado no dia
            PontoDiario pontoDiario = generatePontoDiarioByPontoList(pontosDoDia, data, colaborador.getNome());

            //Recupera um duration do trabalhado no dia
            Duration tempoTrabalhadoDia = Duration.ofHours(pontoDiario
                    .getTotalTrabalhadoDia().getHoras())
                    .plus(Duration.ofMinutes(pontoDiario
                            .getTotalTrabalhadoDia().getMinutos()));

            // Vai somando o tempo de cada dia para ter o do Mês
            tempoTrabalhadoMes = tempoTrabalhadoMes.plus(tempoTrabalhadoDia);

            // Adiciona ponto diário a lista
            pontosDiarios.add(pontoDiario);
        }
        // Geração do periodo trabalhado do mês
        Long horas = tempoTrabalhadoMes.toHours();
        Long min = tempoTrabalhadoMes.minusHours(horas).toMinutes();
        Periodo periodoMes = new Periodo(horas, min);
        //Gera o report com os valores do mês
        PontoReport pontoReport = new PontoReport(pontosDiarios, mesAnoRef, periodoMes, colaborador.getNome());

        this.EnviarEmail(pontoReport, colaborador.getEmail());

        return pontoReport;
    }

    @Override
    public PontoDiario buscaPorData(UUID usuarioId, LocalDate data) throws EntradaInvalidaException {
        List<Ponto> pontoEncontrado = clienteRepositoryAdapterGateway.findByUsuarioIdAndData(usuarioId, data);

        ColaboradorPonto colaborador = pontoColaboradorIntegrationAdapterGateway.buscaColaborador(usuarioId.toString());

        if (colaborador == null){
            throw new EntradaInvalidaException(COLABORADOR_AUSENTE);
        }

        return this.generatePontoDiarioByPontoList(pontoEncontrado, data, colaborador.getNome());
    }

    private PontoDiario generatePontoDiarioByPontoList(List<Ponto> pontosDoDia, LocalDate data, String nomeColaborador){
        // Para armazenar a data da ultima entrada
        LocalDateTime entrada = null;
        // Armazenar o tempo entre os intervalos (Inicializando com zero)
        Duration tempoTrabalhado = Duration.ZERO;

        // Loop para pegar os lançamentos do e contabilizar os intervalos de entradas e saídas
        for (Ponto ponto: pontosDoDia) {
            if(PontoEventType.ENTRADA.equals(ponto.getTipoEvento())){
                // Se o evento é de entrada pega a data hora da entrada
                entrada = ponto.getDataHoraEvento();
            }else{
                // Se não é entrada é saída, então pega o intervalo entre a ultima entrada armazenada
                LocalDateTime saida = ponto.getDataHoraEvento();
                tempoTrabalhado = tempoTrabalhado.plus(Duration.between(entrada, saida));
            }
        }
        // Vai somando o tempo de cada dia para ter o do Mês
        // Cria um registro de período do tempo trabalhado do dia
        Long horas = tempoTrabalhado.toHours();
        Long min = tempoTrabalhado.minusHours(horas).toMinutes();
        Periodo periodo = new Periodo(horas, min);
        // Gera um registro de ponto diário
        return new PontoDiario(pontosDoDia, data, periodo, nomeColaborador);
    }

    private void EnviarEmail(PontoReport pontoReport, String destinatario){
        EmailSender emailSender = new EmailSender();
        int mes = pontoReport.getReferencia().getMes();
        int ano = pontoReport.getReferencia().getAno();
        // Formatar o mês com dois dígitos
        String mesFormatado = String.format("%02d", mes);
        String assunto = "Relatório de ponto referente a " + mesFormatado + "/" + ano;

        String mailContent = gerarHTMLRelatorio(pontoReport);

        try {
            emailSender.enviarEmail(destinatario, assunto , mailContent);
        } catch (MessagingException e) {
            e.printStackTrace();
        }
    }

    public String gerarHTMLRelatorio(PontoReport pontoReport) {
        StringBuilder htmlBuilder = new StringBuilder();
        htmlBuilder.append("<html><body>");
        htmlBuilder.append("<h1>Relatório de Pontos</h1>");
        htmlBuilder.append("<p>Colaborador: ").append(pontoReport.getNomeColaborador()).append("</p>");
        htmlBuilder.append("<p>Data de Referência: ").append(pontoReport.getReferencia().getMes()).append("/").append(pontoReport.getReferencia().getAno()).append("</p>");
        htmlBuilder.append("<p>Total Horas trabalhado no Mês: ").append(formatarPeriodo(pontoReport.getTotalTrabalhadoMes())).append("</p>");
        htmlBuilder.append("<table border='1'>");
        htmlBuilder.append("<tr><th>Data</th><th>Entrada 1</th><th>Saída 1</th><th>Entrada 2</th><th>Saída 2</th><th>Total Horas Trabalhadas</th></tr>");

        // Iterar sobre os pontos diários e adicionar cada um à tabela
        for (PontoDiario pontoDiario : pontoReport.getPontoMensal()) {
            htmlBuilder.append("<tr>");
            htmlBuilder.append("<td>").append(pontoDiario.getData().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))).append("</td>");

            // Iterar sobre os pontos do dia
            int entradas = 0;
            int saidas = 0;
            for (Ponto ponto : pontoDiario.getPontos()) {
                if (ponto.getTipoEvento() == PontoEventType.ENTRADA && entradas < 2) {
                    htmlBuilder.append("<td>").append(ponto.getDataHoraEvento().format(DateTimeFormatter.ofPattern("HH:mm"))).append("</td>");
                    entradas++;
                } else if (ponto.getTipoEvento() == PontoEventType.SAIDA && saidas < 2) {
                    htmlBuilder.append("<td>").append(ponto.getDataHoraEvento().format(DateTimeFormatter.ofPattern("HH:mm"))).append("</td>");
                    saidas++;
                }
            }

            // Preencher as células restantes com vazio, se necessário
            for (int i = entradas; i < 2; i++) {
                htmlBuilder.append("<td></td>");
            }
            for (int i = saidas; i < 2; i++) {
                htmlBuilder.append("<td></td>");
            }

            htmlBuilder.append("<td>").append(formatarPeriodo(pontoDiario.getTotalTrabalhadoDia())).append("</td>");
            htmlBuilder.append("</tr>");
        }

        htmlBuilder.append("</table>");
        htmlBuilder.append("</body></html>");
        return htmlBuilder.toString();
    }

    private String formatarPeriodo(Periodo periodo) {
        return String.format("%02d:%02d", periodo.getHoras(), periodo.getMinutos());
    }
}