-- Insert default users
INSERT IGNORE INTO users (username, password, role, full_name, contact, created_at) VALUES
('admin', '$2a$10$Tu1vHvGAFYYY9N.8pB.8.OXZzJ9Jk8L8VwJ8cY8vY8vY8vY8vY8vY', 'ADMIN', 'System Admin', '090-000-0000', NOW()),
('sales', '$2a$10$Tu1vHvGAFYYY9N.8pB.8.OXZzJ9Jk8L8VwJ8cY8vY8vY8vY8vY8vY', 'SALES', 'Sales User', '090-111-1111', NOW()),
('maint', '$2a$10$Tu1vHvGAFYYY9N.8pB.8.OXZzJ9Jk8L8VwJ8cY8vY8vY8vY8vY8vY', 'MAINTENANCE', 'Maintenance', '090-222-2222', NOW()),
('manager', '$2a$10$Tu1vHvGAFYYY9N.8pB.8.OXZzJ9Jk8L8VwJ8cY8vY8vY8vY8vY8vY', 'MANAGER', 'Manager User', '090-333-3333', NOW());

-- Insert sample cars
INSERT IGNORE INTO cars (brand, model, year, license_plate, vin, mileage, purchase_price, status, created_at) VALUES
('Toyota', 'Camry', 2020, 'AB1234', '1HGCM82633A123456', 15000, 20000.00, 'READY_FOR_SALE', NOW()),
('Honda', 'Civic', 2019, 'CD5678', '2HGFA16507H123456', 20000, 18000.00, 'READY_FOR_SALE', NOW()),
('Ford', 'Focus', 2018, 'EF9012', '3FA6P0HD7JR123456', 25000, 15000.00, 'MAINTENANCE', NOW());

-- Insert sample customers
INSERT IGNORE INTO customers (first_name, last_name, phone, email, national_id, address, created_at, updated_at) VALUES
('John', 'Doe', '081-111-1111', 'john.doe@email.com', '1234567890123', '123 Main St, Bangkok', NOW(), NOW()),
('Jane', 'Smith', '082-222-2222', 'jane.smith@email.com', '9876543210987', '456 Sukhumvit Rd, Bangkok', NOW(), NOW());