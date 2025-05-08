package com.pelicano.drone_delivery_api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableJpaAuditing(auditorAwareRef = "auditAwareImpl")
@SpringBootApplication
@EnableScheduling
public class DroneDeliveryApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(DroneDeliveryApiApplication.class, args);
	}

}
