CREATE DATABASE  IF NOT EXISTS `dbmsChat` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `dbmsChat`;
-- MySQL dump 10.13  Distrib 8.0.34, for Linux (x86_64)
--
-- Host: 127.0.0.1    Database: dbmsChat
-- ------------------------------------------------------
-- Server version	8.0.34-0ubuntu0.23.04.1

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
-- Table structure for table `contact`
--

DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
  `id` int NOT NULL AUTO_INCREMENT,
  `id_user` int NOT NULL,
  `id_user_contact` int NOT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `unique_contact` (`id_user`,`id_user_contact`),
  KEY `fk_contact_1_idx` (`id_user`),
  KEY `fk_contact_2_idx` (`id_user_contact`),
  CONSTRAINT `fk_contact_1` FOREIGN KEY (`id_user`) REFERENCES `user` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_contact_2` FOREIGN KEY (`id_user_contact`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`
--

LOCK TABLES `contact` WRITE;
/*!40000 ALTER TABLE `contact` DISABLE KEYS */;
INSERT INTO `contact` VALUES (2,11,7,0),(5,7,11,0),(12,7,12,0),(13,11,13,0),(14,13,11,0);
/*!40000 ALTER TABLE `contact` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `text` longtext NOT NULL,
  `dataM` date NOT NULL,
  `timeM` time NOT NULL,
  `idusersender` int NOT NULL,
  `iduserreceiver` int NOT NULL,
  `hasbeenread` tinyint NOT NULL,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  KEY `FK_1` (`idusersender`),
  KEY `FK_2` (`iduserreceiver`),
  CONSTRAINT `FK_1` FOREIGN KEY (`idusersender`) REFERENCES `user` (`id`),
  CONSTRAINT `FK_2` FOREIGN KEY (`iduserreceiver`) REFERENCES `user` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
INSERT INTO `message` VALUES (13,'Test 1','2023-08-31','09:31:40',7,11,1,319),(14,'Test 2','2023-08-31','09:33:39',7,11,1,318),(15,'Test 3','2023-08-31','09:34:56',7,11,1,318),(16,'Test 4','2023-08-31','09:36:02',11,7,1,506),(17,'Test 5','2023-08-31','09:36:16',11,7,1,506),(18,'Test 6','2023-08-31','09:36:20',11,7,1,506),(19,'Test 7','2023-08-31','09:36:25',11,7,1,506),(20,'Test 8','2023-08-31','09:36:28',11,7,1,506),(21,'Test 9','2023-08-31','09:36:37',7,11,1,318),(22,'Test 10','2023-08-31','09:36:41',7,11,1,318),(23,'Test 11','2023-08-31','10:25:13',7,11,1,318),(24,'Test 12','2023-09-01','10:15:35',7,11,1,318),(25,'Test 13','2023-09-02','18:10:48',11,7,1,495),(26,'Test 14','2023-09-02','18:44:22',11,7,1,476),(27,'Test 15','2023-09-02','18:48:35',11,7,1,475),(28,'Test 16','2023-09-02','18:49:04',11,7,1,474),(29,'Test 17','2023-09-03','09:57:48',11,7,1,473),(30,'Test 18','2023-09-03','10:09:52',11,7,1,468),(31,'Test 19','2023-09-03','10:29:56',11,7,1,448),(32,'Test 20','2023-09-03','10:38:50',11,7,1,426),(33,'Test 21','2023-09-03','10:39:56',11,7,1,423),(34,'Test 22','2023-09-03','10:40:37',11,7,1,420),(35,'Test 23','2023-09-03','10:41:50',11,7,1,417),(36,'Test 24','2023-09-03','10:43:19',11,7,1,414),(37,'Test 24','2023-09-03','10:46:07',11,7,1,407),(38,'Test 26','2023-09-03','10:46:32',11,7,1,405),(39,'Test 27','2023-09-03','10:58:57',11,7,1,379),(40,'Test 28','2023-09-03','11:01:17',11,7,1,373),(41,'Test 29','2023-09-06','18:25:12',11,7,1,235),(42,'Test 30','2023-09-06','18:25:47',7,11,1,218),(43,'Test 31','2023-09-06','18:27:39',7,11,1,213),(44,'Test 32','2023-09-06','18:30:23',7,11,1,211),(45,'Test 33','2023-09-06','18:36:16',7,11,1,199),(46,'','2023-09-08','15:07:15',11,13,1,36),(47,'','2023-09-08','15:15:04',11,13,1,36),(48,'','2023-09-08','15:31:21',13,11,1,102),(49,' sudhfidsufhsdiufh biudhfidsuhfiudsh','2023-09-08','15:32:40',13,11,1,102),(50,'biudhfidsuhfiudsh asdkjnsakdjn \nasdlnsakdjn  sadsadasljdnki \nasdkmnasdlkjn ','2023-09-08','15:34:50',13,11,1,102),(51,'','2023-09-08','15:35:07',13,11,1,102),(52,'jngvhjgvhg','2023-09-30','17:28:50',11,7,0,0),(53,'kjnbjkhbjh\n','2023-09-30','17:31:10',13,11,1,83),(54,'uhiuhuiybh\n','2023-09-30','17:31:25',11,13,1,17),(55,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris leo lectus, malesuada in augue sed, laoreet bibendum est. Nulla eget pulvinar risus.','2023-10-01','16:20:51',11,7,0,0),(56,'Lorem ipsum dolor sit amet, consectetur adipiscing elit. Mauris leo lectus, malesuada in augue sed, laoreet bibendum est. Nulla eget pulvinar risus.','2023-10-01','16:22:09',13,11,1,28),(57,'Duis non arcu arcu. Nullam non mauris euismod, venenatis ipsum nec','2023-10-01','16:24:31',11,13,1,4),(58,'Sed viverra molestie sem et porta. Integer non mi lorem. Integer rutrum nulla sit amet risus fringilla, ac faucibus est pellentesque. Donec risus neque, malesuada in erat vel, commodo tristique nisi. ','2023-10-01','16:24:53',13,11,1,9),(59,'Integer malesuada augue eros, eu hendrerit eros dignissim id. Phasellus urna orci, elementum at dolor ac, tempus congue lacus. ','2023-10-01','16:25:17',11,13,1,2);
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `nickname` varchar(45) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `email` varchar(45) NOT NULL,
  `picture` mediumtext,
  `version` int DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `id_UNIQUE` (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (7,'nickdudu','4e31285ed5660d5f92c258ea316b93f940409f32be4bef85722ed3e3d90b6b05','nick@email','picture_contact/nick@email',5),(11,'mario','852dfefc6008d552c9514d975be3394298a8383e81d85ab41124af324018ef02','mario@email','picture_contact/frog.jpg',0),(12,'luigi','2b3ac19d5bcf441304cb0656b5d08ea4b93b9e49e6d4e78920e3e80b2d432493','luigi@email','picture_contact/frog.jpg',0),(13,'karen','5b61ba1037e777160634c0d07895fb3fbf086a9b560adaad287069884b0ebaee','karen@email','picture_contact/frog.jpg',0);
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-10-05  9:42:08
