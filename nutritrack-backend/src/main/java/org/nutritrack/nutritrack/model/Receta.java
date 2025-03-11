package org.nutritrack.nutritrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.nutritrack.nutritrack.enums.TipoComida;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "recetas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Receta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(columnDefinition = "TEXT")
    private String descripcion;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_receta_usuario"))
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @OneToMany(mappedBy = "receta", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<RecetaAlimento> ingredientes = new HashSet<>();

    @Column
    private String imageUrl;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private TipoComida tipoComida;

    @Column(nullable = true)
    private Double caloriasTotales;


    @Transient
    public Set<Alergeno> getAlergenos() {
        return ingredientes.stream()
                .flatMap(ingrediente -> ingrediente.getAlimento().getAlergenos().stream())
                .collect(Collectors.toSet());
    }

}

