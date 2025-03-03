package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.Alergeno;
import org.nutritrack.nutritrack.service.AlergenoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/alergenos")
public class AlergenoController {

    @Autowired
    private AlergenoService alergenoService;

    @GetMapping
    public ResponseEntity<List<Alergeno>> getAllAlergenos() {
        return ResponseEntity.ok(alergenoService.getAllAlergenos());
    }

    @GetMapping("/{id}")
    public ResponseEntity<Alergeno> getAlergenoById(@PathVariable Long id) {
        return alergenoService.getAlergenoById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Alergeno> saveAlergeno(@RequestBody Alergeno alergeno) {
        return ResponseEntity.ok(alergenoService.saveAlergeno(alergeno));
    }
}
