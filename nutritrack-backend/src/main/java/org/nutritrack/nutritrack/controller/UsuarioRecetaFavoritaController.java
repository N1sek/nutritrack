package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.UsuarioRecetaFavorita;
import org.nutritrack.nutritrack.service.UsuarioRecetaFavoritaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/favoritos")
public class UsuarioRecetaFavoritaController {

    @Autowired
    UsuarioRecetaFavoritaService usuarioRecetaFavoritaService;


    @PutMapping("/toggle")
    public ResponseEntity<Map<String, Boolean>> toggleFavorito(@RequestParam Long userId, @RequestParam Long recetaId) {
        boolean isFavorito = usuarioRecetaFavoritaService.toggleFavorito(userId, recetaId);
        Map<String, Boolean> response = new HashMap<>();
        response.put("favorito", isFavorito);
        return ResponseEntity.ok(response);
    }


    @GetMapping("/{userId}")
    public ResponseEntity<List<UsuarioRecetaFavorita>> obtenerFavoritos(@PathVariable Long userId) {
        return ResponseEntity.ok(usuarioRecetaFavoritaService.obtenerFavoritos(userId));
    }
}

