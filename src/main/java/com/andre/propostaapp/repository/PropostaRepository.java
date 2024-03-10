package com.andre.propostaapp.repository;

import com.andre.propostaapp.entity.Proposta;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface PropostaRepository extends CrudRepository<Proposta, Long> {

    List<Proposta> findAllByIntegradaIsFalse();

    @Transactional
    @Modifying
    @Query(value = "UPDATE tb_proposta SET aprovada = :aprovada, observacao = :observacao WHERE id = :id", nativeQuery = true)
    void atualizarProposta(Long id, Boolean aprovada, String observacao);

    @Transactional
    @Modifying
    @Query(value = "UPDATE Proposta SET integrada = :integrada WHERE id = :idUsuario")
    void atualizarStatusIntegrada(Long idUsuario, boolean integrada);
}
