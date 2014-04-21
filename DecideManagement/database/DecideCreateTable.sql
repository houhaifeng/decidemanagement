
use decide;

DROP TABLE IF EXISTS `product`;
create table `product`(
	`id` bigint(20) AUTO_INCREMENT,
	`name` varchar(500) DEFAULT NULL,
	`link` varchar(500) DEFAULT NULL,
	`category` varchar(100) DEFAULT NULL,
	`brand` varchar(100) DEFAULT NULL,
	`image` varchar(500) DEFAULT NULL,
	`view_rate` int(11) DEFAULT 0,
	`collect_rate` int(11) DEFAULT 0,
	`visibilty` boolean DEFAULT NULL,
	`score` double DEFAULT 0,
	`parameters` varchar(500) DEFAULT NULL,
	`create_date` bigint(20) DEFAULT NULL,
	`source_id` bigint(20) DEFAULT 0,
	`source` varchar(100) DEFAULT NULL,
	`exist` boolean DEFAULT NULL,
	`price` double DEFAULT NULL,
	PRIMARY KEY(`id`)
)ENGINE=InnoDB DEFAULT CHARSET=utf8;


DROP TABLE IF EXISTS `category`;
CREATE TABLE `category` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `idenityname` varchar(100) DEFAULT NULL,
  `description` varchar(100) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `grade` int(11) DEFAULT NULL,
  `uri` varchar(100) DEFAULT NULL,
  `orders` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `idenityname` (`idenityname`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `comment`;
CREATE TABLE `comment` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `title` varchar(200) DEFAULT NULL,
  `content` text,
  `starnum` int(11) DEFAULT NULL,
  `create_date` bigint(20) DEFAULT NULL,
  `state` int(11) DEFAULT NULL,
  `pid` int(11) DEFAULT NULL,
  `uid` int(11) DEFAULT NULL,
  `orderid` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;

DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `mail` varchar(100) NOT NULL,
  `password` varchar(100) DEFAULT NULL,
  `date` bigint(20) DEFAULT NULL,
  `role` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `mail` (`mail`)
) ENGINE=InnoDB AUTO_INCREMENT=0 DEFAULT CHARSET=utf8;