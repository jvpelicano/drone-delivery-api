package com.pelicano.drone_delivery_api.dto;

import com.pelicano.drone_delivery_api.enums.DroneModel;
import com.pelicano.drone_delivery_api.enums.DroneState;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/*
 * @created 07/05/2025 - 8:39 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DroneDto {

    private Long id;

    @NotNull(message = "Serial number is required")
    @Size(min = 1, max = 100, message = "Serial number must be between 1 and 100 characters")
    private String serialNumber;

    @NotNull(message = "Drone model is required")
    private DroneModel model;

    @NotNull(message = "Weight limit is required")
    @Min(value = 0, message = "Weight limit must be positive")
    @Max(value = 1000, message = "Weight limit cannot exceed 1000g")
    private Double weightLimit;

    @NotNull(message = "Battery capacity is required")
    @Min(value = 0, message = "Battery capacity cannot be negative")
    @Max(value = 100, message = "Battery capacity cannot exceed 100%")
    private Double batteryCapacity;

    private DroneState state;

    private Double currentWeight;
    private Double remainingCapacity;
}
