package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.programacao.Programacao;
import edu.ucan.bilheteira.programacao.ProgramacaoRepository;
import edu.ucan.bilheteira.user.User;
import edu.ucan.bilheteira.user.UserRepositoty;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;
    @Autowired
    private UserRepositoty userRepositoty;

    @Autowired
    private ProgramacaoRepository programacaoRepository;
    @Autowired
    private  PedidoAssentoRepository pedidoAssentoRepository;

//    public Pedido create(PedidoDTO pedidoDTO){
//        User user = userRepositoty.findById(pedidoDTO.getPkUser()).orElseThrow( () -> new RuntimeException("Usuário não encontrado"));
//        Programacao programacao = programacaoRepository.findById(pedidoDTO.getPkProgramacao()).orElseThrow( () ->new RuntimeException("Programação não encontrada"));
//
//        Pedido p = new Pedido();
//        p.setUser(user);
//        p.setProgramacao(programacao);
//        p.setEntidade(pedidoDTO.getEntidade());
//        p.setDataExpiracao(LocalDateTime.now().plusMinutes(1));
//        p.setReferencia(PedidoUtil.generateRandomReference());
//        p.setEstadoPedido(EstadoPedido.PENDENTE);
//        p.setTotalPago(pedidoDTO.getTotalPago());
//        p.setEstado(true);
//        return pedidoRepository.save(p);
//    }
public Pedido create(PedidoDTO pedidoDTO){
    User user = userRepositoty.findById(pedidoDTO.getPkUser()).orElseThrow( () -> new RuntimeException("Usuário não encontrado"));
    Programacao programacao = programacaoRepository.findById(pedidoDTO.getPkProgramacao()).orElseThrow( () ->new RuntimeException("Programação não encontrada"));

    Pedido p = new Pedido();
    p.setUser(user);
    p.setProgramacao(programacao);
    p.setEntidade(pedidoDTO.getEntidade());
    p.setDataExpiracao(LocalDateTime.now().plusMinutes(5));
    p.setReferencia(PedidoUtil.generateRandomReference());
    p.setEstadoPedido(EstadoPedido.PENDENTE);
    p.setTotalPago(pedidoDTO.getTotalPago());

    p.setEstado(true);
    p =  pedidoRepository.save(p);

    List<Assento> assentosSelecionados = pedidoDTO.getAssentosSelecionados();
    for (Assento assento : assentosSelecionados) {
        PedidoAssento pedidoAssento = new PedidoAssento();
        pedidoAssento.setFkPedido(p);
        pedidoAssento.setFkAssento(assento);
        pedidoAssentoRepository.save(pedidoAssento);
    }

    return p;
}

    public Pedido getPedidoById(UUID pedidoId) {

    return pedidoRepository.findByPkPedido(pedidoId);
    }

    public LocalDateTime findDataExpiracaoByPkPedido(UUID pkPedido) {
        return pedidoRepository.findDataExpiracaoByPkPedido(pkPedido);
    }


}
