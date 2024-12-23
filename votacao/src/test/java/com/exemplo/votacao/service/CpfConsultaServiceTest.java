package com.exemplo.votacao.service;
import com.exemplo.votacao.dto.CpfResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.client.RestTemplate;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CpfConsultaServiceTest {

    @InjectMocks
    private CpfConsultaService cpfConsultaService;  // Serviço que estamos testando

    @Mock
    private RestTemplate restTemplate;  // Mock do RestTemplate

    @BeforeEach
    void setUp() {
        // Inicializa os mocks
        // @MockitoExtension já garante que os mocks sejam injetados
    }

    @Test
    void testConsultarCpfAbleToVote() {
        // Simulando a resposta da API para um CPF válido
        String cpf = "12345678900";  // CPF de exemplo
        CpfResponse mockResponse = new CpfResponse();
        mockResponse.setStatus(1);  // Status indicando que o CPF pode votar

        // Configurando o comportamento do mock do RestTemplate
        when(restTemplate.getForObject(anyString(), eq(CpfResponse.class))).thenReturn(mockResponse);

        // Chamada do método
        String resultado = cpfConsultaService.consultarCpf(cpf);

    }

    @Test
    void testConsultarCpfUnableToVote() {
        // Simulando a resposta da API para um CPF inválido
        String cpf = "12345678901";  // CPF de exemplo
        CpfResponse mockResponse = new CpfResponse();
        mockResponse.setStatus(0);  // Status indicando que o CPF não pode votar

        // Configurando o comportamento do mock do RestTemplate
        when(restTemplate.getForObject(anyString(), eq(CpfResponse.class))).thenReturn(mockResponse);

        // Chamada do método
        String resultado = cpfConsultaService.consultarCpf(cpf);

        // Verificando o resultado
        assertEquals("UNABLE_TO_VOTE", resultado);

     
    }

    @Test
    void testConsultarCpfException() {
        // Simulando uma exceção durante a consulta (por exemplo, erro na API externa)
        String cpf = "12345678900";  // CPF de exemplo

        // Configurando o comportamento do mock do RestTemplate para lançar uma exceção
        when(restTemplate.getForObject(anyString(), eq(CpfResponse.class))).thenThrow(new RuntimeException("Erro de conexão"));

        // Chamada do método
        String resultado = cpfConsultaService.consultarCpf(cpf);

        // Verificando que, em caso de erro, o resultado será "UNABLE_TO_VOTE"
        assertEquals("UNABLE_TO_VOTE", resultado);

        
    }
}
