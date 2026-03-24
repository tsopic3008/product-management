INSERT INTO category (id, name) VALUES (1, 'Electronics');
INSERT INTO category (id, name) VALUES (2, 'Books');

INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (1, 'Laptop Pro', '15-inch laptop', 1499.99, 'https://example.com/laptop.jpg', 1);
INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (2, 'Wireless Mouse', 'Ergonomic mouse', 29.99, 'https://example.com/mouse.jpg', 1);
INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (3, 'Clean Code', 'Agile software craftsmanship', 35.00, 'https://example.com/cleancode.jpg', 2);

-- Reset H2 sequences so auto-generated IDs don't collide with manually inserted ones above
ALTER TABLE category ALTER COLUMN id RESTART WITH 100;
ALTER TABLE product ALTER COLUMN id RESTART WITH 100;
ALTER TABLE orders ALTER COLUMN id RESTART WITH 100;
ALTER TABLE order_item ALTER COLUMN id RESTART WITH 100;
