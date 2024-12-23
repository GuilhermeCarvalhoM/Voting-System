package com.exemplo.votacao.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

import com.exemplo.votacao.entity.Sessao;
import com.exemplo.votacao.entity.Voto;
import com.exemplo.votacao.repository.SessaoRepository;
import com.exemplo.votacao.repository.VotoRepository;
import com.exemplo.votacao.util.SessaoEncerradaException;
import com.exemplo.votacao.util.VotoJaRegistradoException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.exemplo.votacao.config.RabbitMQConfig;

@Service
public class VotoService {
    @Autowired
    private VotoRepository votoRepository;
    
    @Autowired
    private SessaoRepository sessaoRepository;
    
    @Autowired
    private RabbitTemplate rabbitTemplate;

    public Voto registrarVoto(Long sessaoId, Long associadoId, Boolean voto) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        if (votoRepository.existsBySessaoIdAndAssociadoId(sessaoId, associadoId)) {
            throw new VotoJaRegistradoException("Associado já votou nesta sessão");
        }

        // Verifica se a sessão está encerrada antes de registrar o voto
        if (LocalDateTime.now().isAfter(sessao.getFim())) {
        	encerrarSessao(sessaoId);
            throw new SessaoEncerradaException("Sessão já encerrada");
        }

        Voto novoVoto = new Voto();
        novoVoto.setSessao(sessao);
        novoVoto.setAssociadoId(associadoId);
        novoVoto.setVoto(voto);
        Voto votoSalvo = votoRepository.save(novoVoto);

        return votoSalvo;
    }
    
    public Map<String, Long> contabilizarVotos(Long sessaoId) {
    	 Sessao sessao = sessaoRepository.findById(sessaoId)
    	            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));
    	         
        List<Object[]> resultado = votoRepository.countVotosPorSessao(sessaoId);

        Map<String, Long> votosContabilizados = new HashMap<>();
        for (Object[] obj : resultado) {
            Boolean voto = (Boolean) obj[0];
            Long count = (Long) obj[1];
            votosContabilizados.put(voto ? "Sim" : "Não", count);
        }

        return votosContabilizados;
    }

    // Método para encerrar a sessão e enviar o resultado
    private void encerrarSessao(Long sessaoId) {
        Sessao sessao = sessaoRepository.findById(sessaoId)
            .orElseThrow(() -> new RuntimeException("Sessão não encontrada"));

        // Atualiza o status da sessão para "Encerrada"
        sessao.setStatus("Encerrada");
        sessaoRepository.save(sessao);

        // Envia o resultado da votação para o RabbitMQ
        enviarResultadoVotacao(sessaoId);
    }

    private void enviarResultadoVotacao(Long sessaoId) {
        Map<String, Long> resultado = contabilizarVotos(sessaoId);
        try {
            // Converte o resultado para JSON (sem serializar o objeto Java)
            String jsonPayload = new ObjectMapper().writeValueAsString(resultado);
            
            rabbitTemplate.convertAndSend(RabbitMQConfig.VOTACAO_RESULTADO_QUEUE, jsonPayload, message -> {
                message.getMessageProperties().setContentType("application/json");
                return message;
            });
            
            System.out.println("Mensagem enviada para RabbitMQ: " + jsonPayload);
        } catch (Exception e) {
            System.err.println("Erro ao enviar mensagem para RabbitMQ: " + e.getMessage());
        }
    }

}
