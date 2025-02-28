package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;



public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
}
