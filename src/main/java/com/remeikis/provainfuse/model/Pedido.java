package com.remeikis.provainfuse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table

//@JacksonXmlRootElement

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Pedido
{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private int numeroControle;

    @Column(nullable = false)
    //@Temporal(TemporalType.DATE)
    private Date dataCadastro;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private float valor;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private int codigoCliente;
}
