package com.pelicano.drone_delivery_api.scheduler;

import com.pelicano.drone_delivery_api.enums.DroneState;
import com.pelicano.drone_delivery_api.model.Drone;
import com.pelicano.drone_delivery_api.repository.DroneRepository;
import com.pelicano.drone_delivery_api.service.impl.DroneServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Random;

/*
 * @created 07/05/2025 - 8:48 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
@Component
public class DroneScheduler {


    private static final Logger logger = LoggerFactory.getLogger(DroneScheduler.class);
    private static final double BATTERY_REDUCTION_PERCENTAGE = 5.0;
    private final DroneRepository droneRepository;
    private final DroneServiceImpl droneService;
    private final Random random = new Random();

    @Autowired
    public DroneScheduler(DroneRepository droneRepository, DroneServiceImpl droneService) {
        this.droneRepository = droneRepository;
        this.droneService = droneService;
    }

    // Run every 30 seconds
    @Scheduled(fixedRate = 30000)
    public void manageDroneStates() {
        logger.info("Running drone state management scheduler");

        // Process LOADED drones - they should start delivering
        List<Drone> loadedDrones = droneRepository.findByState(DroneState.LOADED);
        for (Drone drone : loadedDrones) {
            if (random.nextBoolean()) { // Simulate random decision to start delivery
                logger.info("Drone {} starting delivery", drone.getSerialNumber());
                droneService.updateDroneState(drone.getSerialNumber(), DroneState.DELIVERING);
            }
        }

        // Process DELIVERING drones - they should eventually be DELIVERED
        List<Drone> deliveringDrones = droneRepository.findByState(DroneState.DELIVERING);
        for (Drone drone : deliveringDrones) {
            if (random.nextDouble() < 0.3) { // 30% chance to complete delivery
                logger.info("Drone {} completed delivery", drone.getSerialNumber());
                droneService.updateDroneState(drone.getSerialNumber(), DroneState.DELIVERED);
                // Reduce battery level after delivery
                droneService.reduceBatteryLevel(drone.getSerialNumber(), BATTERY_REDUCTION_PERCENTAGE);
            }
        }

        // Process DELIVERED drones - they should return to base
        List<Drone> deliveredDrones = droneRepository.findByState(DroneState.DELIVERED);
        for (Drone drone : deliveredDrones) {
            if (random.nextDouble() < 0.5) { // 50% chance to start returning
                logger.info("Drone {} returning to base", drone.getSerialNumber());
                droneService.updateDroneState(drone.getSerialNumber(), DroneState.RETURNING);
            }
        }

        // Process RETURNING drones - they should eventually be IDLE and unload medications
        List<Drone> returningDrones = droneRepository.findByState(DroneState.RETURNING);
        for (Drone drone : returningDrones) {
            if (random.nextDouble() < 0.4) { // 40% chance to complete return
                logger.info("Drone {} returned to base", drone.getSerialNumber());

                // Unload all medications by setting drone to null
                drone.getMedications().forEach(medication -> medication.setDrone(null));
                droneRepository.save(drone);

                // Update state to IDLE
                droneService.updateDroneState(drone.getSerialNumber(), DroneState.IDLE);

                // Additional battery reduction for the return trip
                droneService.reduceBatteryLevel(drone.getSerialNumber(), BATTERY_REDUCTION_PERCENTAGE);
            }
        }
    }

    // Run every minute - check battery levels
    @Scheduled(fixedRate = 60000)
    public void checkDroneBatteryLevels() {
        logger.info("Running battery level check scheduler");

        List<Drone> allDrones = droneRepository.findAll();
        for (Drone drone : allDrones) {
            logger.info("Drone {}: Battery level at {}%",
                    drone.getSerialNumber(), drone.getBatteryCapacity());

            // Log warning for low battery drones
            if (drone.getBatteryCapacity() < 25.0) {
                logger.warn("Drone {} has low battery: {}%",
                        drone.getSerialNumber(), drone.getBatteryCapacity());
            }
        }
    }

}
