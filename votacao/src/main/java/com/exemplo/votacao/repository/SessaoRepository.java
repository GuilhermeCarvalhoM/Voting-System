package com.exemplo.votacao.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.exemplo.votacao.entity.Sessao;

@Repository
public interface SessaoRepository extends JpaRepository<Sessao, Long> {}