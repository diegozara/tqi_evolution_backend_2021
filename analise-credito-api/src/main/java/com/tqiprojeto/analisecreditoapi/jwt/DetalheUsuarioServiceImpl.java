package com.tqiprojeto.analisecreditoapi.jwt;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.repository.ClienteRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DetalheUsuarioServiceImpl implements UserDetailsService {

    private final ClienteRepository clienteRepository;

    public DetalheUsuarioServiceImpl(ClienteRepository clienteRepository) {
        this.clienteRepository = clienteRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        Optional<Cliente> cliente = clienteRepository.findByEmail(username);
        if (cliente.isEmpty()){
            throw new UsernameNotFoundException("E-mail: " +username+ " não encontrado");
        }
        return new DetalheUsuarioData(cliente);
    }
}
