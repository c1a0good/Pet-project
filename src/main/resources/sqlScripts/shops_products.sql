CREATE TABLE IF NOT EXISTS shops_products (
	shop_product_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    shop_id BIGINT NOT NULL,
    product_id BIGINT NOT NULL,
    price DOUBLE,
    quantity BIGINT,
    status VARCHAR(9) DEFAULT 'NONE',
    FOREIGN KEY (shop_id) REFERENCES shops (shop_id),
    FOREIGN KEY (product_id) REFERENCES products (product_id),
    CHECK (status IN ('AVAILABLE', 'IN_STOCK', 'PRE-ORDER', 'NONE'))
);