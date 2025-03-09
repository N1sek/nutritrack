package org.nutritrack.nutritrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nutritrack.nutritrack.enums.NivelActividad;
import org.nutritrack.nutritrack.enums.Objetivo;
import org.nutritrack.nutritrack.enums.Rol;
import org.nutritrack.nutritrack.model.Alergeno;
import org.nutritrack.nutritrack.model.Alimento;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.repository.AlergenoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
@ExtendWith(SpringExtension.class)
public class AlergenoRepositoryTest {

    @Autowired
    AlergenoRepository alergenoRepository;

    @Test
    public void testCreateAlergeno() {
        Optional<Alergeno> existingAlergeno = alergenoRepository.findByNombre("Soja");
        existingAlergeno.ifPresent(alergenoRepository::delete);

        Alergeno alergeno = Alergeno.builder()
                .nombre("Soja")
                .build();
        alergenoRepository.save(alergeno);


        Alergeno foundAlergeno = alergenoRepository.findById(alergeno.getId()).orElse(null);
        assertNotNull(foundAlergeno);
        assertEquals("Soja", foundAlergeno.getNombre());
    }
}
