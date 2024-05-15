package edu.ucan.bilheteira.preco;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class PrecoViagemService {

    @Autowired
    PrecoViagemRepository precoViagemRepository;

    @Transactional
    public PrecoViagem create(PrecoViagem preco){
        return this.precoViagemRepository.save(preco);
    }

    public PrecoViagem findById(UUID pkPreco){
        Optional<PrecoViagem> rotaOptional = this.precoViagemRepository.findById(pkPreco);
        return rotaOptional.orElseThrow(()-> new CustomEntityNotFoundException("Rota não encontrada com o idÇ " + pkPreco));
    }

    @Transactional
    public PrecoViagem update(PrecoViagem newPreco, UUID pkPreco){
        PrecoViagem preco = findById(pkPreco);
        LocalDateTime createAtOriginal = preco.getDataCadastro();
//        preco.setEstado(true);
        preco.setDataCadastro(createAtOriginal);
        preco.setPreco(newPreco.getPreco());
        preco.setFkRota(newPreco.getFkRota() != null ? newPreco.getFkRota(): preco.getFkRota());
        preco.setFkTransporte(newPreco.getFkTransporte() != null ? newPreco.getFkTransporte() : preco.getFkTransporte());

        return this.precoViagemRepository.save(preco);
    }

//    public void delete(UUID pkRota) {
//        Rota rota = findById(pkRota);
//        rota.setEstado(false);
//        this.rotaRepository.save(rota);
//    }

    public List<PrecoViagem> findAll(){
        List<PrecoViagem> precos = precoViagemRepository.findAllOrderByPrecoAsc();
        if(precos.isEmpty()){
            throw new CustomEntityNotFoundException("Lista de rotas está Vazia");
        }

        return precos;
    }
    ////////////////////////////////////////////////////////////////
    public BigDecimal findPrecoByRotaAndTransporte(UUID pkRota, UUID pkTransporte){
        System.err.println("Chave" + pkRota);
        System.err.println("Chave T: " + pkTransporte);
        BigDecimal preco = precoViagemRepository.findPrecoByRotaAndTransporte(pkRota, pkTransporte);
        System.out.println("Preço retornado: " + preco);
        return preco;
    }

}
