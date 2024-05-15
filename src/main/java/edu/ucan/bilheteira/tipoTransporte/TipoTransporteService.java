package edu.ucan.bilheteira.tipoTransporte;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class TipoTransporteService {

    @Autowired
    private TipoTransporteRepository tipoTransporteRepository;

    @Transactional
    public TipoTransporte create(TipoTransporte tipoTransporte){
        tipoTransporte.setEstado(true);
        return this.tipoTransporteRepository.save(tipoTransporte);
    }

    public TipoTransporte findById (UUID pkTipoTransporte){
        Optional<TipoTransporte> tipoTransporteOpt = this.tipoTransporteRepository.findById(pkTipoTransporte);
        return tipoTransporteOpt.orElseThrow( () -> new CustomEntityNotFoundException("Tipo de transporte não encontrado com o id" +pkTipoTransporte));
    }

    @Transactional
    public TipoTransporte update(TipoTransporte newTipoTransporte, UUID pkTipoTransporte){
        TipoTransporte tipoTransporte = findById(pkTipoTransporte);
        LocalDateTime createAtOriginal = tipoTransporte.getDataCadastro();
        tipoTransporte.setDesignacao(newTipoTransporte.getDesignacao());
        tipoTransporte.setEstado(true);
        tipoTransporte.setDataCadastro(createAtOriginal);
        return  this.tipoTransporteRepository.save(tipoTransporte);
    }

    public void delete(UUID pktipoTransporte){
        TipoTransporte tipoTransporte = findById(pktipoTransporte);
        tipoTransporte.setEstado(false);
        this.tipoTransporteRepository.save(tipoTransporte);
    }

    public List<TipoTransporte> findByEstadoTrueOrderByDesignationAsc(){
        List<TipoTransporte> tipoTransportes = this.tipoTransporteRepository.findByEstadoTrueOrderByDesignacaoAsc();
        if (tipoTransportes.isEmpty()){
            throw  new CustomEntityNotFoundException("A lista está vazia");
        }
        return tipoTransportes;
    }
}
