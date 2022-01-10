package com.tqiprojeto.analisecreditoapi.jwt;

import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

/**
 * @author Diego Zaratini Constantino - Adaptado do Tutorial do Rodrigo Tavares do canal Expertos Tech do youtube
 * @link https://www.youtube.com/watch?v=WM8Ty4ITcFc
 * @version 1.0.0
 * @since Release 1.0
 */
public class DetalheUsuarioData implements UserDetails {

    private final Optional<Cliente> cliente;

    public DetalheUsuarioData(Optional<Cliente> cliente) {
        this.cliente = cliente;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList<>();
    }

    /**
     * Busca pelo atributo senha do cliente para geração do TOKEN
     *
     * @return      Senha do cliente
     */
    @Override
    public String getPassword() { return cliente.orElse(new Cliente()).getSenha(); }

    /**
     * Sera fornecido pelo atributo email do cliente para geração do TOKEN
     *
     * @return      Email do cliente
     */
    @Override
    public String getUsername() { return cliente.orElse(new Cliente()).getEmail();}

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
