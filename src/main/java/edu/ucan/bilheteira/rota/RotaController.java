package edu.ucan.bilheteira.rota;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import edu.ucan.bilheteira.localidade.provincia.ProvinciaRepository;
import edu.ucan.bilheteira.transporte.TransporteRepository;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("rota")
public class RotaController {

    @Autowired
    private RotaService rotaService;
    @Autowired
    private ProvinciaRepository provinciaRepository;
    @Autowired
    private TransporteRepository transporteRepository;

    @PostMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> createRota(@Valid @RequestBody Rota rota){
        try{
            return ResponseEntity.ok(this.rotaService.create(rota));
        }
        catch (Exception e){
            return ResponseEntity.badRequest().body("Erro ao cadastrar a rota" + e.getMessage());
        }
    }



//    @PostMapping("/")
//    @PreAuthorize("hasRole('ADMIN')")
//    public ResponseEntity<?> createRota(@Valid @RequestBody RotaDTO rotaDTO){
//        try{
//
//            Provincia provinciaOrigem = provinciaRepository.findById(UUID.fromString(rotaDTO.getFkProvinciaOrigemId()))
//                    .orElseThrow(() -> new CustomEntityNotFoundException("Provincia de origem não encontrada com o ID: " + rotaDTO.getFkProvinciaOrigemId()));
//
//            Provincia provinciaDestino = provinciaRepository.findById(UUID.fromString(rotaDTO.getFkProvinciaDestinoId()))
//                    .orElseThrow(() -> new CustomEntityNotFoundException("Provincia de destino não encontrada com o ID: " + rotaDTO.getFkProvinciaDestinoId()));
//
//            Transporte transporte = transporteRepository.findById(UUID.fromString(rotaDTO.getFkTransporteId()))
//                    .orElseThrow(() -> new CustomEntityNotFoundException("Transporte não encontrado com o ID: " + rotaDTO.getFkTransporteId()));
//
//            Rota rota = new Rota();
//            rota.setFkProvinciaOrigem(provinciaOrigem);
//            rota.setFkProvinciaDestino(provinciaDestino);
//
//            PrecoViagem precoViagem = new PrecoViagem();
//            precoViagem.setFkTransporte(transporte);
//            precoViagem.setPreco(rotaDTO.getPreco());
//
//            rotaService.create(rota, precoViagem);
//            //return ResponseEntity.ok("Sucesso");
//            return ResponseEntity.ok(Map.of("message", "Sucesso"));
//        }
//        catch (Exception e){
//            return ResponseEntity.badRequest().body("Erro ao cadastrar a rota" + e.getMessage());
//        }
//
//    }

    @PutMapping("/update/{pkRota}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> update(@Valid @RequestBody Rota rota, @PathVariable UUID pkRota){
        rota.setPkRota(pkRota);
        try{
            return ResponseEntity.ok(this.rotaService.update(rota, pkRota));
        }
        catch (CustomEntityNotFoundException c){
            return ResponseEntity.badRequest().body("Erro ao actualizar os dados: " + c.getMessage());
        }

    }
    @DeleteMapping("/{pkRota}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> delete(@PathVariable UUID pkRota){
        try{
            this.rotaService.delete(pkRota);
            return ResponseEntity.ok("Rota desactivada com sucesso");
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Erro ao desactivar a rota");
        }
    }

    @GetMapping("/findById/{pkRota}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> findById(@PathVariable UUID pkRota){
        try{
            return ResponseEntity.ok(this.rotaService.findById(pkRota));
        }
        catch(Exception e){
            return ResponseEntity.badRequest().body("Erro ao buscar a rota");
        }
    }

    @GetMapping()
    public ResponseEntity<?> findAll(){
        try{
            List<Rota> rotas = this.rotaService.findAll();
            return ResponseEntity.ok(rotas);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body("Erro ao listar os dados");
        }
    }

    @GetMapping("/getAllRotas")
    public ResponseEntity<?> getAllRotasWithTransporteAndPreco(){
        try{
            List<RotaTransportePrecoDTO> rotas = this.rotaService.getAllRotasWithTransporteAndPreco();
            return ResponseEntity.ok(rotas);
        }
        catch (Exception e){
            return  ResponseEntity.badRequest().body("Erro ao listar os dados: " + e.getMessage());
        }
    }





}
