package com.pelicano.drone_delivery_api.repository;

import com.pelicano.drone_delivery_api.enums.DroneState;
import com.pelicano.drone_delivery_api.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

/*
 * @created 07/05/2025 - 8:23 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
public interface DroneRepository extends JpaRepository<Drone, Long> {

    Optional<Drone> findBySerialNumber(String serialNumber);
    List<Drone> findByState(DroneState state);
    List<Drone> findByStateAndBatteryCapacityGreaterThanEqual(DroneState state, Double batteryCapacity);
}
