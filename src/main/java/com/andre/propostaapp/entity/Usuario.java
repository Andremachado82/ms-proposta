package com.andre.propostaapp.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;

import java.math.BigDecimal;

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
@Entity
@Table(name = "tb_usuario")
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Campo não informado")
    private String nome;

    @NotBlank(message = "Campo não informado")
    private String sobrenome;

    @NotBlank(message = "Campo não informado")
    @CPF(message = "Campo inválido")
    private String cpf;

    @NotBlank(message = "Campo não informado")
    private String telefone;

    @NotNull(message = "Campo não informado")
    private BigDecimal renda;

    @OneToOne(mappedBy ="usuario")
    @JsonBackReference
    private Proposta proposta;
}
