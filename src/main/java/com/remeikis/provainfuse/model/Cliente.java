package com.remeikis.provainfuse.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Cliente
{
    @Id
    //@GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Integer codigo;

    @Column(nullable = false)
    private String nome;
}
