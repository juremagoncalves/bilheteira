package edu.ucan.bilheteira.localidade.provincia;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;

@RestController
@RequestMapping("provincia")
public class ProvinciaController {

    @Autowired
    private ProvinciaService provinciaService;
    @PostMapping("/")
    public ResponseEntity<?> create(@RequestParam("file") MultipartFile file){
        try {
            this.provinciaService.saveProvincia(file);
            return ResponseEntity.ok(Map.of("Message", "Provincia salva com sucesso"));
        }
        catch (Exception e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(this.provinciaService.findAll());
    }


}
