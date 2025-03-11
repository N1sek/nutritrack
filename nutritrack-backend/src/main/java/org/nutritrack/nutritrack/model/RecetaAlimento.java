package org.nutritrack.nutritrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.nutritrack.nutritrack.enums.UnidadMedida;

import java.math.BigDecimal;

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
    @JoinColumn(name = "receta_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recetaalimento_receta"))
    @JsonIgnore
    @ToString.Exclude
    private Receta receta;

    @ManyToOne
    @JoinColumn(name = "alimento_id", nullable = false, foreignKey = @ForeignKey(name = "fk_recetaalimento_alimento"))
    private Alimento alimento;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal cantidad;

    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;
}
