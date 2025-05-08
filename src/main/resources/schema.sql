CREATE TABLE IF NOT EXISTS `drone` (
  id LONG AUTO_INCREMENT PRIMARY KEY,
 `serial_number` VARCHAR(100) NOT NULL UNIQUE,
 `drone_model` VARCHAR(100) NOT NULL,
 `weight_limit` DOUBLE NOT NULL,
 `battery_capacity` DOUBLE NOT NULL,
 `state` VARCHAR(100) NOT NULL,
 `created_at` date NOT NULL,
 `created_by` varchar(20) NOT NULL,
 `updated_at` date DEFAULT NULL,
 `updated_by` varchar(20) DEFAULT NULL
);

CREATE TABLE IF NOT EXISTS `medication` (
  id LONG AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  weight INT NOT NULL,
  code VARCHAR(100) NOT NULL,
  image_url VARCHAR(255)
);

--CREATE TABLE IF NOT EXISTS `medication_delivery` (
--    id INT AUTO_INCREMENT PRIMARY KEY,
--    drone_id INT NOT NULL,
--    medication_id INT NOT NULL,
--    quantity INT NOT NULL,
--    created_at TIMESTAMP,
--    delivered_at TIMESTAMP,
--    FOREIGN KEY (drone_id) REFERENCES drone(id),
--    FOREIGN KEY (medication_id) REFERENCES medication(id)
--);
--
---- Create an audit log table for tracking battery levels and state changes
--CREATE TABLE IF NOT EXISTS `drone_audit_log` (
--    id INT AUTO_INCREMENT PRIMARY KEY,
--    serial_number INT NOT NULL,
--    battery_level INT NOT NULL,
--    old_state VARCHAR(100),
--    new_state VARCHAR(100),
--    log_time TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
--    FOREIGN KEY (drone_id) REFERENCES drone(id)
--);

-- Insert sample drones
INSERT INTO drone (serial_number, drone_model,weight_limit, battery_capacity, state, created_at, created_by) VALUES
('DRN-001-2025', 'LIGHTWEIGHT', 100, 100, 'IDLE', CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),  -- Lightweight drone, full battery, IDLE
('DRN-002-2025', 'LIGHTWEIGHT', 100, 85, 'IDLE',CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Lightweight drone, good battery, IDLE
('DRN-003-2025', 'MIDDLEWEIGHT', 200, 90, 'IDLE',CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Middleweight drone, good battery, IDLE
('DRN-004-2025', 'MIDDLEWEIGHT', 200, 20, 'IDLE',CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Middleweight drone, low battery, IDLE (below 25%)
('DRN-005-2025', 'CRUISERWEIGHT', 300, 75, 'LOADED',CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Cruiserweight drone, good battery, LOADED
('DRN-006-2025', 'CRUISERWEIGHT', 300, 60, 'DELIVERING', CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Cruiserweight drone, decent battery, DELIVERING
('DRN-007-2025', 'HEAVYWEIGHT', 500, 80, 'IDLE', CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Heavyweight drone, good battery, IDLE
('DRN-008-2025', 'HEAVYWEIGHT', 500, 50, 'RETURNING', CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Heavyweight drone, medium battery, RETURNING
('DRN-009-2025', 'MIDDLEWEIGHT', 200, 95, 'IDLE', CURRENT_TIMESTAMP, 'ACCOUNT_MS' ),   -- Middleweight drone, excellent battery, IDLE
('DRN-010-2025', 'CRUISERWEIGHT', 300, 30, 'IDLE', CURRENT_TIMESTAMP, 'ACCOUNT_MS' );   -- Cruiserweight drone, low battery but above 25%, IDLE

-- Insert sample medications
INSERT INTO medication (name, weight, code, image_url) VALUES
('Amoxicillin', 50, 'MED_AMOX_001', '/images/medications/amoxicillin.jpg'),
('Ibuprofen', 30, 'MED_IBU_002', '/images/medications/ibuprofen.jpg'),
('Insulin', 25, 'MED_INS_003', '/images/medications/insulin.jpg'),
('EpiPen', 45, 'MED_EPI_004', '/images/medications/epipen.jpg'),
('Antibiotics', 60, 'MED_ANT_005', '/images/medications/antibiotics.jpg'),
('Morphine', 15, 'MED_MOR_006', '/images/medications/morphine.jpg'),
('Ventolin', 20, 'MED_VEN_007', '/images/medications/ventolin.jpg'),
('FirstAidKit', 150, 'MED_FAK_008', '/images/medications/firstaidkit.jpg'),
('BloodPlasma', 200, 'MED_BPL_009', '/images/medications/bloodplasma.jpg'),
('EmergencyVaccine', 40, 'MED_VAC_010', '/images/medications/vaccine.jpg');

-- Insert sample medication deliveries
--INSERT INTO medication_delivery (drone_id, medication_id, quantity, delivered_at) VALUES
--(5, 1, 3, NULL),           -- Drone 5 is LOADED with 3 units of Amoxicillin
--(5, 7, 2, NULL),           -- Drone 5 is also LOADED with 2 units of Ventolin
--(6, 3, 5, NULL),           -- Drone 6 is DELIVERING 5 units of Insulin
--(6, 4, 1, NULL),           -- Drone 6 is also DELIVERING 1 unit of EpiPen
--(8, 2, 4, '2025-05-07'),   -- Drone 8 RETURNED after delivering 4 units of Ibuprofen
--(8, 5, 2, '2025-05-07');   -- Drone 8 RETURNED after delivering 2 units of Antibiotics
--
---- Create a view to calculate total payload on each drone
--CREATE VIEW drone_payload AS
--SELECT
--    d.id AS drone_id,
--    d.serial_number,
--    dm.name AS model_name,
--    dm.weight_limit,
--    COALESCE(SUM(md.quantity * m.weight), 0) AS current_payload,
--    dm.weight_limit - COALESCE(SUM(md.quantity * m.weight), 0) AS remaining_capacity
--FROM
--    drone d
--JOIN
--    drone_model dm ON d.model_id = dm.id
--LEFT JOIN
--    medication_delivery md ON d.id = md.drone_id AND md.delivered_at IS NULL
--LEFT JOIN
--    medication m ON md.medication_id = m.id
--GROUP BY
--    d.id, d.serial_number, dm.name, dm.weight_limit;
--
--
---- Sample audit logs
--INSERT INTO drone_audit_log (drone_id, battery_level, old_state, new_state) VALUES
--(5, 80, 'IDLE', 'LOADING'),  -- Drone 5 went from IDLE to LOADING with 80% battery
--(5, 78, 'LOADING', 'LOADED'),  -- Drone 5 went from LOADING to LOADED with 78% battery
--(6, 70, 'LOADED', 'DELIVERING'),  -- Drone 6 went from LOADED to DELIVERING with 70% battery
--(8, 65, 'DELIVERING', 'DELIVERED'),  -- Drone 8 went from DELIVERING to DELIVERED with 65% battery
--(8, 55, 'DELIVERED', 'RETURNING');  -- Drone 8 went from DELIVERED to RETURNING with 55% battery