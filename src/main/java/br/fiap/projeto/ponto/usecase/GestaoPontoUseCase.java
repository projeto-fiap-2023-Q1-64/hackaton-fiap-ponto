package br.fiap.projeto.ponto.usecase;

import br.fiap.projeto.ponto.entity.*;
import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import br.fiap.projeto.ponto.usecase.port.IGestaoPontoUsecase;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

public class GestaoPontoUseCase implements IGestaoPontoUsecase {

    private final IPontoRepositoryAdapterGateway clienteRepositoryAdapterGateway;

    public GestaoPontoUseCase(IPontoRepositoryAdapterGateway pontoRepository) {
        this.clienteRepositoryAdapterGateway = pontoRepository;
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
            tempoTrabalhadoMes = tempoTrabalhadoMes.plus(tempoTrabalhado);
            // Cria um registro de período do tempo trabalhado do dia
            Long horas = tempoTrabalhado.toHours();
            Long min = tempoTrabalhado.minusHours(horas).toMinutes();
            Periodo periodo = new Periodo(horas, min);
            // Gera um registro de ponto diário
            PontoDiario pontoDiario = new PontoDiario(pontosDoDia, data, periodo);
            // Adiciona ponto diário a lista
            pontosDiarios.add(pontoDiario);
        }
        Long horas = tempoTrabalhadoMes.toHours();
        Long min = tempoTrabalhadoMes.minusHours(horas).toMinutes();
        Periodo periodoMes = new Periodo(horas, min);
        PontoReport pontoReport = new PontoReport(pontosDiarios, mesAnoRef, periodoMes);
        return pontoReport;
    }

    @Override
    public Ponto buscaPorData(Date data) throws EntidadeNaoEncontradaException {
        Optional<Ponto> pontoEncontrado = clienteRepositoryAdapterGateway.buscaPorData(data);
        if (!pontoEncontrado.isPresent()) {
            throw new EntidadeNaoEncontradaException("Entidade Não encontrada");
        }
        return pontoEncontrado.get();
    }
}