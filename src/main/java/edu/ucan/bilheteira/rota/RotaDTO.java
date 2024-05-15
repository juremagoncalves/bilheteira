package edu.ucan.bilheteira.rota;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class RotaDTO {
//    private Rota rota;
//    private PrecoViagem precoViagem;
    private String fkProvinciaOrigemId;
    private String fkProvinciaDestinoId;
    private String fkTransporteId;
    private BigDecimal preco;
}
