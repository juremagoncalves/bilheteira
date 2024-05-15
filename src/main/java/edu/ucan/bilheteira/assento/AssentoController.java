package edu.ucan.bilheteira.assento;

import edu.ucan.bilheteira.reserva.ReservaAssentoTemporaria;
import edu.ucan.bilheteira.reserva.ReservaAssentoTemporarioService;
import edu.ucan.bilheteira.user.User;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping("assento")
public class AssentoController {
    @Autowired
    private AssentoService assentoService;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private ReservaAssentoTemporarioService reservaAssentoTemporarioService;

//    @PostMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> create(@Valid @RequestBody Assento assento){
//        try{
//            System.err.println("kkkkkkkkkkkkkkkkkkkkkk");
//            this.assentoService.save(assento);
//            System.err.println("llllllllllllll");
//            return ResponseEntity.ok("Assento salvo com sucesso");
//        }
//        catch (RuntimeException e){
//            return ResponseEntity.badRequest().body("Excedeu a capacidade" + e.getMessage());
//        }
//    }

    @PutMapping("/update/{pkAssento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Assento assento, @PathVariable UUID pkAssento){
        assento.setPkAssento(pkAssento);
        try{

            return ResponseEntity.ok(this.assentoService.update(assento,pkAssento ));
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro ao actualizar o assento");
        }
    }

    @PutMapping("/{userIdLogado}/{seatId}/{newState}")
    public ResponseEntity<?> updateSeatState(@PathVariable UUID seatId, @PathVariable EstadoAssento newState,@PathVariable UUID userIdLogado) {
        System.err.println("assentos");
        try{
            Assento updateSeat = assentoService.updateSeatState(seatId, newState);

           if(newState.equals(EstadoAssento.RESERVADO)){
               ReservaAssentoTemporaria reserva = new ReservaAssentoTemporaria();
               Assento assento = new Assento();
               assento.setPkAssento(seatId);
               reserva.setAssento(assento);
               User user = new User();
               user.setPkUsuario(userIdLogado);
               reserva.setUser(user);
               reservaAssentoTemporarioService.save(reserva);
           }

            simpMessagingTemplate.convertAndSend("/topic/seatUpdate", updateSeat);
            return  ResponseEntity.ok(updateSeat);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro na mudança de estado do assento");
        }
    }

    @DeleteMapping("/{pkAssento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID pkAssento){
        try{
            this.assentoService.delete(pkAssento);
            return ResponseEntity.ok("Assento desactivado com sucesso");
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Erro ao desactivar o assento");
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{
            List<Assento> lista = this.assentoService.findAll();
           return ResponseEntity.ok(lista);
        }
        catch (RuntimeException e){
            return  ResponseEntity.badRequest().body("Erro ao listar os transportes");
        }
    }
    @GetMapping("/{pkAssento}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable UUID pkAssento){
        try{
            return ResponseEntity.ok(this.assentoService.findById(pkAssento));
        }
        catch (RuntimeException e){
            return  ResponseEntity.badRequest().body("Erro ao buscar o assento");
        }
    }

    @GetMapping("findByPkTransporte/{pkTransporte}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByPkTransporte(@PathVariable UUID pkTransporte){
        try{
            return ResponseEntity.ok(this.assentoService.findAssentoByPkTransporte(pkTransporte));
        }
        catch (RuntimeException e){
            return  ResponseEntity.badRequest().body("Erro ao buscar os assentos");
        }
    }


}
