package edu.ucan.bilheteira.rota;

import edu.ucan.bilheteira.preco.PrecoViagem;
import edu.ucan.bilheteira.transporte.Transporte;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RotaTransportePrecoDTO {
    private Rota rota;
    private Transporte transporte;
    private PrecoViagem precoViagem;
}
