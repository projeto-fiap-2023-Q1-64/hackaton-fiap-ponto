package br.fiap.projeto.ponto.external.api;

import br.fiap.projeto.ponto.adapter.controller.port.IPontoRestAdapterController;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoDiarioDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoReportDTO;
import br.fiap.projeto.ponto.adapter.controller.rest.response.PontoResponseDTO;
import br.fiap.projeto.ponto.usecase.exception.EntidadeNaoEncontradaException;
import br.fiap.projeto.ponto.usecase.exception.EntradaInvalidaException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/ponto")
@RequiredArgsConstructor
@Log4j2
@Api(tags = {"ponto"})
public class PontoApiController {

    private final IPontoRestAdapterController pontoAdapterController;

    @PostMapping("/{usuarioId}")
    @SneakyThrows
    @ApiOperation(value = "Gera novo ponto para o usuário", notes = "Este endpoint insere novo registro de ponto para usuario")
    public ResponseEntity<PontoResponseDTO> insere(@PathVariable String usuarioId) {
        return ResponseEntity.status(HttpStatus.CREATED).body(pontoAdapterController.insere(UUID.fromString(usuarioId)));
    }

    @GetMapping("/{usuarioId}/mensal")
    public ResponseEntity<PontoReportDTO> getPontosPorUsuarioEMesEAno(
            @PathVariable UUID usuarioId,
            @RequestParam(name = "mes") int mes,
            @RequestParam(name = "ano") int ano) throws EntidadeNaoEncontradaException, EntradaInvalidaException {
        // Chame o serviço para buscar pontos por usuário, mês e ano
        PontoReportDTO pontos = pontoAdapterController.getPontosPorMesAno(usuarioId, mes, ano);
        return ResponseEntity.ok(pontos);
    }

    @GetMapping("/{usuarioId}/diario")
    @SneakyThrows
    @ApiOperation(value = "Busca pontos por data", notes = "Este endpoint busca as informações de pontos por data do usuarioo")
    public PontoDiarioDTO buscaPorData(
            @PathVariable UUID usuarioId,
            @RequestParam(name = "data") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) Date data) {
        LocalDate localDate = data.toInstant().atZone(ZoneOffset.UTC).toLocalDate();
        return pontoAdapterController.buscaPorData(usuarioId, localDate);
    }
}
