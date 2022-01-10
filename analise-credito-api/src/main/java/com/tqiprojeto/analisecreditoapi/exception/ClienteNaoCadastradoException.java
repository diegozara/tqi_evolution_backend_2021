package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.service.ClienteService
 * @see com.tqiprojeto.analisecreditoapi.controller.ClienteController
 * @since Release 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClienteNaoCadastradoException extends Exception{

    /**
     * Tratamento da Exceção de cliente não cadastrado
     *
     * Pode ser gerada pelos métodos buscarPorId, atualizar e deletar das classes ClienteService e ClienteController
     *
     * @param id        ID do cliente
     */
     public ClienteNaoCadastradoException(Integer id){
            super("Cliente nao cadastrado com id: "+ id);
        }
    }

