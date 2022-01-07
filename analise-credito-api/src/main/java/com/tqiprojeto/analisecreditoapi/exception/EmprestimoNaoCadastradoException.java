package com.tqiprojeto.analisecreditoapi.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class EmprestimoNaoCadastradoException extends  Exception{

        public EmprestimoNaoCadastradoException(Integer id){
            super("Emprestimo nao cadastrado com id: "+ id);
        }
    }

