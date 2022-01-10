package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.service.EmprestimoService
 * @see com.tqiprojeto.analisecreditoapi.controller.EmprestimoController
 * @see EmprestimoRetornoSimples
 * @see EmprestimoRetornoDetalhado
 * @since Release 1.0
 */
@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    /**
     * Query para retorno da listagem simples dos clientes
     *
     * valores são atribuidos na interface de projeção EmprestimoRetornoSimples
     *
     * @param id    Id do cliente
     * @return      lista simples de emprestimos para aquele cliente
     */
    @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas " +
                   "FROM Emprestimo where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoSimples> listaSimples(Integer id);


    /**
     * Query para retorno da listagem detalhada dos clientes
     *
     * valores são atribuidos na interface de projeção EmprestimoRetornoDetalhado
     *
     * @param id        Id do cliente
     * @return          lista de detalhada de emprestimos para aquele cliente
     */
    @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas, emprestimo.data_primeira_parcela," +
                   " cliente.email, cliente.renda FROM Emprestimo INNER JOIN Cliente " +
                   "on cliente_id = cliente.id where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (Integer id);

}


