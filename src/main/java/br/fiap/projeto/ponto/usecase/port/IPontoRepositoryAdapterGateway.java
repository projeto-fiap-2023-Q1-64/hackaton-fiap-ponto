package br.fiap.projeto.ponto.usecase.port;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface IPontoRepositoryAdapterGateway {

    Optional<Ponto> busca(UUID pontoId) throws EntradaInvalidaException;

    Ponto insere(Ponto ponto);

    Optional<Ponto> buscaPorData(Date data);

    Optional<Ponto> buscaUltimoRegistroCorrete(UUID usuarioId);

    List<Ponto>findByUsuarioIdAndMesEAno(UUID usuarioId, int mes, int ano);
    List<Ponto>findByUsuarioIdAndData(UUID usuarioId, LocalDate data);
}
