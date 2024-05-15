package edu.ucan.bilheteira.reserva;

import lombok.Data;

import java.util.UUID;

@Data
public class PedidoAssentoDTO {
    private UUID pkPedido;
    private UUID pkAssento;
}
