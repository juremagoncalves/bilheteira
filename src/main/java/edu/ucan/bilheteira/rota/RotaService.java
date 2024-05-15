package edu.ucan.bilheteira.rota;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import edu.ucan.bilheteira.preco.PrecoViagem;
import edu.ucan.bilheteira.preco.PrecoViagemRepository;
import edu.ucan.bilheteira.transporte.Transporte;
import edu.ucan.bilheteira.transporte.TransporteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RotaService {

    @Autowired
    private RotaRepository rotaRepository;

    @Autowired
    private PrecoViagemRepository precoViagemRepository;

    @Autowired
    private TransporteRepository transporteRepository;

    @Transactional
    public Rota create(Rota rota){
        rota.setEstado(true);
       return this.rotaRepository.save(rota);
    }

//    @Transactional
//    public void create(Rota rota, PrecoViagem preco){
//        rota.setEstado(true);
//        Rota r = this.rotaRepository.save(rota);
//        //PrecoViagem preco = new PrecoViagem();
//        preco.setFkRota(r);
//        precoViagemRepository.save(preco);
//
//    }

    public Rota findById(UUID pkRota){
        Optional<Rota> rotaOptional = this.rotaRepository.findById(pkRota);
        return rotaOptional.orElseThrow(()-> new CustomEntityNotFoundException("Rota não encontrada com o idÇ " + pkRota));
    }

    @Transactional
    public Rota update(Rota newRota, UUID pkRota){
        Rota rota = findById(pkRota);
        LocalDateTime createAtOriginal = rota.getDataCadastro();
        rota.setEstado(true);
        rota.setDataCadastro(createAtOriginal);
//        rota.setPreco(newRota.getPreco());

        //rota.setFkTransporte(newRota.getFkTransporte() != null ? newRota.getFkTransporte() : rota.getFkTransporte());
        rota.setFkProvinciaDestino(newRota.getFkProvinciaDestino() != null ? newRota.getFkProvinciaDestino(): rota.getFkProvinciaDestino());
        rota.setFkProvinciaOrigem(newRota.getFkProvinciaOrigem() != null ? newRota.getFkProvinciaOrigem() : rota.getFkProvinciaOrigem());

        return this.rotaRepository.save(rota);
    }

    public void delete(UUID pkRota) {
        Rota rota = findById(pkRota);
        rota.setEstado(false);
        this.rotaRepository.save(rota);
    }

    public List<Rota> findAll(){
        List<Rota> rotas = this.rotaRepository.findByEstadoTrue();
        if(rotas.isEmpty()){
           throw new CustomEntityNotFoundException("Lista de rotas está Vazia");
        }

        return rotas;
    }

    public List<RotaTransportePrecoDTO> getAllRotasWithTransporteAndPreco() {
        List<Rota> rotas = rotaRepository.findAll();
        List<RotaTransportePrecoDTO> dtos = new ArrayList<>();

        for (Rota rota : rotas) {
            if (rota.isEstado()) {
                PrecoViagem preco = precoViagemRepository.findByFkRota(rota);
                if (preco != null) {
                    Transporte transporte = preco.getFkTransporte();

                    RotaTransportePrecoDTO dto = new RotaTransportePrecoDTO();
                    dto.setRota(rota);
                    dto.setTransporte(transporte);
                    dto.setPrecoViagem(preco);

                    dtos.add(dto);
                }
            }
        }

        return dtos;
    }



}
