package com.andre.propostaapp.listener;

import com.andre.propostaapp.dto.PropostaResponseDTO;
import com.andre.propostaapp.entity.Proposta;
import com.andre.propostaapp.mapper.PropostaMapper;
import com.andre.propostaapp.repository.PropostaRepository;
import com.andre.propostaapp.service.WebSocketService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class PropostaConcluidaListener {

    @Autowired
    private PropostaRepository propostaRepository;

    @Autowired
    private WebSocketService webSocketService;

    @RabbitListener(queues = "${rabbitmq.queue.proposta.concluida}")
    public void propostaConcluida(Proposta proposta) {
        if(null != proposta) {
            atualizarProposta(proposta);
            PropostaResponseDTO propostaResponseDTO = PropostaMapper.INSTANCE.converteEntityParaDto(proposta);
            webSocketService.notificar(propostaResponseDTO);
        }
    }

    private void atualizarProposta(Proposta proposta) {
        propostaRepository.atualizarProposta(proposta.getId(), proposta.getAprovada(), proposta.getObservacao());
    }
}
