CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(30) NOT NULL,
    full_name VARCHAR(100),
    contact VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE cars (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    brand VARCHAR(255) NOT NULL,
    model VARCHAR(255),
    year INT,
    license_plate VARCHAR(255) UNIQUE,
    vin VARCHAR(255),
    mileage INT,
    purchase_price DECIMAL(15,2),
    sale_price DECIMAL(12,2),
    status VARCHAR(50) NOT NULL DEFAULT 'RECEIVED',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

CREATE TABLE customers (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    phone VARCHAR(30),
    email VARCHAR(120),
    national_id VARCHAR(20),
    address VARCHAR(255),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    UNIQUE KEY uk_customers_phone (phone),
    UNIQUE KEY uk_customers_email (email),
    INDEX idx_customers_name (first_name, last_name),
    INDEX idx_customers_phone (phone)
);

CREATE TABLE maintenance (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_id BIGINT NOT NULL,
    detail TEXT,
    cost DECIMAL(15,2) NOT NULL,
    date DATE NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (car_id) REFERENCES cars(id)
);

CREATE TABLE sales (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    car_id BIGINT UNIQUE NOT NULL,
    customer_id BIGINT,
    customer_name VARCHAR(200),
    customer_phone VARCHAR(20),
    sale_date DATE NOT NULL,
    final_price DECIMAL(15,2) NOT NULL,
    payment_method VARCHAR(20),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (car_id) REFERENCES cars(id),
    FOREIGN KEY (customer_id) REFERENCES customers(id),
    INDEX idx_sales_customer (customer_id),
    INDEX idx_sales_car (car_id)
);