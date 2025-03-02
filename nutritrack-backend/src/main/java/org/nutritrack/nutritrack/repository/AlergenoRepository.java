package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlergenoRepository extends JpaRepository<Alergeno, Long> {

    Optional<Alergeno> findByNombre(String nombre);
}
