package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.dto.UserDTO;
import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.regex.Pattern;

@Controller
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody User user){
        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalArgumentException("El email es obligatorio.");
        }
        if (!isValidEmail(user.getEmail())) {
            throw new IllegalArgumentException("Formato de email inválido.");
        }
        if (user.getPassword() == null || user.getPassword().isBlank()) {
            throw new IllegalArgumentException("La contraseña es obligatoria.");
        }
        if (user.getPassword().length() < 6) {
            throw new IllegalArgumentException("La contraseña debe tener al menos 6 caracteres.");
        }

        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }


    private boolean isValidEmail(String email) {
        String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        return Pattern.compile(emailRegex).matcher(email).matches();
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User user){
        Optional<User> loggedUser = userService.loginUser(user.getEmail(), user.getPassword());

        if (loggedUser.isPresent()) {
            User safeUser = loggedUser.get();
            UserDTO userDTO = new UserDTO(
                    safeUser.getId(),
                    safeUser.getNickname(),
                    safeUser.getEmail(),
                    safeUser.getRol()
            );

            return ResponseEntity.ok(userDTO);
        } else {
            return ResponseEntity.status(401).body("Credenciales invalidas");
        }
    }
}
