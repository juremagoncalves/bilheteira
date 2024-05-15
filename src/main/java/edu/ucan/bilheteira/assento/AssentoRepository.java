package edu.ucan.bilheteira.assento;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface AssentoRepository extends JpaRepository<Assento, UUID> {

    @Query(value = "SELECT * FROM assento WHERE estado IN ('DISPONIVEL', 'RESERVADO') ORDER BY numero_assento", nativeQuery = true)
    List<Assento> findbyEstadoDisponiveisEReservadosOrderByNumeroAssento();

    @Query("SELECT capacidade FROM Transporte t WHERE t.pkTransporte = :pkTransporte")
    Integer findCapacidadeByPkTransporte(@Param("pkTransporte") UUID pkTransporte);

    @Query("SELECT a FROM Assento a WHERE a.fkTransporte.pkTransporte = :pkTransporte ORDER BY a.numeroAssento")
    List<Assento> findByPkTransporte(@Param("pkTransporte") UUID pkTransporte);

//            findAssentoByPkTransporte



}
