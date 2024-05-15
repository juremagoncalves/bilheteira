package edu.ucan.bilheteira.marca;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.List;

@RestController
@RequestMapping("/marca")
public class MarcaController {

    @Autowired
    MarcaService marcaService;
//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody Marca marca){
        return ResponseEntity.ok(this.marcaService.create(marca));
    }

    @PutMapping("/update/{pkMarca}")
    public ResponseEntity<?> update(@Valid @RequestBody Marca marca, @PathVariable UUID pkMarca){
        marca.setPkMarca(pkMarca);
        try{
            return ResponseEntity.ok(marcaService.update(marca,pkMarca));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao actualizar" + e.getMessage());
        }
    }

    @DeleteMapping("/{pkMarca}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID pkMarca){
        try{
            marcaService.delete(pkMarca);
            return ResponseEntity.ok("Marca desactivada com sucesso");
        }
        catch (CustomEntityNotFoundException e){
           return ResponseEntity.badRequest().body("Erro ao desactivar a marca \n" + e.getMessage());
        }

    }

    @GetMapping()
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findAll(){
        try{
            List<Marca> marcas = this.marcaService.findByEstadoTrueOrderByDesignationAsc();
            return ResponseEntity.ok(marcas);
        }
        catch (CustomEntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/findById/{pkMarca}")
    public ResponseEntity<?> findById(@PathVariable UUID pkMarca){
        try{
            return ResponseEntity.ok(this.marcaService.findById(pkMarca));
        }
        catch (CustomEntityNotFoundException e){
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }


}
