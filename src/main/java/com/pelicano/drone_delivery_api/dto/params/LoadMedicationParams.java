package com.pelicano.drone_delivery_api.dto.params;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/*
 * @created 07/05/2025 - 8:51 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoadMedicationParams {

    @NotNull(message = "Drone serial number is required")
    private String droneSerialNumber;

    @NotEmpty(message = "At least one medication is required")
    private List<String> medicationCodes;
}
