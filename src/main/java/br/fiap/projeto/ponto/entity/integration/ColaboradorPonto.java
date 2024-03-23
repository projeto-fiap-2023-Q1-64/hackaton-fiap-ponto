package br.fiap.projeto.ponto.entity.integration;

public class ColaboradorPonto {
    private String codigo;
    private String nome;
    private String matricula;
    private String email;

    public ColaboradorPonto(String codigo, String nome, String matricula, String email) {
        this.codigo = codigo;
        this.nome = nome;
        this.matricula = matricula;
        this.email = email;
    }

    public String getCodigo() {
        return codigo;
    }

    public String getNome() {
        return nome;
    }

    public String getMatricula() {
        return matricula;
    }

    public String getEmail() {
        return email;
    }
}
