package br.fiap.projeto.ponto.external.config;

import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

@Configuration @RequiredArgsConstructor
public class PostgresPontoDataLoader {

    private final IPontoRepositoryAdapterGateway clienteRepository;

    @PostConstruct
    @SneakyThrows
    public void init() {
//        List<Ponto> pontos = Collections.singletonList(
//                new Ponto(UUID.randomUUID().toString(), "Cliente1", "01234567890", "cliente1@test.com", "11999998888")
//        );
//        pontos.forEach(clienteRepository::insere);
    }
}
