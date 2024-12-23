package com.exemplo.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.votacao.entity.Pauta;
import com.exemplo.votacao.service.PautaService;

@RestController
@RequestMapping("/api/pautas")
public class PautaController {
    @Autowired
    private PautaService pautaService;

    @PostMapping
    public Pauta criarPauta(@RequestBody String descricao) {
        return pautaService.criarPauta(descricao);
    }
}

