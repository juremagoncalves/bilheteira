package edu.ucan.bilheteira.classeServico;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface ClasseServicoRepository extends JpaRepository<ClasseServico, UUID> {
    List<ClasseServico> findByEstadoTrueOrderByDesignacaoAsc();

}
