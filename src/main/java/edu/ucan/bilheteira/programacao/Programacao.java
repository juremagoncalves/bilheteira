package edu.ucan.bilheteira.programacao;

import edu.ucan.bilheteira.rota.Rota;
import edu.ucan.bilheteira.transporte.Transporte;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Data
@Table(name = "programacao")
public class Programacao {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_programacao")
    private UUID pkProgramacao;

    @ManyToOne
    @JoinColumn(name = "fk_rota", nullable = false)
    @NotNull(message = "O campo [Rota] não deve estar em branco")
    private Rota fkRota;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_transporte")
    @NotNull(message = "O campo [Transporte] não deve estar em branco")
    private Transporte fkTransporte;


    @Column(name = "dia_semana", nullable = false)
    //@NotNull(message = "O campo [dia semana] não deve estar em branco")
    private String diaSemana;

    @Column(name = "hora_chegada", nullable = false)
    @NotNull(message = "O campo [Hora chegada] não deve estar em branco")
    private String horaChegada;

    @Column(name = "hora_partida", nullable = false)
    @NotNull(message = "O campo [hora partida] não deve estar em branco")
    private String horaPartida;

    @Column(name = "data_viagem", nullable = false)
    @NotNull(message = "O campo [data viagem] não deve estar em branco")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate dataViagem;

    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

}
