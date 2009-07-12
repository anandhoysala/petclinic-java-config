-- phpMyAdmin SQL Dump
-- version 2.10.3
-- http://www.phpmyadmin.net
-- 
-- Host: localhost
-- Generation Time: Jul 12, 2009 at 05:00 PM
-- Server version: 5.0.51
-- PHP Version: 5.2.8

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";

-- 
-- Database: `petclinic_dev`
-- 

-- --------------------------------------------------------

-- 
-- Table structure for table `owners`
-- 

CREATE TABLE `owners` (
  `id` int(11) NOT NULL auto_increment,
  `firstName` varchar(255) default NULL,
  `lastName` varchar(255) default NULL,
  `address` varchar(255) default NULL,
  `city` varchar(255) default NULL,
  `telephone` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=11 ;

-- 
-- Dumping data for table `owners`
-- 

INSERT INTO `owners` VALUES (1, 'Georgex', 'Franklin', '110 W. Liberty St.', 'Madison', '6085551023');
INSERT INTO `owners` VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', 'Sun Prairie', '6085551749');
INSERT INTO `owners` VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', 'McFarland', '6085558763');
INSERT INTO `owners` VALUES (4, 'Harold', 'Davis', '563 Friendly St.', 'Windsor', '6085553198');
INSERT INTO `owners` VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', 'Madison', '6085552765');
INSERT INTO `owners` VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', 'Monona', '6085552654');
INSERT INTO `owners` VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', 'Monona', '6085555387');
INSERT INTO `owners` VALUES (8, 'Maria', 'Escobito', '345 Maple St.', 'Madison', '6085557683');
INSERT INTO `owners` VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', 'Madison', '6085559435');
INSERT INTO `owners` VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', 'Waunakee', '6085555487');

-- --------------------------------------------------------

-- 
-- Table structure for table `pets`
-- 

CREATE TABLE `pets` (
  `id` int(11) NOT NULL auto_increment,
  `birthDate` date default NULL,
  `name` varchar(255) default NULL,
  `owner_fk` int(11) default NULL,
  `pettype_fk` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK256B348C45C774` (`owner_fk`),
  KEY `FK256B34C58CC5D4` (`pettype_fk`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=14 ;

-- 
-- Dumping data for table `pets`
-- 

INSERT INTO `pets` VALUES (1, '2000-09-07', 'Leo', 1, 1);
INSERT INTO `pets` VALUES (2, '2002-08-06', 'Basil', 2, 6);
INSERT INTO `pets` VALUES (3, '2001-04-17', 'Rosy', 3, 2);
INSERT INTO `pets` VALUES (4, '2000-03-07', 'Jewel', 3, 2);
INSERT INTO `pets` VALUES (5, '2000-11-30', 'Iggy', 4, 3);
INSERT INTO `pets` VALUES (6, '2000-01-20', 'George', 5, 4);
INSERT INTO `pets` VALUES (7, '1995-09-04', 'Samantha', 6, 1);
INSERT INTO `pets` VALUES (8, '1995-09-04', 'Max', 6, 1);
INSERT INTO `pets` VALUES (9, '1999-08-06', 'Lucky', 7, 5);
INSERT INTO `pets` VALUES (10, '1997-02-24', 'Mulligan', 8, 2);
INSERT INTO `pets` VALUES (11, '2000-03-09', 'Freddy', 9, 5);
INSERT INTO `pets` VALUES (12, '2000-06-24', 'Lucky', 10, 2);
INSERT INTO `pets` VALUES (13, '2002-06-08', 'Sly', 10, 1);

-- --------------------------------------------------------

-- 
-- Table structure for table `specialties`
-- 

CREATE TABLE `specialties` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=4 ;

-- 
-- Dumping data for table `specialties`
-- 

INSERT INTO `specialties` VALUES (1, 'radiology');
INSERT INTO `specialties` VALUES (2, 'dentistry');
INSERT INTO `specialties` VALUES (3, 'surgery');

-- --------------------------------------------------------

-- 
-- Table structure for table `types`
-- 

CREATE TABLE `types` (
  `id` int(11) NOT NULL auto_increment,
  `name` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- Dumping data for table `types`
-- 

INSERT INTO `types` VALUES (1, 'cat');
INSERT INTO `types` VALUES (2, 'xx');
INSERT INTO `types` VALUES (3, 'aa');
INSERT INTO `types` VALUES (4, 'snake');
INSERT INTO `types` VALUES (5, 'oo');
INSERT INTO `types` VALUES (6, 'bb');

-- --------------------------------------------------------

-- 
-- Table structure for table `vets`
-- 

CREATE TABLE `vets` (
  `id` int(11) NOT NULL auto_increment,
  `firstName` varchar(255) default NULL,
  `lastName` varchar(255) default NULL,
  PRIMARY KEY  (`id`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=7 ;

-- 
-- Dumping data for table `vets`
-- 

INSERT INTO `vets` VALUES (1, 'James', 'Carter');
INSERT INTO `vets` VALUES (2, 'Helen', 'Leary');
INSERT INTO `vets` VALUES (3, 'Linda', 'Douglas');
INSERT INTO `vets` VALUES (4, 'Rafael', 'Ortega');
INSERT INTO `vets` VALUES (5, 'Henry', 'Stevens');
INSERT INTO `vets` VALUES (6, 'Sharon', 'Jenkins');

-- --------------------------------------------------------

-- 
-- Table structure for table `vets_specialties`
-- 

CREATE TABLE `vets_specialties` (
  `vet_id` int(11) NOT NULL,
  `name` int(11) NOT NULL,
  UNIQUE KEY `vet_id` (`vet_id`,`name`),
  KEY `FK97B3F0ABDC2ABE79` (`name`),
  KEY `FK97B3F0AB4174468A` (`vet_id`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8;

-- 
-- Dumping data for table `vets_specialties`
-- 

INSERT INTO `vets_specialties` VALUES (2, 1);
INSERT INTO `vets_specialties` VALUES (3, 2);
INSERT INTO `vets_specialties` VALUES (3, 3);

-- --------------------------------------------------------

-- 
-- Table structure for table `visits`
-- 

CREATE TABLE `visits` (
  `id` int(11) NOT NULL auto_increment,
  `description` varchar(255) default NULL,
  `name` varchar(255) default NULL,
  `visitDate` date default NULL,
  `pet_fk` int(11) default NULL,
  PRIMARY KEY  (`id`),
  KEY `FK96EC508837371BF4` (`pet_fk`)
) ENGINE=MyISAM  DEFAULT CHARSET=utf8 AUTO_INCREMENT=5 ;

-- 
-- Dumping data for table `visits`
-- 

INSERT INTO `visits` VALUES (1, 'rabies shot', 'name', '1996-03-04', 7);
INSERT INTO `visits` VALUES (2, 'rabies shot', 'name', '1996-03-04', 8);
INSERT INTO `visits` VALUES (3, 'neutered', 'name', '1996-03-04', 8);
INSERT INTO `visits` VALUES (4, 'spayed', 'name', '1996-03-04', 7);
