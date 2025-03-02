package org.nutritrack.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "recetas_alimentos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class RecetaAlimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "alimento_id", nullable = false)
    private Alimento alimento;

    @Column(nullable = false)
    private double cantidad;
}
