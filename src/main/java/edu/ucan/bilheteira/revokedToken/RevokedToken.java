package edu.ucan.bilheteira.revokedToken;

import jakarta.persistence.*;
import lombok.Data;


import java.time.Instant;
import java.util.UUID;

@Data
@Entity
@Table(name = "token_revogado")
public class RevokedToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_token_revogado")
    private UUID pkTokenRevogado;

    @Column(nullable = false)
    private String token;

    @Column(name = "data_revogado", nullable = false)
    private Instant dataRevogado;
}
