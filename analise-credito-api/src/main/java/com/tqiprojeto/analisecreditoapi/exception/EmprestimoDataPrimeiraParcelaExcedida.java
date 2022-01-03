package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class EmprestimoDataPrimeiraParcelaExcedida extends Exception {

    public EmprestimoDataPrimeiraParcelaExcedida(){
        super("Data permida da primeira parcela é no maximo apos 3 meses");
    }

}
