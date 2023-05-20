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


INSERT INTO products (name, brand, category, price, stock, description)
VALUES 
  ('Apples', 'Red Delicious', 'Fruit', 2, 100, 'Sweet and juicy apples.'),
  ('Bananas', 'Chiquita', 'Fruit', 05, 200, 'Perfectly ripe bananas.'),
  ('Milk', 'Organic Valley', 'Dairy', 3.5, 50, 'Fresh, organic milk.'),
  ('Eggs', 'Eggland\'s Best', 'Dairy', 3, 100, 'Large, cage-free eggs.'),
  ('Bread', 'Wonder Bread', 'Bakery', 2.5, 75, 'Classic white bread.'),
  ('Pasta', 'Barilla', 'Pantry', 2, 150, 'Perfect for a quick and easy dinner.'),
  ('Cereal', 'Kellogg\'s', 'Breakfast', 4, 50, 'Crunchy and delicious cereal.'),
  ('Chicken', 'Perdue', 'Meat', 6, 25, 'Fresh chicken breasts.'),
  ('Beef', 'Angus Beef', 'Meat', 10, 10, 'High-quality, grass-fed beef.');
