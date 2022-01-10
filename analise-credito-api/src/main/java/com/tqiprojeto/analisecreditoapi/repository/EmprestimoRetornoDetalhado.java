package com.tqiprojeto.analisecreditoapi.repository;

import java.util.Date;


/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.service.EmprestimoService
 * @see com.tqiprojeto.analisecreditoapi.controller.EmprestimoController
 * @since Release 1.0
 */
public interface EmprestimoRetornoDetalhado {


    /**
     * Interface de projeção do JPA
     *
     * Os valores ID, valor, quantidade_parcelas e data_primeira_parcela são da classe Emprestimo
     * Os valoes email e renda são da classe cliente
     *
     * Utilizado no método listarEmprestimoDetalhado da classe EmprestimoController e EmpresitmoService
     *
     * @return      Os campos solicitados para listagem do emprestimo detalhado
     */
    Integer getId();
    Double getValor();
    Integer getquantidade_parcelas();
    Date getdata_primeira_parcela();

    String getemail();
    Double getrenda();
}