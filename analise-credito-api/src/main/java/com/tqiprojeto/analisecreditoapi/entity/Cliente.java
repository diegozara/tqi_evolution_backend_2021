package com.tqiprojeto.analisecreditoapi.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.Email;

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
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @CPF(message = "CPF invalido")
    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false, unique = true)
    private String rg;

    @Email(message = "email invalido")
    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private Double renda;

    @Column(nullable = false)
    private String cep;

    @Column(nullable = false)
    private String numeroCasa;

    @Column(nullable = false)
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private String senha;


    //objeto de acesso da classe endereço (responsavel por fazer a busca automatica pelo CEP)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;

}
