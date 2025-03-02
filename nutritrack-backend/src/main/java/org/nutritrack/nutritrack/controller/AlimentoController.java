package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Alimento;
import org.nutritrack.nutritrack.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    // ✅ Obtener todos los alimentos
    @GetMapping
    public ResponseEntity<List<Alimento>> getAllAlimentos() {
        return ResponseEntity.ok(alimentoService.getAllAlimentos());
    }

    // ✅ Buscar alimento por ID
    @GetMapping("/{id}")
    public ResponseEntity<Alimento> getAlimentoById(@PathVariable Long id) {
        return alimentoService.getAlimentoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    // ✅ Buscar alimento por nombre
    @GetMapping("/buscar")
    public ResponseEntity<Optional<Alimento>> getAlimentoByName(@RequestParam String name) {
        return ResponseEntity.ok(alimentoService.getAlimentoByName(name));
    }

    // ✅ Guardar un alimento
    @PostMapping
    public ResponseEntity<Alimento> saveAlimento(@RequestBody Alimento alimento) {
        return ResponseEntity.ok(alimentoService.saveAlimento(alimento));
    }
}
