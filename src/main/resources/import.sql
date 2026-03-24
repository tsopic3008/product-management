-- Dev / test seed data (loaded when hibernate-orm.database.generation = drop-and-create)
INSERT INTO category (id, name) VALUES (1, 'Electronics');
INSERT INTO category (id, name) VALUES (2, 'Books');

INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (1, 'Laptop Pro', '15-inch laptop with M3 chip', 1499.99, 'https://example.com/laptop.jpg', 1);
INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (2, 'Wireless Mouse', 'Ergonomic wireless mouse', 29.99, 'https://example.com/mouse.jpg', 1);
INSERT INTO product (id, name, description, price, image_url, category_id) VALUES (3, 'Clean Code', 'A handbook of agile software craftsmanship', 35.00, 'https://example.com/cleancode.jpg', 2);
