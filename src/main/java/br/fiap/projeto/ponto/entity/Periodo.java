package br.fiap.projeto.ponto.entity;

public class Periodo {
    private long horas;
    private long minutos;

    public Periodo(long horas, long minutos) {
        this.horas = horas;
        this.minutos = minutos;
    }

    public long getHoras() {
        return horas;
    }

    public long getMinutos() {
        return minutos;
    }
}
