package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.Alimento;
import org.nutritrack.nutritrack.repository.AlimentoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlimentoService {

    @Autowired
    private AlimentoRepository alimentoRepository;

    // Obtener todos los alimentos
    public List<Alimento> getAllAlimentos() {
        return alimentoRepository.findAll();
    }

    // Buscar alimento por ID
    public Optional<Alimento> getAlimentoById(Long id) {
        return alimentoRepository.findById(id);
    }

    // Buscar alimento por nombre
    public Optional<Alimento> getAlimentoByName(String name) {
        return alimentoRepository.findByName(name);
    }

    // Guardar un alimento
    public Alimento saveAlimento(Alimento alimento) {
        return alimentoRepository.save(alimento);
    }

    // Eliminar un alimento
    public void deleteAlimento(Long id) {
        alimentoRepository.deleteById(id);
    }
}
