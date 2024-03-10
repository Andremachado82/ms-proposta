package com.andre.propostaapp.service;

import com.andre.propostaapp.dto.PropostaRequestDTO;
import com.andre.propostaapp.dto.PropostaResponseDTO;
import com.andre.propostaapp.entity.Proposta;
import com.andre.propostaapp.mapper.PropostaMapper;
import com.andre.propostaapp.repository.PropostaRepository;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class PropostaService {

    private String exchange;
    @Autowired
    private PropostaRepository propostaRepository;
    @Autowired
    private NotificacaoRabbitService notificacaoRabbitService;

    public PropostaService(@Value("${rabbitmq.propostapendente.exchange}") String exchange,
                           PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoRabbitService) {
        this.exchange = exchange;
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO propostaRequestDTO) {
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoParaProposta(propostaRequestDTO);

        propostaRepository.save(proposta);

        MessagePostProcessor mensagemPrioridade = setarPrioridade(proposta);

        notificarRabbitMQ(proposta, mensagemPrioridade);

        return PropostaMapper.INSTANCE.converteEntityParaDto(proposta);
    }

    public MessagePostProcessor setarPrioridade(Proposta proposta) {
        Integer prioridade = proposta.getUsuario().getRenda().intValue() > 5000 ? 10 : 5;
        return message -> {
            message.getMessageProperties().setPriority(prioridade);
            return message;
        };
    }

    private void notificarRabbitMQ(Proposta proposta, MessagePostProcessor mensagemPrioridade) {
        try {
            notificacaoRabbitService.notificar(proposta, exchange, mensagemPrioridade);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDTO> consultarPropostas() {
        return PropostaMapper.INSTANCE.converterListaEntityParaDto(propostaRepository.findAll());
    }
}
