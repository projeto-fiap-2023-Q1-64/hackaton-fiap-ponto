package br.fiap.projeto.ponto.external.config;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.time.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Configuration @RequiredArgsConstructor
public class PostgresPontoDataLoader {

    private final IPontoRepositoryAdapterGateway pontoRepository;

    @PostConstruct
    @SneakyThrows
    public void init() {
        UUID usuarioId = UUID.fromString("2a643454-e2e6-4ed4-9f77-52a94ec60642");
        List<Ponto> pontos = new ArrayList<>();

        // Obtém o primeiro dia de fevereiro de 2024
        LocalDate dataAtual = LocalDate.of(2024, 2, 1);

        // Loop para percorrer cada dia de fevereiro de 2024
        while (dataAtual.getMonth() == Month.FEBRUARY) {
            // Verifica se é um dia útil (segunda a sexta-feira)
            DayOfWeek diaDaSemana = dataAtual.getDayOfWeek();
            if (diaDaSemana != DayOfWeek.SATURDAY && diaDaSemana != DayOfWeek.SUNDAY) {
                // Adiciona os pontos para o dia útil atual
                pontos.add(new Ponto(UUID.randomUUID(), usuarioId, LocalDateTime.of(dataAtual, LocalTime.of(9, 0)), PontoEventType.ENTRADA));
                pontos.add(new Ponto(UUID.randomUUID(), usuarioId, LocalDateTime.of(dataAtual, LocalTime.of(12, 0)), PontoEventType.SAIDA));
                pontos.add(new Ponto(UUID.randomUUID(), usuarioId, LocalDateTime.of(dataAtual, LocalTime.of(13, 0)), PontoEventType.ENTRADA));
                pontos.add(new Ponto(UUID.randomUUID(), usuarioId, LocalDateTime.of(dataAtual, LocalTime.of(18, 0)), PontoEventType.SAIDA));
            }
            // Avança para o próximo dia
            dataAtual = dataAtual.plusDays(1);
        }

        pontos.forEach(pontoRepository::insere);
    }
}
