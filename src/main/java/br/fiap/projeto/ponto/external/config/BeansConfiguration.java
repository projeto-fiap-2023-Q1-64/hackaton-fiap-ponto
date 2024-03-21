package br.fiap.projeto.ponto.external.config;

import br.fiap.projeto.ponto.adapter.controller.PontoRestAdapterController;
import br.fiap.projeto.ponto.adapter.controller.port.IPontoRestAdapterController;
import br.fiap.projeto.ponto.adapter.gateway.PontoRepositoryAdapterGateway;
import br.fiap.projeto.ponto.external.repository.postgres.SpringPontoRepository;
import br.fiap.projeto.ponto.usecase.GestaoPontoUseCase;
import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import br.fiap.projeto.ponto.usecase.port.IGestaoPontoUsecase;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeansConfiguration {
    @Bean
    public IGestaoPontoUsecase gestaoPontoUsecase(IPontoRepositoryAdapterGateway pontoRepository) {
        return new GestaoPontoUseCase(pontoRepository);
    }

    @Bean
    public IPontoRestAdapterController clienteRestAdapterController(IGestaoPontoUsecase gestaoPontoUsecase) {
        return new PontoRestAdapterController(gestaoPontoUsecase);
    }

    @Bean
    public IPontoRepositoryAdapterGateway pontoRepositoryAdapterGateway(SpringPontoRepository springPontoRepository) {
        return new PontoRepositoryAdapterGateway(springPontoRepository);
    }
}
