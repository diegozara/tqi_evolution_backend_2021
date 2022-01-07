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

@RestController
@RequestMapping ("/api/v1/clientes")
public class ClienteController {


    private ClienteService clienteService;

    @Autowired
    public ClienteController(ClienteService clienteService) {
        this.clienteService = clienteService;
    }

    //listar todos os clientes cadastrados
    @GetMapping
    public List<Cliente> listarClientes(){

        return clienteService.listarTodos();
    }

    //listar cliente cadastrado por id
    @GetMapping("/{id}")
    public Cliente buscarPorId(@PathVariable Integer id) throws ClienteNaoCadastradoException {
        return clienteService.buscarPorId(id);
    }

    //cadastrar cliente
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cliente inserir (@RequestBody @Valid Cliente cliente) {
        return clienteService.inserir(cliente);
    }

    //atualizar cliente cadastrado
    @PutMapping ("/{id}")
    public Cliente atualizar (@PathVariable Integer id, @RequestBody @Valid Cliente cliente) throws ClienteNaoCadastradoException {
        return clienteService.atualizar(id,cliente);
    }

    //deletar cliente por id
    @DeleteMapping ("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletar (@PathVariable Integer id) throws ClienteNaoCadastradoException, ClienteDbException {
        clienteService.deletar(id);
    }


    //validação de login através do email e senha que foram cadastrados
    @GetMapping("/login")
    public ResponseEntity<Boolean> login (@RequestParam String email, @RequestParam String senha){

       return clienteService.login(email, senha);

    }

}


