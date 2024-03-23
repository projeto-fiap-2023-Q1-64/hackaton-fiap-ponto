package br.fiap.projeto.ponto.external.integration.port;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Colaborador {
    private String codigo;
    private String nome;
    private String matricula;
    private String email;
}
