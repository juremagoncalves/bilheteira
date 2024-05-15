package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.user.User;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "reserva_assento_temporaria")
@Data
public class ReservaAssentoTemporaria {

    @Column(name = "pk_ReservaAssentoTemporaria")
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID pkReservaAssentoTemporaria;

    @ManyToOne
    @JoinColumn(name = "fk_assento", nullable = false)
    @NotNull(message = "O campo [Assento] não deve estar em branco")
    private Assento assento;

    @ManyToOne
    @JoinColumn(name = "fk_user", nullable = false)
    @NotNull(message = "O campo [User] não deve estar em branco")
    private User user;
//    private Integer duracao;

}
