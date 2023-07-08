CREATE DATABASE  IF NOT EXISTS `supermarket` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `supermarket`;
-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: supermarket
-- ------------------------------------------------------
-- Server version	8.0.32

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `brand` varchar(255) NOT NULL,
  `category` varchar(255) NOT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Apples','Red Delicious','Fruit','Sweet and juicy apples.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(2,'Bananas','Chiquita','Fruit','Perfectly ripe bananas.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(3,'Milk','Organic Valley','Dairy','Fresh, organic milk.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(4,'Eggs','Eggland\'s Best','Dairy','Large, cage-free eggs.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(5,'Bread','Wonder Bread','Bakery','Classic white bread.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(6,'Pasta','Barilla','Pantry','Perfect for a quick and easy dinner.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(7,'Cereal','Kellogg\'s','Breakfast','Crunchy and delicious cereal.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(8,'Chicken','Perdue','Meat','Fresh chicken breasts.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg'),(9,'Beef','Angus Beef','Meat','High-quality, grass-fed beef.','https://upload.wikimedia.org/wikipedia/commons/1/14/No_Image_Available.jpg');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-07-08 17:42:09
