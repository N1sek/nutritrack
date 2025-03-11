package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Alimento;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;


public interface AlimentoRepository extends JpaRepository<Alimento, Long> {
    Optional<Alimento> findByName(String name);

    @Query(
            value = "SELECT A FROM Alimento A ", countQuery = "SELECT count(*) FROM Alimento A"
    )
    Page<Alimento> findByNameContainingIgnoreCase(String name, Pageable pageable);
}
