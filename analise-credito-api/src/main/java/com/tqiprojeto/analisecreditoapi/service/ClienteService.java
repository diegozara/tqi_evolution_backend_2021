package com.tqiprojeto.analisecreditoapi.service;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.entity.Endereco;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

import java.util.Optional;


@Service
public class ClienteService {


    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder;


    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public List<Cliente> listarTodos() {

        return clienteRepository.findAll();

    }

    public Cliente buscarPorId(Integer id) throws ClienteNaoCadastradoException {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        return cliente;
    }

    public Cliente inserir(Cliente cliente) {

        setEnderecoCliente(cliente);
        encriptarSenha(cliente);

        return clienteRepository.save(cliente);
    }

    public Cliente atualizar(Integer id, Cliente cliente) throws ClienteNaoCadastradoException {

        clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        cliente.setId(id);
        setEnderecoCliente(cliente);

        return clienteRepository.save(cliente);
    }


    public void deletar(Integer id) throws ClienteNaoCadastradoException{
        clienteRepository.deleteById(id);
    }

    private Endereco criarEndereco (String zipCode) {

        String url = "https://viacep.com.br/ws/"+zipCode+"/json/";
        RestTemplate restTemplate = new RestTemplate();
        Endereco endereco = restTemplate.getForObject(url, Endereco.class);
        return endereco;
    }

    public ResponseEntity<Boolean> login (String email, String senha){

        Optional<Cliente> clienteEmail = clienteRepository.findByEmail(email);

        if (clienteEmail.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(false);
        }

        Cliente cliente = clienteEmail.get();
        boolean valid = passwordEncoder.matches(senha, cliente.getSenha());

        HttpStatus status = (valid) ? HttpStatus.OK : HttpStatus.UNAUTHORIZED;
        return ResponseEntity.status(status).body(valid);

    }

    private Cliente setEnderecoCliente (Cliente cliente){

        Endereco endereco = criarEndereco(cliente.getCep());
        cliente.setEndereco(endereco);
        return cliente;
    }

    private Cliente encriptarSenha (Cliente cliente){

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return cliente;
    }

}
