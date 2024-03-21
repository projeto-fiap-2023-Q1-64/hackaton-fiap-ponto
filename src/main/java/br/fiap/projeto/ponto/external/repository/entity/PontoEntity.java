package br.fiap.projeto.ponto.external.repository.entity;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;


@Entity @Getter
@Table(name = "ponto")
public class PontoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID pontoId;
    @Column(nullable = false)
    private UUID usuarioId;
    @Column(nullable = false)
    private LocalDateTime dataHoraEvento;
    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private PontoEventType tipoEvento;

    public PontoEntity() {
    }

    public PontoEntity(UUID pontoId, UUID usuarioId, LocalDateTime dataHoraEvento, PontoEventType tipoEvento) {
        this.pontoId = pontoId;
        this.usuarioId = usuarioId;
        this.dataHoraEvento = dataHoraEvento;
        this.tipoEvento = tipoEvento;
    }
    public static PontoEntity fromPonto(Ponto ponto) {
        return new PontoEntity(ponto.getPontoId(), ponto.getUsuarioId(), ponto.getDataHoraEvento(), ponto.getTipoEvento());
    }

    public Ponto toPonto() {
        try {
            return new Ponto(this.pontoId, this.usuarioId, this.dataHoraEvento, this.tipoEvento);
        } catch (EntradaInvalidaException e) {
            throw new RuntimeException(e);
        }
    }
}
