package br.fiap.projeto.ponto.external.integration;

import br.fiap.projeto.ponto.external.integration.port.Colaborador;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value="pontoColaboradorIntegration", url = "http://${colaboradores.host:integration.port}/")
public interface PontoColaboradorIntegration {
    @RequestMapping(method = RequestMethod.GET, value = "/{codigo}")
    public Colaborador busca(@PathVariable("codigo") String codigo);
}
