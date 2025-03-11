package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Alimento;
import org.nutritrack.nutritrack.service.AlimentoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/alimentos")
public class AlimentoController {

    @Autowired
    private AlimentoService alimentoService;

    @GetMapping("/buscar")
    public ResponseEntity<Page<Alimento>> buscarAlimentos(@RequestParam String nombre, Pageable pageable) {
        Page<Alimento> alimentos = alimentoService.buscarAlimentosPorNombre(nombre, pageable);
        return ResponseEntity.ok(alimentos);
    }

    // Guardar un alimento seleccionado desde OpenFoodDatabase
    @PostMapping("/guardar-seleccion")
    public ResponseEntity<Alimento> guardarAlimentoSeleccionado(@RequestBody Alimento alimento) {
        return ResponseEntity.ok(alimentoService.guardarAlimentoSeleccionado(alimento));
    }

    @PostMapping("/crear-alimento")
    public ResponseEntity<Alimento> crearAlimento(@RequestBody Alimento alimento, @RequestParam Long userId) {
        return ResponseEntity.ok(alimentoService.crearAlimento(alimento, userId));
    }

}

