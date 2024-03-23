package br.fiap.projeto.ponto.usecase.port;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.PontoDiario;
import br.fiap.projeto.ponto.entity.PontoReport;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;

import java.time.LocalDate;
import java.util.UUID;

public interface IGestaoPontoUsecase {

    PontoReport getPontosPorMesAno(UUID usuarioId, int mes, int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException;

    Ponto insere(UUID usuarioId) throws EntradaInvalidaException, EntidadeNaoEncontradaException;

    PontoDiario buscaPorData(UUID usuarioId, LocalDate data) throws EntradaInvalidaException;
}
