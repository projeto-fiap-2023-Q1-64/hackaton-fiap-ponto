package br.fiap.projeto.ponto.external.repository.postgres;

import br.fiap.projeto.ponto.entity.Ponto;
import br.fiap.projeto.ponto.external.repository.entity.PontoEntity;
import org.hibernate.type.UUIDBinaryType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SpringPontoRepository extends JpaRepository<PontoEntity, String> {
    Optional<PontoEntity> findByPontoId(UUID pontoId);
    @Query(value = "SELECT * FROM ponto WHERE usuario_id = :usuarioId AND DATE(data_hora_evento) = CURRENT_DATE ORDER BY data_hora_evento DESC LIMIT 1", nativeQuery = true)
    Optional<PontoEntity> findLastByUsuarioIdAndDataAtual(UUID usuarioId);
    @Query("SELECT p FROM PontoEntity p WHERE p.usuarioId = :usuarioId AND FUNCTION('MONTH', p.dataHoraEvento) = :mes AND FUNCTION('YEAR', p.dataHoraEvento) = :ano ORDER BY p.dataHoraEvento ASC")
    List<PontoEntity> findByUsuarioIdAndMesEAno(UUID usuarioId, int mes, int ano);
    @Query("SELECT p FROM PontoEntity p WHERE p.usuarioId = :usuarioId AND FUNCTION('DATE', p.dataHoraEvento) = :dataEvento ORDER BY p.dataHoraEvento ASC")
    List<PontoEntity> findByUsuarioIdAndData(UUID usuarioId, LocalDate dataEvento);
}
