package com.tqiprojeto.analisecreditoapi.repository;

//interface de projeção do JPA para retorno dos campos minimos da listagem do emprestimo
public interface EmprestimoRetornoSimples {

    Integer getId();
    Double getValor();
    Integer getquantidade_parcelas();

   }
