package edu.ucan.bilheteira.pagamento;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("pagamento")
public class PagamentoController {
    @Autowired
    private PagamentoService pagamentoService;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;
    // Endpoint para criar um novo pagamento
    @PostMapping("/")
    public ResponseEntity<?> createPagamento(@RequestBody PagamentoDTO pagamentoDTO) {
        try {
            this.pagamentoService.proccessarPagamento(pagamentoDTO);
//            simpMessagingTemplate.convertAndSend("/topic/pagamento", pagamentoDTO);
            return new ResponseEntity<>(HttpStatus.CREATED);
        } catch (EntityNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/")
    public ResponseEntity<?> findAll() {
        List<Pagamento> pagamentos = new ArrayList<Pagamento>();

        pagamentoRepository.findAll().forEach(pagamentos::add);

        if (pagamentos.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        return new ResponseEntity<>(pagamentos, HttpStatus.OK);
    }
}
