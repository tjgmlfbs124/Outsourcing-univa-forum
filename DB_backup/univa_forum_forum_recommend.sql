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
-- Table structure for table `forum_recommend`
--

DROP TABLE IF EXISTS `forum_recommend`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum_recommend` (
  `idx` int unsigned NOT NULL AUTO_INCREMENT,
  `forum_idx` int unsigned NOT NULL,
  `user_idx` int unsigned NOT NULL,
  `forum_like` int unsigned NOT NULL DEFAULT '1',
  PRIMARY KEY (`idx`),
  KEY `fk_forum_recommend_forum1_idx` (`forum_idx`),
  KEY `fk_forum_recommend_user1_idx` (`user_idx`),
  CONSTRAINT `fk_forum_recommend_forum1` FOREIGN KEY (`forum_idx`) REFERENCES `forum` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_recommend_user1` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum_recommend`
--

LOCK TABLES `forum_recommend` WRITE;
/*!40000 ALTER TABLE `forum_recommend` DISABLE KEYS */;
INSERT INTO `forum_recommend` VALUES (1,29,5,0),(2,29,4,1),(3,4,5,1),(4,54,4,1),(5,29,6,0),(6,49,4,0),(7,36,4,1),(8,49,5,1),(9,36,5,0),(10,7,5,1),(11,54,5,1),(12,39,4,0);
/*!40000 ALTER TABLE `forum_recommend` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2021-01-07 16:28:16
