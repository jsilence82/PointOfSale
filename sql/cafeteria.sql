-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Mar 23, 2023 at 03:25 PM
-- Server version: 10.4.27-MariaDB
-- PHP Version: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cafeteria`
--

-- --------------------------------------------------------

--
-- Table structure for table `authentication`
--

CREATE TABLE `authentication` (
  `MitarbeiterID` int(11) NOT NULL,
  `passwort` varchar(512) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `authentication`
--

INSERT INTO `authentication` (`MitarbeiterID`, `passwort`) VALUES
(1, '$argon2id$v=19$m=1024,t=4,p=1$Rj1docUpfidsT0lPAYVabQ$wa1v2GQhls+UJiB0x4ersrzRz0Ts2D8MW97hR8Cj2Nc'),
(3, '$argon2id$v=19$m=1048576,t=4,p=1$vnHZWnVUwPFlG4EjyOY/eg$2pStPljP2YxtQogkxWaDNj+UxTcBMPribX+D3A/hQX4'),
(5, '$argon2id$v=19$m=1048576,t=4,p=1$vnHZWnVUwPFlG4EjyOY/eg$2pStPljP2YxtQogkxWaDNj+UxTcBMPribX+D3A/hQX4'),
(6, '$argon2id$v=19$m=1048576,t=4,p=1$BG98t2v7Eka/rIXQF99bPw$2QQJh5jehwP16HsJOlp5zKgdCtzW3MvO4cRh+L35rE4'),
(7, '$argon2id$v=19$m=1024,t=4,p=1$Rj1docUpfidsT0lPAYVabQ$wa1v2GQhls+UJiB0x4ersrzRz0Ts2D8MW97hR8Cj2Nc'),
(99, '$argon2id$v=19$m=1024,t=4,p=1$I7QxfksBo7AdA3zpRlKyiA$dt1jLoGQl2MBekUWfFXW0+3ByW7LhQqcVowe5JdTrkM'),
(100001, '123');

-- --------------------------------------------------------

--
-- Table structure for table `bestellung`
--

CREATE TABLE `bestellung` (
  `BestellungID` varchar(20) NOT NULL,
  `MitarbeiterID` int(11) NOT NULL,
  `Datum` date DEFAULT curdate(),
  `betrag` decimal(10,2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `bestellung`
--

INSERT INTO `bestellung` (`BestellungID`, `MitarbeiterID`, `Datum`, `betrag`) VALUES
('230316111316', 1001, '2023-03-16', '2.00'),
('230316135040', 1001, '2023-03-16', '1.59'),
('230316135057', 1001, '2023-03-16', '1.59'),
('230316135920', 1001, '2023-03-16', '2.30'),
('230316141762', 1001, '2023-03-16', '1.99'),
('230316141826', 1001, '2023-03-16', '1.49'),
('230316141852', 1001, '2023-03-16', '0.00'),
('230316144913', 100001, '2023-03-16', '1.99'),
('230316144935', 100001, '2023-03-16', '1.49'),
('230316145438', 100001, '2023-03-16', '4.87'),
('230316151950', 100001, '2023-03-16', '1.29'),
('230316151954', 100001, '2023-03-16', '1.29'),
('230316152258', 100001, '2023-03-16', '0.00'),
('230316152327', 100001, '2023-03-16', '0.00'),
('230316152332', 100001, '2023-03-16', '3.48'),
('230316152837', 100001, '2023-03-16', '1.99'),
('230316152949', 100001, '2023-03-16', '1.99'),
('230316153283', 100001, '2023-03-16', '5.27'),
('230316221322', 100001, '2023-03-16', '1.29'),
('230316221326', 100001, '2023-03-16', '1.99'),
('230317160644', 3, '2023-03-17', '5.08'),
('230320083492', 1, '2023-03-20', '5.94'),
('230320115727', 1, '2023-03-20', '3.28'),
('230320122614', 3, '2023-03-20', '4.77'),
('230320140169', 1, '2023-03-20', '2.78'),
('230320154787', 1, '2023-03-20', '2.58'),
('230320160858', 3, '2023-03-20', '2.29'),
('230320160882', 3, '2023-03-20', '3.29'),
('230321105864', 1, '2023-03-21', '1.49'),
('230321113812', 1, '2023-03-21', '3.28'),
('230321114098', 1, '2023-03-21', '3.59'),
('230321152611', 3, '2023-03-21', '2.78'),
('230321161123', 3, '2023-03-21', '7.16'),
('230321161213', 3, '2023-03-21', '6.45'),
('230321162055', 3, '2023-03-21', '9.14'),
('230322155980', 3, '2023-03-22', '5.58'),
('230323093891', 1, '2023-03-23', '3.00');

-- --------------------------------------------------------

--
-- Table structure for table `essen`
--

CREATE TABLE `essen` (
  `EssenID` int(11) NOT NULL,
  `Essen` varchar(50) NOT NULL,
  `Anzahl` int(11) NOT NULL,
  `Preis` double NOT NULL,
  `LieferantenID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `essen`
