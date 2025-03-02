package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.RecetaAlimento;
import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.model.Alimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaAlimentoRepository extends JpaRepository<RecetaAlimento, Long> {

    List<RecetaAlimento> findByReceta(Receta receta);

    List<RecetaAlimento> findByAlimento(Alimento alimento);
}
