package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.UsuarioRecetaFavorita;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.model.Receta;
import org.nutritrack.nutritrack.repository.UsuarioRecetaFavoritaRepository;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.nutritrack.nutritrack.repository.RecetaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UsuarioRecetaFavoritaService {

    @Autowired
    private UsuarioRecetaFavoritaRepository usuarioRecetaFavoritaRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RecetaRepository recetaRepository;

    public boolean toggleFavorito(Long userId, Long recetaId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Receta receta = recetaRepository.findById(recetaId)
                .orElseThrow(() -> new RuntimeException("Receta no encontrada"));

        Optional<UsuarioRecetaFavorita> favoritoExistente = usuarioRecetaFavoritaRepository.findByUserAndReceta(user, receta);

        if (favoritoExistente.isPresent()) {
            usuarioRecetaFavoritaRepository.delete(favoritoExistente.get());
            return false; // Se eliminó de favoritos
        } else {
            UsuarioRecetaFavorita favorito = new UsuarioRecetaFavorita();
            favorito.setUser(user);
            favorito.setReceta(receta);
            usuarioRecetaFavoritaRepository.save(favorito);
            return true; // Se agregó a favoritos
        }
    }


    public List<UsuarioRecetaFavorita> obtenerFavoritos(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return usuarioRecetaFavoritaRepository.findByUser(user);
    }
}
