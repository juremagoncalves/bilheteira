package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import jakarta.persistence.*;
import lombok.Data;

import java.util.UUID;

@Table(name = "pedido_assento")
@Entity
@Data
public class PedidoAssento {
    @Id
    @Column(name = "pk_pedido_assento")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPedidoAssento;

    @ManyToOne
    @JoinColumn(name = "fk_assento", nullable = false)
    private Assento fkAssento;

    @ManyToOne
    @JoinColumn(name = "fk_pedido", nullable = false)
    private Pedido fkPedido;
}
