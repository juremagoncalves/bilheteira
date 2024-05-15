package edu.ucan.bilheteira.pagamento;

import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.assento.AssentoRepository;
import edu.ucan.bilheteira.assento.EstadoAssento;
import edu.ucan.bilheteira.bilhete.Bilhete;
import edu.ucan.bilheteira.bilhete.BilheteRepository;
import edu.ucan.bilheteira.reserva.*;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PagamentoService {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private PagamentoRepository pagamentoRepository;

    @Autowired
    private BilheteRepository bilheteRepository;

    @Autowired
    private PedidoAssentoRepository pedidoAssentoRepository;

    @Autowired
    private ReservaAssentoTemporarioService reservaAssentoTemporarioService;


    @Autowired
    private AssentoRepository assentoRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Transactional
    public void proccessarPagamento(PagamentoDTO pagamentoDTO) {
        Pedido pedido = pedidoRepository.findByReferencia(pagamentoDTO.getReferencia())
                .orElseThrow(() -> new EntityNotFoundException("Pedido não encontrado"));

        //Criação do Pagamento
        Pagamento pagamento = new Pagamento();
        pagamento.setPedido(pedido);
        pagamento.setTotalPago(pagamentoDTO.getTotalPago());
        pagamento.setDataCadastro(LocalDateTime.now());
        pagamento.setReferencia(pagamentoDTO.getReferencia());
        pagamento.setTipoPagamento(pagamentoDTO.getTipoPagamento());
        pagamentoRepository.save(pagamento);

        //Criação do Bilhete
        Bilhete bilhete = new Bilhete();
        bilhete.setCodigo(PedidoUtil.generateRandomReference());
        bilhete.setPedido(pedido);
        bilhete.setDataCadastro(LocalDateTime.now());
        bilheteRepository.save(bilhete);

        List<PedidoAssento> pedidoAssentos = pedidoAssentoRepository.findByFkPedido(pedido);

        pedidoAssentos.forEach(pedidoAssento -> {
            Assento assento = pedidoAssento.getFkAssento();
            assento.setEstado(EstadoAssento.INATIVO);
            assentoRepository.save(assento);
            simpMessagingTemplate.convertAndSend("/topic/seatUpdate", assento);
        });

        pedido.setEstadoPedido(EstadoPedido.Concluido);
        pedidoRepository.save(pedido);
    }
}
