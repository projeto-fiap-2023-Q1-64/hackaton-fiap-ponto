package br.fiap.projeto.ponto.adapter.controller;

import br.fiap.projeto.ponto.adapter.controller.port.IPontoRestAdapterController;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoReportDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoResponseDTO;
import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.entity.PontoReport;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import br.fiap.projeto.ponto.usecase.port.IGestaoPontoUsecase;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public PontoResponseDTO buscaPorData(Date data) throws EntidadeNaoEncontradaException {
        Ponto ponto = gestaoClienteUsecase.buscaPorData(data);
        return PontoResponseDTO.fromPonto(ponto);
    }
}
