package com.exemplo.votacao.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.votacao.entity.Pauta;
import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.repository.PautaRepository;
import com.exemplo.votacao.repository.SessaoRepository;

@Service
public class SessaoService {
    @Autowired
    private SessaoRepository sessaoRepository;
    @Autowired
    private PautaRepository pautaRepository;

    public Sessao abrirSessao(Long pautaId, Long duracaoMinutos) {
        Pauta pauta = pautaRepository.findById(pautaId)
            .orElseThrow(() -> new RuntimeException("Pauta não encontrada"));
        
        Sessao sessao = new Sessao();
        sessao.setPauta(pauta);
        sessao.setInicio(LocalDateTime.now());
        sessao.setFim(LocalDateTime.now().plusMinutes(duracaoMinutos != null ? duracaoMinutos : 1));
        return sessaoRepository.save(sessao);
    }
}
