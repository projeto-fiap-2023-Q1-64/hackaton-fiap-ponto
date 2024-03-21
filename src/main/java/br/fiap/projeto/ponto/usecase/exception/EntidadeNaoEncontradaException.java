package br.fiap.projeto.ponto.usecase.exception;

public class EntidadeNaoEncontradaException extends BaseException {

    public EntidadeNaoEncontradaException(String message) {
        super(4001, message);
    }

}
