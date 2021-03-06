package com.tqiprojeto.analisecreditoapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.tqiprojeto.analisecreditoapi.entity.Cliente;
import com.tqiprojeto.analisecreditoapi.jwt.DetalheUsuarioData;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;

/**
 * @author Diego Zaratini Constantino - Adaptado do Tutorial do Rodrigo Tavares do canal Expertos Tech do youtube
 * @link https://www.youtube.com/watch?v=WM8Ty4ITcFc
 * @version 1.0.0
 * @since Release 1.0
 */
public class JWTAutenticarFilter extends UsernamePasswordAuthenticationFilter {

    public static final int TOKEN_EXPIRACAO = 600_000; //tempo equivalente a 10 minutos

    public static final String TOKEN_SENHA = "1a0c92f34cc5452a82ec116f29009490"; //gerada pelo guidgenerator (https://www.guidgenerator.com/online-guid-generator.aspx)

    private final AuthenticationManager authenticationManager;

    /**
     * Construtor da Classe
     *
     * @param authenticationManager     Parametro de autenticação
     */
    public JWTAutenticarFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    /**
     * Autenticação onde é verificado as informações dos clientes (e-mail e senha) cadastrados no banco
     *
     * @param request       Requisição para o serviço
     * @param response      Resposta ao serviço requisitado
     * @return              Sucesso da autenticação
     * @throws AuthenticationException      Se não for autenticado email e senha
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {

        try {
            Cliente cliente = new ObjectMapper().readValue(request.getInputStream(), Cliente.class);

            return authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    cliente.getEmail(),
                    cliente.getSenha(),
                    new ArrayList<>()
            ));

        } catch (IOException e) {
            throw new RuntimeException("Falha de autenticação", e);
        }
    }

    /**
     * Criação do token, tempo de expiração e acesso as informações de validação e-mail e senha dos clientes
     *
     * @param request           Requisição para o serviço
     * @param response          Resposta ao serviço requisitado
     * @param chain             Filtro de segurança do Spring Security
     * @param authResult        Autenticação do TOKEN
     * @throws IOException      Se houver aluma falha de verificação dos parametros
     * @throws ServletException Se a requisição não for processada
     */
    @Override
    protected void successfulAuthentication(HttpServletRequest request,
                                            HttpServletResponse response,
                                            FilterChain chain,
                                            Authentication authResult) throws IOException, ServletException {

        DetalheUsuarioData usuarioData = (DetalheUsuarioData) authResult.getPrincipal();

        String token = JWT.create()
                .withSubject(usuarioData.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis() + TOKEN_EXPIRACAO))
                .sign(Algorithm.HMAC512(TOKEN_SENHA));


        response.getWriter().write(token);
        response.getWriter().flush();
    }
}
