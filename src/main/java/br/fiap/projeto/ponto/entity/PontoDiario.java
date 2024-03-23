package br.fiap.projeto.ponto.entity;

import java.time.LocalDate;
import java.util.List;

public class PontoDiario {
    private List<Ponto> pontos;
    private LocalDate data;
    private Periodo totalTrabalhadoDia;
    private String nomeColaborador;

    public PontoDiario(List<Ponto> pontos, LocalDate data, Periodo totalTrabalhadoDia, String nomeColaborador) {
        this.pontos = pontos;
        this.data = data;
        this.totalTrabalhadoDia = totalTrabalhadoDia;
        this.nomeColaborador = nomeColaborador;
    }

    public List<Ponto> getPontos() {
        return pontos;
    }

    public LocalDate getData() {
        return data;
    }

    public Periodo getTotalTrabalhadoDia() {
        return totalTrabalhadoDia;
    }

    public String getNomeColaborador() {
        return nomeColaborador;
    }
}
