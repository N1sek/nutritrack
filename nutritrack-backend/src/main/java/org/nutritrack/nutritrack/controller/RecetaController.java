package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.service.RecetaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/recetas")
public class RecetaController {

    @Autowired
    private RecetaService recetaService;

    // ✅ Obtener todas las recetas
    @GetMapping
    public ResponseEntity<List<Receta>> getAllRecetas() {
        return ResponseEntity.ok(recetaService.getAllRecetas());
    }

    // ✅ Obtener recetas de un usuario
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Receta>> getRecetasByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(recetaService.getRecetasByUser(userId));
    }

    // ✅ Buscar receta por ID
    @GetMapping("/{id}")
    public ResponseEntity<Receta> getRecetaById(@PathVariable Long id) {
        return recetaService.getRecetaById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Guardar una receta
    @PostMapping
    public ResponseEntity<Receta> saveReceta(@RequestBody Receta receta) {
        return ResponseEntity.ok(recetaService.saveReceta(receta));
    }

    // ✅ Eliminar una receta (solo el creador puede eliminarla)
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
