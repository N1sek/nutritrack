package org.nutritrack.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;
import org.nutritrack.nutritrack.enums.NivelActividad;
import org.nutritrack.nutritrack.enums.Objetivo;
import org.nutritrack.nutritrack.enums.Rol;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "users")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Rol rol;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String nickname;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false)
    private double weight;

    @Column(nullable = false)
    private double height;

    @Column(nullable = false)
    private String gender;

    @Column(nullable = false)
    private int age;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private NivelActividad nivelActividad;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private Objetivo objetivo;

    @Column(nullable = false)
    private LocalDateTime createdOn;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Alimento> alimentos;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<UsuarioAlergeno> usuarioAlergenos;

}
