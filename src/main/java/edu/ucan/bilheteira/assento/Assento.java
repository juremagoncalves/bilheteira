package edu.ucan.bilheteira.assento;

import edu.ucan.bilheteira.transporte.Transporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "assento")
public class Assento {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_assento")
    private UUID pkAssento;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_transporte")
    @NotNull(message = "O campo [Transporte] não deve estar em branco")
    private Transporte fkTransporte;

    @NotNull(message = "O campo [Número Assento] não deve estar em branco")
    private String numeroAssento;

    @Column(nullable = false)
    private EstadoAssento estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

}
