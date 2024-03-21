package br.fiap.projeto.ponto.external.config;

import br.fiap.projeto.ponto.external.repository.entity.PontoEntity;
import br.fiap.projeto.ponto.external.repository.postgres.SpringPontoRepository;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackageClasses = SpringPontoRepository.class)
@EntityScan(basePackageClasses = PontoEntity.class)
public class PostgresPontoConfiguration {

}
