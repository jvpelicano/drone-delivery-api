package com.pelicano.drone_delivery_api.service.impl;

import com.pelicano.drone_delivery_api.dto.DroneDto;
import com.pelicano.drone_delivery_api.dto.MedicationDto;
import com.pelicano.drone_delivery_api.dto.params.LoadMedicationParams;
import com.pelicano.drone_delivery_api.enums.DroneState;
import com.pelicano.drone_delivery_api.exception.DroneException;
import com.pelicano.drone_delivery_api.mapper.DroneMapper;
import com.pelicano.drone_delivery_api.model.Drone;
import com.pelicano.drone_delivery_api.model.Medication;
import com.pelicano.drone_delivery_api.repository.DroneRepository;
import com.pelicano.drone_delivery_api.repository.MedicationRepository;
import com.pelicano.drone_delivery_api.service.IDroneService;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.pelicano.drone_delivery_api.mapper.DroneMapper.convertToDto;

/*
 * @created 07/05/2025 - 8:46 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Service
public class DroneServiceImpl implements IDroneService {

    private static final Logger logger = LoggerFactory.getLogger(DroneServiceImpl.class);

    @Autowired
    private DroneRepository droneRepository;

    @Autowired
    private MedicationRepository medicationRepository;


    @Transactional
    @Override
    public DroneDto registerDrone(DroneDto droneDto) {
        logger.info("Registering drone with id: {}", droneDto.getId());
        if (droneRepository.findBySerialNumber(droneDto.getSerialNumber()).isPresent()) {
            throw new DroneException("A drone with the same serial number already exists");
        }

        // Set the weight limit based on the drone model if not provided
        if (droneDto.getWeightLimit() == null || droneDto.getWeightLimit() == 0) {
            droneDto.setWeightLimit(droneDto.getModel().getMaxWeightCapacity());
        }

        // Validate weight limit against drone model's max capacity
        if (droneDto.getWeightLimit() > droneDto.getModel().getMaxWeightCapacity()) {
            throw new DroneException("Weight limit exceeds the maximum capacity for the drone model");
        }

        // Create and save the drone
        Drone drone = new Drone();
        drone.setSerialNumber(droneDto.getSerialNumber());
        drone.setModel(droneDto.getModel());
        drone.setWeightLimit(droneDto.getWeightLimit());
        drone.setBatteryCapacity(droneDto.getBatteryCapacity());
        drone.setState(DroneState.IDLE); // Initial state is IDLE

        Drone savedDrone = droneRepository.save(drone);
        return DroneMapper.convertToDto(savedDrone);

    }

    @Transactional
    @Override
    public DroneDto loadDroneWithMedications(LoadMedicationParams loadMedicationParams) {
       // Get the drone
        Drone drone = droneRepository.findBySerialNumber(loadMedicationParams.getDroneSerialNumber())
                .orElseThrow(() -> new DroneException("Drone not found with serial number: " + loadMedicationParams.getDroneSerialNumber()));

        // Check if the drone is in the correct state
        if (drone.getState() != DroneState.IDLE && drone.getState() != DroneState.LOADING) {
            throw new DroneException("Cannot load drone that is not in IDLE or LOADING state");
        }

        // Check battery level
        if (drone.getBatteryCapacity() < 25) {
            throw new DroneException("Cannot load drone with battery level below 25%");
        }

        // Change drone state to LOADING
        drone.setState(DroneState.LOADING);
        droneRepository.save(drone);

        // Load each medication
        double totalWeight = drone.getCurrentWeight();
        for (String code : loadMedicationParams.getMedicationCodes()) {
            Medication medication = medicationRepository.findByCode(code)
                    .orElseThrow(() -> new DroneException("Medication not found with code: " + code));

            // Check if the medication is already loaded on a drone
            if (medication.getDrone() != null) {
                throw new DroneException("Medication already loaded on another drone: " + code);
            }

            // Check weight limit
            if (totalWeight + medication.getWeight() > drone.getWeightLimit()) {
                throw new DroneException("Loading this medication would exceed the drone's weight limit");
            }

            // Update medication
            medication.setDrone(drone);
            medicationRepository.save(medication);
            totalWeight += medication.getWeight();
        }

        // Update drone state to LOADED if medications were added
        if (!loadMedicationParams.getMedicationCodes().isEmpty()) {
            drone.setState(DroneState.LOADED);
            droneRepository.save(drone);
        }

        return DroneMapper.convertToDto(drone);
    }

    @Override
    public List<MedicationDto> getLoadedMedications(String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneException("Drone not found with serial number: " + serialNumber));


        List<Medication> medications = medicationRepository.findByDroneId(drone.getId());
        return medications.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<DroneDto> getAvailableDrones() {
        List<Drone> availableDrones = droneRepository.findByStateAndBatteryCapacityGreaterThanEqual(
                DroneState.IDLE, 25.0);

        DroneDto droneDto = new DroneDto();

        return availableDrones.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    @Override
    public DroneDto getDroneInfo( String serialNumber) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneException("Drone not found with serial number: " + serialNumber));

        return convertToDto(drone);
    }

    @Transactional
    @Override
    public void updateDroneState(String serialNumber, DroneState newState) {
        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneException("Drone not found with serial number: " + serialNumber));

        // Update the state
        drone.setState(newState);
        droneRepository.save(drone);
    }

    @Transactional
    @Override
    public void reduceBatteryLevel(String serialNumber, double percentage) {

        Drone drone = droneRepository.findBySerialNumber(serialNumber)
                .orElseThrow(() -> new DroneException("Drone not found with serial number: " + serialNumber));

        double newBatteryLevel = Math.max(0, drone.getBatteryCapacity() - percentage);
        drone.setBatteryCapacity(newBatteryLevel);
        droneRepository.save(drone);

    }

    // Helper methods to convert between entities and DTOs
    private DroneDto convertToDto(Drone drone) {
        DroneDto dto = new DroneDto();
        dto.setId(drone.getId());
        dto.setSerialNumber(drone.getSerialNumber());
        dto.setModel(drone.getModel());
        dto.setWeightLimit(drone.getWeightLimit());
        dto.setBatteryCapacity(drone.getBatteryCapacity());
        dto.setState(drone.getState());
        dto.setCurrentWeight(drone.getCurrentWeight());
        dto.setRemainingCapacity(drone.getRemainingCapacity());
        return dto;
    }

    private MedicationDto convertToDto(Medication medication) {
        MedicationDto dto = new MedicationDto();
        dto.setId(medication.getId());
        dto.setName(medication.getName());
        dto.setWeight(medication.getWeight());
        dto.setCode(medication.getCode());
        dto.setImageUrl(medication.getImageUrl());
        return dto;
    }
}
