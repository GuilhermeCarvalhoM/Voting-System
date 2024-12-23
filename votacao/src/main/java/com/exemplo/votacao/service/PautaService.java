package com.exemplo.votacao.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exemplo.votacao.entity.Pauta;
import com.exemplo.votacao.repository.PautaRepository;

@Service
public class PautaService {
    @Autowired
    private PautaRepository pautaRepository;

    public Pauta criarPauta(String descricao) {
        Pauta pauta = new Pauta();
        pauta.setDescricao(descricao);
        return pautaRepository.save(pauta);
    }
}
