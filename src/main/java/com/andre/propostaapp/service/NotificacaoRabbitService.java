package com.andre.propostaapp.service;

import com.andre.propostaapp.entity.Proposta;
import lombok.AllArgsConstructor;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class NotificacaoRabbitService {

    private RabbitTemplate rabbitTemplate;

    public void notificar(Proposta proposta, String exchange, MessagePostProcessor mensagemPrioridade) {
        rabbitTemplate.convertAndSend(exchange, "", proposta, mensagemPrioridade);
    }
}
