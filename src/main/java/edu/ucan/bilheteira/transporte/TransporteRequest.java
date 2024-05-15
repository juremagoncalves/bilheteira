package edu.ucan.bilheteira.transporte;

import edu.ucan.bilheteira.assento.Assento;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class TransporteRequest {
    private Transporte transporte;
    private Assento assento;
}
