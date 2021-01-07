-- MySQL dump 10.13  Distrib 8.0.21, for Win64 (x86_64)
--
-- Host: 192.168.0.8    Database: univa_forum
-- ------------------------------------------------------
-- Server version	8.0.19

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
-- Table structure for table `forum_subject`
--

DROP TABLE IF EXISTS `forum_subject`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_subject` (
  `idx` int unsigned NOT NULL AUTO_INCREMENT,
  `forum_idx` int unsigned NOT NULL,
  `subject_idx` int unsigned NOT NULL,
  PRIMARY KEY (`idx`),
  KEY `fk_forum_subject_forum_idx` (`forum_idx`),
  KEY `fk_forum_subject_subject1_idx` (`subject_idx`),
  CONSTRAINT `fk_forum_subject_forum` FOREIGN KEY (`forum_idx`) REFERENCES `forum` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_subject_subject1` FOREIGN KEY (`subject_idx`) REFERENCES `subject` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_subject`
--

LOCK TABLES `forum_subject` WRITE;
/*!40000 ALTER TABLE `forum_subject` DISABLE KEYS */;
INSERT INTO `forum_subject` VALUES (1,1,1),(2,1,2),(3,4,1),(4,4,2),(5,5,1),(6,6,1),(7,6,2),(16,20,1),(17,20,2),(18,21,1),(19,21,2),(20,22,1),(21,22,2),(22,23,1),(23,23,2),(24,25,1),(25,13,1),(26,28,1),(27,28,2),(28,29,1),(29,29,2),(30,56,2);
/*!40000 ALTER TABLE `forum_subject` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-07 16:28:17
