package edu.ucan.bilheteira.pagamento;

import edu.ucan.bilheteira.reserva.Pedido;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pagamento")
@Data
public class Pagamento {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPagamento;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "fk_pedido", nullable = false)
    private Pedido pedido;

    @Column(nullable = false)
    private LocalDateTime dataCadastro;

    @Column(precision = 10, scale = 2, name = "total_pago")
    private BigDecimal totalPago;

    private String referencia;

    @Enumerated(EnumType.STRING)
    private TipoPagamento tipoPagamento;
}
