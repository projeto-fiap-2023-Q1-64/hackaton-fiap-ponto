package br.fiap.projeto.ponto.usecase.exception;

public class EntradaInvalidaException extends BaseException {

    public EntradaInvalidaException(String message) {
        super(4002, message);
    }
}
