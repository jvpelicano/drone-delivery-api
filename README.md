# drone-delivery-api
Drone Delivery Service
This application provides a RESTful API for The Drone company to manage their drone-based medication delivery system.
The service allows registering drones, loading medications, checking drone status, and managing the delivery process.
Technology Stack

Spring Boot 3.2.0
Java 17
Spring Data JPA
H2 In-memory Database
Maven

Requirements

Java 17 or higher
Maven 3.6 or higher

How to Build and Run
Building the Application

Clone the repository
Navigate to the project root directory
Run the Maven build command:

bashmvn clean install
Running the Application
Run the application using Maven:
bashmvn spring-boot:run
Or you can run the JAR file directly:
bashjava -jar target/drone-delivery-service-0.0.1-SNAPSHOT.jar
The application will start on port 8080 by default.
Database
The application uses H2 in-memory database for data storage. You can access the H2 console at:
http://localhost:8080/h2-console
Connection details:

JDBC URL: jdbc:h2:mem:dronedb
Username: sa
Password: password
