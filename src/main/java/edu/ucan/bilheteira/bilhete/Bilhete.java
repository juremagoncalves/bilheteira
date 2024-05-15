package edu.ucan.bilheteira.bilhete;

import edu.ucan.bilheteira.reserva.Pedido;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "bilhete")
@Data
public class Bilhete {

    @Id
    @Column(name = "pk_pedido")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPedido;
    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable = false)
//    @NotNull(message = "O campo [User] não deve estar em branco")
    private Pedido pedido;

    @Column(unique = true)
    private String codigo;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;
}
