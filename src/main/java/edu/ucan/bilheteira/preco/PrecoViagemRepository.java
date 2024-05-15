package edu.ucan.bilheteira.preco;

import edu.ucan.bilheteira.rota.Rota;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

@Repository
public interface PrecoViagemRepository extends JpaRepository<PrecoViagem, UUID> {

//    List<PrecoViagem> findByFkRota(Rota rota);
    @Query("SELECT p FROM PrecoViagem p WHERE p.fkRota = ?1")
    PrecoViagem findByFkRota(Rota rota);
    @Query("SELECT pv FROM PrecoViagem pv ORDER BY pv.preco ASC")
    List<PrecoViagem> findAllOrderByPrecoAsc();

    @Query("SELECT pv.preco FROM PrecoViagem pv " +
            "WHERE pv.fkRota.pkRota = :rotaId AND pv.fkTransporte.pkTransporte = :transporteId")
    BigDecimal findPrecoByRotaAndTransporte(@Param("rotaId") UUID rotaId, @Param("transporteId") UUID transporteId);

}
