package br.fiap.projeto.ponto.adapter.controller.rest.response;

import br.fiap.projeto.ponto.entity.PontoReport;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class PontoReportDTO {
    PontoReport pontoReport;

    public PontoReport getPontoReport() {
        return pontoReport;
    }
}
