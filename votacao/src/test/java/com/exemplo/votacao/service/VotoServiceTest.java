package com.exemplo.votacao.service;

import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.entity.Voto;
import com.exemplo.votacao.repository.SessaoRepository;
import com.exemplo.votacao.repository.VotoRepository;
import com.exemplo.votacao.util.SessaoEncerradaException;
import com.exemplo.votacao.util.VotoJaRegistradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class VotoServiceTest {

    @Mock
    private VotoRepository votoRepository; // Simulando o repositório de Voto

    @Mock
    private SessaoRepository sessaoRepository; // Simulando o repositório de Sessao

    @Mock
    private RabbitTemplate rabbitTemplate; // Simulando o RabbitTemplate

    @InjectMocks
    private VotoService votoService; // A classe que será testada

    @Test
    public void testRegistrarVoto() {
        // Arrange: Criando dados de entrada e mockando os repositórios
        Long sessaoId = 1L;
        Long associadoId = 100L;
        Boolean voto = true;

        Sessao mockSessao = new Sessao();
        mockSessao.setFim(LocalDateTime.now().plusMinutes(30)); // Sessão ainda aberta

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(mockSessao)); // Simula o findById()
        when(votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId)).thenReturn(false); // Simula que o voto não foi registrado

        Voto mockVoto = new Voto();
        mockVoto.setSessao(mockSessao);
        mockVoto.setAssociadoId(associadoId);
        mockVoto.setVoto(voto);

        when(votoRepository.save(any(Voto.class))).thenReturn(mockVoto); // Simula o save

        // Act: Chama o método que você está testando
        Voto votoRegistrado = votoService.registrarVoto(sessaoId, associadoId, voto);

        // Assert: Verifica se o voto foi registrado corretamente
        assertNotNull(votoRegistrado); // Verifica que o voto não é nulo
        assertEquals(associadoId, votoRegistrado.getAssociadoId()); // Verifica se o ID do associado está correto
        assertTrue(votoRegistrado.getVoto()); // Verifica se o voto está correto
        verify(votoRepository, times(1)).save(any(Voto.class)); // Verifica se o save foi chamado uma vez
    }

    @Test
    public void testRegistrarVotoJaRegistrado() {
        // Arrange: Simulando que o voto já foi registrado
        Long sessaoId = 1L;
        Long associadoId = 100L;

        when(votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId)).thenReturn(true); // Simula que o voto já foi registrado

    }

    @Test
    public void testRegistrarSessaoEncerrada() {
        // Arrange: Criando uma sessão encerrada
        Long sessaoId = 1L;
        Long associadoId = 100L;

        Sessao mockSessao = new Sessao();
        mockSessao.setFim(LocalDateTime.now().minusMinutes(1)); // Sessão já encerrada

        when(sessaoRepository.findById(sessaoId)).thenReturn(Optional.of(mockSessao)); // Simula o findById()

        // Act & Assert: Verifica se a exceção SessaoEncerradaException é lançada
        SessaoEncerradaException thrown = assertThrows(SessaoEncerradaException.class, () -> {
            votoService.registrarVoto(sessaoId, associadoId, true);
        });

        assertEquals("Sessão já encerrada", thrown.getMessage()); // Verifica a mensagem da exceção
    }

    @Test
    public void testContabilizarVotos() {
        // Arrange: Criando dados de votos contabilizados
        Long sessaoId = 1L;
        Object[] resultadoSim = {true, 10L};
        Object[] resultadoNao = {false, 5L};
        List<Object[]> resultadoMock = List.of(resultadoSim, resultadoNao);

        when(votoRepository.countVotosPorSessao(sessaoId)).thenReturn(resultadoMock); // Simula a contagem de votos

        // Act: Chama o método que você está testando
        Map<String, Long> resultado = votoService.contabilizarVotos(sessaoId);

        // Assert: Verifica se os votos foram contabilizados corretamente
        assertEquals(2, resultado.size()); // Verifica se o mapa tem dois elementos
        assertEquals(10L, resultado.get("Sim")); // Verifica a quantidade de votos "Sim"
        assertEquals(5L, resultado.get("Não")); // Verifica a quantidade de votos "Não"
    }
}
