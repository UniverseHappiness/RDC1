/*
SQLyog Enterprise v12.09 (64 bit)
MySQL - 5.7.33-log : Database - db1
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`db1` /*!40100 DEFAULT CHARACTER SET utf8 */;

USE `db1`;

/*Table structure for table `note` */

DROP TABLE IF EXISTS `note`;

CREATE TABLE `note` (
  `like` int(11) NOT NULL,
  `note_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) DEFAULT NULL,
  `title` varchar(25) DEFAULT NULL,
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `visibility` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`note_id`),
  KEY `note_user_user_name__fk` (`user_name`),
  CONSTRAINT `note_user_user_name__fk` FOREIGN KEY (`user_name`) REFERENCES `user` (`user_name`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=42 DEFAULT CHARSET=utf8;

/*Data for the table `note` */

insert  into `note`(`like`,`note_id`,`user_name`,`title`,`create_time`,`update_time`,`visibility`) values (0,29,'李华','高三','2021-04-14 08:40:07','2021-04-15 16:52:37',0),(0,30,'李华','bcjksbcjkbdsjk','2021-04-14 08:40:27',NULL,0),(2,31,'66','生物2','2021-04-14 08:52:20','2021-04-14 21:48:08',1),(0,32,'66','bbhkbkj','2021-04-14 23:20:50',NULL,0),(0,33,'66','dd','2021-04-14 23:54:33',NULL,0),(0,34,'66','ss','2021-04-14 23:57:02',NULL,0),(0,35,'66','2','2021-04-15 14:12:19',NULL,0),(0,36,'测试101','22','2021-04-15 14:13:12',NULL,0),(0,37,'123','','2021-04-15 17:43:10','2021-04-15 17:43:15',0),(0,38,'123','gf','2021-04-15 21:16:36',NULL,0),(0,39,'123','23','2021-04-15 21:18:32',NULL,0),(0,40,'66','123','2021-04-15 21:21:12',NULL,0);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `user_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_name` varchar(25) DEFAULT NULL,
  `password` varchar(20) DEFAULT NULL,
  `note_number` int(11) DEFAULT '0',
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `user_name` (`user_name`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

/*Data for the table `user` */

insert  into `user`(`user_id`,`user_name`,`password`,`note_number`) values (11,'66','33',7),(12,'123','123',7),(13,'李华','111',7),(14,'11','22',7),(15,'aa','11',7),(16,'1','',7),(17,'测试101','1',7);

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
