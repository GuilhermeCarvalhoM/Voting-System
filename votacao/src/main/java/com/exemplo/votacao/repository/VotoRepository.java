package com.exemplo.votacao.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.entity.Voto;

@Repository
public interface VotoRepository extends JpaRepository<Voto, Long> {
    boolean existsBySessaoIdAndAssociadoId(Long sessaoId, Long associadoId);

	Optional<Sessao> findAllBySessaoId(Long sessaoId);
	
	 @Query("SELECT v.voto, COUNT(v) FROM Voto v WHERE v.sessao.id = :sessaoId GROUP BY v.voto")
	  List<Object[]> countVotosPorSessao(@Param("sessaoId") Long sessaoId);
}
