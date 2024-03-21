package br.fiap.projeto.ponto.entity;

import java.util.List;

public class PontoReport {
    List<PontoDiario> pontoMensal;
    MesAno referencia;
    Periodo totalTrabalhadoMes;

    public PontoReport(List<PontoDiario> pontoMensal, MesAno referencia, Periodo totalTrabalhadoMes) {
        this.pontoMensal = pontoMensal;
        this.referencia = referencia;
        this.totalTrabalhadoMes = totalTrabalhadoMes;
    }

    public List<PontoDiario> getPontoMensal() {
        return pontoMensal;
    }

    public MesAno getReferencia() {
        return referencia;
    }

    public Periodo getTotalTrabalhadoMes() {
        return totalTrabalhadoMes;
    }
}
