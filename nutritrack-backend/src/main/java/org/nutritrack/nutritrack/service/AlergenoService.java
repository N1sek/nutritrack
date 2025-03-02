package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.Alergeno;
import org.nutritrack.nutritrack.repository.AlergenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AlergenoService {

    @Autowired
    private AlergenoRepository alergenoRepository;

    public List<Alergeno> getAllAlergenos() {
        return alergenoRepository.findAll();
    }

    public Optional<Alergeno> getAlergenoById(Long id) {
        return alergenoRepository.findById(id);
    }

    public Alergeno saveAlergeno(Alergeno alergeno) {
        return alergenoRepository.save(alergeno);
    }
    
    public void deleteAlergeno(Long id) {
        alergenoRepository.deleteById(id);
    }
}
