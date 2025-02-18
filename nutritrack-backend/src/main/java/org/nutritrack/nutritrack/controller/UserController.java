package org.nutritrack.nutritrack.controller;

import org.nutritrack.nutritrack.model.User;
import org.nutritrack.nutritrack.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/api/users")
@CrossOrigin(origins = "http://localhost:4200")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService){
        this.userService = userService;
    }

    @PostMapping("/register")
    public ResponseEntity<User> register(@RequestBody User user){
        User newUser = userService.registerUser(user);
        return ResponseEntity.ok(newUser);
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody User user){
        Optional<User> loggedUser = userService.loginUser(user.getEmail(), user.getPassword());
        if (loggedUser.isPresent()){
            return ResponseEntity.ok(loggedUser.get());
        } else {
            return ResponseEntity.status(401).body("Credenciales invalidas");
        }
    }
}
