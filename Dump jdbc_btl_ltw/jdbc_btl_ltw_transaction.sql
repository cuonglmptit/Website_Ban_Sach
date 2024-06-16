-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: localhost    Database: jdbc_btl_ltw
-- ------------------------------------------------------
-- Server version	8.0.34

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
-- Table structure for table `transaction`
--

DROP TABLE IF EXISTS `transaction`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaction` (
  `transid` int NOT NULL AUTO_INCREMENT,
  `buyeruid` int DEFAULT NULL,
  `bookbid` int DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `deliveryaddress` text,
  `buyername` varchar(45) DEFAULT NULL,
  `buyerphone` varchar(45) DEFAULT NULL,
  `buyeremail` varchar(100) DEFAULT NULL,
  `created` datetime DEFAULT NULL,
  `note` text,
  `status` int DEFAULT NULL,
  PRIMARY KEY (`transid`),
  KEY `fk_trans_uid_idx` (`buyeruid`),
  KEY `fk_trans_book_idx` (`bookbid`),
  CONSTRAINT `fk_trans_book` FOREIGN KEY (`bookbid`) REFERENCES `book` (`bid`),
  CONSTRAINT `fk_trans_uid` FOREIGN KEY (`buyeruid`) REFERENCES `user` (`uid`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaction`
--

LOCK TABLES `transaction` WRITE;
/*!40000 ALTER TABLE `transaction` DISABLE KEYS */;
INSERT INTO `transaction` VALUES (1,2,1,10,'LaoKay','User mới 2JE3EMUUE1L','0123456789','cuong1@gmail.com','2024-06-16 20:45:29','Giao gio hanh chinh',2),(2,2,1,1,'LC','User mới 2JE3EMUUE1L','0123456789','cuong1@gmail.com','2024-06-16 20:46:24','Nhanh',3),(3,2,1,1,'LC','User mới 2JE3EMUUE1L','0123456789','cuong1@gmail.com','2024-06-16 20:46:57','Hihi',1);
/*!40000 ALTER TABLE `transaction` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-16 20:51:27
