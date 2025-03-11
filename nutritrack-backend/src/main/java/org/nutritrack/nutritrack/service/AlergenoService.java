package org.nutritrack.nutritrack.service;

import org.nutritrack.nutritrack.model.Alergeno;
import org.nutritrack.nutritrack.repository.AlergenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    public Alergeno saveAlergeno(Alergeno alergeno) {
        return alergenoRepository.save(alergeno);
    }

    @Transactional
    public void deleteAlergeno(Long id) {
        alergenoRepository.deleteById(id);
    }
}
