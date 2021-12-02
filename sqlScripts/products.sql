CREATE TABLE IF NOT EXISTS products (
	product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    description VARCHAR(255),
    properties JSON NOT NULL
);
    