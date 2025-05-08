package com.pelicano.drone_delivery_api.mapper;

import com.pelicano.drone_delivery_api.dto.DroneDto;
import com.pelicano.drone_delivery_api.model.Drone;

/*
 * @created 07/05/2025 - 8:38 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
public class DroneMapper {

    public static DroneDto convertToDto(Drone drone) {
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


    public static Drone convertToDrone(DroneDto dto) {
        Drone drone = new Drone();
        drone.setId(dto.getId());
        drone.setSerialNumber(dto.getSerialNumber());
        drone.setModel(dto.getModel());
        drone.setWeightLimit(dto.getWeightLimit());
        drone.setBatteryCapacity(dto.getBatteryCapacity());
        drone.setState(dto.getState());
//        drone.setCurrentWeight(droneDto.getCurrentWeight());
//        drone.setRemainingCapacity(droneDto.getRemainingCapacity());
        return drone;
    }
}
