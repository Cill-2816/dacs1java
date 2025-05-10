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
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `FileID` int unsigned NOT NULL AUTO_INCREMENT,
  `FileExtension` varchar(255) DEFAULT NULL,
  `BlurHash` varchar(255) DEFAULT NULL,
  `Status` char(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`FileID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `files`
--

LOCK TABLES `files` WRITE;
/*!40000 ALTER TABLE `files` DISABLE KEYS */;
INSERT INTO `files` VALUES (1,'.jpg','LTJuDjOau6kX~9SjjYW?adbHRPae','1'),(2,'.docx',NULL,'0'),(3,'.docx',NULL,'0'),(4,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(5,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(6,'.c',NULL,'0'),(7,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(8,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(9,'.docx',NULL,'0'),(10,'.docx',NULL,'0'),(11,'.docx',NULL,'0'),(12,'.docx',NULL,'0'),(13,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(14,'.jpg',NULL,'0'),(15,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(16,'.cpp',NULL,'0'),(17,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(18,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(19,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(20,'.rar',NULL,'0'),(21,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(22,'.docx',NULL,'0'),(23,'.rar',NULL,'0'),(24,'.rar',NULL,'0'),(25,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(26,'.jpg','LEJQAl~B010L9b0ff*-oIpf$-oxZ','1'),(27,'.docx',NULL,'0'),(28,'.docx',NULL,'0'),(29,'.jpg','L7IhNoIu00iu00.9o{%0004n_Mxt','1'),(30,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1'),(31,'.docx',NULL,'0'),(32,'.jpg','LUKnF|tS9F-o_NozIUt7Ips:aeR+','1');
/*!40000 ALTER TABLE `files` ENABLE KEYS */;
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
