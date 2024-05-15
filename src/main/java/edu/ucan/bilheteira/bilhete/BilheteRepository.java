package edu.ucan.bilheteira.bilhete;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface BilheteRepository extends JpaRepository<Bilhete, UUID> {
//    List<Bilhete> findByPedidoPkPedido(UUID pkPedido);

    @Query("SELECT b FROM Bilhete b WHERE b.pedido.pkPedido = :pedidoId")
    List<Bilhete> findByPedidoPkPedido(@Param("pedidoId") UUID pedidoId);
    @Query("SELECT b FROM Bilhete b WHERE b.pedido.user.pkUsuario = :userId")
    List<Bilhete> findAllByUserId(@Param("userId") UUID userId);



}
