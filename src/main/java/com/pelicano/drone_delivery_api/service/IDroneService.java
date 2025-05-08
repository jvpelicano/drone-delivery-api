package com.pelicano.drone_delivery_api.service;

import com.pelicano.drone_delivery_api.dto.DroneDto;
import com.pelicano.drone_delivery_api.dto.MedicationDto;
import com.pelicano.drone_delivery_api.dto.params.LoadMedicationParams;
import com.pelicano.drone_delivery_api.enums.DroneState;

import java.util.List;

/*
 * @created 07/05/2025 - 8:45 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
public interface IDroneService {

    public DroneDto registerDrone(DroneDto droneDto);

    public DroneDto loadDroneWithMedications(LoadMedicationParams loadMedicationParams);

    public List<MedicationDto> getLoadedMedications(String serialNumber);

    public List<DroneDto> getAvailableDrones();

    public DroneDto getDroneInfo( String serialNumber);

    public void updateDroneState(String serialNumber, DroneState newState);

    public void reduceBatteryLevel(String serialNumber, double percentage);
}
