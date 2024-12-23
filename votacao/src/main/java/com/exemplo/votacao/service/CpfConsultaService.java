package com.exemplo.votacao.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.exemplo.votacao.dto.CpfResponse;

@Service
public class CpfConsultaService {

    private static final String API_URL = "https://api.cpfcnpj.com.br/5ae973d7a997af13f0aaf2bf60e65803/9/";

    private final RestTemplate restTemplate;

    public CpfConsultaService() {
        this.restTemplate = new RestTemplate();
    }

    public String consultarCpf(String cpf) {
        String url = API_URL + cpf;

        try {
            CpfResponse response = restTemplate.getForObject(url, CpfResponse.class);

            // Verificando o status diretamente no objeto
            if (response != null && response.getStatus() == 1) {
                return "ABLE_TO_VOTE";
            } else {
                return "UNABLE_TO_VOTE";
            }
        } catch (Exception e) {
            return "UNABLE_TO_VOTE";
        }
    }
}
