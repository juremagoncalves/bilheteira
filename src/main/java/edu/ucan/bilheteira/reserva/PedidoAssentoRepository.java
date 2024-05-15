package edu.ucan.bilheteira.reserva;

import edu.ucan.bilheteira.assento.Assento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PedidoAssentoRepository extends JpaRepository<PedidoAssento, UUID> {

//    List<PedidoAssento>
    List<PedidoAssento> findByFkPedido(Pedido pedido);

    @Query("SELECT pa.fkAssento FROM PedidoAssento pa WHERE pa.fkPedido.pkPedido = :pedidoId")
    List<Assento> findAssentosByPedidoId(@Param("pedidoId") UUID pedidoId);
}
