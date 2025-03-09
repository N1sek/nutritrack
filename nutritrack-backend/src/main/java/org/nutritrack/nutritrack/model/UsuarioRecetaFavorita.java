package org.nutritrack.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "usuarios_recetas_favoritas")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class UsuarioRecetaFavorita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "receta_id", nullable = false)
    private Receta receta;
}
