package com.exemplo.votacao.controller;


import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.votacao.dto.VotoRequest;
import com.exemplo.votacao.entity.Voto;
import com.exemplo.votacao.service.VotoService;

@RestController
@RequestMapping("/api/votos")
public class VotoController {
    @Autowired
    private VotoService votoService;

    @PostMapping
    public Voto registrarVoto(@RequestBody VotoRequest votoRequest) {
        return votoService.registrarVoto(
            votoRequest.getSessaoId(),
            votoRequest.getAssociadoId(),
            votoRequest.getVoto()
        );
    }
    
    // Endpoint para contabilizar votos
    @GetMapping("/resultado/{sessaoId}")
    public ResponseEntity<Map<String, Long>> contabilizarVotos(@PathVariable Long sessaoId) {
        Map<String, Long> resultado = votoService.contabilizarVotos(sessaoId);
        return ResponseEntity.ok(resultado);
    }
}
