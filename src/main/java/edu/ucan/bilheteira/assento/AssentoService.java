package edu.ucan.bilheteira.assento;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static edu.ucan.bilheteira.assento.EstadoAssento.INATIVO;

@Service
public class AssentoService {

    @Autowired
    private AssentoRepository assentoRepository;



    public Assento findById(UUID pkAssento){
        Optional<Assento> assentoOpt = this.assentoRepository.findById(pkAssento);
        return assentoOpt.orElseThrow(() -> new RuntimeException("Erro ao buscar o assento com o id" + pkAssento));
    }
    @Transactional
    public Assento update(Assento newAssento, UUID pkAssento){
        Assento assento = findById(pkAssento);
        LocalDateTime createAtOriginal = assento.getDataCadastro();
        assento.setFkTransporte(assento.getFkTransporte());
        assento.setDataCadastro(createAtOriginal);
        assento.setEstado(newAssento.getEstado());
        assento.setNumeroAssento(newAssento.getNumeroAssento());
        return this.assentoRepository.save(assento);
    }

    public void delete(UUID pkAssento){
        Assento assento = findById(pkAssento);
        assento.setEstado(INATIVO);
        this.assentoRepository.save(assento);
    }


    public List<Assento> findAll(){
        return this.assentoRepository.findbyEstadoDisponiveisEReservadosOrderByNumeroAssento();
    }
    public List<Assento> findAssentoByPkTransporte(UUID pkTransporte){
        return this.assentoRepository.findByPkTransporte(pkTransporte);
    }

    public Assento updateSeatState(UUID pkAssento, EstadoAssento newEstado){
        Assento assento = findById(pkAssento);
        assento.setEstado(newEstado);
        return  assentoRepository.save(assento);
    }
}
