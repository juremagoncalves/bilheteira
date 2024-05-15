package edu.ucan.bilheteira.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PedidoRepository extends JpaRepository<Pedido, UUID> {
    Pedido findByPkPedido(UUID pkPedido);

    Optional<Pedido> findByReferencia(String referencia);

    LocalDateTime findDataExpiracaoByPkPedido(UUID pkPedido);
}
