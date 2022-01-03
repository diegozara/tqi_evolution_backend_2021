package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmprestimoParcelasExcedida extends RuntimeException {

    public EmprestimoParcelasExcedida (Integer quantidadeParcelas){
        super("Quantidade máxima de parcelas permitidas são 60, solicitada foram: "+ quantidadeParcelas);
    }

}
