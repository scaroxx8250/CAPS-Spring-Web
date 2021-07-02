DROP DATABASE IF EXISTS caps;
CREATE DATABASE IF NOT EXISTS caps;
USE caps;


DROP TABLE IF EXISTS `admin`;
CREATE TABLE IF NOT EXISTS `admin` (
  `admin_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`admin_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


INSERT INTO `admin` (`admin_id`, `email`, `first_name`, `last_name`, `password`) VALUES
	(1, 'admin@gmail.com', 'John', 'Doe', 'admin'),
	(2, 'dan.harmon@u.nus.edu', 'Dan', 'Harmon', '1234');



DROP TABLE IF EXISTS `lecturer`;
CREATE TABLE IF NOT EXISTS `lecturer` (
  `lecturer_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `faculty` int DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `personal_email` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`lecturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `student`;
CREATE TABLE IF NOT EXISTS `student` (
  `student_id` int NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `matr_date` date NOT NULL,
  `matric_no` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `person_email` varchar(255) DEFAULT NULL,
  `student_courses` tinyblob,
  PRIMARY KEY (`student_id`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

DROP TABLE IF EXISTS `course`;
CREATE TABLE IF NOT EXISTS `course` (
  `course_id` int NOT NULL AUTO_INCREMENT,
  `course_name` varchar(255) DEFAULT NULL,
  `course_occupancy` int DEFAULT NULL,
  `course_start_date` date NOT NULL,
  `credits` int NOT NULL,
  `description` varchar(255) DEFAULT NULL,
  `faculty` int DEFAULT NULL,
  `size` int NOT NULL,
  `student_courses` tinyblob,
  `lecturer_lecturer_id` int DEFAULT NULL,
  PRIMARY KEY (`course_id`),
  KEY `FKldi1sgfhtnl1iuv8duxwkeif2` (`lecturer_lecturer_id`),
  CONSTRAINT `FKldi1sgfhtnl1iuv8duxwkeif2` FOREIGN KEY (`lecturer_lecturer_id`) REFERENCES `lecturer` (`lecturer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;


DROP TABLE IF EXISTS `student_course`;
CREATE TABLE IF NOT EXISTS `student_course` (
  `course_id` int NOT NULL,
  `student_id` int NOT NULL,
  `grade` double DEFAULT NULL,
  PRIMARY KEY (`course_id`,`student_id`),
  KEY `FKq7yw2wg9wlt2cnj480hcdn6dq` (`student_id`),
  CONSTRAINT `FKejrkh4gv8iqgmspsanaji90ws` FOREIGN KEY (`course_id`) REFERENCES `course` (`course_id`),
  CONSTRAINT `FKq7yw2wg9wlt2cnj480hcdn6dq` FOREIGN KEY (`student_id`) REFERENCES `student` (`student_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

INSERT INTO `lecturer` (`lecturer_id`, `email`, `faculty`, `first_name`, `last_name`, `password`, `personal_email`) VALUES
	(1, 'benjamin.franklin@u.nus.edu', 0, 'Benjamin', 'Franklin', '1234', 'benjfr@gmail.com'),
	(2, 'buzz.hickey@u.nus.edu', 1, 'Buzz', 'Hickey', '5678', 'buzhey@gmail.com'),
	(3, 'craig.pelton@u.nus.edu', 2, 'Craig', 'Pelton', '1234', 'craigpel@gmail.com'),
	(4, 'ian.duncon@u.nus.edu', 3, 'Ian', 'Duncon', '1234', 'ian.d@gmail.com'),
	(5, 'michelle.slator@u.nus.edu', 4, 'Michelle', 'Slator', '1234', 'mslator@gmail.com');
	
	
INSERT INTO `student` (`student_id`, `email`, `first_name`, `last_name`, `matr_date`, `matric_no`, `password`, `person_email`, `student_courses`) VALUES
(1, '47319@u.nus.edu', 'Alex', 'Osbourne', '2021-06-22', 'A9998B', '1234', 'ao@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(2, '24634@u.nus.edu', 'Leonard', 'Rodriguez', '2021-06-22', 'A9997B', '5678', 'lr@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(3, '434eb@u.nus.edu', 'Vaughn', 'Miller', '2021-06-22', 'A9996B', '1234', 'vm@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(4, 'bc8c5@u.nus.edu', 'Rich', 'Stephenson', '2021-06-22', 'A9995B', '5678', 'rs@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(5, '4213c@u.nus.edu', 'Garett', 'Lambert', '2021-06-22', 'A9994B', '1234', 'gl@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(6, '96bb9@u.nus.edu', 'Todd', 'Jacobson', '2021-06-22', 'A9993B', '1234', 'tj@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(7, '42319@u.nus.edu', 'Tod', 'Barker', '2021-06-22', 'A9992B', '5678', 'tb@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(8, '4e319@u.nus.edu', 'Sharon', 'Tan', '2021-06-22', 'A9991B', '1234', 'sharonTan@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078),
(9, '3c31b@u.nus.edu', 'Evelyn', 'Lim', '2021-06-22', 'A9990B', '1234', 'sharonLim@gmail.com', _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078);


INSERT INTO `course` (`course_id`, `course_name`, `course_occupancy`, `course_start_date`, `credits`, `description`, `faculty`, `size`, `student_courses`, `lecturer_lecturer_id`) VALUES
	(1, 'Advanced Criminal Law', 0, '2021-08-22', 5, 'The body of law that relates to crime', 3, 40, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 4),
	(2, 'Beginner Pottery', 0, '2021-08-22', 5, 'The ulitimate pottery lesson for doctors.', 4, 5, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 5),
	(3, 'Communication Studies', 0, '2019-03-10', 5, 'Learn how to influence people with ease.', 0, 60, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 1),
	(4, 'Debate 109', 0, '2021-08-11', 5, 'Learn how to debate!', 3, 40, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 4),
	(5, 'English as a Second Language', 0, '2020-08-27', 5, 'English for non-native speakers.', 0, 20, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 1),
	(6, 'Environmental Science', 0, '2021-08-27', 5, 'Integrates physical, biological and information sciences.', 1, 80, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 5),
	(7, 'Football, Feminism and You', 0, '2021-08-27', 5, 'Learn about equal rights and opportunities', 1, 60, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 4),
	(8, 'Interpretive Dance', 0, '2021-08-27', 5, 'Dance, dance, dance!', 0, 10, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 3),
	(9, 'Modern Warfare', 0, '2021-08-27', 5, 'New Military concepts, methods, and technology', 3, 60, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 2),
	(10, 'Pascal\'s Triangle Revisited', 0, '2021-08-27', 5, 'The infinite triangular arrangement of numbers.', 2, 40, _binary 0xaced0005737200116a6176612e7574696c2e48617368536574ba44859596b8b7340300007870770c000000013f4000000000000078, 3);


INSERT INTO `student_course` (`course_id`, `student_id`, `grade`) VALUES
	(1, 1, 4.7),
	(2, 1, 3.8),
	(2, 2, NULL),
	(2, 3, NULL),
	(2, 4, NULL),
	(3, 1, 3.3),
	(4, 2, NULL),
	(4, 3, NULL),
	(5, 1, 3.5),
	(6, 1, NULL),
	(7, 1, NULL),
	(8, 1, NULL),
	(9, 1, NULL),
	(10, 1, NULL);
