CREATE TABLE purchase_orders (
    order_id VARCHAR(64) NOT NULL,
    name VARCHAR(128) NOT NULL,
    email VARCHAR(128) NOT NULL,
    address VARCHAR(255) NOT NULL,
    phone VARCHAR(64) NOT NULL,
    total DECIMAL(10,2) NOT NULL,
    CONSTRAINT pk_order_id PRIMARY KEY (order_id)
);

CREATE TABLE items (
    id INT AUTO_INCREMENT,
    order_id VARCHAR(50),
    product_id VARCHAR(50),
    product_name VARCHAR(255),
    price DECIMAL(10,2),
    display_unit VARCHAR(255),
    image_url VARCHAR(255),
    quantity INT,
    CONSTRAINT pk_item_id PRIMARY KEY (id),
    CONSTRAINT fk_order_id FOREIGN KEY (order_id) REFERENCES purchase_orders(order_id)
);