package org.nutritrack.nutritrack;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.nutritrack.nutritrack.enums.NivelActividad;
import org.nutritrack.nutritrack.enums.Objetivo;
import org.nutritrack.nutritrack.enums.Rol;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser() {
        Optional<User> existingUser = userRepository.findByEmail("user@example.com");
        existingUser.ifPresent(userRepository::delete);

        User user = User.builder()
                .rol(Rol.USER)
                .email("user@example.com")
                .password("password")
                .nickname("testuser")
                .fullName("Test User")
                .weight(70.0)
                .height(175.0)
                .gender("Male")
                .age(30)
                .nivelActividad(NivelActividad.MODERADO)
                .objetivo(Objetivo.GANARPESO)
                .createdOn(LocalDateTime.now())
                .build();
        userRepository.save(user);


        User foundUser = userRepository.findById(user.getId()).orElse(null);
        assertNotNull(foundUser);
        assertEquals("user@example.com", foundUser.getEmail());
        assertEquals("testuser", foundUser.getNickname());
    }
}