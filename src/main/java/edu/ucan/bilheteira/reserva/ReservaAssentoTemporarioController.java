package edu.ucan.bilheteira.reserva;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("reservaTemporaria")
public class ReservaAssentoTemporarioController {

    @Autowired
    private ReservaAssentoTemporarioService reservaAssentoTemporarioService;

    @GetMapping
    public ResponseEntity<?> findAll(){
        try{
            List<ReservaAssentoTemporaria> lista = this.reservaAssentoTemporarioService.findAll();
            return ResponseEntity.ok(lista);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @DeleteMapping("/{pkAssento}")
    public ResponseEntity<?> delete(@PathVariable UUID pkAssento) {
        try {
            this.reservaAssentoTemporarioService.delete(pkAssento);
            return ResponseEntity.ok("Deletado com sucesso");
        }
        catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao deletar");
        }
    }
}
