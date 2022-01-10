package com.tqiprojeto.analisecreditoapi.repository;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.service.EmprestimoService
 * @see com.tqiprojeto.analisecreditoapi.controller.EmprestimoController
 * @since Release 1.0
 */
public interface EmprestimoRetornoSimples {


    /**
     * Interface de projeção do JPA
     *
     * Utilizado no método listarEmprestimos da classe EmprestimoController e EmpresitmoService
     *
     * @return Os campos solicitados para listagem simples do emprestimo
     */
    Integer getId();
    Double getValor();
    Integer getquantidade_parcelas();

   }
