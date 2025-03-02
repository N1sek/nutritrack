package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Optional<Alimento> findByName(String name);

    List<Alimento> findByCaloriesLessThanEqual(double calorias);
}
