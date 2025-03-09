package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        return ResponseEntity.ok(recetaService.getAllRecetas());
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Receta>> getRecetasByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recetaService.getRecetasByUser(userId));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Long id) {
        return recetaService.getRecetaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/sin-alergenos")
    public ResponseEntity<List<Receta>> getRecetasSinAlergenos(@RequestParam List<String> alergenoNombres) {
        return ResponseEntity.ok(recetaService.getRecetasSinAlergenos(alergenoNombres));
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
