package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByUser(User user);

    @Query("SELECT r FROM Receta r " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM r.alergenos a WHERE a.nombre IN :alergenoNombres" +
            ")")
    List<Receta> findRecetasSinAlergenos(@Param("alergenoNombres") List<String> alergenoNombres);



    @Query("SELECT r FROM Receta r " +
            "WHERE (:nombre IS NULL OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:minCalorias IS NULL OR NOT EXISTS " +
            "     (SELECT 1 FROM r.ingredientes i WHERE i.alimento.calories < :minCalorias)) " +
            "AND (:maxCalorias IS NULL OR NOT EXISTS " +
            "     (SELECT 1 FROM r.ingredientes i WHERE i.alimento.calories > :maxCalorias)) " +
            "AND (:tipoComida IS NULL OR r.tipoComida = :tipoComida) " +
            "AND (:favoritos = false OR :userId IS NOT NULL AND r IN " +
            "     (SELECT f.receta FROM UsuarioRecetaFavorita f WHERE f.user.id = :userId)) " +
            "AND NOT EXISTS (SELECT 1 FROM r.alergenos a WHERE a.id IN :alergenoIds)")
    List<Receta> findRecetasFiltradas(
            @Param("nombre") String nombre,
            @Param("minCalorias") Double minCalorias,
            @Param("maxCalorias") Double maxCalorias,
            @Param("tipoComida") String tipoComida,
            @Param("favoritos") Boolean favoritos,
            @Param("userId") Long userId,
            @Param("alergenoIds") List<Long> alergenoIds
    );



}
