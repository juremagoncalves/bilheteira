package edu.ucan.bilheteira.preco;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("preco")
public class PrecoViagemController {
    @Autowired
    private PrecoViagemService precoViagemService;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> create(@Valid @RequestBody PrecoViagem p){
        try{
            System.err.println("Entrei aqui");
            return ResponseEntity.ok(precoViagemService.create(p));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao criar o preço viagem");
        }
    }


    @PutMapping("/update/{pkPreco}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody PrecoViagem preco, @PathVariable UUID pkPreco){
        preco.setPkPrecoViagem(pkPreco);
        try{
            return ResponseEntity.ok(this.precoViagemService.update(preco, pkPreco));
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao actualizar os dados: " + c.getMessage());
        }

    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{
            List<PrecoViagem> precoViagemList = this.precoViagemService.findAll();
            return ResponseEntity.ok(precoViagemList);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body("Erro ao listar os dados");
        }
    }

    @GetMapping("/{pkRota}/{pkTransporte}")
    public ResponseEntity<?> findPrecoByRotaAndTransporte(@PathVariable UUID pkRota, @PathVariable UUID pkTransporte){
        try{
            BigDecimal preco = precoViagemService.findPrecoByRotaAndTransporte(pkRota, pkTransporte);
            return ResponseEntity.ok(preco);
//            return ResponseEntity.ok(precoViagemService.findPrecoByRotaAndTransporte(pkRota, pkTransporte));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao buscar o preco " + e.getMessage());
        }
    }
}
