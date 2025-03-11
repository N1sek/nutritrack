package org.nutritrack.nutritrack.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;
import org.nutritrack.nutritrack.enums.UnidadMedida;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "alimentos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal calories;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal proteins;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal fats;

    @Column(nullable = false, precision = 6, scale = 2)
    private BigDecimal carbs;

    @Column(nullable = false, precision = 10, scale = 2)
    private BigDecimal quantity;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private UnidadMedida unidadMedida;

    @Column
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false, foreignKey = @ForeignKey(name = "fk_alimento_usuario"))
    @JsonIgnore
    @ToString.Exclude
    private User user;

    @ManyToMany
    @JoinTable(
            name = "alimentos_alergenos",
            joinColumns = @JoinColumn(name = "id_alimento"),
            inverseJoinColumns = @JoinColumn(name = "id_alergeno")
    )
    private List<Alergeno> alergenos;

    @Column(nullable = false)
    private String createdBy;

    @PrePersist
    public void prePersist() {
        if (this.createdBy == null && this.user != null) {
            this.createdBy = this.user.getNickname();
        }
    }
}
