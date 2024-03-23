package br.fiap.projeto.ponto.adapter.mapper;

import br.fiap.projeto.ponto.entity.integration.ColaboradorPonto;
import br.fiap.projeto.ponto.external.integration.port.Colaborador;

public class ColaboradorMapper {
    public static ColaboradorPonto toColaboradorPonto(Colaborador colaborador){
        return new ColaboradorPonto(colaborador.getCodigo(), colaborador.getNome(), colaborador.getMatricula(), colaborador.getEmail());
    }
}
