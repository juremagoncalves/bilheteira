package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.exceptioes.CustomEntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class ReservaAssentoTemporarioService {

    @Autowired
    private ReservaAssentoTemporariaRepository reservaAssentoTemporariaRepository;

    public ReservaAssentoTemporaria save( ReservaAssentoTemporaria r){


        return reservaAssentoTemporariaRepository.save(r) ;
    }

    public List<ReservaAssentoTemporaria> findAll(){
        List<ReservaAssentoTemporaria> lista = reservaAssentoTemporariaRepository.findAll();
        if (lista.isEmpty()){
            throw  new CustomEntityNotFoundException("LISTA VAZIA");
        }
        return lista;
    }
    @Transactional
    public void delete(UUID pkAssento){
        this.reservaAssentoTemporariaRepository.deleteReservaAssentoTemporarioByPkAssento(pkAssento);
    }

//    public UUID getUserIdLoggedIn() {
//        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
//        if (authentication != null && authentication.getPrincipal() instanceof UserPrincipal) {
//            UserPrincipal userPrincipal = (UserPrincipal) authentication.getPrincipal();
//            return userPrincipal
//        } else {
//            return null;
//        }
//    }
}
