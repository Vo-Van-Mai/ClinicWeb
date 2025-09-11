-- MySQL dump 10.13  Distrib 8.0.40, for Win64 (x86_64)
--
-- Host: localhost    Database: healthdb
-- ------------------------------------------------------
-- Server version	9.1.0

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
-- Table structure for table `appointment`
--

DROP TABLE IF EXISTS `appointment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('PENDING','CONFIRM','CANCELLED','DONE') COLLATE utf8mb4_unicode_ci DEFAULT 'PENDING',
  `online` tinyint(1) DEFAULT '1',
  `room_url` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `payment_for_service` tinyint(1) DEFAULT '0',
  `payment_for_prescription` tinyint(1) DEFAULT '0',
  `patient_id` int DEFAULT NULL,
  `appointment_slot` int DEFAULT NULL,
  `service_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patient_appoint_idx` (`patient_id`),
  KEY `fk_apppoint_slot_idx` (`appointment_slot`),
  KEY `fk_service_appoint_idx` (`service_id`),
  CONSTRAINT `fk_apppoint_slot` FOREIGN KEY (`appointment_slot`) REFERENCES `appointmentslot` (`id`),
  CONSTRAINT `fk_patient_appoint` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`),
  CONSTRAINT `fk_service_appoint` FOREIGN KEY (`service_id`) REFERENCES `service` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointment`
--

LOCK TABLES `appointment` WRITE;
/*!40000 ALTER TABLE `appointment` DISABLE KEYS */;
INSERT INTO `appointment` VALUES (2,'2025-09-05 03:34:42','PENDING',1,'https://online-room.com/abcde',0,0,3,1,1);
/*!40000 ALTER TABLE `appointment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `appointmentslot`
--

