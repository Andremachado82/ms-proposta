package com.andre.propostaapp.service;

import com.andre.propostaapp.dto.PropostaRequestDTO;
import com.andre.propostaapp.dto.PropostaResponseDTO;
import com.andre.propostaapp.entity.Proposta;
import com.andre.propostaapp.mapper.PropostaMapper;
import com.andre.propostaapp.repository.PropostaRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PropostaService {

    private String exchange;
    private PropostaRepository propostaRepository;
    private NotificacaoRabbitService notificacaoRabbitService;

    public PropostaService(@Value("${rabbitmq.proposta-pendente.ex}") String exchange,
                           PropostaRepository propostaRepository,
                           NotificacaoRabbitService notificacaoRabbitService) {
        this.exchange = exchange;
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
    }

    public PropostaResponseDTO criar(PropostaRequestDTO propostaRequestDTO) {
        Proposta proposta = PropostaMapper.INSTANCE.converteDtoParaProposta(propostaRequestDTO);
        propostaRepository.save(proposta);

        notificarRabbitMQ(proposta);

        return PropostaMapper.INSTANCE.converteEntityParaDto(proposta);
    }

    private void notificarRabbitMQ(Proposta proposta) {
        try {
            notificacaoRabbitService.notificar(proposta, exchange);
        } catch (RuntimeException ex) {
            proposta.setIntegrada(false);
            propostaRepository.save(proposta);
        }
    }

    public List<PropostaResponseDTO> consultarPropostas() {
        return PropostaMapper.INSTANCE.converterListaEntityParaDto(propostaRepository.findAll());
    }
}
