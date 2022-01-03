package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ClienteRepository extends JpaRepository<Cliente, Integer> {

    Optional<Cliente> findByEmail(String email);

}
