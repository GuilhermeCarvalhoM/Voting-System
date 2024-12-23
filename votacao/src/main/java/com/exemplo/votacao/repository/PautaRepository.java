package com.exemplo.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.votacao.entity.Pauta;

@Repository
public interface PautaRepository extends JpaRepository<Pauta, Long> {}