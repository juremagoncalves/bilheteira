package edu.ucan.bilheteira.transporte;

import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.assento.AssentoRepository;
import edu.ucan.bilheteira.classeServico.ClasseServicoRepository;
import edu.ucan.bilheteira.classeServico.ClasseServicoService;
import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import edu.ucan.bilheteira.marca.MarcaService;
import edu.ucan.bilheteira.tipoTransporte.TipoTransporteService;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static edu.ucan.bilheteira.assento.EstadoAssento.*;

@Service
public class TransporteService {
    @Autowired
    private TransporteRepository transporteRepository;

    @Autowired
    private AssentoRepository assentoRepository;
    @Autowired
    MarcaService marcaService;
    @Autowired
    TipoTransporteService tipoTransporteService;

    @Autowired
    ClasseServicoService classeServicoService;
    @Autowired
    private ClasseServicoRepository classeServicoRepository;

    @Transactional
    public void create(Transporte transporte){
        transporte.setEstado(true);
        transporte.setFkMarca(this.marcaService.findById(transporte.getFkMarca().getPkMarca()));
        transporte.setFkTipoTransporte(this.tipoTransporteService.findById(transporte.getFkTipoTransporte().getPkTipoTransporte()));
        transporte.setFkClasseServico((this.classeServicoService.findById(transporte.getFkClasseServico().getPkClasseServico())));
        Transporte newTransporte =  this.transporteRepository.save(transporte);
        Integer capacidade = newTransporte.getCapacidade();
        System.err.println("a capacisade " + capacidade);
        for(int i = 1; i <= capacidade; i++){
            Assento assento = new Assento();
            assento.setFkTransporte(newTransporte);
            assento.setNumeroAssento("A" +i);
            assento.setEstado(DISPONIVEL);
            this.assentoRepository.save(assento);
        }
    }

//    @Transactional
//    public Transporte create(Transporte transporte){
//        transporte.setEstado(true);
//        transporte.setFkMarca(this.marcaService.findById(transporte.getFkMarca().getPkMarca()));
//        transporte.setFkTipoTransporte(this.tipoTransporteService.findById(transporte.getFkTipoTransporte().getPkTipoTransporte()));
//        transporte.setFkClasseServico((this.classeServicoService.findById(transporte.getFkClasseServico().getPkClasseServico())));
//        return this.transporteRepository.save(transporte);
//    }

    public Transporte findById(UUID pkTransporte){
        Optional<Transporte> transporteOpt = this.transporteRepository.findById(pkTransporte);
        return  transporteOpt.orElseThrow( () -> new CustomEntityNotFoundException("Transporte nao encontrado com o id:" + pkTransporte));
    }

    public Transporte update(Transporte newTransporte, UUID pkTransporte){
        Transporte transporte = findById(pkTransporte);
        LocalDateTime createAtOriginal = transporte.getDataCadastro();
        transporte.setEstado(true);
        transporte.setDataCadastro(createAtOriginal);
        transporte.setCapacidade(newTransporte.getCapacidade()!= null ? newTransporte.getCapacidade() : transporte.getCapacidade());
        transporte.setMatricula(newTransporte .getMatricula()!= null ? newTransporte.getMatricula() : transporte.getMatricula());
        transporte.setFkClasseServico(newTransporte.getFkClasseServico() != null ? newTransporte.getFkClasseServico() : transporte.getFkClasseServico());
        transporte.setFkTipoTransporte(newTransporte.getFkTipoTransporte() != null ? newTransporte.getFkTipoTransporte() : transporte.getFkTipoTransporte());
        transporte.setFkMarca(newTransporte.getFkMarca() != null ? newTransporte.getFkMarca() : transporte.getFkMarca());
        return this.transporteRepository.save(transporte);

    }

    public void delete(UUID pkTransporte){
        Transporte transporte = findById(pkTransporte);
        transporte.setEstado(false);
        this.transporteRepository.save(transporte);
    }

//    public void estadoReservado(){
//        Assento assento = new Assento();
//        assento.setEstado(RESERVADO);
//        assentoRepository.save(assento);
//
//    }
//    public void estadoDisponivel(){
//        Assento assento = new Assento();
//        assento.setEstado(DISPONIVEL);
//        assentoRepository.save(assento);
//
//    }
//
//    public void estadoInactivo(){
//        Assento assento = new Assento();
//        assento.setEstado(INATIVO);
//        assentoRepository.save(assento);
//
//    }

    public List<Transporte> findAllWithStatusTrueOrderByMatriculaAsc(){
        List<Transporte> transportes = this.transporteRepository.findByEstadoTrueOrderByMatriculaAsc();
        if (transportes.isEmpty()){
            throw new CustomEntityNotFoundException("A Lista esta vazia");
        }
        return transportes;
    }

    public List<Transporte> buscarTransportesPorIdClasseServico(UUID idClasseServico) {

         return transporteRepository.findByFkClasseServicoPkClasseServico(idClasseServico);
    }

}
