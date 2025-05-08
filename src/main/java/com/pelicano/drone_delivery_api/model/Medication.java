package com.pelicano.drone_delivery_api.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @created 07/05/2025 - 7:01 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    @Column(name="name")
    private String name;

    @NotNull
    @Min(0)
    @Column(name="weight")
    private Double weight;

    @NotNull
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscores")
    @Column(name="code")
    private String code;

    @Column(name="image_url")
    private String imageUrl;

    @ManyToOne
    @JoinColumn(name = "drone_id")
    private Drone drone;
}
