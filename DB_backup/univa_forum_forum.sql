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
-- Table structure for table `forum`
--

DROP TABLE IF EXISTS `forum`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `forum` (
  `idx` int unsigned NOT NULL AUTO_INCREMENT,
  `user_idx` int unsigned NOT NULL DEFAULT '0',
  `parent_idx` int unsigned DEFAULT NULL,
  `type` int unsigned NOT NULL COMMENT '글의 타입 (answer 인지 question 인지)\n',
  `title` varchar(255) NOT NULL,
  `content` text NOT NULL,
  `history_parent_idx` int unsigned DEFAULT NULL,
  `update_date` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `state` int NOT NULL COMMENT '글의 상태 (변경 요청? 변경 완료??)',
  `hits` int unsigned NOT NULL DEFAULT '0',
  `modifying_parent_idx` int unsigned DEFAULT NULL,
  PRIMARY KEY (`idx`),
  KEY `fk_forum_user1_idx` (`user_idx`),
  KEY `fk_forum_forum1_idx` (`parent_idx`),
  KEY `fk_forum_forum2_idx` (`history_parent_idx`),
  KEY `fk_forum_forum3` (`modifying_parent_idx`),
  CONSTRAINT `fk_forum_forum1` FOREIGN KEY (`parent_idx`) REFERENCES `forum` (`idx`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_forum2` FOREIGN KEY (`history_parent_idx`) REFERENCES `forum` (`idx`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_forum3` FOREIGN KEY (`modifying_parent_idx`) REFERENCES `forum` (`idx`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `fk_forum_user1` FOREIGN KEY (`user_idx`) REFERENCES `user` (`idx`) ON DELETE SET DEFAULT ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `forum`
--

LOCK TABLES `forum` WRITE;
/*!40000 ALTER TABLE `forum` DISABLE KEYS */;
INSERT INTO `forum` VALUES (1,4,NULL,0,'테스트 질문','1',NULL,'2020-12-02 17:13:10',20,161,NULL),(2,4,29,100,'테스트 답변1','1-2',NULL,'2020-12-02 17:13:58',20,1,NULL),(3,4,29,100,'테스트 답변2','1-3',NULL,'2020-12-02 17:14:02',0,2,NULL),(4,4,25,100,'테스트 2의 질문','1-2-4',NULL,'2020-12-03 15:43:58',0,20,NULL),(5,4,25,100,'테스트 4의 답','1-2-4-5',NULL,'2020-12-04 10:43:10',0,19,NULL),(6,4,NULL,0,'제목3','내용3',NULL,'2020-12-04 11:22:18',20,21,NULL),(7,5,NULL,0,'나의제목1','나의내용1',NULL,'2020-12-04 16:30:31',0,27,NULL),(8,4,54,100,'테스트 제목','내용내용',NULL,'2020-12-07 10:35:35',0,2,NULL),(9,5,54,100,'테스트 제목2','내용내용3',NULL,'2020-12-07 11:26:26',0,0,NULL),(10,5,8,100,'이것은제목','내용내용내용',NULL,'2020-12-07 17:24:30',0,0,NULL),(11,5,4,100,'이것도제목','내용내용내용내용',NULL,'2020-12-07 18:00:00',0,1,NULL),(12,5,25,100,'이것은제목제목','내용내용',NULL,'2020-12-07 18:03:03',0,0,NULL),(13,5,NULL,100,'동해물과 백두산이 마르고닳도록','하느님이 보우하사 우리나라만세',NULL,'2020-12-08 15:34:34',20,89,NULL),(20,4,NULL,0,'제목3','6+수정내용1',6,'2020-12-09 15:22:28',20,69,6),(21,7,NULL,0,'나도 질문을 하고싶어요','나도 질문을 하고싶은데 \n할수가없네\n어떡해야할까\n아니 이것은 무엇',NULL,'2020-12-09 16:06:07',20,13,NULL),(22,4,NULL,50,'제목3','6+수정내용2',NULL,'2020-12-09 16:16:46',30,0,6),(23,4,NULL,50,'제목3','6+수정내용3',NULL,'2020-12-04 11:22:18',30,0,6),(25,4,29,100,'테스트 답변1+수정','1-2 +수정내용',2,'2020-12-02 17:13:58',0,0,2),(26,7,NULL,50,'수정 해ㅜ저','수정~',NULL,'2020-12-09 16:06:07',30,0,21),(27,7,NULL,0,'수정2','수정2',21,'2020-12-09 16:06:07',0,75,21),(28,4,NULL,50,'테스트 질문 <수정>','내용 수정했습니다\n\n확인 부탁드립니다.',NULL,'2020-12-02 17:13:10',30,0,1),(29,4,NULL,0,'테스트 질문 <수정2>','질문 수정수정',1,'2020-12-02 17:13:10',0,299,1),(30,5,36,0,'동해물과 백두사닝 마르고닳도록 댓글','내용내용',NULL,'2020-12-11 11:37:00',0,0,NULL),(31,5,30,0,'동해물과 백두산이 마르고닳도록 답글','내용내용내용',NULL,'2020-12-11 11:40:00',0,0,NULL),(32,5,NULL,100,'동해물과 백두산이 마르고닳도록 <수정>','하느님이 보우하사 우리나라만세 <수정>',13,'2020-12-08 15:34:34',20,95,13),(33,5,NULL,50,'동해물과 백두산이 마르고닳도록 <수정3>','하느님이 보우하사 우리나라만세 <수정3>',NULL,'2020-12-08 15:34:34',30,0,13),(34,5,NULL,100,'동해물과 백두산이 마르고닳도록 <사진삭제>','하느님이 보우하사 우리나라만세 <사진삭제>',32,'2020-12-08 15:34:34',20,245,32),(35,5,NULL,100,'동해물과 백두산이 마르고닳도록 <사진추가>','하느님이 보우하사 우리나라만세 <사진추가>',34,'2020-12-08 15:34:34',20,270,34),(36,5,NULL,100,'동해물과 백두산이 마르고닳도록12312312311231동해물과 백두산이 마르고닳도록1231231231123123123 동해물과 백두산이 마르고닳도록1231231231123123123 동해물과 백두산이 마르고닳도록1231231231123123123 23123 <사진추가>','하느님이 보우하사 우리나라만세 <사진추가>',35,'2020-12-11 17:44:16',0,298,35),(37,5,NULL,50,'하느님이 보우하사 우리나라만세','무궁화 삼천리 우리나라만세',NULL,'2020-12-11 17:49:22',10,0,36),(38,5,NULL,50,'가을하늘 공활한데','높고 구름없이',NULL,'2020-12-11 17:49:49',10,0,36),(39,5,NULL,0,'제목제목','이것은 내용\n내용내용',NULL,'2020-12-15 09:51:53',20,60,NULL),(40,5,49,0,'이것도 제목제목','내용내용',NULL,'2020-12-15 09:56:20',0,0,NULL),(42,5,49,0,'이것도 댓글댓글','댓글추가\n댓글댓글',NULL,'2020-12-15 10:44:12',0,3,NULL),(43,5,40,0,'','',NULL,'2020-12-15 10:45:37',0,0,NULL),(44,5,43,0,'','',NULL,'2020-12-15 11:11:24',0,0,NULL),(45,5,49,0,'일단 이거 테스트','테스트테스트\n테스트테스트',NULL,'2020-12-15 11:27:31',0,1,NULL),(46,5,45,0,'여기다가 댓글을ㄷ ㅏㄹ면?','달면달면?',NULL,'2020-12-15 11:27:50',0,0,NULL),(47,5,42,0,'여기 사진댓글 추가요','추가추가\n추가추가',NULL,'2020-12-15 11:44:32',0,0,NULL),(48,5,42,0,'여기 사진추가 파일 추가요','추가추가',NULL,'2020-12-15 11:45:28',0,0,NULL),(49,5,NULL,0,'제목제목','이것은 내용\n내용내용',39,'2020-12-15 12:00:07',0,274,39),(50,5,49,0,'123','123',NULL,'2020-12-16 14:54:17',0,0,NULL),(51,5,NULL,50,'동해물과 백두산이 마르고닳도록 <사진추가>','하느님이 보우하사 \n우리나라만세 <사진추가>',NULL,'2020-12-16 15:28:24',10,0,36),(52,4,NULL,50,'제목3-수정','6+수정내용1+수정요청',NULL,'2020-12-28 14:35:45',30,0,20),(53,4,54,0,'test','test',NULL,'2021-01-05 10:04:05',0,0,NULL),(54,4,NULL,0,'제목3','6+수정내용1_2021',20,'2021-01-05 10:10:57',0,70,20),(55,5,NULL,50,'제목제목','이것은 내용\n내용내용_2021',NULL,'2021-01-05 10:40:36',10,0,49),(56,5,NULL,0,'2021년','2021년 첫글',NULL,'2021-01-05 11:19:39',20,1,NULL),(57,5,58,0,'test','test',NULL,'2021-01-05 11:20:12',0,0,NULL),(58,5,NULL,0,'2021년_test','2021년 첫글_test',56,'2021-01-05 11:20:34',0,1,56);
/*!40000 ALTER TABLE `forum` ENABLE KEYS */;
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
