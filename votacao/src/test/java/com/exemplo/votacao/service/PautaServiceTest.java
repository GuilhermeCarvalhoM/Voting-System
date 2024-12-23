package com.exemplo.votacao.service;

import com.exemplo.votacao.entity.Pauta;
import com.exemplo.votacao.repository.PautaRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class PautaServiceTest {

    @Mock
    private PautaRepository pautaRepository; // Simulando o repositório

    @InjectMocks
    private PautaService pautaService; // A classe que será testada

    @Test
    public void testCriarPauta() {
        // Arrange: Criando dados de entrada e mockando o comportamento do repositório
        String descricao = "Pauta sobre Tecnologia";
        Pauta mockPauta = new Pauta();
        mockPauta.setDescricao(descricao);

        when(pautaRepository.save(any(Pauta.class))).thenReturn(mockPauta); // Simulando o save()

        // Act: Chama o método que você está testando
        Pauta pautaCriada = pautaService.criarPauta(descricao);

        // Assert: Verifica se o método foi executado conforme esperado
        assertNotNull(pautaCriada); // Verifica que o objeto não é nulo
        assertEquals(descricao, pautaCriada.getDescricao()); // Verifica se a descrição está correta
        verify(pautaRepository, times(1)).save(any(Pauta.class)); // Verifica se o save foi chamado uma vez
    }
}
