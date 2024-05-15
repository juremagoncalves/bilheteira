package edu.ucan.bilheteira.reserva;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ReservaAssentoTemporariaRepository extends JpaRepository<ReservaAssentoTemporaria, UUID> {
//    @Query("SELECT capacidade FROM Transporte t WHERE t.pkTransporte = :pkTransporte")
//    Integer findCapacidadeByPkTransporte(@Param("pkTransporte") UUID pkTransporte);

    @Modifying
    @Query("DELETE  FROM ReservaAssentoTemporaria r WHERE r.assento.pkAssento = :pkAssento ")
    void deleteReservaAssentoTemporarioByPkAssento(@Param("pkAssento") UUID pkAssento);


}
