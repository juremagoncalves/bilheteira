package edu.ucan.bilheteira.classeServico;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("classeServico")
public class ClasseServicoController {
    @Autowired
    private ClasseServicoService classeServicoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody ClasseServico classeServico){
        return ResponseEntity.ok(this.classeServicoService.create(classeServico));
    }

    @PutMapping("/update/{pkClasseServico}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody ClasseServico classeServico, @PathVariable UUID pkClasseServico){
        classeServico.setPkClasseServico(pkClasseServico);
        try{
            return  ResponseEntity.ok(this.classeServicoService.update(classeServico, pkClasseServico));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Erro ao actualizar a classe de serviço" + e.getMessage());
        }
    }
    @DeleteMapping("/{pkClasseServico}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete (@PathVariable UUID pkClasseServico){
        try {
            this.classeServicoService.delete(pkClasseServico);
            return ResponseEntity.ok("Classe de serviço desactivada com sucesso");
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao desactivar a classe de serviço: " + e.getMessage());
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{

            List<ClasseServico> ClasseServicos = this.classeServicoService.findByEstadoTrueOrderByDesignationAsc();
            return ResponseEntity.ok(ClasseServicos);
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao listar" + e.getMessage());
        }
    }
    @GetMapping("/findById/{pkClasseServico}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable UUID pkClasseServico){
        try {
            return ResponseEntity.ok(this.classeServicoService.findById(pkClasseServico));
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body(e.getMessage());
        }
    }

}
