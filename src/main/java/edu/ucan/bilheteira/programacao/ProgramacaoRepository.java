package edu.ucan.bilheteira.programacao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;
@Repository
public interface ProgramacaoRepository extends JpaRepository<Programacao, UUID> {
    List<Programacao> findByEstadoTrueOrderByDataViagemAsc();
    @Query("SELECT p FROM Programacao p WHERE p.fkRota.pkRota = :pkRota AND p.dataViagem = :dataViagem ")
    List<Programacao>findByRotaAndDataViagem(@Param("pkRota") UUID pkRota, @Param("dataViagem") LocalDate dataViagem);

}
