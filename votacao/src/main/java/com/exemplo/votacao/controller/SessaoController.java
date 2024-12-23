package com.exemplo.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.service.SessaoService;

@RestController
@RequestMapping("/api/sessoes")
public class SessaoController {
    @Autowired
    private SessaoService sessaoService;

    @PostMapping("/{pautaId}")
    public Sessao abrirSessao(@PathVariable Long pautaId, @RequestParam(required = false) Long duracaoMinutos) {
        return sessaoService.abrirSessao(pautaId, duracaoMinutos);
    }
}
