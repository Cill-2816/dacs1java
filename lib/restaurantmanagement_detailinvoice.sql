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
-- Table structure for table `detailinvoice`
--

DROP TABLE IF EXISTS `detailinvoice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `detailinvoice` (
  `invoice_id` varchar(10) NOT NULL,
  `foods_id` varchar(10) NOT NULL,
  `amount` int NOT NULL,
  PRIMARY KEY (`invoice_id`,`foods_id`),
  KEY `foods_id` (`foods_id`),
  CONSTRAINT `detailinvoice_ibfk_1` FOREIGN KEY (`invoice_id`) REFERENCES `invoice` (`id`),
  CONSTRAINT `detailinvoice_ibfk_2` FOREIGN KEY (`foods_id`) REFERENCES `foods` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `detailinvoice`
--

LOCK TABLES `detailinvoice` WRITE;
/*!40000 ALTER TABLE `detailinvoice` DISABLE KEYS */;
INSERT INTO `detailinvoice` VALUES ('HD001','MA001',1),('HD001','MA005',2),('HD002','MA003',1),('HD002','MA011',1),('HD002','MA020',1),('HD003','MA006',2),('HD003','MA014',1),('HD004','MA002',1),('HD004','MA004',1),('HD004','MA007',1),('HD005','MA012',1),('HD005','MA015',1),('HD006','MA002',1),('HD006','MA010',2),('HD007','MA009',1),('HD007','MA017',1),('HD008','MA004',1),('HD008','MA019',2),('HD009','MA013',2),('HD009','MA016',1),('HD010','MA001',1),('HD010','MA018',1),('HD011','MA006',1),('HD011','MA008',2),('HD012','MA007',1),('HD012','MA020',2),('HD013','MA005',1),('HD013','MA008',1),('HD013','MA011',1),('HD014','MA009',2),('HD014','MA018',1),('HD015','MA004',1),('HD015','MA005',1),('HD015','MA016',1);
/*!40000 ALTER TABLE `detailinvoice` ENABLE KEYS */;
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
