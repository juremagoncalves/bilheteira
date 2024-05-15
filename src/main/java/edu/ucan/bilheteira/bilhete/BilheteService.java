package edu.ucan.bilheteira.bilhete;

import edu.ucan.bilheteira.reserva.Pedido;
import edu.ucan.bilheteira.reserva.PedidoRepository;
import edu.ucan.bilheteira.reserva.PedidoUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class BilheteService {
    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private BilheteRepository bilheteRepository;

    public Bilhete save(BilheteDTO bilheteDTO){
        Pedido pedido = pedidoRepository.findById(bilheteDTO.getPkPedido()).orElseThrow( () ->
            new  RuntimeException("Erro ao encontrar o pedido id"));
        Bilhete bilhete = new Bilhete();
        bilhete.setPedido(pedido);
        bilhete.setEstado(true);
        bilhete.setCodigo(PedidoUtil.generateRandomReference());
            return bilheteRepository.save(bilhete)  ;
    }

    public List<Bilhete> getBilhetesByPedidoId(UUID pedidoId) {
        return bilheteRepository.findByPedidoPkPedido(pedidoId);
    }

    public List<Bilhete> getBilhetesDoUsuario(UUID pkUsuario) {
        return bilheteRepository.findAllByUserId(pkUsuario);
    }
}
