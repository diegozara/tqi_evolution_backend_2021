package com.tqiprojeto.analisecreditoapi.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see Endereco
 * @since Release 1.0
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Valid
public class Cliente {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //gerenciamento automático do ID pelo banco de dados

    @Column(nullable = false)
    private String nome;

    @CPF(message = "CPF invalido") //verificação da validade do CPF
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String rg;

    @Email(message = "email invalido") //verificação da validade do e-mail
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Double renda;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numeroCasa;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY) //configuração de segurança para não retornar a senha no objeto após cadastro
    private String senha;


    //objeto de acesso da classe endereço (responsavel por fazer a busca automatica pelo CEP)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
