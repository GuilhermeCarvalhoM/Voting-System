package com.exemplo.votacao.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String VOTACAO_RESULTADO_QUEUE = "votacao_resultado_queue";

    @Bean
    public Queue votacaoQueue() {
        return new Queue(VOTACAO_RESULTADO_QUEUE, true);
    }
}
