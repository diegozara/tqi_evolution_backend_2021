package com.tqiprojeto.analisecreditoapi.service;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.entity.Endereco;
import com.tqiprojeto.analisecreditoapi.exception.ClienteDbException;
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

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see ClienteRepository
 * @see PasswordEncoder
 * @since Release 1.0
 */
@Service
public class ClienteService {


    private ClienteRepository clienteRepository;
    private PasswordEncoder passwordEncoder; //usado para criptografar a senha do cliente

    /**
     * Construtor da Classe
     *
     * @param clienteRepository     Objeto de acesso da Classe ClienteRepository
     * @param passwordEncoder       Objeto de acesso para criptografar a senha do cliente
     */
    @Autowired
    public ClienteService(ClienteRepository clienteRepository, PasswordEncoder passwordEncoder) {
        this.clienteRepository = clienteRepository;
        this.passwordEncoder = passwordEncoder;

    }

    /**
     * Método GET de todos os clientes cadastrados
     *
     * @return      Lista de todos os clientes que estão cadastrados
     */
    public List<Cliente> listarTodos() {

        return clienteRepository.findAll();

    }

    /**
     * Método GET para busca de um cliente pelo seu ID
     *
     * @param id    ID do cliente que sera informado para realizar a busca
     * @return      Todos os dados do cliente
     * @throws ClienteNaoCadastradoException    Se não houver cliente com o ID informado
     */
    public Cliente buscarPorId(Integer id) throws ClienteNaoCadastradoException {

        Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        return cliente;
    }

    /**
     * Cadastro de clientes
     *
     * @param cliente       Payload dos dados para cadastro do cliente
     * @return              Retorna os campos do cliente criado, com exceção do campo senha
     */
    public Cliente inserir(Cliente cliente) {

        setEnderecoCliente(cliente);
        encriptarSenha(cliente);

        return clienteRepository.save(cliente);
    }

    /**
     * Atualizar algum dado do cliente informando pelo ID
     *
     * @param id        ID do cliente que será atualizado
     * @param cliente   Payload com os dados atualizados do cliente
     * @return          Os campos do cliente atualizado com exceção do campo senha
     * @throws ClienteNaoCadastradoException    Se não houver nenhum cliente com aquele ID
     */
    public Cliente atualizar(Integer id, Cliente cliente) throws ClienteNaoCadastradoException {

        clienteRepository.findById(id).orElseThrow(() -> new ClienteNaoCadastradoException(id));
        cliente.setId(id);
        setEnderecoCliente(cliente);

        return clienteRepository.save(cliente);
    }

    /**
     * Excluir um Cliente
     *
     * @param id        ID do cliente que será excluido
     * @throws ClienteNaoCadastradoException  Se não houver nenhum cliente com aquele ID
     * @throws ClienteDbException             Restrição de chave estrangeira, se houver algum emprestimo vinculado a esse cliente
     *                                        não será possível a exclusão sem antes excluir o emprestmo vinculado a ele
     */
    public void deletar(Integer id) throws ClienteNaoCadastradoException, ClienteDbException {

        verificarExistencia(id);

       try {clienteRepository.deleteById(id);}

       catch (Exception e){

           throw new ClienteDbException(id);
       }

    }

    /**
     * Busca automática dos campos da Classe endereço (logradouro, bairro, localidade, uf)
     *
     * Utilizada API do site VIACEP, com retorno dos dados no modelo JSON
     *
     * @param zipCode       CEP do cliente
     * @return              Dados do endereço fornecidos pelo VIACEP
     */
    private Endereco criarEndereco (String zipCode) {

        String url = "https://viacep.com.br/ws/"+zipCode+"/json/";
        RestTemplate restTemplate = new RestTemplate();
        Endereco endereco = restTemplate.getForObject(url, Endereco.class);
        return endereco;
    }

    /**
     * Método para validar o login, verificado pelo email e senha do cliente cadastrado
     *
     @param email        E-mail do cliente já cadastrado
      * @param senha     Senha do cliente já cadastrado
     * @return           true ou false conforme a verificação do email e senha
     */
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

    /**
     * Usado para setar as informações do endereço do cliente
     *
     * Informações do endereço fornecidas pelo método criarEndereço
     *
     * @param cliente       Objeto Cliente
     * @return              Objeto Cliente com as informações do endereço adicionadas
     */
    private Cliente setEnderecoCliente (Cliente cliente){

        Endereco endereco = criarEndereco(cliente.getCep());
        cliente.setEndereco(endereco);
        return cliente;
    }

    /**
     * Utilizado para criptografar a senha do cliente
     *
     *
     * @param cliente       Objeto com as informações do Cliente
     * @return              Objeto cliente com a senha criptografada
     */
    private Cliente encriptarSenha (Cliente cliente){

        cliente.setSenha(passwordEncoder.encode(cliente.getSenha()));
        return cliente;
    }

    /**
     * Verificar a existencia de cliente cadastrado para realizar exclusão
     *
     * @param id        ID do cliente
     * @return          Cliente localizado pelo ID
     * @throws ClienteNaoCadastradoException        Se não houver cliente cadastrado com aquele ID informado
     */
    private Cliente verificarExistencia(Integer id) throws ClienteNaoCadastradoException {
        return clienteRepository.findById(id)
                .orElseThrow(() -> new ClienteNaoCadastradoException(id));
    }


}
