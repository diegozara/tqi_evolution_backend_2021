package com.tqiprojeto.analisecreditoapi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0 *
 * @since Release 1.0
 */
@SpringBootApplication
public class AnaliseCreditoApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(AnaliseCreditoApiApplication.class, args);}

	/**
	 * Criptografar a senha - chamado pelo método encriptarSenha na classe ClienteService
	 *
	 * @return		senha criptografada
	 */
		@Bean
		public PasswordEncoder getPasswordEncoder() {

		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		return encoder;
	}

}

