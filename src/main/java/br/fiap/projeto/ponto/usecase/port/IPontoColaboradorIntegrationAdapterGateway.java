package br.fiap.projeto.ponto.usecase.port;

import br.fiap.projeto.ponto.entity.integration.ColaboradorPonto;

public interface IPontoColaboradorIntegrationAdapterGateway {
    ColaboradorPonto buscaColaborador(String codigoColaborador);
}
