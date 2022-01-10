package com.tqiprojeto.analisecreditoapi.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;


/**
 * @author Diego Zaratini Constantino - Adaptado do Tutorial do Rodrigo Tavares do canal Expertos Tech do youtube
 * @link https://www.youtube.com/watch?v=WM8Ty4ITcFc
 * @version 1.0.0
 * @since Release 1.0
 */
public class JWTValidarFilter extends BasicAuthenticationFilter {

    public static final String HEADER_ATRIBUTO = "Authorization";
    public static final String ATRIBUTO_PREFIXO = "Bearer ";

    /**
     * Construtor da Classe
     *
     * @param authenticationManager     Para validar e autenticar o TOKEN
     */
    public JWTValidarFilter(AuthenticationManager authenticationManager) {
        super(authenticationManager);
    }

    /**
     * Filtro de segurança do TOKEN
     *
     * @param request               Requisição para o serviço
     * @param response              Resposta ao serviço requisitado
     * @param chain                 Filtro de segurança do Spring Security
     * @throws IOException          Se houver aluma falha de verificação dos parametros
     * @throws ServletException     Se a requisição não for processada
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String atributo = request.getHeader(HEADER_ATRIBUTO);

        if (atributo == null){
            chain.doFilter(request, response);
            return;
        }

        if (!atributo.startsWith(ATRIBUTO_PREFIXO)){
            chain.doFilter(request, response);
            return;
        }

        String token = atributo.replace(ATRIBUTO_PREFIXO, "");

        UsernamePasswordAuthenticationToken authenticationToken = getAuthenticationToken(token);

        SecurityContextHolder.getContext().setAuthentication(authenticationToken);
        chain.doFilter(request, response);

    }

    /**
     * Verificação do Token através das informações de e-mail e senha do cliente cadastrado informados no token gerado
     *
     * @param token     Token gerado
     * @return          Token autenticado
     */
    private UsernamePasswordAuthenticationToken getAuthenticationToken (String token) {

        String usuario = JWT.require(Algorithm.HMAC512(JWTAutenticarFilter.TOKEN_SENHA))
                .build()
                .verify(token)
                .getSubject();

        if (usuario == null){
            return null;
        }

        return new UsernamePasswordAuthenticationToken(usuario, null, new ArrayList<>());
    }

}
