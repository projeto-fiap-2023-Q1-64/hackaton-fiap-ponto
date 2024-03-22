package br.fiap.projeto.ponto.adapter.controller.rest.response;

import br.fiap.projeto.ponto.entity.PontoDiario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoDiarioDTO {
    PontoDiario pontoDiario;

    public PontoDiario getPontoDiario() {
        return pontoDiario;
    }
}
