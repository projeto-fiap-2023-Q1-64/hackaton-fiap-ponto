package br.fiap.projeto.ponto.adapter.controller.rest.exception;

public class MensagemErroDTO {

    private final int code;
    private final String erro;

    public MensagemErroDTO(int code, String erro) {
        this.code = code;
        this.erro = erro;
    }

    public int getCode() {
        return code;
    }

    public String getErro() {
        return erro;
    }
}
