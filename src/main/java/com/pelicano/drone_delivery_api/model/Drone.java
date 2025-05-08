package com.pelicano.drone_delivery_api.model;

/*
 * @created 07/05/2025 - 6:49 AM
 * @project drone-delivery-api
 * @author Janice Pelicano
 */

import com.pelicano.drone_delivery_api.enums.DroneModel;
import com.pelicano.drone_delivery_api.enums.DroneState;
import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Drone extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="id")
    private Long id;

    @NotNull
    @Size(max = 100)
    @Column(unique = true, name="serial_number")
    private String serialNumber;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="drone_model")
    private DroneModel model;

    @NotNull
    @Min(0)
    @Max(1000)
    @Column(name="weight_limit")
    private Double weightLimit;

    @NotNull
    @Min(0)
    @Max(100)
    @Column(name="battery_capacity")
    private Double batteryCapacity;

    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(name="state")
    private DroneState state;

    @OneToMany(mappedBy = "drone", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Medication> medications = new ArrayList<>();

    public double getCurrentWeight() {
        return this.medications.stream()
                .mapToDouble(Medication::getWeight)
                .sum();
    }

    public double getRemainingCapacity() {
        return this.weightLimit - getCurrentWeight();
    }

    public boolean canLoad(double additionalWeight) {
        return this.getRemainingCapacity() >= additionalWeight;
    }

    public boolean hasSufficientBattery() {
        return this.batteryCapacity >= 25.0;
    }
}
