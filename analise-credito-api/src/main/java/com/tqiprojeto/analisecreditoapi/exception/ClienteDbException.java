package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteDbException extends Exception {

    public ClienteDbException(Integer id) {
        super("Cliente: " +id+ " não pode ser excluido pois possui emprestimo em seu nome");
    }

}

