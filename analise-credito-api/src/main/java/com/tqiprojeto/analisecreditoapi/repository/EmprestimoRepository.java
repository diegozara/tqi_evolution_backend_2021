package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EmprestimoRepository extends JpaRepository<Emprestimo, Integer> {


    @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas " +
                   "FROM Emprestimo where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoSimples> listaSimples(Integer id);

  /*  @Query(value = "SELECT emprestimo.id, emprestimo.valor, emprestimo.quantidade_parcelas, emprestimo.data_primeira_parcela," +
                   " cliente.email, cliente.renda FROM Emprestimo INNER JOIN Cliente " +
                   "where cliente_id = :id", nativeQuery = true)
    List<EmprestimoRetornoDetalhado> listarEmprestimoDetalhado (Integer id);*/

    @Query(value = "Select e from Emprestimo e join fetch e.cliente")
    List<Emprestimo> listarEmprestimoDetalhado (Integer id);

}