DROP TABLE IF EXISTS `appointmentslot`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `appointmentslot` (
  `id` int NOT NULL AUTO_INCREMENT,
  `start_time` time NOT NULL,
  `end_time` time NOT NULL,
  `is_booked` tinyint(1) DEFAULT '0',
  `schedule_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appslot_schedule_idx` (`schedule_id`),
  CONSTRAINT `fk_appslot_schedule` FOREIGN KEY (`schedule_id`) REFERENCES `workschedule` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `appointmentslot`
--

LOCK TABLES `appointmentslot` WRITE;
/*!40000 ALTER TABLE `appointmentslot` DISABLE KEYS */;
INSERT INTO `appointmentslot` VALUES (1,'08:00:00','08:30:00',0,1),(2,'08:30:00','09:00:00',0,1),(3,'13:00:00','13:30:00',0,2);
/*!40000 ALTER TABLE `appointmentslot` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `pharmacy_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pharmacy_category_idx` (`pharmacy_id`),
  CONSTRAINT `fk_pharmacy_category` FOREIGN KEY (`pharmacy_id`) REFERENCES `pharmacy` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category`
--

LOCK TABLES `category` WRITE;
/*!40000 ALTER TABLE `category` DISABLE KEYS */;
INSERT INTO `category` VALUES (1,'Painkillers','Thuốc giảm đau.',1),(2,'Antibiotics','Thuốc kháng sinh điều trị nhiễm khuẩn.',1),(3,'Kháng viêm','Thuốc chống viêm không steroid (NSAID)',1),(4,'Tim mạch','Thuốc điều trị các bệnh về tim mạch',1),(5,'Hô hấp','Thuốc điều trị các bệnh đường hô hấp',1),(6,'Tiêu hóa','Thuốc hỗ trợ tiêu hóa, trị dạ dày',1),(7,'Vitamin - Khoáng chất','Vitamin và khoáng chất bổ sung',1),(8,'Nội tiết','Thuốc điều trị rối loạn nội tiết',1),(9,'Da liễu','Thuốc điều trị các bệnh ngoài da',1),(10,'Thần kinh','Thuốc an thần, giảm lo âu, động kinh',1),(11,'Kháng sinh','Thuốc diệt hoặc ức chế vi khuẩn',1),(12,'Giảm đau - Hạ sốt','Thuốc giảm đau và hạ sốt thông thường',1);
/*!40000 ALTER TABLE `category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `category_medicine`
--

DROP TABLE IF EXISTS `category_medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `category_id` int DEFAULT NULL,
  `medicine_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_category_catemedicine_idx` (`category_id`),
  KEY `fk_medicine_catemedicine_idx` (`medicine_id`),
  CONSTRAINT `fk_category_catemedicine` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_medicine_catemedicine` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `category_medicine`
--

LOCK TABLES `category_medicine` WRITE;
/*!40000 ALTER TABLE `category_medicine` DISABLE KEYS */;
INSERT INTO `category_medicine` VALUES (1,1,1),(2,2,2),(3,1,2);
/*!40000 ALTER TABLE `category_medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `id` int NOT NULL AUTO_INCREMENT,
  `doctor_id` int DEFAULT NULL,
  `patient_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_doctor_chat_idx` (`doctor_id`),
  KEY `fk_patient_chat_idx` (`patient_id`),
  CONSTRAINT `fk_doctor_chat` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE SET NULL,
  CONSTRAINT `fk_patient_chat` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `chat`
--

LOCK TABLES `chat` WRITE;
/*!40000 ALTER TABLE `chat` DISABLE KEYS */;
/*!40000 ALTER TABLE `chat` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor`
--

DROP TABLE IF EXISTS `doctor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor` (
  `id` int NOT NULL,
  `experience` text COLLATE utf8mb4_unicode_ci,
  `year_of_experience` int DEFAULT NULL,
  `license_number` varchar(45) COLLATE utf8mb4_unicode_ci NOT NULL,
  `is_verified` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_doctor` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor`
--

LOCK TABLES `doctor` WRITE;
/*!40000 ALTER TABLE `doctor` DISABLE KEYS */;
INSERT INTO `doctor` VALUES (2,'Cardiology specialist with over 15 years of experience.',15,'LIC-12345',1),(5,'General practitioner with 10 years of experience.',10,'LIC-67890',1),(6,'Chuyên khoa Tim mạch',10,'LIC-1006',1),(7,'Chuyên khoa Nội tổng quát',8,'LIC-1007',1),(8,'Chuyên khoa Nhi khoa',12,'LIC-1008',1),(9,'Chuyên khoa Ngoại tổng quát',15,'LIC-1009',1),(10,'Chuyên khoa Da liễu',7,'LIC-1010',1),(11,'Chuyên khoa Răng - Hàm - Mặt',9,'LIC-1011',1),(12,'Chuyên khoa Tai - Mũi - Họng',11,'LIC-1012',1),(13,'Chuyên khoa Mắt',13,'LIC-1013',1),(14,'Chuyên khoa Sản - Phụ khoa',10,'LIC-1014',1),(15,'Chuyên khoa Thần kinh',14,'LIC-1015',1);
/*!40000 ALTER TABLE `doctor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `doctor_specialize`
--

DROP TABLE IF EXISTS `doctor_specialize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `doctor_specialize` (
  `id` int NOT NULL AUTO_INCREMENT,
  `doctor_id` int DEFAULT NULL,
  `specialize_id` int DEFAULT NULL,
  `join_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_together` (`doctor_id`,`specialize_id`),
  KEY `fk_doctor_specialize_idx` (`doctor_id`),
  KEY `fk_specialize_id_idx` (`specialize_id`),
  CONSTRAINT `fk_doctor_id` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_specialize_id` FOREIGN KEY (`specialize_id`) REFERENCES `specialize` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `doctor_specialize`
--

LOCK TABLES `doctor_specialize` WRITE;
/*!40000 ALTER TABLE `doctor_specialize` DISABLE KEYS */;
INSERT INTO `doctor_specialize` VALUES (1,2,1,'2025-09-09 19:59:53'),(2,2,3,'2025-09-09 19:59:53'),(3,5,3,'2025-09-09 19:59:53'),(4,5,1,'2025-09-10 16:10:51'),(5,6,2,'2025-09-10 16:10:51'),(6,7,3,'2025-09-10 16:10:51'),(7,8,4,'2025-09-10 16:10:51'),(8,9,5,'2025-09-10 16:10:51'),(9,10,1,'2025-09-10 16:10:51'),(10,11,2,'2025-09-10 16:10:51'),(11,12,3,'2025-09-10 16:10:51'),(12,13,4,'2025-09-10 16:10:51'),(13,14,5,'2025-09-10 16:10:51'),(14,15,1,'2025-09-10 16:10:51');
/*!40000 ALTER TABLE `doctor_specialize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicalrecord`
--

DROP TABLE IF EXISTS `medicalrecord`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicalrecord` (
  `id` int NOT NULL AUTO_INCREMENT,
  `diagnosis` text COLLATE utf8mb4_unicode_ci,
  `symptoms` text COLLATE utf8mb4_unicode_ci,
  `test_results` text COLLATE utf8mb4_unicode_ci,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `appointment_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointment_medicalrecord_idx` (`appointment_id`),
  CONSTRAINT `fk_appointment_medicalrecord` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicalrecord`
--

LOCK TABLES `medicalrecord` WRITE;
/*!40000 ALTER TABLE `medicalrecord` DISABLE KEYS */;
INSERT INTO `medicalrecord` VALUES (2,'Common Cold','Sổ mũi, hắt hơi, đau họng.','Không có kết quả bất thường.','2025-09-05 03:50:35','2025-09-05 03:50:35',2);
/*!40000 ALTER TABLE `medicalrecord` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `medicine`
--

DROP TABLE IF EXISTS `medicine`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `medicine` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `brand_name` varchar(255) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `unit` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `expiry_date` datetime DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `insurance_covered` tinyint(1) DEFAULT '0',
  `stock` int DEFAULT NULL,
  `status` enum('AVAILABLE','OUT_OF_STOCK','EXPIRED','DISCONTINUED') COLLATE utf8mb4_unicode_ci DEFAULT 'AVAILABLE',
  `updated_date` datetime DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `medicine`
--

LOCK TABLES `medicine` WRITE;
/*!40000 ALTER TABLE `medicine` DISABLE KEYS */;
INSERT INTO `medicine` VALUES (1,'Paracetamol','Panadol',100,'tablet','Thuốc giảm đau và hạ sốt.','2026-12-31 00:00:00',5.50,1,500,'AVAILABLE','2025-09-05 03:44:44','2025-09-05 03:44:44'),(2,'Amoxicillin','Amoxil',50,'capsule','Kháng sinh điều trị nhiễm khuẩn.','2027-06-30 00:00:00',12.75,1,250,'AVAILABLE','2025-09-05 03:44:44','2025-09-05 03:44:44'),(3,'Amoxicillin','Amoxil',100,'viên','Kháng sinh phổ rộng','2025-12-31 00:00:00',5000.00,1,100,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(4,'Cefuroxime','Zinnat',50,'viên','Kháng sinh điều trị viêm họng, viêm phổi','2026-06-30 00:00:00',15000.00,1,50,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(5,'Paracetamol','Panadol',200,'viên','Thuốc giảm đau, hạ sốt','2025-08-15 00:00:00',2000.00,1,200,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(6,'Ibuprofen','Brufen',120,'viên','Thuốc giảm đau, kháng viêm','2026-01-20 00:00:00',7000.00,1,120,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(7,'Diclofenac','Voltaren',90,'viên','Kháng viêm không steroid (NSAID)','2026-04-10 00:00:00',8000.00,1,90,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(8,'Amlodipine','Norvasc',75,'viên','Điều trị cao huyết áp, đau thắt ngực','2026-02-28 00:00:00',12000.00,1,75,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(9,'Atorvastatin','Lipitor',60,'viên','Giảm cholesterol máu','2025-11-15 00:00:00',18000.00,1,60,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(10,'Salbutamol','Ventolin',40,'ống hít','Thuốc giãn phế quản, trị hen suyễn','2026-07-01 00:00:00',90000.00,1,40,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(11,'Bromhexin','Bisotab',150,'viên','Thuốc long đờm','2025-10-20 00:00:00',4000.00,1,150,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(12,'Omeprazole','Losec',100,'viên','Điều trị trào ngược dạ dày thực quản','2026-03-30 00:00:00',10000.00,1,100,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(13,'Domperidone','Motilium',80,'viên','Chống nôn, khó tiêu','2025-09-25 00:00:00',7000.00,1,80,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(14,'Vitamin C','Cebion',300,'viên','Bổ sung vitamin C tăng sức đề kháng','2027-03-10 00:00:00',1000.00,0,300,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(15,'Vitamin D3','Vigantol',80,'lọ','Bổ sung vitamin D cho xương chắc khỏe','2026-09-01 00:00:00',25000.00,0,80,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(16,'Calcium Carbonate','CalciK2',120,'viên','Bổ sung canxi','2027-01-15 00:00:00',15000.00,0,120,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(17,'Levothyroxine','Euthyrox',50,'viên','Điều trị suy giáp','2026-06-10 00:00:00',9000.00,1,50,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(18,'Ketoconazole','Nizoral',30,'chai','Dầu gội trị nấm da đầu','2026-05-05 00:00:00',65000.00,0,30,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(19,'Clobetasol','Dermovate',45,'tuýp','Thuốc bôi trị viêm da, vảy nến','2025-12-12 00:00:00',120000.00,0,45,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(20,'Diazepam','Seduxen',25,'viên','Thuốc an thần, chống co giật','2026-08-22 00:00:00',5000.00,1,25,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(21,'Carbamazepine','Tegretol',40,'viên','Điều trị động kinh, đau dây thần kinh','2026-11-11 00:00:00',18000.00,1,40,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35'),(22,'Amitriptyline','Laroxyl',60,'viên','Điều trị trầm cảm, đau thần kinh','2026-10-05 00:00:00',15000.00,1,60,'AVAILABLE','2025-09-09 22:03:35','2025-09-09 22:03:35');
/*!40000 ALTER TABLE `medicine` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `message`
--

DROP TABLE IF EXISTS `message`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `message` (
  `id` int NOT NULL AUTO_INCREMENT,
  `content` text COLLATE utf8mb4_unicode_ci,
  `sender` int NOT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `chat_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_message_chat_idx` (`chat_id`),
  CONSTRAINT `fk_message_chat` FOREIGN KEY (`chat_id`) REFERENCES `chat` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `message`
--

LOCK TABLES `message` WRITE;
/*!40000 ALTER TABLE `message` DISABLE KEYS */;
/*!40000 ALTER TABLE `message` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `notification`
--

DROP TABLE IF EXISTS `notification`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `notification` (
  `id` int NOT NULL AUTO_INCREMENT,
  `title` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `message` text COLLATE utf8mb4_unicode_ci NOT NULL,
  `type` enum('SYSTEM','WARNING','INFO') COLLATE utf8mb4_unicode_ci DEFAULT 'INFO',
  `check_read` tinyint(1) DEFAULT '0',
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_notification_user_idx` (`user_id`),
  CONSTRAINT `fk_notification_user` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `notification`
--

LOCK TABLES `notification` WRITE;
/*!40000 ALTER TABLE `notification` DISABLE KEYS */;
/*!40000 ALTER TABLE `notification` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `patient`
--

DROP TABLE IF EXISTS `patient`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `patient` (
  `id` int NOT NULL,
  `insurance` varchar(45) COLLATE utf8mb4_unicode_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  CONSTRAINT `fk_user_patient` FOREIGN KEY (`id`) REFERENCES `user` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `patient`
--

LOCK TABLES `patient` WRITE;
/*!40000 ALTER TABLE `patient` DISABLE KEYS */;
INSERT INTO `patient` VALUES (3,'AETNA-123');
/*!40000 ALTER TABLE `patient` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payment`
--

DROP TABLE IF EXISTS `payment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payment` (
  `id` int NOT NULL AUTO_INCREMENT,
  `method` enum('CASH','ONLINE') COLLATE utf8mb4_unicode_ci DEFAULT 'ONLINE',
  `total_amount` decimal(10,2) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `type_service_payment` enum('SERVICE','PRESCRIPTION') COLLATE utf8mb4_unicode_ci DEFAULT 'SERVICE',
  `appointment_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_appointment_payment_idx` (`appointment_id`),
  CONSTRAINT `fk_appointment_payment` FOREIGN KEY (`appointment_id`) REFERENCES `appointment` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payment`
--

LOCK TABLES `payment` WRITE;
/*!40000 ALTER TABLE `payment` DISABLE KEYS */;
/*!40000 ALTER TABLE `payment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `paymentdetail`
--

DROP TABLE IF EXISTS `paymentdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `paymentdetail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `amount` decimal(10,2) NOT NULL,
  `reference_id` int DEFAULT NULL,
  `status` enum('PENDING','SUCCESSFUL','FAILD') COLLATE utf8mb4_unicode_ci DEFAULT 'PENDING',
  `payment_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_payment_paymentdetail_idx` (`payment_id`),
  CONSTRAINT `fk_payment_paymentdetail` FOREIGN KEY (`payment_id`) REFERENCES `payment` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `paymentdetail`
--

LOCK TABLES `paymentdetail` WRITE;
/*!40000 ALTER TABLE `paymentdetail` DISABLE KEYS */;
/*!40000 ALTER TABLE `paymentdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pharmacy`
--

DROP TABLE IF EXISTS `pharmacy`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pharmacy` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pharmacy`
--

LOCK TABLES `pharmacy` WRITE;
/*!40000 ALTER TABLE `pharmacy` DISABLE KEYS */;
INSERT INTO `pharmacy` VALUES (1,'City Pharmacy');
/*!40000 ALTER TABLE `pharmacy` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescriptdetail`
--

DROP TABLE IF EXISTS `prescriptdetail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescriptdetail` (
  `id` int NOT NULL AUTO_INCREMENT,
  `medicine_id` int DEFAULT NULL,
  `prescription_id` int DEFAULT NULL,
  `quantiry` int DEFAULT NULL,
  `price_now` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_medicine_detail_idx` (`medicine_id`),
  KEY `fk_pres_detail_idx` (`prescription_id`),
  CONSTRAINT `fk_medicine_detail` FOREIGN KEY (`medicine_id`) REFERENCES `medicine` (`id`),
  CONSTRAINT `fk_pres_detail` FOREIGN KEY (`prescription_id`) REFERENCES `prescription` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescriptdetail`
--

LOCK TABLES `prescriptdetail` WRITE;
/*!40000 ALTER TABLE `prescriptdetail` DISABLE KEYS */;
INSERT INTO `prescriptdetail` VALUES (1,1,2,21,5.50);
/*!40000 ALTER TABLE `prescriptdetail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `prescription`
--

DROP TABLE IF EXISTS `prescription`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `prescription` (
  `id` int NOT NULL AUTO_INCREMENT,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `note` text COLLATE utf8mb4_unicode_ci,
  `frequency` int DEFAULT NULL,
  `duration` int DEFAULT NULL,
  `intruction` text COLLATE utf8mb4_unicode_ci,
  `medicalrecord_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_pres_medicalrecord_idx` (`medicalrecord_id`),
  CONSTRAINT `fk_pres_medicalrecord` FOREIGN KEY (`medicalrecord_id`) REFERENCES `medicalrecord` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `prescription`
--

LOCK TABLES `prescription` WRITE;
/*!40000 ALTER TABLE `prescription` DISABLE KEYS */;
INSERT INTO `prescription` VALUES (2,'2025-09-05 03:51:25','Uống sau bữa ăn.',3,7,'Uống một viên ba lần một ngày trong 7 ngày.',2);
/*!40000 ALTER TABLE `prescription` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `rating`
--

DROP TABLE IF EXISTS `rating`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `rating` (
  `id` int NOT NULL AUTO_INCREMENT,
  `star_number` decimal(2,1) NOT NULL,
  `patient_id` int DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_patient_rating_idx` (`patient_id`),
  KEY `fk_doctor_rating_idx` (`doctor_id`),
  CONSTRAINT `fk_doctor_rating` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE,
  CONSTRAINT `fk_patient_rating` FOREIGN KEY (`patient_id`) REFERENCES `patient` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `rating`
--

LOCK TABLES `rating` WRITE;
/*!40000 ALTER TABLE `rating` DISABLE KEYS */;
INSERT INTO `rating` VALUES (3,4.3,3,2),(4,4.5,3,5);
/*!40000 ALTER TABLE `rating` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `specialize_id` int DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name_UNIQUE` (`name`),
  KEY `fk_service_specialize_idx` (`specialize_id`),
  CONSTRAINT `fk_service_specialize` FOREIGN KEY (`specialize_id`) REFERENCES `specialize` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (1,'Tư vấn tim mạch',500000.00,1,1),(2,'Kiểm tra da liễu',200000.00,2,1),(3,'Khám tai mũi họng cơ bản',150000.00,3,1),(4,'Khám tim mạch tổng quát',300000.00,1,1),(5,'Điện tâm đồ (ECG)',250000.00,1,1),(6,'Siêu âm tim Doppler màu',600000.00,1,1),(7,'Khám da liễu tổng quát',200000.00,2,1),(8,'Điều trị mụn chuyên sâu',500000.00,2,1),(9,'Xóa sẹo bằng laser',1200000.00,2,1),(10,'Khám tai mũi họng tổng quát',200000.00,3,1),(11,'Nội soi tai mũi họng',350000.00,3,1),(12,'Lấy dị vật tai - mũi - họng',400000.00,3,1),(13,'Khám sức khỏe tổng quát',400000.00,4,1),(14,'Xét nghiệm máu cơ bản',250000.00,4,1),(15,'Khám và tư vấn tiểu đường',300000.00,4,1),(16,'Khám ngoại khoa tổng quát',350000.00,5,1),(17,'Phẫu thuật cắt ruột thừa',7000000.00,5,1),(18,'Cắt u mỡ dưới da',2000000.00,5,1),(19,'Khám nhi tổng quát',250000.00,6,1),(20,'Tiêm chủng mở rộng cho trẻ',150000.00,6,1),(21,'Khám và điều trị sốt siêu vi',200000.00,6,1),(22,'Khám phụ khoa tổng quát',300000.00,7,1),(23,'Siêu âm thai 4D',500000.00,7,1),(24,'Khám và tư vấn kế hoạch hóa gia đình',250000.00,7,1);
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `specialize`
--

DROP TABLE IF EXISTS `specialize`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `specialize` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL,
  `description` text COLLATE utf8mb4_unicode_ci,
  `is_active` tinyint(1) DEFAULT '1',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `specialize`
--

LOCK TABLES `specialize` WRITE;
/*!40000 ALTER TABLE `specialize` DISABLE KEYS */;
INSERT INTO `specialize` VALUES (1,'Tim mạch','Chuyên tư vấn và giải quyết các vấn đề về tim mạch, Tim mạch là nền tảng của sự sống',1),(2,'Da liễu','Chuyên tư vấn và giải quyết các vấn đề về da liễu, các bệnh ngoài gia, viêm ngữa,...',1),(3,'Tai mũi họng','Chẩn đoán và điều trị các bệnh lý về tai, mũi, họng và thanh quản.',1),(4,'Nội tổng quát','Khám và điều trị các bệnh lý nội khoa phổ biến như huyết áp, tiểu đường, tim mạch.',1),(5,'Ngoại tổng quát','Thực hiện phẫu thuật và điều trị các bệnh lý cần can thiệp ngoại khoa.',1),(6,'Nhi khoa','Khám và điều trị bệnh cho trẻ sơ sinh, trẻ nhỏ và thanh thiếu niên.',1),(7,'Sản - Phụ khoa','Khám, tư vấn và điều trị các vấn đề sức khỏe sinh sản, thai sản và phụ khoa.',1);
/*!40000 ALTER TABLE `specialize` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user`
--

DROP TABLE IF EXISTS `user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `password` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `first_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `last_name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
  `date_of_birth` datetime DEFAULT NULL,
  `phone` varchar(15) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `email` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `is_admin` tinyint(1) DEFAULT '0',
  `gender` enum('MALE','FEMALE','OTHER') COLLATE utf8mb4_unicode_ci DEFAULT 'OTHER',
  `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci DEFAULT NULL,
  `created_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `role` enum('ADMIN','DOCTOR','PATIENT') CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_520_ci NOT NULL DEFAULT 'PATIENT',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username_UNIQUE` (`username`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `phone_UNIQUE` (`phone`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user`
--

LOCK TABLES `user` WRITE;
/*!40000 ALTER TABLE `user` DISABLE KEYS */;
INSERT INTO `user` VALUES (2,'aaaaa','123456','A','Nguyen Van','1980-05-15 00:00:00','1234567890','nguyenvana@gmail.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-05 03:16:59','DOCTOR'),(3,'bbbbb','123456','B','Nguyen Van','1980-05-15 00:00:00','1234567891','nguyenvanb@gmail.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-05 03:16:59','PATIENT'),(4,'admin','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Admin','User','1975-01-01 00:00:00','1122334455','admin@example.com',1,1,'OTHER','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-05 03:16:59','ADMIN'),(5,'anhtuan','password789','Anh','Tuan','1985-03-25 00:00:00','1112223333','anhtuan@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-05 03:16:59','DOCTOR'),(6,'vanmai','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Mãi','Võ Văn','1980-05-12 00:00:00','0901234567','nguyen.bacsi@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(7,'honganh','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Anh','Nguyễn Thị Hồng ','1985-03-20 00:00:00','0902234567','tran.bacsi@example.com',1,0,'FEMALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(8,'phuongnam','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Nam','Đặng Phương','1978-09-08 00:00:00','0903234567','le.bacsi@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(9,'dr.pham','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Lan','Phạm Hoàng ','1990-11-02 00:00:00','0904234567','pham.bacsi@example.com',1,0,'FEMALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(10,'dr.hoang','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Sơn','Hoàng Văn','1982-01-15 00:00:00','0905234567','hoang.bacsi@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(11,'dr.vo','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Anh','Võ Thị Minh','1992-07-25 00:00:00','0906234567','vo.bacsi@example.com',1,0,'FEMALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(12,'dr.bui','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Minh','Bùi Anh ','1988-12-05 00:00:00','0907234567','bui.bacsi@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(13,'dr.dang','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Ly','Phan Hoang Yến','1995-04-30 00:00:00','0908234567','dang.bacsi@example.com',1,0,'FEMALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(14,'dr.truong','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Tín','Lê Trung','1983-08-18 00:00:00','0909234567','truong.bacsi@example.com',1,0,'MALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR'),(15,'dr.dinh','$2a$10$NqClHgKfkwMVYLpDBLIC5u2CIei42Hoo7PP.dL1PO7r2FCOsqp.GO','Hoa','Trân Thị','1987-06-22 00:00:00','0910234567','dinh.bacsi@example.com',1,0,'FEMALE','https://res.cloudinary.com/disqxvj3s/image/upload/v1754715964/fsgua3oudxllx9iry1g8.jpg','2025-09-07 23:00:51','DOCTOR');
/*!40000 ALTER TABLE `user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `workschedule`
--

DROP TABLE IF EXISTS `workschedule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `workschedule` (
  `id` int NOT NULL AUTO_INCREMENT,
  `date_work` datetime DEFAULT NULL,
  `start_time` time DEFAULT NULL,
  `end_time` time DEFAULT NULL,
  `doctor_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_doctor_workschedule_idx` (`doctor_id`),
  CONSTRAINT `fk_doctor_workschedule` FOREIGN KEY (`doctor_id`) REFERENCES `doctor` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `workschedule`
--

LOCK TABLES `workschedule` WRITE;
/*!40000 ALTER TABLE `workschedule` DISABLE KEYS */;
INSERT INTO `workschedule` VALUES (1,'2025-09-08 00:00:00','08:00:00','12:00:00',2),(2,'2025-09-09 00:00:00','13:00:00','17:00:00',5);
/*!40000 ALTER TABLE `workschedule` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-09-11 12:36:51
