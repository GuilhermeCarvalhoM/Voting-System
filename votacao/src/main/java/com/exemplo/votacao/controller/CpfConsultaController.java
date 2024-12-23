package com.exemplo.votacao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.exemplo.votacao.dto.VotoRequest;
import com.exemplo.votacao.service.CpfConsultaService;
import com.exemplo.votacao.service.VotoService;

@RestController
@RequestMapping("/api/cpf")
public class CpfConsultaController {

    @Autowired
    private CpfConsultaService cpfConsultaService;

    @Autowired
    private VotoService votoService;

    // Versão que suporta tanto o uso do CPF como associadoId quanto o associadoId convencional
    @GetMapping("/{cpf}")
    public String consultarCpf(@PathVariable String cpf, @RequestBody VotoRequest votoRequest) {
        String statusVoto = cpfConsultaService.consultarCpf(cpf);

        // Se o CPF for válido para votar, verifica a lógica de associadoId
        if ("ABLE_TO_VOTE".equals(statusVoto)) {
            // Caso o CPF seja passado diretamente, ele será usado como o associadoId
            if (votoRequest.getAssociadoId() == null) {
                // Usando o CPF como associadoId
                votoRequest.setAssociadoId(Long.valueOf(cpf.replaceAll("[^\\d]", ""))); // Remove pontuação e converte para Long
            }

            // Registra o voto
            votoService.registrarVoto(votoRequest.getSessaoId(), votoRequest.getAssociadoId(), votoRequest.getVoto());
            return "Voto registrado com sucesso!";
        } else {
            return "CPF não habilitado para votar.";
        }
    }
}
