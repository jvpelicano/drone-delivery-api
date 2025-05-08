package com.pelicano.drone_delivery_api.enums;

/*
 * @created 07/05/2025 - 7:19 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */
public enum DroneModel {

    LIGHTWEIGHT(100.0),
    MIDDLEWEIGHT(200.0),
    CRUISERWEIGHT(300.0),
    HEAVYWEIGHT(500.0);

    private final Double maxWeightCapacity;

    DroneModel(Double maxWeightCapacity) {
        this.maxWeightCapacity = maxWeightCapacity;
    }

    public Double getMaxWeightCapacity() {
        return maxWeightCapacity;
    }
}
