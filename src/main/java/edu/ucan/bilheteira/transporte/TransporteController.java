package edu.ucan.bilheteira.transporte;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("transporte")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Transporte transporte){
        try{
           this.transporteService.create(transporte);
//
            return ResponseEntity.ok(Map.of("message", "Sucesso"));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao salvar os transportes");
        }

    }
//    @PostMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> create(@Valid @RequestBody Transporte transporte){
//        return ResponseEntity.ok(this.transporteService.create(transporte));
//    }

    @PutMapping("/update/{pkTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Transporte transporte, @PathVariable UUID pkTransporte){
        transporte.setPkTransporte(pkTransporte);
        try{
            return ResponseEntity.ok(this.transporteService.update(transporte, pkTransporte));
        }
        catch (CustomEntityNotFoundException c){
           return ResponseEntity.badRequest().body("Erro ao actualizar os dados" + c.getMessage());
        }
    }

    @DeleteMapping("/{pkTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete (@PathVariable UUID pkTransporte){
        try{
            this.transporteService.delete(pkTransporte);
            return ResponseEntity.ok("Transporte desactivado com sucesso");
        }

        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao desactivar o transporte");
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try {
            List<Transporte> transportes = this.transporteService.findAllWithStatusTrueOrderByMatriculaAsc();
            return ResponseEntity.ok(transportes);
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body(c.getMessage());
        }
    }

    @GetMapping("/findById/{pkTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable UUID pkTransporte){
        try{
            return  ResponseEntity.ok(this.transporteService.findById(pkTransporte));
        }
        catch(CustomEntityNotFoundException c){
            return  ResponseEntity.badRequest().body(c.getMessage());
        }
    }

    @GetMapping("findByPkClasseServico/{pkClasseServico}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> buscarTransportesPorIdClasseServico(@PathVariable UUID pkClasseServico){
        try{
            return  ResponseEntity.ok(this.transporteService.buscarTransportesPorIdClasseServico(pkClasseServico));
        }
        catch(CustomEntityNotFoundException c){
            return  ResponseEntity.badRequest().body(c.getMessage());
        }
    }
}
