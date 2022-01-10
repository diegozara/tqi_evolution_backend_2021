package com.tqiprojeto.analisecreditoapi.security;

import com.tqiprojeto.analisecreditoapi.jwt.DetalheUsuarioServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

/**
 * @author Diego Zaratini Constantino - Adaptado do Tutorial do Rodrigo Tavares do canal Expertos Tech do youtube
 * @link https://www.youtube.com/watch?v=WM8Ty4ITcFc
 * @version 1.0.0
 * @since Release 1.0
 */
@EnableWebSecurity
public class JWTConfiguracao extends WebSecurityConfigurerAdapter {

    private  final DetalheUsuarioServiceImpl usuarioService;
    private  final PasswordEncoder passwordEncoder;

    /**
     * Construtor da Classe
     *
     * @param usuarioService        Objeto da classe DetalheUsuarioServiceImpl
     * @param passwordEncoder       Objeto da classe PasswordEncoder
     */
    public JWTConfiguracao(DetalheUsuarioServiceImpl usuarioService, PasswordEncoder passwordEncoder) {
        this.usuarioService = usuarioService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Utilizar as classes que foram desenvolvidas dentro do projeto e não as implementações padrão do Spring Security
     *
     * @param auth              Parametro de autenticação
     * @throws Exception        Se não for possível a autenticação
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(usuarioService).passwordEncoder(passwordEncoder);
    }

    /**
     * Configuração dos endpoints permitidos, autorizados apenas o de login e de cadastro de clientes
     *
     * @param http              Configuração de segurança do HTTP
     * @throws Exception        Se houver alguma solicitação que não esteja nas configurações
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable().authorizeHttpRequests()
                .antMatchers(HttpMethod.POST, "/login").permitAll()
                .antMatchers(HttpMethod.POST, "/api/v1/clientes").permitAll() // autorização do cadastro de clientes sem autenticação
                .anyRequest().authenticated()
                .and()
                .addFilter(new JWTAutenticarFilter(authenticationManager()))
                .addFilter(new JWTValidarFilter(authenticationManager()))
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

    }

    /**
     * Permissão para requisições externas, autorização de requisições de outras URLs
     *
     * @return  Autorização
     */
    @Bean
    CorsConfigurationSource corsConfigurationSource(){
        final UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();

        CorsConfiguration corsConfiguration = new CorsConfiguration().applyPermitDefaultValues();

        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }

}
