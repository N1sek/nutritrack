package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.UsuarioRecetaFavorita;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.model.Receta;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UsuarioRecetaFavoritaRepository extends JpaRepository<UsuarioRecetaFavorita, Long> {
    List<UsuarioRecetaFavorita> findByUser(User user);
    Optional<UsuarioRecetaFavorita> findByUserAndReceta(User user, Receta receta);
    void deleteByUserAndReceta(User user, Receta receta);
}
