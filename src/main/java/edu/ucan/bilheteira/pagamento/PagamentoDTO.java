package edu.ucan.bilheteira.pagamento;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class PagamentoDTO {
    private String referencia;
    private BigDecimal totalPago;
    private TipoPagamento tipoPagamento;
}


