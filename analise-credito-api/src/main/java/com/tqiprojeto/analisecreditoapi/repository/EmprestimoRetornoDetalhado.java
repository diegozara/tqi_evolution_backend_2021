package com.tqiprojeto.analisecreditoapi.repository;

import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;

public interface EmprestimoRetornoDetalhado {

    Integer getId();
    Double getValor();
    Integer getquantidade_parcelas();
    Date getdata_primeira_parcela();

    String getemail();
    Double getrenda();
}