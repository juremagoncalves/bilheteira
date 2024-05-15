package edu.ucan.bilheteira.scheduledTask;


import edu.ucan.bilheteira.assento.Assento;
import edu.ucan.bilheteira.assento.AssentoRepository;
import edu.ucan.bilheteira.assento.EstadoAssento;
import edu.ucan.bilheteira.reserva.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class PedidoExpiradoTarefa {
    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private ReservaAssentoTemporarioService reservaAssentoTemporarioService;

    @Autowired
    private PedidoAssentoRepository pedidoAssentoRepository;

    @Autowired
    private AssentoRepository assentoRepository;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    // Roda a cada minuto
    // @Scheduled(cron = "0 * * * * ?")
    @Scheduled(cron = "*/30 * * * * *")
    public void pedidosExpirados() {
        List<Pedido> pedidosExpirados = pedidoRepository.findAll().stream()
                .filter(pedido -> pedido.getDataExpiracao().isBefore(LocalDateTime.now()))
                .collect(Collectors.toList());

        pedidosExpirados.forEach(pedido -> {
            if (!pedido.getEstadoPedido().equals(EstadoPedido.Concluido)
            && !pedido.getEstadoPedido().equals(EstadoPedido.REJEITADO)
            ) {
                pedido.setEstadoPedido(EstadoPedido.REJEITADO);
                pedidoRepository.save(pedido);

                List<PedidoAssento> pedidoAssentos = pedidoAssentoRepository.findByFkPedido(pedido);

                pedidoAssentos.forEach(pedidoAssento -> {
                    Assento assento = pedidoAssento.getFkAssento();
                    assento.setEstado(EstadoAssento.DISPONIVEL);
                    assentoRepository.save(assento);
                    simpMessagingTemplate.convertAndSend("/topic/seatUpdate", assento);
                });
            }

        });
        //pedidoRepository.deleteAll(pedidosExpirados);

        System.out.println("CronJob Executando...");
    }
}
