-- MySQL dump 10.13  Distrib 8.0.29, for Win64 (x86_64)
--
-- Host: localhost    Database: restaurantmanagement
-- ------------------------------------------------------
-- Server version	8.0.29

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
-- Table structure for table `historychat`
--

DROP TABLE IF EXISTS `historychat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `historychat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `sent_id` varchar(20) NOT NULL,
  `recieve_id` varchar(20) NOT NULL,
  `message_type` varchar(10) DEFAULT NULL,
  `message` text,
  `sent_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `historychat`
--

LOCK TABLES `historychat` WRITE;
/*!40000 ALTER TABLE `historychat` DISABLE KEYS */;
INSERT INTO `historychat` VALUES (1,'Anhtdd','Chauttn','text','Danh khong khung','2025-05-10 16:00:39'),(2,'Bob','Chauttn','text','Danh khung','2025-05-10 01:47:59'),(3,'Chauttn','Anhtdd','text','Danh khung 123','2025-05-10 15:59:07'),(4,'Chauttn','Bob','text','Khung','2025-05-10 01:47:59'),(5,'Chauttn','Khaipm','text','Khai khung','2025-05-10 16:00:59'),(6,'Khaipm','Chauttn','text','Khai khong khung','2025-05-10 16:01:17'),(7,'Chauttn','Anhtdd','text','Danh co khung khong','2025-05-10 10:03:14');
/*!40000 ALTER TABLE `historychat` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-10 22:08:39
