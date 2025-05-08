package com.pelicano.drone_delivery_api.mapper;

import com.pelicano.drone_delivery_api.dto.MedicationDto;
import com.pelicano.drone_delivery_api.model.Medication;

/*
 * @created 07/05/2025 - 8:37 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */public class MedicationMapper {

    public static MedicationDto convertToDto(Medication medication) {
        MedicationDto dto = new MedicationDto();
        dto.setId(medication.getId());
        dto.setName(medication.getName());
        dto.setWeight(medication.getWeight());
        dto.setCode(medication.getCode());
        dto.setImageUrl(medication.getImageUrl());
        return dto;
    }
}
