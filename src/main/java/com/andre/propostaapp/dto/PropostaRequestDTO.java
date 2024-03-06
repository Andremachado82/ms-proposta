package com.andre.propostaapp.dto;

import com.andre.propostaapp.entity.Usuario;
import jakarta.persistence.*;
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

    private int prazoPagamento;
}
