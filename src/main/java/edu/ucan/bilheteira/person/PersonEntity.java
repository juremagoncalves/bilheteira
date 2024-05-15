package edu.ucan.bilheteira.person;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

import java.util.UUID;
 @Data
 @Entity
 @Table(name = "pessoa")
public class PersonEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_pessoa")
    private UUID pkPessoa;
    @Column(nullable = false)
    @NotBlank(message = "O campo [nome] não deve estar em branco")
    private String nome;
    @NotBlank(message = "O campo [bi] não deve estar em branco")
    @Column(nullable = false, unique = true)
    private String bi;
    @Column(nullable = false)
    @NotBlank(message = "O campo [telefone] não deve estar em branco")
    private String telefone;



 }
