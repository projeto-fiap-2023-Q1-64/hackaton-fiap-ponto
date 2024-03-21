package br.fiap.projeto.ponto.usecase.port;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.PontoReport;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public interface IGestaoPontoUsecase {

    PontoReport getPontosPorMesAno(UUID usuarioId, int mes, int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException;

    Ponto insere(UUID usuarioId) throws EntradaInvalidaException, EntidadeNaoEncontradaException;

    Ponto buscaPorData(Date data) throws EntidadeNaoEncontradaException;
}
