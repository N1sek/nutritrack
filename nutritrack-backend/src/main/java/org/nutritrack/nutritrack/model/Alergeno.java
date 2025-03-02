package org.nutritrack.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "alergenos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Alergeno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String nombre;

    @ManyToMany(mappedBy = "alergenos")
    private List<User> usuarios;

    @ManyToMany(mappedBy = "alergenos")
    private List<Alimento> alimentos;

    @ManyToMany
    @JoinTable(
            name = "alimentos_alergenos",
            joinColumns = @JoinColumn(name = "id_alimento"),
            inverseJoinColumns = @JoinColumn(name = "id_alergeno")
    )
    private List<Alergeno> alergenos;
}
