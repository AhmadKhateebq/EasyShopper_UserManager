create database easy_shopper;
use easy_shopper;
CREATE TABLE users (
    id INT PRIMARY KEY AUTO_INCREMENT,
    username VARCHAR(15),
    fname VARCHAR(15),
    lname VARCHAR(15),
    email VARCHAR(50)
);
CREATE TABLE passwords (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    password VARCHAR(25),
    CONSTRAINT FK_UsersPassword FOREIGN KEY (user_id)
        REFERENCES users (id)
);
CREATE TABLE admins (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    CONSTRAINT FK_UsersAmins FOREIGN KEY (user_id)
        REFERENCES users (id)
);
