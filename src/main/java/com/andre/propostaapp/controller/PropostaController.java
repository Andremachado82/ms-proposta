package com.andre.propostaapp.controller;

import com.andre.propostaapp.dto.PropostaRequestDTO;
import com.andre.propostaapp.dto.PropostaResponseDTO;
import com.andre.propostaapp.service.PropostaService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/proposta")
public class PropostaController {

    private PropostaService propostaService;

    @PostMapping
    public ResponseEntity<PropostaResponseDTO> criar(@RequestBody @Valid PropostaRequestDTO propostaDTO) {
        PropostaResponseDTO propostaResponseDTO = propostaService.criar(propostaDTO);
        return ResponseEntity
                .created(ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(propostaResponseDTO.getId())
                .toUri())
                .body(propostaResponseDTO);
    }

    @GetMapping
    public ResponseEntity<List<PropostaResponseDTO>> consultarPropostas() {
        List<PropostaResponseDTO> propostaResponseDTOS = propostaService.consultarPropostas();
        return ResponseEntity.ok(propostaResponseDTOS);
    }
}
