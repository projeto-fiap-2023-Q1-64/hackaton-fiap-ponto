package br.fiap.projeto.ponto.adapter.gateway;

import br.fiap.projeto.ponto.adapter.mapper.ColaboradorMapper;
import br.fiap.projeto.ponto.entity.integration.ColaboradorPonto;
import br.fiap.projeto.ponto.external.integration.PontoColaboradorIntegration;
import br.fiap.projeto.ponto.usecase.port.IPontoColaboradorIntegrationAdapterGateway;

public class PontoColaboradorIntegrationAdapterGateway implements IPontoColaboradorIntegrationAdapterGateway {
    private final PontoColaboradorIntegration pontoColaboradorIntegration;

    public PontoColaboradorIntegrationAdapterGateway(PontoColaboradorIntegration pontoColaboradorIntegration) {
        this.pontoColaboradorIntegration = pontoColaboradorIntegration;
    }

    @Override
    public ColaboradorPonto buscaColaborador(String codigoColaborador) {
        return ColaboradorMapper.toColaboradorPonto(
                pontoColaboradorIntegration.busca(codigoColaborador)
        );
    }
}
