CREATE DATABASE supermarket;
USE supermarket;

CREATE TABLE products (
                        id INT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL,
                        brand VARCHAR(255) NOT NULL,
                        category VARCHAR(255) NOT NULL,
                        description TEXT
);
CREATE TABLE supermarkets (
                            id INT AUTO_INCREMENT PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            locationX VARCHAR(255) NOT NULL,
                            locationY VARCHAR(255) NOT NULL
);
CREATE TABLE supermarkets_products (
                                     id INT AUTO_INCREMENT PRIMARY KEY,
                                     supermarket_id INT,
                                     product_id INT,
                                     price double(10 , 2 ) default(null),
    stock INT NOT NULL,
    CONSTRAINT fk_supermarket_id_supermarket FOREIGN KEY (supermarket_id)
        REFERENCES supermarkets (id),
    CONSTRAINT fk_supermarket_product FOREIGN KEY (product_id)
        REFERENCES products (id)
);

INSERT INTO products (name, brand, category,  description)
VALUES
    ('Apples', 'Red Delicious', 'Fruit',  'Sweet and juicy apples.'),
    ('Bananas', 'Chiquita', 'Fruit', 'Perfectly ripe bananas.'),
    ('Milk', 'Organic Valley', 'Dairy', 'Fresh, organic milk.'),
    ('Eggs', 'Eggland\'s Best', 'Dairy', 'Large, cage-free eggs.'),
  ('Bread', 'Wonder Bread', 'Bakery', 'Classic white bread.'),
  ('Pasta', 'Barilla', 'Pantry', 'Perfect for a quick and easy dinner.'),
  ('Cereal', 'Kellogg\'s', 'Breakfast',  'Crunchy and delicious cereal.'),
    ('Chicken', 'Perdue', 'Meat',  'Fresh chicken breasts.'),
    ('Beef', 'Angus Beef', 'Meat',  'High-quality, grass-fed beef.');
