package br.fiap.projeto.ponto.adapter.gateway;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.external.repository.entity.PontoEntity;
import br.fiap.projeto.ponto.external.repository.postgres.SpringPontoRepository;
import br.fiap.projeto.ponto.usecase.port.IPontoRepositoryAdapterGateway;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

public class PontoRepositoryAdapterGateway implements IPontoRepositoryAdapterGateway {

    private static final Logger log = LoggerFactory.getLogger(PontoRepositoryAdapterGateway.class);

    private final SpringPontoRepository springPontoRepository;

    public PontoRepositoryAdapterGateway(SpringPontoRepository springPontoRepository) {
        this.springPontoRepository = springPontoRepository;
    }

    @Override
    public Ponto insere(Ponto ponto) {
        PontoEntity pontoEntity = PontoEntity.fromPonto(ponto);
        pontoEntity = springPontoRepository.save(pontoEntity);
        return pontoEntity.toPonto();
    }

    @Override
    public Optional<Ponto> buscaPorData(Date data) {
        return Optional.empty();
    }

    @Override
    public Optional<Ponto> busca(UUID pontoId) {
        Optional<PontoEntity> pontoRecuperado = springPontoRepository.findByPontoId(pontoId);
        return pontoRecuperado.map(PontoEntity::toPonto);
    }
    @Override
    public Optional<Ponto> buscaUltimoRegistroCorrete(UUID usuarioId) {
        Optional<PontoEntity> pontoRecuperado = springPontoRepository.findLastByUsuarioIdAndDataAtual(usuarioId);
        return pontoRecuperado.map(PontoEntity::toPonto);
    }

    @Override
    public List<Ponto> findByUsuarioIdAndMesEAno(UUID usuarioId, int mes, int ano) {
        List<PontoEntity> pontosRecuperados = springPontoRepository.findByUsuarioIdAndMesEAno(usuarioId, mes, ano);
        List<Ponto> pontos = pontosRecuperados.stream()
                .map(PontoEntity::toPonto) // Converte cada PontoEntity em Ponto
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
        return pontos;
    }

    @Override
    public List<Ponto> findByUsuarioIdAndData(UUID usuarioId, LocalDate data) {
        List<PontoEntity> pontosRecuperados = springPontoRepository.findByUsuarioIdAndData(usuarioId, data);
        List<Ponto> pontos = pontosRecuperados.stream()
                .map(PontoEntity::toPonto) // Converte cada PontoEntity em Ponto
                .collect(Collectors.toList()); // Coleta os resultados em uma lista
        return pontos;
    }
}
