package br.fiap.projeto.ponto.adapter.controller;

import br.fiap.projeto.ponto.adapter.controller.port.IPontoRestAdapterController;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoDiarioDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoReportDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoResponseDTO;
import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.PontoDiario;
import br.fiap.projeto.ponto.entity.PontoReport;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import br.fiap.projeto.ponto.usecase.port.IGestaoPontoUsecase;

import java.time.LocalDate;
import java.util.UUID;

public class PontoRestAdapterController implements IPontoRestAdapterController {

    private final IGestaoPontoUsecase gestaoClienteUsecase;

    public PontoRestAdapterController(IGestaoPontoUsecase gestaoClienteUsecase) {
        this.gestaoClienteUsecase = gestaoClienteUsecase;
    }

    @Override
    public PontoResponseDTO insere(UUID usuarioId) throws EntradaInvalidaException, EntidadeNaoEncontradaException {
        Ponto pontoSalvo = gestaoClienteUsecase.insere(usuarioId);
        return PontoResponseDTO.fromPonto(pontoSalvo);
    }

    @Override
    public PontoReportDTO getPontosPorMesAno(UUID usuarioId, int mes, int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException {
        PontoReport pontoRecuperado = gestaoClienteUsecase.getPontosPorMesAno(usuarioId, mes, ano);
        //List<PontoResponseDTO> pontoResponseDTOs = pontoRecuperado.stream().map(PontoResponseDTO::fromPonto).collect(Collectors.toList());
        return new PontoReportDTO(pontoRecuperado);
    }

    @Override
    public PontoDiarioDTO buscaPorData(UUID usuarioId, LocalDate data) throws EntidadeNaoEncontradaException, EntradaInvalidaException {
        PontoDiario pontoRecuperado = gestaoClienteUsecase.buscaPorData(usuarioId, data);
        return new PontoDiarioDTO(pontoRecuperado);
    }
}
