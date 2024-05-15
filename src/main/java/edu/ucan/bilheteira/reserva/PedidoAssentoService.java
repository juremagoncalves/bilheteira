package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.assento.AssentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class PedidoAssentoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private AssentoRepository assentoRepository;

    @Autowired
    private PedidoAssentoRepository pedidoAssentoRepository;

    public PedidoAssento create (PedidoAssentoDTO pedidoAssentoDTO){
        Assento assento = assentoRepository.findById(pedidoAssentoDTO.getPkAssento()).orElseThrow( ()-> new RuntimeException("Id do assento não encontrado"));
        Pedido pedido = pedidoRepository.findById(pedidoAssentoDTO.getPkPedido()).orElseThrow( () -> new RuntimeException("Id não encontrado"));
        PedidoAssento p = new PedidoAssento();
        p.setFkAssento(assento);
        p.setFkPedido(pedido);
        return pedidoAssentoRepository.save(p);
    }

    public List<Assento> findAssentosByPedidoId(UUID pkPedido){
        return pedidoAssentoRepository.findAssentosByPedidoId(pkPedido);
    }
}
