package com.pelicano.drone_delivery_api.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @created 07/05/2025 - 8:41 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MedicationDto {

    private Long id;

    @NotNull(message = "Name is required")
    @Pattern(regexp = "^[a-zA-Z0-9_-]+$", message = "Name can only contain letters, numbers, hyphens, and underscores")
    private String name;

    @NotNull(message = "Weight is required")
    @Min(value = 0, message = "Weight must be positive")
    private Double weight;

    @NotNull(message = "Code is required")
    @Pattern(regexp = "^[A-Z0-9_]+$", message = "Code can only contain uppercase letters, numbers, and underscores")
    private String code;

    private String imageUrl;
}
