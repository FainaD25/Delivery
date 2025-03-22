CREATE TABLE base_fees (
                           id BIGINT AUTO_INCREMENT PRIMARY KEY,
                           city VARCHAR(255) NOT NULL,
                           vehicle_type VARCHAR(50) NOT NULL,
                           base_fee DECIMAL(10,2) NOT NULL
);
