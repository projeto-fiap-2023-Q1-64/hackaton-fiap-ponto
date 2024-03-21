package br.fiap.projeto.ponto.entity;

import br.fiap.projeto.ponto.entity.enums.PontoEventType;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;

import java.time.LocalDateTime;
import java.util.UUID;

public class Ponto {
    public static final String CODIGO_AUSENTE = "Informe o código do cliente!";
    public static final String MES_AUSENTE = "Informe o mês!";
    public static final String ANO_AUSENTE = "Informe o ano!";
    public static final String MES_INVALIDO = "Informe um mês válido!";
    public static final String ANO_INVALIDO = "Informe um ano válido!";
    private UUID pontoId;
    private UUID usuarioId;
    private LocalDateTime dataHoraEvento;
    private PontoEventType tipoEvento;

    public Ponto(UUID pontoId, UUID usuarioId, LocalDateTime dataHoraEvento, PontoEventType tipoEvento) throws EntradaInvalidaException {
        this.pontoId = pontoId;
        this.usuarioId = usuarioId;
        this.dataHoraEvento = dataHoraEvento;
        this.tipoEvento = tipoEvento;
    }

    public Ponto(UUID usuarioId, PontoEventType pontoEventType) throws EntradaInvalidaException {
        this.usuarioId = usuarioId;
        this.dataHoraEvento = LocalDateTime.now();
        this.tipoEvento = pontoEventType;
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
