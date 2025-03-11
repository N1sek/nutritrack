package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.*;
import org.nutritrack.nutritrack.repository.AlergenoRepository;
import org.nutritrack.nutritrack.repository.RecetaRepository;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.nutritrack.nutritrack.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class RecetaService {

    @Autowired
    UserRepository userRepository;

    @Autowired
    RecetaRepository recetaRepository;

    @Autowired
    AlimentoRepository alimentoRepository;

    @Autowired
    AlergenoRepository alergenoRepository;

    /**
     * Guarda una nueva receta en la base de datos, asignando correctamente los ingredientes y heredando los alérgenos.
     */
    @Transactional
    public Receta saveReceta(Receta receta, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        receta.setUser(user);

        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            throw new IllegalArgumentException("Una receta debe contener al menos un ingrediente.");
        }

        for (RecetaAlimento ingrediente : receta.getIngredientes()) {
            Alimento alimento = alimentoRepository.findById(ingrediente.getAlimento().getId())
                    .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

            ingrediente.setReceta(receta);
            ingrediente.setAlimento(alimento);
        }

        return recetaRepository.save(receta);
    }

    /**
     * Obtiene todas las recetas sin paginación, incluyendo sus ingredientes y alérgenos dinámicamente.
     */
    public List<Receta> obtenerTodasLasRecetas() {
        return recetaRepository.findAll();
    }

    /**
     * Obtiene todas las recetas paginadas, incluyendo sus ingredientes y alérgenos dinámicamente.
     */
    public Page<Receta> obtenerRecetasPaginadas(Pageable pageable) {
        return recetaRepository.findAll(pageable);
    }

    /**
     * Obtiene una receta por ID.
     */
    public Optional<Receta> getRecetaById(Long id) {
        return recetaRepository.findById(id);
    }

    /**
     * Obtiene todas las recetas creadas por un usuario específico.
     */
    public List<Receta> getRecetasByUser(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return recetaRepository.findByUser(user);
    }

    /**
     * Obtiene los alérgenos de una receta de forma dinámica, recorriendo sus ingredientes.
     */
    public Set<Alergeno> obtenerAlergenosDeReceta(Long recetaId) {
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        return receta.getIngredientes().stream()
                .flatMap(ingrediente -> ingrediente.getAlimento().getAlergenos().stream())
                .collect(Collectors.toSet());
    }

    /**
     * Filtra recetas excluyendo aquellas que contengan ciertos alérgenos.
     */
    public List<Receta> filtrarRecetasPorAlergenos(List<String> alergenoNombres) {
        return recetaRepository.findRecetasSinAlergenos(alergenoNombres);
    }

    /**
     * Filtra recetas por nombre, calorías, tipo de comida y alérgenos de manera dinámica.
     */
    public List<Receta> buscarRecetasFiltradas(
            String nombre, Double minCalorias, Double maxCalorias, String tipoComida, Boolean favoritos, Long userId, List<Long> alergenoIds) {

        if (alergenoIds != null && alergenoIds.isEmpty()) {
            alergenoIds = null;
        }

        // Convertir IDs a nombres de alérgenos
        List<String> alergenoNombres = null;
        if (alergenoIds != null) {
            alergenoNombres = alergenoIds.stream()
                    .map(id -> alergenoRepository.findById(id).map(Alergeno::getNombre).orElse(null))
                    .filter(Objects::nonNull)
                    .collect(Collectors.toList());
        }

        return recetaRepository.findRecetasFiltradas(nombre, minCalorias, maxCalorias, tipoComida, favoritos, userId, alergenoNombres);
    }



    /**
     * Elimina una receta solo si el usuario que intenta eliminarla es el creador.
     */
    @Transactional
    public void deleteReceta(Long recetaId, Long userId) {
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (!receta.getUser().getId().equals(userId)) {
            throw new RuntimeException("No tienes permiso para eliminar esta receta.");
        }

        recetaRepository.delete(receta);
    }
}
