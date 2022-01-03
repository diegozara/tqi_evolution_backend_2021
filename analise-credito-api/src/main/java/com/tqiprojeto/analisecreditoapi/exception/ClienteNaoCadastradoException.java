package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClienteNaoCadastradoException extends Exception{

        public ClienteNaoCadastradoException(Integer id){
            super("Cliente nao cadastrado com id: "+ id);
        }
    }

