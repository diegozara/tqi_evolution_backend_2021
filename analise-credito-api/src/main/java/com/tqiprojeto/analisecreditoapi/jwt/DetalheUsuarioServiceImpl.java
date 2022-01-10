package com.tqiprojeto.analisecreditoapi.jwt;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

/**
 * @author Diego Zaratini Constantino - Adaptado do Tutorial do Rodrigo Tavares do canal Expertos Tech do youtube
 * @link https://www.youtube.com/watch?v=WM8Ty4ITcFc
 * @version 1.0.0
 * @since Release 1.0
 */
@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    /**
     * Construtor da Classe
     *
     * @param clienteRepository  Objeto de acesso da classe ClienteRepository
     */
    public DetalheUsuarioServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }


    /**
     * Bbusca dos clientes para geração do token (verificação através do e-mail)
     *
     * @param username      Email do cliente já cadastrado
     * @return              Objeto com as informações do cliente
     * @throws UsernameNotFoundException        Se não houver cliente com aquele email informado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Cliente> cliente = clienteRepository.findByEmail(username);
        if (cliente.isEmpty()){
            throw new UsernameNotFoundException("E-mail: " +username+ " não encontrado");
        }
        return new DetalheUsuarioData(cliente);
    }
}
