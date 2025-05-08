package com.pelicano.drone_delivery_api.controller;

import com.pelicano.drone_delivery_api.constants.DroneConstants;
import com.pelicano.drone_delivery_api.dto.DroneDto;
import com.pelicano.drone_delivery_api.dto.MedicationDto;
import com.pelicano.drone_delivery_api.dto.ResponseDto;
import com.pelicano.drone_delivery_api.dto.params.LoadMedicationParams;
import com.pelicano.drone_delivery_api.service.IDroneService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/*
 * @created 07/05/2025 - 10:44 PM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@RestController
@RequestMapping("/api/drones")
public class DroneController {

    private IDroneService droneService;

    @Autowired
    public DroneController(IDroneService droneService) {
        this.droneService = droneService;
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseDto> registerDrone(@Valid @RequestBody DroneDto droneDto) {
        droneService.registerDrone(droneDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(new ResponseDto(DroneConstants.STATUS_201, DroneConstants.MESSAGE_201));
    }

    @PostMapping("/load")
    public ResponseEntity<DroneDto> loadDroneWithMedications(@Valid @RequestBody LoadMedicationParams params) {
        DroneDto loadedDrone = droneService.loadDroneWithMedications(params);
        return ResponseEntity.ok(loadedDrone);
    }

    @GetMapping("/{serialNumber}/medications")
    public ResponseEntity<List<MedicationDto>> getLoadedMedications(@PathVariable String serialNumber) {
        List<MedicationDto> medications = droneService.getLoadedMedications(serialNumber);
        return ResponseEntity.ok(medications);
    }

    @GetMapping("/available")
    public ResponseEntity<List<DroneDto>> getAvailableDrones() {
        List<DroneDto> availableDrones = droneService.getAvailableDrones();
        return ResponseEntity.ok(availableDrones);
    }

    @GetMapping("/{serialNumber}")
    public ResponseEntity<DroneDto> getDroneInfo(@PathVariable String serialNumber) {
        DroneDto droneDto = droneService.getDroneInfo(serialNumber);
        return ResponseEntity.ok(droneDto);
    }

    @GetMapping("/{serialNumber}/battery")
    public ResponseEntity<Map<String, Object>> getDroneBatteryLevel(@PathVariable String serialNumber) {
        DroneDto droneDto = droneService.getDroneInfo(serialNumber);
        return ResponseEntity.ok(Map.of(
                "serialNumber", droneDto.getSerialNumber(),
                "batteryLevel", droneDto.getBatteryCapacity()
        ));
    }
}
