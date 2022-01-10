package com.tqiprojeto.analisecreditoapi.controller;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.exception.ClienteDbException;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.service.ClienteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see ClienteService
 * @since Release 1.0
 */
@RestController
@RequestMapping ("/api/v1/clientes") //Endpoint para as funcionalidades referente a Classe Cliente
public class ClienteController {


    private ClienteService clienteService;

    /**
     * Construtor da Classe
     *
     * @param clienteService    Objeto de acesso da classe ClienteService
     */
    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    /**
     * Método GET de todos os clientes cadastrados
     *
     * @return      Lista de todos os clientes que estão cadastrados
     */
    @GetMapping
    public List<Cliente> listarClientes(){

        return clienteService.listarTodos();
    }

    /**
     * Método GET para busca de um cliente pelo seu ID
     *
     * @param id    ID do cliente que sera informado para realizar a busca
     * @return      Todos os dados do cliente
     * @throws ClienteNaoCadastradoException    Se não houver cliente com o ID informado
     */
    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Integer id) throws ClienteNaoCadastradoException {
        return clienteService.buscarPorId(id);
    }

    /**
     * Cadastro de clientes
     *
     * @param cliente       Payload dos dados para cadastro do cliente
     * @return              Retorna os campos do cliente criado, com exceção do campo senha
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserir (@RequestBody @Valid Cliente cliente) {
        return clienteService.inserir(cliente);
    }

    /**
     * Atualizar algum dado do cliente informando pelo ID
     *
     * @param id        ID do cliente que será atualizado
     * @param cliente   Payload com os dados atualizados do cliente
     * @return          Os campos do cliente atualizado com exceção do campo senha
     * @throws ClienteNaoCadastradoException    Se não houver nenhum cliente com aquele ID
     */
    @PutMapping ("/{id}")
    public Cliente atualizar (@PathVariable Integer id, @RequestBody @Valid Cliente cliente) throws ClienteNaoCadastradoException {
        return clienteService.atualizar(id,cliente);
    }

    /**
     * Excluir um Cliente
     *
     * @param id        ID do cliente que será excluido
     * @throws ClienteNaoCadastradoException  Se não houver nenhum cliente com aquele ID
     * @throws ClienteDbException             Restrição de chave estrangeira, se houver algum emprestimo vinculado a esse cliente
     *                                        não será possível a exclusão sem antes excluir o emprestmo vinculado a ele
     */
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Integer id) throws ClienteNaoCadastradoException, ClienteDbException {
        clienteService.deletar(id);
    }

    /**
     * Método para validar o login, verificado pelo email e senha do cliente cadastrado
     *
     * @param email     E-mail do cliente já cadastrado
     * @param senha     Senha do cliente já cadastrado
     * @return          true ou false conforme a verificação do email e senha
     */
    @GetMapping("/login")
    public ResponseEntity<Boolean> login (@RequestParam String email, @RequestParam String senha){

       return clienteService.login(email, senha);

    }

}


