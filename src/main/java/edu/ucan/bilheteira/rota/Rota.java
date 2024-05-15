package edu.ucan.bilheteira.rota;

import com.fasterxml.jackson.annotation.JsonProperty;
import edu.ucan.bilheteira.localidade.provincia.Provincia;
import edu.ucan.bilheteira.programacao.Programacao;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@Entity
@Table(name = "rota")
public class Rota {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "pk_rota")
    private UUID pkRota ;

//    @ManyToOne
//    @JoinColumn(nullable = false, name = "fk_transporte")
//    @NotNull(message = "O campo [Transporte] não deve estar em branco")
//    private Transporte fkTransporte;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_provincia_origem")
    @NotNull(message = "O campo [Origem] não deve estar em branco")
    private Provincia fkProvinciaOrigem;

    @ManyToOne
    @JoinColumn(nullable = false, name = "fk_provincia_destino")
    @NotNull(message = "O campo [Destino] não deve estar em branco")
    private Provincia fkProvinciaDestino;



    @Column(columnDefinition = "boolean default true")
    private boolean estado;

    @Column(name = "data_cadastro")
    @CreationTimestamp
    private LocalDateTime dataCadastro;

    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    @OneToMany(mappedBy = "fkRota")
    private List<Programacao> programacoes = new ArrayList<>();



}
