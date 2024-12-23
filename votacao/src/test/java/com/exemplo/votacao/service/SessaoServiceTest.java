package com.exemplo.votacao.service;

import com.exemplo.votacao.entity.Pauta;
import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.repository.PautaRepository;
import com.exemplo.votacao.repository.SessaoRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class SessaoServiceTest {

    @Mock
    private SessaoRepository sessaoRepository; // Simulando o repositório de Sessao

    @Mock
    private PautaRepository pautaRepository; // Simulando o repositório de Pauta

    @InjectMocks
    private SessaoService sessaoService; // A classe que será testada

    @Test
    public void testAbrirSessao() {
        // Arrange: Criando dados de entrada e mockando o comportamento do repositório
        Long pautaId = 1L;
        String descricao = "Pauta sobre Tecnologia";
        Pauta mockPauta = new Pauta();
        mockPauta.setDescricao(descricao);

        Sessao mockSessao = new Sessao();
        mockSessao.setPauta(mockPauta);
        mockSessao.setInicio(LocalDateTime.now());
        mockSessao.setFim(LocalDateTime.now().plusMinutes(30));

        when(pautaRepository.findById(pautaId)).thenReturn(Optional.of(mockPauta)); // Simulando o findById()
        when(sessaoRepository.save(any(Sessao.class))).thenReturn(mockSessao); // Simulando o save()

        // Act: Chama o método que você está testando
        Sessao sessaoCriada = sessaoService.abrirSessao(pautaId, 30L);

        // Assert: Verifica se o método foi executado conforme esperado
        assertNotNull(sessaoCriada); // Verifica que a sessão não é nula
        assertEquals(mockPauta, sessaoCriada.getPauta()); // Verifica se a pauta está correta
        assertNotNull(sessaoCriada.getInicio()); // Verifica se o início está configurado
        assertNotNull(sessaoCriada.getFim()); // Verifica se o fim está configurado
        assertEquals(30L, java.time.Duration.between(sessaoCriada.getInicio(), sessaoCriada.getFim()).toMinutes()); // Verifica se a duração está correta
        verify(pautaRepository, times(1)).findById(pautaId); // Verifica se o findById foi chamado uma vez
        verify(sessaoRepository, times(1)).save(any(Sessao.class)); // Verifica se o save foi chamado uma vez
    }

    @Test
    public void testAbrirSessaoPautaNaoEncontrada() {
        // Arrange: Simulando o retorno de uma pauta não encontrada
        Long pautaId = 999L; // ID de pauta que não existe
        when(pautaRepository.findById(pautaId)).thenReturn(Optional.empty()); // Simula que a pauta não foi encontrada

        // Act & Assert: Verifica se uma exceção é lançada quando a pauta não é encontrada
        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            sessaoService.abrirSessao(pautaId, 30L);
        });

        assertEquals("Pauta não encontrada", thrown.getMessage()); // Verifica a mensagem da exceção
    }
}
