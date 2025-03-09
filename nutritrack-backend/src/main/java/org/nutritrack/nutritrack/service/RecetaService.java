package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.*;
import org.nutritrack.nutritrack.repository.AlimentoRepository;
import org.nutritrack.nutritrack.repository.RecetaRepository;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private AlimentoRepository alimentoRepository;

    public List<Receta> getAllRecetas() {
        return recetaRepository.findAll();
    }

    public List<Receta> getRecetasByUser(Long userId) {
        Optional<User> user = userRepository.findById(userId);

        if (user.isEmpty()) {
            throw new RuntimeException("Usuario no encontrado");
        }

        return recetaRepository.findByUser(user.get());
    }


    public Optional<Receta> getRecetaById(Long id) {
        return recetaRepository.findById(id);
    }

    public List<Receta> getRecetasSinAlergenos(List<String> alergenoNombres) {
        return recetaRepository.findRecetasSinAlergenos(alergenoNombres);
    }


    public List<Receta> buscarRecetasFiltradas(String nombre, Double minCalorias, Double maxCalorias,
                                               String tipoComida, Boolean favoritos, Long userId, List<Long> alergenoIds) {
        return recetaRepository.findRecetasFiltradas(nombre, minCalorias, maxCalorias, tipoComida, favoritos, userId, alergenoIds);
    }


    public Receta saveReceta(Receta receta, Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        receta.setUser(user);

        if (receta.getIngredientes() == null || receta.getIngredientes().isEmpty()) {
            throw new IllegalArgumentException("Una receta debe contener al menos un ingrediente.");
        }

        Set<Alergeno> alergenosReceta = new HashSet<>();
        for (RecetaAlimento ingrediente : receta.getIngredientes()) {
            Alimento alimento = alimentoRepository.findById(ingrediente.getAlimento().getId())
                    .orElseThrow(() -> new RuntimeException("Alimento no encontrado"));

            ingrediente.setReceta(receta); // Asigna la receta a cada ingrediente
            ingrediente.setAlimento(alimento);
            alergenosReceta.addAll(alimento.getAlergenos()); // Hereda los alérgenos del alimento
        }

        receta.setAlergenos(new ArrayList<>(alergenosReceta));

        return recetaRepository.save(receta);
    }



    public void deleteReceta(Long id, Long userId) {
        Receta receta = recetaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        if (!receta.getUser().getId().equals(userId)) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "No tienes permiso para eliminar esta receta.");
        }

        recetaRepository.deleteById(id);
    }
}
