package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.model.Alergeno;
import org.nutritrack.nutritrack.service.RecetaService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    private final RecetaService recetaService;

    public RecetaController(RecetaService recetaService) {
        this.recetaService = recetaService;
    }

    @GetMapping({"", "/"})
    public ResponseEntity<?> getAllRecetas(Pageable pageable) {
        Page<Receta> recetasPaginadas = recetaService.obtenerRecetasPaginadas(pageable);

        return ResponseEntity.ok(recetasPaginadas);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Long id) {
        Optional<Receta> receta = recetaService.getRecetaById(id);
        return receta.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Receta>> getRecetasByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recetaService.getRecetasByUser(userId));
    }

    // Obtiene los alergenos de una receta de forma dinamica a partir de sus ingredientes
    @GetMapping("/{recetaId}/alergenos")
    public ResponseEntity<Set<Alergeno>> getAlergenosDeReceta(@PathVariable Long recetaId) {
        return ResponseEntity.ok(recetaService.obtenerAlergenosDeReceta(recetaId));
    }

    @GetMapping("/sin-alergenos")
    public ResponseEntity<List<Receta>> getRecetasSinAlergenos(@RequestParam List<String> alergenoNombres) {
        return ResponseEntity.ok(recetaService.filtrarRecetasPorAlergenos(alergenoNombres));
    }


    @GetMapping("/buscar")
    public ResponseEntity<List<Receta>> buscarRecetas(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) Double minCalorias,
            @RequestParam(required = false) Double maxCalorias,
            @RequestParam(required = false) String tipoComida,
            @RequestParam(required = false) Boolean favoritos,
            @RequestParam(required = false) Long userId,
            @RequestParam(required = false) List<Long> alergenoIds) {

        List<Receta> recetas = recetaService.buscarRecetasFiltradas(nombre, minCalorias, maxCalorias, tipoComida, favoritos, userId, alergenoIds);
        return ResponseEntity.ok(recetas);
    }


    @PostMapping
    public ResponseEntity<Receta> saveReceta(@RequestBody Receta receta, @RequestParam Long userId) {
        return ResponseEntity.ok(recetaService.saveReceta(receta, userId));
    }

    //    Elimina la receta solo si la ha creado el usuario que solicita eliminarla
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteReceta(@PathVariable Long id, @RequestParam Long userId) {
        try {
            recetaService.deleteReceta(id, userId);
            return ResponseEntity.ok("Receta eliminada exitosamente.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(403).body(e.getMessage());
        }
    }
}
