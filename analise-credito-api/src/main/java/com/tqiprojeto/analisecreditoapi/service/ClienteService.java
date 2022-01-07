package com.tqiprojeto.analisecreditoapi.service;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.entity.Emprestimo;
import com.tqiprojeto.analisecreditoapi.entity.Endereco;
import com.tqiprojeto.analisecreditoapi.exception.ClienteDbException;
import com.tqiprojeto.analisecreditoapi.exception.ClienteNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.exception.EmprestimoNaoCadastradoException;
import com.tqiprojeto.analisecreditoapi.repository.ClienteRepository;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRepository;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoDetalhado;
import com.tqiprojeto.analisecreditoapi.repository.EmprestimoRetornoSimples;
import org.hibernate.JDBCException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.validation.ConstraintViolationException;
import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {


    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder;

    private EmprestimoRepository emprestimoRepository;

    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;
    }

    //método para listar todos os clientes
    public List<Cliente> listarTodos() {

        return clienteRepository.findAll();

    }

    //método buscar um cliente por ID
    public Cliente buscarPorId(Integer id) throws ClienteNaoCadastradoException {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        return cliente;
    }

    //método para inserir um cliente
    public Cliente inserir(Cliente cliente) {

        setEnderecoCliente(cliente);
        encriptarSenha(cliente);

        return clienteRepository.save(cliente);
    }

    //método para atualizar um cliente cadastrado, informando o ID e demais atributos do cliente a ser atualizado
    public Cliente atualizar(Integer id, Cliente cliente) throws ClienteNaoCadastradoException {

        clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        cliente.setId(id);
        setEnderecoCliente(cliente);

        return clienteRepository.save(cliente);
    }

    //método para excluir um cliente cadastrado
    public void deletar(Integer id) throws ClienteNaoCadastradoException, ClienteDbException {

        verificarExistencia(id);

        List<EmprestimoRetornoDetalhado> emprestimo = emprestimoRepository.listarEmprestimoDetalhado(id);

            if (emprestimo.isEmpty()){
                clienteRepository.deleteById(id);
            }

        else {throw new ClienteDbException(id);}

    }

    //método para buscar um endereço automaticamente através do CEP (utilizado API do VIA CEP)
    private Endereco criarEndereco (String zipCode) {

        String url = "https://viacep.com.br/ws/"+zipCode+"/json/";
        RestTemplate restTemplate = new RestTemplate();
        Endereco endereco = restTemplate.getForObject(url, Endereco.class);
        return endereco;
    }

    //método para validar o login, verificado pelo email e senha do cliente cadastrado
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

    //método usado para setar as informações do endereço do cliente (informações do endereço fornecidas pelo método criarEndereço)
    private Cliente setEnderecoCliente (Cliente cliente){

        Endereco endereco = criarEndereco(cliente.getCep());
        cliente.setEndereco(endereco);
        return cliente;
    }

    //método utilizado para criptografar a senha do cliente
    private Cliente encriptarSenha (Cliente cliente){

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return cliente;
    }

    //método para everificar a existencia de cliente cadastrado para realizar exclusão
    private Cliente verificarExistencia(Integer id) throws ClienteNaoCadastradoException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoCadastradoException(id));
    }


}
