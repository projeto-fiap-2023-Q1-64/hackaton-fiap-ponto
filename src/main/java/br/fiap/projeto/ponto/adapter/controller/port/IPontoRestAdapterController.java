package br.fiap.projeto.ponto.adapter.controller.port;

import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoDiarioDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoReportDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoResponseDTO;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;

import java.time.LocalDate;
import java.util.UUID;

public interface IPontoRestAdapterController {

    PontoReportDTO getPontosPorMesAno(UUID usuarioId, int mes, int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException;

    PontoResponseDTO insere(UUID usuarioID) throws EntradaInvalidaException, EntidadeNaoEncontradaException;

    PontoDiarioDTO buscaPorData(UUID usuarioID, LocalDate data) throws EntidadeNaoEncontradaException, EntradaInvalidaException;
}
