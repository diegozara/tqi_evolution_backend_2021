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
public class EmprestimoParcelasExcedida extends RuntimeException {


    /**
     * Caso a quantidade máxima de parcelas solicitadas seja maior que 60
     *
     * Pode ser gerada pelo método solicitar das classes EmprestimoController e EmprestimoService
     *
     * @param quantidadeParcelas        Payload do método solicitar da classe EmprestimoController
     */
    public EmprestimoParcelasExcedida (Integer quantidadeParcelas){
        super("Quantidade máxima de parcelas permitidas são 60, solicitada foram: "+ quantidadeParcelas);
    }

}
