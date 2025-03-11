package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RecetaRepository extends JpaRepository<Receta, Long> {

    List<Receta> findByUser(User user);

    @EntityGraph(attributePaths = {"ingredientes.alimento.alergenos"})
    Page<Receta> findAll(Pageable pageable);

    // Filtra aquellos que contengan los alergenos especificados
    @Query("SELECT DISTINCT r FROM Receta r " +
            "WHERE NOT EXISTS (" +
            "   SELECT 1 FROM RecetaAlimento ra " +
            "   JOIN ra.alimento a " +
            "   JOIN a.alergenos al " +
            "   WHERE ra.receta = r AND al.nombre IN :alergenoNombres" +
            ")")
    List<Receta> findRecetasSinAlergenos(@Param("alergenoNombres") List<String> alergenoNombres);





    // Esto no funca
    @Query("SELECT DISTINCT r FROM Receta r " +
            "LEFT JOIN r.ingredientes i " +
            "LEFT JOIN i.alimento a " +
            "LEFT JOIN a.alergenos al " +
            "WHERE (:nombre IS NULL OR LOWER(r.nombre) LIKE LOWER(CONCAT('%', :nombre, '%'))) " +
            "AND (:tipoComida IS NULL OR r.tipoComida = :tipoComida) " +
            "AND (:favoritos IS NULL OR :favoritos = false OR :userId IS NOT NULL AND r IN " +
            "     (SELECT f.receta FROM UsuarioRecetaFavorita f WHERE f.user.id = :userId)) " +
            "AND (:minCalorias IS NULL OR (SELECT COALESCE(SUM(ra.alimento.calories), 0) FROM RecetaAlimento ra WHERE ra.receta = r) >= :minCalorias) " +
            "AND (:maxCalorias IS NULL OR (SELECT COALESCE(SUM(ra.alimento.calories), 0) FROM RecetaAlimento ra WHERE ra.receta = r) <= :maxCalorias) " +
            "AND (:alergenoNombres IS NULL OR NOT EXISTS ( " +
            "     SELECT 1 FROM RecetaAlimento ra2 " +
            "     JOIN ra2.alimento a2 " +
            "     JOIN a2.alergenos al2 " +
            "     WHERE ra2.receta = r AND al2.nombre IN :alergenoNombres)) " +
            "GROUP BY r.id")
    List<Receta> findRecetasFiltradas(
            @Param("nombre") String nombre,
            @Param("minCalorias") Double minCalorias,
            @Param("maxCalorias") Double maxCalorias,
            @Param("tipoComida") String tipoComida,
            @Param("favoritos") Boolean favoritos,
            @Param("userId") Long userId,
            @Param("alergenoNombres") List<String> alergenoNombres
    );

}
