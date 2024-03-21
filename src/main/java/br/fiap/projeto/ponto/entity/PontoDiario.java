package br.fiap.projeto.ponto.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

public class PontoDiario {
    private List<Ponto> pontos;
    private LocalDate data;
    private Periodo totalTrabalhadoDia;

    public PontoDiario(List<Ponto> pontos, LocalDate data, Periodo totalTrabalhadoDia) {
        this.pontos = pontos;
        this.data = data;
        this.totalTrabalhadoDia = totalTrabalhadoDia;
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
}
