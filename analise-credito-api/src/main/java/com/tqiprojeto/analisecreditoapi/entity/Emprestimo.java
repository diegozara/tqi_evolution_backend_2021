package com.tqiprojeto.analisecreditoapi.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;


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
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"}) //evita a serialização (looping do objeto cliente)
public class Emprestimo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;//gerenciamento automático do ID pelo banco de dados

    @Column(nullable = false)
    private Double valor;

    @Column(nullable = false)
    private Integer quantidadeParcelas;

    @Column(nullable = false)
    @JsonFormat(pattern = "dd/MM/yyyy") //fomado de inserção da data dia/mes/ano com 4 dígitos
    private LocalDate dataPrimeiraParcela;

    //objeto de acesso da classe cliente e seu mapeamento
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}
