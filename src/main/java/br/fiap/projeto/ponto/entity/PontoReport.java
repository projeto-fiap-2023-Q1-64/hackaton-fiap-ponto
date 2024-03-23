package br.fiap.projeto.ponto.entity;

import java.util.List;

public class PontoReport {
    List<PontoDiario> pontoMensal;
    MesAno referencia;
    Periodo totalTrabalhadoMes;
    String nomeColaborador;

    public PontoReport(List<PontoDiario> pontoMensal, MesAno referencia, Periodo totalTrabalhadoMes, String nomeColaborador){
        this.pontoMensal = pontoMensal;
        this.referencia = referencia;
        this.totalTrabalhadoMes = totalTrabalhadoMes;
        this.nomeColaborador = nomeColaborador;
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
    public String getNomeColaborador() {
        return nomeColaborador;
    }}
