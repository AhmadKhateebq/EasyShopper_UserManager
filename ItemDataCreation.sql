CREATE DATABASE supermarket;

USE supermarket;

CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(255) NOT NULL,
  brand VARCHAR(255) NOT NULL,
  category VARCHAR(255) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  stock INT NOT NULL,
  description TEXT
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
