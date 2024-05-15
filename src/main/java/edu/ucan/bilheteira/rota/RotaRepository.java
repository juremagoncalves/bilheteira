package edu.ucan.bilheteira.rota;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface RotaRepository  extends JpaRepository<Rota, UUID> {


    List<Rota> findByEstadoTrue();
}
