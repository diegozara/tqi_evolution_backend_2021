package com.tqiprojeto.analisecreditoapi.repository;

import java.util.Date;

public interface EmprestimoRetornoDetalhado {

    Integer getId();
    Double getValor();
    Integer getquantidade_parcelas();
    Date getdata_primeira_parcela();

    String getemail();
    Double getrenda();
}