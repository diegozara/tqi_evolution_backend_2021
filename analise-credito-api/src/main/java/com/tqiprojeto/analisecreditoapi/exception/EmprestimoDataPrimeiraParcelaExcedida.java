package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.service.EmprestimoService
 * @see com.tqiprojeto.analisecreditoapi.controller.EmprestimoController
 * @since Release 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmprestimoDataPrimeiraParcelaExcedida extends Exception {

    /**
     * Caso a data da primeira parcela seja superior a 3 meses do dia da solicitação de emprestimo
     *
     * Pode ser gerada pelo método solicitar das classes EmprestimoController e EmprestimoService
     *
     */
    public EmprestimoDataPrimeiraParcelaExcedida(){
        super("Data permida da primeira parcela é no maximo apos 3 meses");
    }

}
