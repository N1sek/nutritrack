package org.nutritrack.nutritrack.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "alimentos")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Getter
@Setter
public class Alimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column
    private String description;

    @Column
    private double calories;

    @Column
    private double proteins;

    @Column
    private double fats;

    @Column
    private double carbs;

    @Column
    private int quantity;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(nullable = false)
    private String createdBy;


}
