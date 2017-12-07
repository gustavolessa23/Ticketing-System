# ************************************************************
# Sequel Pro SQL dump
# Version 4541
#
# http://www.sequelpro.com/
# https://github.com/sequelpro/sequelpro
#
# Host: 127.0.0.1 (MySQL 5.5.5-10.1.26-MariaDB)
# Database: ticketing
# Generation Time: 2017-12-07 20:21:06 +0000
# ************************************************************


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


# Dump of table tickets
# ------------------------------------------------------------

DROP TABLE IF EXISTS `tickets`;

CREATE TABLE `tickets` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `creation_date` int(50) NOT NULL,
  `close_date` int(50) DEFAULT NULL,
  `tech_id` int(11) unsigned NOT NULL,
  `description` text,
  `priority` enum('Urgent','Normal','Longterm') NOT NULL DEFAULT 'Normal',
  `time_taken` int(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `tech_id_ticket` (`tech_id`),
  CONSTRAINT `tech_id_ticket` FOREIGN KEY (`tech_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `tickets` WRITE;
/*!40000 ALTER TABLE `tickets` DISABLE KEYS */;

INSERT INTO `tickets` (`id`, `creation_date`, `close_date`, `tech_id`, `description`, `priority`, `time_taken`)
VALUES
	(17,1510690456,1510697275,9,'User had problems accessing his account.\nProblem is being addressed.\n\nSolved.','Urgent',6819),
	(23,1510702086,1511349448,9,'System not starting up.\nWaiting for feedback.\n\nSolved.','Normal',647362),
	(31,1510778240,NULL,9,'Connection can not be made.\nTechnitian sent to installation address.\nWaiting for feedback.','Urgent',NULL),
	(32,1511282265,NULL,10,'All account files were deleted.\nChecking backup availability.','Urgent',NULL),
	(34,1511341884,1511347888,11,'System running slow.\n\nSystem checkup performed.\n\nFresh installation recommended.\n\nCustomer\'s feedbacks says everything is ok.\n\nSolved.','Longterm',6004),
	(36,1511348930,NULL,10,'System shuts down automatically.\n\nWaiting for antivirus report.','Normal',NULL);

/*!40000 ALTER TABLE `tickets` ENABLE KEYS */;
UNLOCK TABLES;


# Dump of table users
# ------------------------------------------------------------

DROP TABLE IF EXISTS `users`;

CREATE TABLE `users` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '',
  `password` varchar(255) NOT NULL DEFAULT '',
  `type` enum('Admin','Manager','Tech') NOT NULL DEFAULT 'Tech',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;

INSERT INTO `users` (`id`, `name`, `password`, `type`)
VALUES
	(1,'admin','$2a$10$wrW9OOfOC.SVCHfK09G20.7fLdMY90uKsZqm7om4TS9gklKZJ8sPe','Admin'),
	(3,'manager','$2a$10$RRm.aJcDRLUsE5plLDltRONJl6JKD8n8a8VBCRvh77mzhhIa0MR0W','Manager'),
	(9,'John','$2a$10$1yEMBrbtfwrLdAQkkzDaWuXDU/KEG8Se4ZsZTQddvMSMZMThBsw/m','Tech'),
	(10,'James','$2a$10$8Hxr9gg/3IZxTlr.JtzN5OJlsMncScbqbZwhFVHmq4EWqEc0DwiL2','Tech'),
	(11,'Johan','$2a$10$P./GN9LuVWir5Qhbrbtg8.3MCYX3VyYAkFIoCytk8vNtzW0Eqx8cu','Tech');

/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;



/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
