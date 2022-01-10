package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see com.tqiprojeto.analisecreditoapi.controller.ClienteController
 * @see com.tqiprojeto.analisecreditoapi.service.ClienteService
 * @since Release 1.0
 */
@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ClienteDbException extends Exception {

    /**
     * Tratamento da Exceção por violação da chave estrangeira
     * Cliente que possuir emprestimos vinculados não podem ser excluídos
     *
     * Pode ser gerada pelo método deletar das classes ClienteService e ClienteController
     *
     * @param id    ID do cliente que será excluido
     */
    public ClienteDbException(Integer id) {
        super("Cliente: " +id+ " não pode ser excluido pois possui emprestimo em seu nome");
    }

}

