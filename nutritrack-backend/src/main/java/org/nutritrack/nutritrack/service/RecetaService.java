package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.repository.RecetaRepository;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RecetaService {

    @Autowired
    private RecetaRepository recetaRepository;

    @Autowired
    private UserRepository userRepository;
    
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

    public Receta saveReceta(Receta receta) {
        return recetaRepository.save(receta);
    }

    // Borrar la receta solo si la ha creado el usuario que la va a borrar
    public void deleteReceta(Long id, Long userId) {
        Optional<Receta> receta = recetaRepository.findById(id);

        if (receta.isPresent() && receta.get().getUser().getId().equals(userId)) {
            recetaRepository.deleteById(id);
        } else {
            throw new RuntimeException("No es tu receta bro");
        }
    }
}
