package br.fiap.projeto.ponto.adapter.controller.rest.response;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Data
@NoArgsConstructor @AllArgsConstructor
public class PontoResponseDTO {

    private UUID pontoId;
    private UUID usuarioId;
    private LocalDateTime dataHoraEvento;
    private PontoEventType tipoEvento;

    public static PontoResponseDTO fromPonto(Ponto ponto) {
        if (ponto == null) {
            return null;
        }
        return new PontoResponseDTO(ponto.getPontoId(),ponto.getUsuarioId(),ponto.getDataHoraEvento(),ponto.getTipoEvento());
    }

    public UUID getPontoId() {
        return pontoId;
    }

    public UUID getUsuarioId() {
        return usuarioId;
    }

    public LocalDateTime getDataHoraEvento() {
        return dataHoraEvento;
    }

    public PontoEventType getTipoEvento() {
        return tipoEvento;
    }
}
