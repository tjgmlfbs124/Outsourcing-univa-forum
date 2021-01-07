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
-- Table structure for table `forum_file`
--

DROP TABLE IF EXISTS `forum_file`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_file` (
  `idx` int unsigned NOT NULL AUTO_INCREMENT,
  `file_url` varchar(255) NOT NULL,
  `forum_idx` int unsigned NOT NULL,
  `file_size` int unsigned DEFAULT '0',
  `original_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `fk_forum_file_forum1_idx` (`forum_idx`),
  CONSTRAINT `fk_forum_file_forum1` FOREIGN KEY (`forum_idx`) REFERENCES `forum` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_file`
--

LOCK TABLES `forum_file` WRITE;
/*!40000 ALTER TABLE `forum_file` DISABLE KEYS */;
INSERT INTO `forum_file` VALUES (1,'q7HVx3JBm0HHGj5SfBTk/img.jpg',6,0,NULL),(2,'q7HVx3JBm0HHGj5SfBTk/img.jpg',6,0,NULL),(3,'q7HVx3JBm0HHGj5SfBTk/img.jpg',6,0,NULL),(4,'K2Ke5SHycKeSm2lezauc/file.jpg',21,0,NULL),(5,'q7HVx3JBm0HHGj5SfBTk/img.jpg',20,0,NULL),(6,'q7HVx3JBm0HHGj5SfBTk/img.jpg',20,0,NULL),(7,'q7HVx3JBm0HHGj5SfBTk/img.jpg',20,0,NULL),(8,'q7HVx3JBm0HHGj5SfBTk/img.jpg',8,0,NULL),(9,'q7HVx3JBm0HHGj5SfBTk/img.jpg',13,0,'티에이싱크 사진'),(10,'q7HVx3JBm0HHGj5SfBTk/img.jpg',13,0,'티에이싱크 로고'),(11,'ww8Y6E75097xW8tVu69Q/file.png',27,39270,'paradrop.png'),(12,'K2Ke5SHycKeSm2lezauc/file.jpg',27,0,NULL),(13,'6A3CDGl6UMg51Dd1n919/file.sql',28,2498,'univa_question_subject.sql'),(14,'cCj23iOwo7xgnA8k6K1O/file.sql',28,1994,'univa_subject.sql'),(15,'qgR5fTcZtpHzHrqr9968/file.PNG',29,206721,'캡처.PNG'),(16,'O5sm2Pv13lI00OVa3DMw/file.sql',29,2498,'univa_question_subject.sql'),(17,'sHc9fs1p8k8zt76jBOMR/file.sql',29,1994,'univa_subject.sql'),(18,'mtyNL9yoPtAx93t4e0H4/file.PNG',32,206721,'캡처.PNG'),(19,'q7HVx3JBm0HHGj5SfBTk/img.jpg',32,0,'티에이싱크 사진'),(20,'q7HVx3JBm0HHGj5SfBTk/img.jpg',32,0,'티에이싱크 로고'),(21,'q7HVx3JBm0HHGj5SfBTk/img.jpg',33,0,'티에이싱크 사진'),(22,'B4ev5wi4R16t1yYHS960/file.PNG',35,206721,'캡처.PNG'),(23,'P2T4nSvjNniZ53cQyE2T/file.xlsx',39,14513,'190614 출장비청구서.xlsx'),(24,'4myW818BDd07e8kB0E22/file.xlsx',46,14513,'190614 출장비청구서.xlsx'),(25,'joC6iw2Ly5TDbwJvvpEL/file.jpg',48,39916,'335854006g.jpg'),(26,'I87SDQCnDDJxwDx3FK95/file.jpg',49,39916,'335854006g.jpg'),(27,'P2T4nSvjNniZ53cQyE2T/file.xlsx',49,14513,'190614 출장비청구서.xlsx'),(28,'aJXeu3ec74p39q5zwy9W/file.db',56,34816,'photothumb.db');
/*!40000 ALTER TABLE `forum_file` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-07 16:28:15
