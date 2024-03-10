package com.andre.propostaapp.agendador;

import com.andre.propostaapp.repository.PropostaRepository;
import com.andre.propostaapp.service.NotificacaoRabbitService;
import com.andre.propostaapp.service.PropostaService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.MessagePostProcessor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.concurrent.TimeUnit;

@Component
public class PropostaSemIntegracao {

    private final PropostaRepository propostaRepository;
    private final NotificacaoRabbitService notificacaoRabbitService;
    private final PropostaService propostaService;
    private final String exchange;

    private final Logger logger = LoggerFactory.getLogger(PropostaSemIntegracao.class);

    public PropostaSemIntegracao(PropostaRepository propostaRepository,
                                 NotificacaoRabbitService notificacaoRabbitService,
                                 @Value("${rabbitmq.propostapendente.exchange}") String exchange,
                                 PropostaService propostaService) {
        this.propostaRepository = propostaRepository;
        this.notificacaoRabbitService = notificacaoRabbitService;
        this.exchange = exchange;
        this.propostaService = propostaService;
    }

    @Scheduled(fixedDelay =  10, timeUnit = TimeUnit.SECONDS)
    public void listaPropostaSemIntegracao() {
        propostaRepository.findAllByIntegradaIsFalse().forEach(proposta -> {
            try {
                MessagePostProcessor mensagemPrioridade = propostaService.setarPrioridade(proposta);
                notificacaoRabbitService.notificar(proposta, exchange, mensagemPrioridade);
                atualizarPropostaESalvar(proposta.getId());
            } catch (RuntimeException ex) {
                logger.error(ex.getMessage());
            }
        });
    }

    private void atualizarPropostaESalvar(Long idUsuario) {
        propostaRepository.atualizarStatusIntegrada(idUsuario, true);
    }
}
