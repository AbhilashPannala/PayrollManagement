
SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


CREATE TABLE `payroll` (
  `id` int(11) NOT NULL,
  `name` varchar(255) DEFAULT NULL,
  `gross` decimal(10,2) DEFAULT NULL,
  `over` decimal(10,2) DEFAULT NULL,
  `basic` decimal(10,2) DEFAULT NULL,
  `house_rent` decimal(10,2) DEFAULT NULL,
  `medical` decimal(10,2) DEFAULT NULL,
  `per_day` decimal(10,2) DEFAULT NULL,
  `per_hour` decimal(10,2) DEFAULT NULL,
  `over_time_pay` decimal(10,2) DEFAULT NULL,
  `payable` decimal(10,2) DEFAULT NULL,
  `usertype` enum('ADMIN','Manager','Employee') DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `payroll`
--

INSERT INTO `payroll` (`id`, `name`, `gross`, `over`, `basic`, `house_rent`, `medical`, `per_day`, `per_hour`, `over_time_pay`, `payable`, `usertype`, `password`) VALUES
(61, 'Michael Jordan', 7000.00, 18.00, 4200.00, 2100.00, 700.00, 161.54, 20.19, 36.34, 4236.34, 'Manager', 'michael123'),
(31, 'Emily Brown', 6200.00, 14.00, 3720.00, 1860.00, 620.00, 143.08, 17.88, 25.03, 3745.03, 'Employee', 'emily456'),
(32, 'William Johnson', 8000.00, 20.00, 4800.00, 2400.00, 800.00, 184.62, 23.08, 46.16, 4846.16, 'Manager', 'william789'),
(894, 'Sophia Davis', 7500.00, 19.00, 4500.00, 2250.00, 750.00, 173.08, 21.63, 41.00, 4541.00, 'Employee', 'sophia123'),
(47, 'Oliver Martinez', 9000.00, 30.00, 5400.00, 2700.00, 900.00, 207.69, 25.96, 77.88, 5477.88, 'Manager', 'oliver456'),
(82, 'Ava Wilson', 5500.00, 12.00, 3300.00, 1650.00, 550.00, 126.92, 15.86, 19.03, 3319.03, 'Employee', 'ava789'),
(612, 'Daniel Taylor', 6200.00, 14.00, 3720.00, 1860.00, 620.00, 143.08, 17.88, 25.03, 3745.03, 'Admin', 'daniel123'),
(92, 'Mia Anderson', 7000.00, 18.00, 4200.00, 2100.00, 700.00, 161.54, 20.19, 36.34, 4236.34, 'Employee', 'mia456'),
(225, 'James Miller', 8000.00, 20.00, 4800.00, 2400.00, 800.00, 184.62, 23.08, 46.16, 4846.16, 'Manager', 'james789'),
(197, 'Isabella Garcia', 7500.00, 19.00, 4500.00, 2250.00, 750.00, 173.08, 21.63, 41.00, 4541.00, 'Admin', 'isabella123');
