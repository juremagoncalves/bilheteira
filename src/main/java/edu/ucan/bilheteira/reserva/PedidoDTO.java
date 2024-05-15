package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Data
public class PedidoDTO {
    private UUID pkUser;
    private UUID pkProgramacao;
    private String entidade;
    private BigDecimal totalPago;
    private List<Assento> assentosSelecionados;

}
