package org.nutritrack.nutritrack.repository;

import org.nutritrack.nutritrack.model.UsuarioAlergeno;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.model.Alergeno;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UsuarioAlergenoRepository extends JpaRepository<UsuarioAlergeno, Long> {

    List<UsuarioAlergeno> findByUser(User user);

    void deleteByUserAndAlergeno(User user, Alergeno alergeno);
}

