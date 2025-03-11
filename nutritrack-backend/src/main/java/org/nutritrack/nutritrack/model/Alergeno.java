package org.nutritrack.nutritrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    @ToString.Exclude
    private List<Alimento> alimentos;


}


