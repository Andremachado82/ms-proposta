package com.andre.propostaapp.service;

import com.andre.propostaapp.dto.PropostaResponseDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class WebSocketService {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    public void  notificar(PropostaResponseDTO propostaResponseDTO) {
        simpMessagingTemplate.convertAndSend("/propostas", propostaResponseDTO);
    }
}
