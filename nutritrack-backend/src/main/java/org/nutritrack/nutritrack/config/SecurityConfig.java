package org.nutritrack.nutritrack.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean

    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http
                .csrf(csrf -> csrf.disable()) // Desactivar CSRF para pruebas en Postman
                .authorizeHttpRequests(auth -> auth

                        .requestMatchers("/api/users/register", "/api/users/login").permitAll() // Permitir acceso sin autenticación

                        .anyRequest().authenticated()

                );
        return http.build();

    }


}
