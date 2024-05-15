package edu.ucan.bilheteira.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("pedidoAssento")
public class PedidoAssentoController {

    @Autowired
    private PedidoAssentoService pedidoAssentoService;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody PedidoAssentoDTO pedidoAssentoDTO){
        try{
            return ResponseEntity.ok(pedidoAssentoService.create(pedidoAssentoDTO));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar o pedido assento");
        }
    }

    @GetMapping("/{pkPedido}")
    public ResponseEntity<?> findAssentoByPkPedido(@PathVariable UUID pkPedido){
        try{
            return ResponseEntity.ok(pedidoAssentoService.findAssentosByPedidoId(pkPedido));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("erro");
        }
    }
}
