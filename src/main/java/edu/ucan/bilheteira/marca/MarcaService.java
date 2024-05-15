package edu.ucan.bilheteira.marca;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;
    @Transactional
    public Marca create(Marca marca){
        marca.setEstado(true);
        return this.marcaRepository.save(marca);
    }

    public Marca findById(UUID pkMarca){
        Optional<Marca> marcaOptional = marcaRepository.findById(pkMarca);
        return marcaOptional.orElseThrow(() -> new CustomEntityNotFoundException("Marca não encotrada com o Id" + pkMarca));
    }

    public Marca update(Marca newMarca, UUID pkMarca){
        Marca marca = findById(pkMarca);
        LocalDateTime createAtOriginal = marca.getDataCadastro();
        marca.setDesignacao(newMarca.getDesignacao());
        marca.setEstado(true);
        marca.setDataCadastro(createAtOriginal);
        return this.marcaRepository.save(marca);
    }
    public void delete(UUID pkMarca){
        Marca marca = findById(pkMarca);
        marca.setEstado(false);
        this.marcaRepository.save(marca);
    }

    public List<Marca> findByEstadoTrueOrderByDesignationAsc(){
        List<Marca> marcas = this.marcaRepository.findByEstadoTrueOrderByDesignacaoAsc();
        if(marcas.isEmpty()){
            throw new CustomEntityNotFoundException("A lista de marcas activadas está vazia");
        }
        return marcas;
    }

}
