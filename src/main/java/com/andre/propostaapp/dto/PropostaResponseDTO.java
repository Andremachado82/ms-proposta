package com.andre.propostaapp.dto;

import com.andre.propostaapp.entity.Usuario;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaResponseDTO {

    private Long id;

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private BigDecimal renda;

    private String valorSolicitadoFmt;

    private int prazoPagamento;

    private Boolean aprovada;

    private String observacao;
}
