package edu.ucan.bilheteira.tipoTransporte;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("tipoTransporte")
public class TipoTransporteController {

    @Autowired
    private TipoTransporteService tipoTransporteService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody TipoTransporte tipoTransporte){

        return ResponseEntity.ok(this.tipoTransporteService.create(tipoTransporte));
    }

    @PutMapping("/update/{pkTipoTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody TipoTransporte tipoTransporte, @PathVariable UUID pkTipoTransporte){
        tipoTransporte.setPkTipoTransporte(pkTipoTransporte);
        try{
            return ResponseEntity.ok(this.tipoTransporteService.update(tipoTransporte, pkTipoTransporte));
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao actualizar os dados" + c.getMessage());
        }
    }

    @DeleteMapping("/{pkTipoTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public  ResponseEntity<?> delete(@PathVariable UUID pkTipoTransporte){
        try{
            this.tipoTransporteService.delete(pkTipoTransporte);
            return ResponseEntity.ok("Tipo Transporte Desactivado com suceso");
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao desactivar o tipo de transporte" + c.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{
            List<TipoTransporte> lista = this.tipoTransporteService.findByEstadoTrueOrderByDesignationAsc();
            return ResponseEntity.ok(lista);
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body(c.getMessage());
        }
    }

    @GetMapping("/findById/{pkTipoTransporte}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable UUID pkTipoTransporte){
        try{
            return ResponseEntity.ok(this.tipoTransporteService.findById(pkTipoTransporte));
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body(c.getMessage());
        }
    }

}
