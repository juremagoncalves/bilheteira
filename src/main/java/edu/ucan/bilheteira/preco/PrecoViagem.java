package edu.ucan.bilheteira.preco;

import edu.ucan.bilheteira.rota.Rota;
import edu.ucan.bilheteira.transporte.Transporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "preco_viagem")
public class PrecoViagem {
   @Id
   @GeneratedValue(strategy = GenerationType.UUID)
   @Column(name = "pk_preco_viagem")
   private UUID pkPrecoViagem;

    @ManyToOne
    @JoinColumn(name = "fk_rota", nullable = false)
    @NotNull(message = "O campo [Rota] não deve estar em branco")
    private Rota fkRota;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_transporte")
    @NotNull(message = "O campo [Transporte] não deve estar em branco")
    private Transporte fkTransporte;

    @Column(precision = 10, scale = 2)
    private BigDecimal preco;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;
}
