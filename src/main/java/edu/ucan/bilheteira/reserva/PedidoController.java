package edu.ucan.bilheteira.reserva;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("pedido")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;

    @PostMapping("/")
    public ResponseEntity<?> create(@Valid @RequestBody PedidoDTO pedidoDTO){
        try{

            return ResponseEntity.status(HttpStatus.CREATED).body( pedidoService.create(pedidoDTO));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar o pedido: " + e.getMessage());
        }
    }

    @GetMapping("/getPedidoById/{pkPedido}")
    public ResponseEntity<?> getPedidoById(@PathVariable UUID pkPedido){
        try{
            return ResponseEntity.ok(this.pedidoService.getPedidoById(pkPedido));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao buscar o pedido");
        }
    }

    @GetMapping("/{id}/data-expiracao")
    public ResponseEntity<LocalDateTime> getDataExpiracao(@PathVariable("id") UUID idPedido) {
        LocalDateTime dataExpiracao = pedidoService.findDataExpiracaoByPkPedido(idPedido);
        if (dataExpiracao != null) {
            return ResponseEntity.ok(dataExpiracao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
