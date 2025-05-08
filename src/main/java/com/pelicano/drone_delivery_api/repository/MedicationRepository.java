package com.pelicano.drone_delivery_api.repository;

import com.pelicano.drone_delivery_api.model.Medication;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * @created 07/05/2025 - 8:40 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
public interface MedicationRepository extends JpaRepository<Medication, Long> {

    List<Medication> findByDroneId(Long droneId);
    Optional<Medication> findByCode(String code);
}
