package com.tqiprojeto.analisecreditoapi.exception;

import com.tqiprojeto.analisecreditoapi.service.EmprestimoService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see EmprestimoService
 * @see com.tqiprojeto.analisecreditoapi.controller.EmprestimoController
 * @since Release 1.0
 */
@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmprestimoNaoCadastradoException extends  Exception{

    /**
     * Tratamento da exceção quando não houver emprestimo cadastrado
     *
     * Pode ser gerada pelos métodos listarEmprestimos, listarEmprestimoDetalhado, buscarPorId, solicitar,
     * atualizar e deletar das classes EmprestimoController e EmprestimoService
     *
     * @param id    ID do emprestimo
     */
     public EmprestimoNaoCadastradoException(Integer id){
            super("Emprestimo nao cadastrado com id: "+ id);
        }
    }

