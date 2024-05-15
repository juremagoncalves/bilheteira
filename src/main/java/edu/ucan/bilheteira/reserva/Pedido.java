package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.programacao.Programacao;
import edu.ucan.bilheteira.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
//import org.hibernate.validator.constraints.UUID;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pedido")
@Data
public class Pedido {
    @Id
    @Column(name = "pk_pedido")
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkPedido;

    @ManyToOne
    @JoinColumn(name = "fk_programacao", nullable = false)
    @NotNull(message = "O campo [Assento] não deve estar em branco")
    private Programacao programacao;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    @NotNull(message = "O campo [User] não deve estar em branco")
    private User user;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado_reserva")
    private EstadoPedido estadoPedido;

    @Column(precision = 10, scale = 2, name = "total_pago")
    private BigDecimal totalPago;

    @Column(unique = true)
    private String referencia;

    @Column(name = "data_expiracao")
    private LocalDateTime dataExpiracao;

    private String entidade;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;
}
