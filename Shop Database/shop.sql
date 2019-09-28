-- phpMyAdmin SQL Dump
-- version 3.1.3.1
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Dec 16, 2018 at 11:38 PM
-- Server version: 5.1.33
-- PHP Version: 5.2.9

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `shop`
--

-- --------------------------------------------------------

--
-- Table structure for table `customer`
--

CREATE TABLE IF NOT EXISTS `customer` (
  `userID` varchar(10) NOT NULL,
  `customerName` varchar(15) NOT NULL,
  `address` varchar(30) NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `customerName` (`customerName`,`address`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `customer`
--

INSERT INTO `customer` (`userID`, `customerName`, `address`) VALUES
('C_1', 'fdhfgh', 'h'),
('C_2', 'Samiul Islam', 'Mirpur, Dhaka'),
('C_0', 'Samiul Islam', 'Mirpur'),
('C_3', 'Test15', '123'),
('C_4', 'Buyer', 'barisal'),
('C_5', 'test', '123'),
('C_6', 'test2', '1234'),
('C_7', 'test22', '33'),
('C_8', 'test', 'kuril'),
('C_9', 'test33', 'rajshahi');

-- --------------------------------------------------------

--
-- Table structure for table `employee`
--

CREATE TABLE IF NOT EXISTS `employee` (
  `userID` varchar(10) NOT NULL,
  `employeeName` varchar(15) NOT NULL,
  `role` varchar(15) NOT NULL,
  `salary` double(10,2) NOT NULL,
  PRIMARY KEY (`userID`),
  KEY `employeeName` (`employeeName`,`role`,`salary`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `employee`
--

INSERT INTO `employee` (`userID`, `employeeName`, `role`, `salary`) VALUES
('E_1', 'admin', 'manager', 10000.00),
('E_0', 'Sami', 'manager', 2.00),
('E_2', 'Test', 'general', 2000.00);

-- --------------------------------------------------------

--
-- Table structure for table `login`
--

CREATE TABLE IF NOT EXISTS `login` (
  `userID` varchar(10) NOT NULL,
  `password` varchar(15) NOT NULL,
  `status` int(1) NOT NULL,
  UNIQUE KEY `userID` (`userID`),
  KEY `password` (`password`,`status`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `login`
--

INSERT INTO `login` (`userID`, `password`, `status`) VALUES
('C_1', '123', 0),
('C_2', '123', 0),
('E_1', 'admin', 1),
('C_0', '123', 0),
('C_3', '123', 0),
('E_0', 'admin', 1),
('C_4', '123', 0),
('E_2', '6249', 1),
('C_5', '123', 0),
('C_6', '123', 0),
('C_7', '123', 0),
('C_8', '1234', 0),
('C_9', '123', 0);

-- --------------------------------------------------------

--
-- Table structure for table `product`
--

CREATE TABLE IF NOT EXISTS `product` (
  `productID` varchar(10) NOT NULL,
  `productName` varchar(20) NOT NULL,
  `price` double(10,2) NOT NULL,
  `availableQuantity` int(10) NOT NULL,
  PRIMARY KEY (`productID`),
  KEY `productName` (`productName`,`price`,`availableQuantity`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `product`
--

INSERT INTO `product` (`productID`, `productName`, `price`, `availableQuantity`) VALUES
('P_0', 'Pen', 20.00, 11),
('P_1', 'Test11', 22.00, 1802),
('P_2', 'Pencil', 22.00, 200),
('P_3', 'Ink', 10.00, 20),
('P_4', 'Paper', 2.99, 1),
('P_5', 'Ruler', 5.99, 23),
('P_6', 'test1', 2.00, 22),
('P_7', 'test2', 22.00, 223),
('P_8', 'test5', 22.20, 3),
('P_9', 'test6', 5.99, 23),
('P_10', 'test7', 5.99, 23),
('P_11', 'testProduct', 1.42, 22);

-- --------------------------------------------------------

--
-- Table structure for table `purchaseinfo`
--

CREATE TABLE IF NOT EXISTS `purchaseinfo` (
  `purchaseID` varchar(10) NOT NULL,
  `productID` varchar(10) NOT NULL,
  `userID` varchar(10) NOT NULL,
  `quantity` int(10) NOT NULL,
  `amount` double(10,2) NOT NULL,
  PRIMARY KEY (`purchaseID`),
  KEY `productID` (`productID`,`userID`,`quantity`,`amount`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

--
-- Dumping data for table `purchaseinfo`
--

INSERT INTO `purchaseinfo` (`purchaseID`, `productID`, `userID`, `quantity`, `amount`) VALUES
('PU_1', 'P_4', 'C_2', 1, 2.00),
('PU_2', 'P_4', 'C_2', 1, 2.00),
('PU_3', 'P_11', 'C_2', 22, 31.24),
('PU_4', 'P_4', 'C_2', 15, 44.85),
('PU_5', 'P_0', 'C_4', 1, 9.99),
('PU_6', 'P_0', 'C_4', 1, 9.99),
('PU_7', 'P_0', 'C_4', 1, 9.99),
('PU_8', 'P_4', 'C_4', 4, 11.96),
('PU_9', 'P_8', 'C_0', 20, 444.00),
('PU_0', 'P_1', 'C_0', 200, 4400.00);
