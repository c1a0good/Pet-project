CREATE TABLE IF NOT EXISTS items (
	item_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_product_id BIGINT NOT NULL,
    quantity BIGINT NOT NULL,
    status VARCHAR(20),
    FOREIGN KEY (shop_product_id) REFERENCES shops_products (shop_product_id)
);
    