package org.nutritrack.nutritrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.nutritrack.nutritrack.enums.TipoComida;

import java.util.List;

@Entity
@Table(name = "recetas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_receta_usuario"))
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RecetaAlimento> ingredientes;

    @Column
    private String imageUrl; // Imagen de la receta opcional

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoComida tipoComida;

    @Column(nullable = true)
    private Double caloriasTotales;


    @ManyToMany
    @JoinTable(
            name = "recetas_alergenos",
            joinColumns = @JoinColumn(name = "receta_id"),
            inverseJoinColumns = @JoinColumn(name = "alergeno_id")
    )
    private List<Alergeno> alergenos;

}

