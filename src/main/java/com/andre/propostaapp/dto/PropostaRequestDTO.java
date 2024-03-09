package com.andre.propostaapp.dto;

import lombok.*;

import java.math.BigDecimal;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PropostaRequestDTO {

    private String nome;

    private String sobrenome;

    private String telefone;

    private String cpf;

    private BigDecimal renda;

    private BigDecimal valorSolicitado;

    private Boolean aprovada;

    private Boolean integrada;

    private int prazoPagamento;
}
