package edu.ucan.bilheteira.programacao;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("programacao")
public class ProgramacaoController {
    @Autowired
    private ProgramacaoService programacaoService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid  @RequestBody Programacao programacao){
        return  ResponseEntity.ok(this.programacaoService.create(programacao));
    }

    @PutMapping("/update/{pkProgramacao}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid  @RequestBody Programacao programacao, @PathVariable UUID pkProgramacao){
        programacao.setPkProgramacao(pkProgramacao);
        try{
            return ResponseEntity.ok(this.programacaoService.update(programacao, pkProgramacao));
        }
        catch (CustomEntityNotFoundException e){
            return  ResponseEntity.badRequest().body("Erro ao actualizar a programação" +e.getMessage());
        }
    }

    @DeleteMapping("/{pkProgramacao}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID pkProgramacao){
        try{
            this.programacaoService.delete(pkProgramacao);
            return ResponseEntity.ok("Programação desactivada com sucesso");
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao desactivar a programação");
        }

    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{
            List<Programacao> lista = this.programacaoService.findAll();
            return ResponseEntity.ok(lista);
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Lista está vazia: " + c.getMessage());
        }
    }

    @GetMapping("/findById/{pkProgramacao}")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable  UUID pkProgramacao){
        try{
            return ResponseEntity.ok(this.programacaoService.findById(pkProgramacao));
        }
        catch (Exception c){
            return  ResponseEntity.badRequest().body("Erro ao buscar a programação com o id " + pkProgramacao);
        }
    }

    @GetMapping("/{pkRota}/{dataViagem}")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findByRotaAndDataViagem(@PathVariable  UUID pkRota,  @PathVariable LocalDate dataViagem){
        try{
            List<Programacao> lista = this.programacaoService.findByRotaAndDataViagem(pkRota, dataViagem);
            return ResponseEntity.ok(lista);
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Lista está vazia: " + c.getMessage());
        }
    }


}
