package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see Cliente
 * @since Release 1.0
 */
public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    /**
     * Busca pelo email do cliente
     *
     * Utilizado para verificação do login, pelo método login das classes ClienteController e ClienteService
     *
     * @param email     Payload Fornecido pelo ClienteController
     * @return          vazio ou o email do cliente caso seja localizado
     */
    Optional<Cliente> findByEmail(String email);

}