--

INSERT INTO `essen` (`EssenID`, `Essen`, `Anzahl`, `Preis`, `LieferantenID`) VALUES
(1, 'Käsebretzel', 8, 1.99, 10),
(2, 'Bretzel', 11, 1.49, 10),
(3, 'Salami Sandwich', 15, 2.3, 11),
(4, 'Schinken Sandwich', 0, 1.59, 11),
(5, 'Berliner', 8, 1, 12),
(6, 'Streusel', 3, 1.29, 12),
(7, 'Tuna Melt', 5, 3.99, 12),
(8, 'Donut', 12, 1.29, 10),
(10, 'Apfeltasche', 20, 0.99, 10);

-- --------------------------------------------------------

--
-- Table structure for table `lieferant`
--

CREATE TABLE `lieferant` (
  `LieferantenID` int(11) NOT NULL,
  `Lieferant` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `lieferant`
--

INSERT INTO `lieferant` (`LieferantenID`, `Lieferant`) VALUES
(10, 'Brezelbub'),
(11, 'Sandwich Kitchen'),
(12, 'Bäckerei Hüftgold'),
(20, 'Coca Cola'),
(21, 'Gerolsteiner'),
(22, 'Kaffee Rösterei Muck und Zuck');

-- --------------------------------------------------------

--
-- Table structure for table `mitarbeiter`
--

CREATE TABLE `mitarbeiter` (
  `MitarbeiterID` int(11) NOT NULL,
  `vorname` varchar(50) NOT NULL,
  `nachname` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `mitarbeiter`
--

INSERT INTO `mitarbeiter` (`MitarbeiterID`, `vorname`, `nachname`) VALUES
(100001, 'Admin', 'Admin'),
(1, 'Jeff', 'Silence'),
(3, 'Max', 'Test'),
(22, 'Lars', 'Hocker'),
(10, 'Sebastian', 'Wamser'),
(10000, 'Tester', 'Test'),
(3000, 'admin2 ', 'admin'),
(5, 'hashtest', 'hashtest'),
(6, 'hashTest2', 'hashtest2'),
(7, 'hashtest3', 'hashtest3'),
(99, 'tester', 'test');

-- --------------------------------------------------------

--
-- Table structure for table `p_essen`
--

CREATE TABLE `p_essen` (
  `BestellungID` varchar(20) NOT NULL,
  `EssenID` int(11) NOT NULL,
  `Menge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `p_essen`
--

INSERT INTO `p_essen` (`BestellungID`, `EssenID`, `Menge`) VALUES
('230316111316', 1, 1),
('230316112636', 5, 1),
('230316113561', 1, 1),
('230316131083', 2, 1),
('230316131083', 3, 2),
('230316132519', 5, 1),
('230316133194', 4, 1),
('230316133241', 4, 1),
('230316133284', 1, 1),
('230316133224', 2, 1),
('230316133418', 1, 1),
('230316133726', 1, 1),
('230316133895', 1, 1),
('230316133898', 1, 1),
('230316134285', 2, 1),
('230316134228', 2, 1),
('230316134429', 2, 1),
('230316134586', 3, 1),
('230316134829', 3, 1),
('230316134996', 3, 1),
('230316135057', 4, 1),
('230316135040', 4, 1),
('230316135920', 3, 1),
('230316141762', 1, 1),
('230316141826', 2, 1),
('230316144935', 2, 1),
('230316144913', 1, 1),
('230316145438', 1, 1),
('230316145438', 4, 1),
('230316152332', 1, 1),
('230316152332', 2, 1),
('230316152837', 1, 1),
('230316152949', 1, 1),
('230316153283', 1, 2),
('230316221326', 1, 1),
('230317160644', 2, 1),
('230317160644', 3, 1),
('230320115727', 1, 1),
('230320122614', 2, 1),
('230320122614', 1, 1),
('230320140169', 2, 1),
('230320154787', 4, 1),
('230320160858', 6, 1),
('230320160882', 6, 1),
('230320160882', 5, 1),
('230321105864', 2, 1),
('230321113812', 1, 1),
('230321114098', 3, 1),
('230321152611', 2, 1),
('230321161123', 6, 3),
('230321161123', 3, 1),
('230321161213', 6, 5),
('230321162055', 4, 4),
('230321162055', 2, 1),
('230322155980', 3, 1);

-- --------------------------------------------------------

--
-- Table structure for table `p_trinken`
--

CREATE TABLE `p_trinken` (
  `BestellungID` varchar(20) NOT NULL,
  `TrinkenID` int(11) NOT NULL,
  `Menge` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `p_trinken`
--

INSERT INTO `p_trinken` (`BestellungID`, `TrinkenID`, `Menge`) VALUES
('230316111316', 1, 1),
('230316112636', 1, 2),
('230316113561', 1, 1),
('230316131083', 1, 1),
('230316132519', 5, 1),
('230316133259', 1, 1),
('230316133843', 3, 3),
('230316133935', 3, 1),
('230316133935', 4, 3),
('230316134297', 2, 1),
('230316135023', 4, 1),
('230316145438', 1, 1),
('230316151950', 3, 1),
('230316151954', 2, 1),
('230316153283', 1, 1),
('230316221322', 1, 1),
('230317160644', 2, 1),
('230320083492', 5, 6),
('230320115727', 1, 1),
('230320122614', 1, 1),
('230320140169', 1, 1),
('230320154787', 5, 1),
('230320160858', 4, 1),
('230320160882', 4, 1),
('230321113812', 1, 1),
('230321114098', 2, 1),
('230321152611', 2, 1),
('230321161123', 5, 1),
('230321162055', 2, 1),
('230322155980', 3, 1),
('230322155980', 4, 1),
('230322155980', 5, 1),
('230323093891', 4, 3);

-- --------------------------------------------------------

--
-- Table structure for table `trinken`
--

CREATE TABLE `trinken` (
  `TrinkenID` int(11) NOT NULL,
  `Trinken` varchar(50) NOT NULL,
  `Anzahl` int(11) NOT NULL,
  `Preis` double NOT NULL,
  `LieferantenID` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

--
-- Dumping data for table `trinken`
--

INSERT INTO `trinken` (`TrinkenID`, `Trinken`, `Anzahl`, `Preis`, `LieferantenID`) VALUES
(1, 'Coca Cola', 10, 1.29, 20),
(2, 'Fanta', 4, 1.29, 20),
(3, 'Cola Light', 4, 1.29, 20),
(4, 'Kaffee', 0, 1, 22),
(5, 'Wasser', 19, 0.99, 21),
(6, 'Coke Zero', 20, 1, 20),
(8, 'Cappucino', 15, 1.99, 22);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `authentication`
--
ALTER TABLE `authentication`
  ADD UNIQUE KEY `MitarbeiterID` (`MitarbeiterID`);

--
-- Indexes for table `bestellung`
--
ALTER TABLE `bestellung`
  ADD PRIMARY KEY (`BestellungID`),
  ADD KEY `MitarbeiterID` (`MitarbeiterID`);

--
-- Indexes for table `essen`
--
ALTER TABLE `essen`
  ADD PRIMARY KEY (`EssenID`),
  ADD KEY `LieferantenID_2` (`LieferantenID`);

--
-- Indexes for table `lieferant`
--
ALTER TABLE `lieferant`
  ADD PRIMARY KEY (`LieferantenID`);

--
-- Indexes for table `p_essen`
--
ALTER TABLE `p_essen`
  ADD KEY `BestellungID` (`BestellungID`),
  ADD KEY `EssenID` (`EssenID`);

--
-- Indexes for table `p_trinken`
--
ALTER TABLE `p_trinken`
  ADD KEY `BestellungID` (`BestellungID`),
  ADD KEY `TrinkenID` (`TrinkenID`);

--
-- Indexes for table `trinken`
--
ALTER TABLE `trinken`
  ADD PRIMARY KEY (`TrinkenID`),
  ADD KEY `LieferantenID_2` (`LieferantenID`);

--
-- Constraints for dumped tables
--

--
-- Constraints for table `essen`
--
ALTER TABLE `essen`
  ADD CONSTRAINT `essen_ibfk_1` FOREIGN KEY (`LieferantenID`) REFERENCES `lieferant` (`LieferantenID`);

--
-- Constraints for table `trinken`
--
ALTER TABLE `trinken`
  ADD CONSTRAINT `trinken_ibfk_1` FOREIGN KEY (`LieferantenID`) REFERENCES `lieferant` (`LieferantenID`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
