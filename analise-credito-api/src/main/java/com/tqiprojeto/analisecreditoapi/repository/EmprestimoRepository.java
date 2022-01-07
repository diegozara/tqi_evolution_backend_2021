package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {

    //query para retorno da listagem simples dos clientes
    @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas " +
                   "FROM Emprestimo where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoSimples> listaSimples(Integer id);

    //query para retorno da listagem detalhada dos clientes
    @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas, emprestimo.data_primeira_parcela," +
                   " cliente.email, cliente.renda FROM Emprestimo INNER JOIN Cliente " +
                   "on cliente_id = cliente.id where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (Integer id);

}


