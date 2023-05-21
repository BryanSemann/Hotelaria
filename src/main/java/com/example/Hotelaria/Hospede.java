package com.example.Hotelaria;

import jakarta.persistence.*;

@Entity
@Table(name = "hospede")
public class Hospede {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private String nome;
    private String documento;
    private String telefone;
}
