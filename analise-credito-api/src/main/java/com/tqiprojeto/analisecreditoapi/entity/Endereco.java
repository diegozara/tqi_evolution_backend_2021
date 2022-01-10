package com.tqiprojeto.analisecreditoapi.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;

/**
 * @author Diego Zaratini Constantino
 * @version 1.0.0
 * @see Cliente
 * @since Release 1.0
 */
@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //evita a serialização (looping do objeto cliente)
public class Endereco {

    @Id
    private String cep;

    private String logradouro;

    private String bairro;

    private String localidade;

    private String uf;

}