package edu.ucan.bilheteira.bilhete;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("bilhete/")
public class BilheteController {

    @Autowired
    private BilheteService biilheteService;

    @Autowired
    private BilheteRepository bilheteRepository;

    @PostMapping("/")
    public ResponseEntity<?> create(@RequestBody BilheteDTO bilheteDTO ){
        try{
            return ResponseEntity.status(HttpStatus.CREATED).body(biilheteService.save(bilheteDTO));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao criar o pedido: " + e.getMessage());
        }
    }

    @GetMapping("bi/{pkPedido}")
    public ResponseEntity<?>getBilhetesByPedidoId(@PathVariable UUID pkPedido){
        try{
            return ResponseEntity.ok(biilheteService.getBilhetesByPedidoId(pkPedido));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erro ao listar");
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<Bilhete> bilhetes = new ArrayList<Bilhete>();

        bilheteRepository.findAll().forEach(bilhetes::add);

        if (bilhetes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(bilhetes, HttpStatus.OK);
    }

    @GetMapping("user/{userId}")
    public ResponseEntity<?> findByUserId(@PathVariable("userId") UUID userId) {
        List<Bilhete> bilhetes = new ArrayList<Bilhete>();

        bilheteRepository.findAllByUserId(userId).forEach(bilhetes::add);

        if (bilhetes.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(bilhetes, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Bilhete> findById(@PathVariable("id") UUID id) {
        Optional<Bilhete> bilhete = bilheteRepository.findById(id);
        return bilhete.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
//    @PostMapping("/")
//    public ResponseEntity<?> create(@Valid @RequestBody PedidoDTO pedidoDTO){
//        try{
//            return ResponseEntity.status(HttpStatus.CREATED).body(pedidoService.create(pedidoDTO));
//        }
//        catch (Exception e){
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
//                    .body("Erro ao criar o pedido: " + e.getMessage());
//        }
//    }
}
