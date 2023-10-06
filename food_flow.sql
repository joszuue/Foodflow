-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1:3306
-- Tiempo de generación: 06-10-2023 a las 23:43:49
-- Versión del servidor: 8.0.31
-- Versión de PHP: 8.0.26

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `food_flow`
--
CREATE DATABASE IF NOT EXISTS `food_flow` DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci;
USE `food_flow`;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `categorias`
--

DROP TABLE IF EXISTS `categorias`;
CREATE TABLE IF NOT EXISTS `categorias` (
  `idCategoria` int NOT NULL AUTO_INCREMENT,
  `Nombre` varchar(100) NOT NULL,
  PRIMARY KEY (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `clientes`
--

DROP TABLE IF EXISTS `clientes`;
CREATE TABLE IF NOT EXISTS `clientes` (
  `codigoClient` varchar(8) NOT NULL,
  `apellido` varchar(100) NOT NULL,
  `idMesa` int NOT NULL,
  `estado` varchar(25) NOT NULL,
  PRIMARY KEY (`codigoClient`),
  KEY `idMesa` (`idMesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empleados`
--

DROP TABLE IF EXISTS `empleados`;
CREATE TABLE IF NOT EXISTS `empleados` (
  `codigo` varchar(8) NOT NULL,
  `contraseña` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `nombres` varchar(100) NOT NULL,
  `apellidos` varchar(100) NOT NULL,
  `rol` varchar(25) NOT NULL,
  `estado` varchar(25) NOT NULL,
  PRIMARY KEY (`codigo`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Volcado de datos para la tabla `empleados`
--

INSERT INTO `empleados` (`codigo`, `contraseña`, `nombres`, `apellidos`, `rol`, `estado`) VALUES
('MA239793', '$2a$10$0yXHuCVzKIJ9VSqjQCwlQOAWUKS5ta6hNRrbeExMhQULR/hOME1Zu', 'Julián Alejandro', 'Méndez Arévalo', 'Mesero', 'Activo'),
('VH235733', '$2a$10$Zc4.sqsBzMxFeer5wGDzA.cgSIRtxHsyIyFp8HsbWCYcLiCx2QQ9i', 'Kennet Adonay', 'Vargas Hernández', 'Cocina', 'Activo'),
('VR233781', '$2a$10$wGGq5hIGg4YoUe7qP3h5Duf0H9mMJcrWMGvi2.criCPQ6zBWTVdRe', 'Denis Josué', 'Vásquez Rodríguez', 'Admin', 'Activo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `mesas`
--

DROP TABLE IF EXISTS `mesas`;
CREATE TABLE IF NOT EXISTS `mesas` (
  `idMesa` int NOT NULL AUTO_INCREMENT,
  `Capacidad` int NOT NULL,
  `Estado` varchar(25) NOT NULL,
  PRIMARY KEY (`idMesa`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orden`
--

DROP TABLE IF EXISTS `orden`;
CREATE TABLE IF NOT EXISTS `orden` (
  `idOrden` int NOT NULL AUTO_INCREMENT,
  `codigoClient` varchar(8) NOT NULL,
  `idProducto` int NOT NULL,
  `cantidad` int NOT NULL,
  `fecha` datetime NOT NULL,
  `tiempoEspera` time NOT NULL,
  `total` int NOT NULL,
  `estado` varchar(25) NOT NULL,
  PRIMARY KEY (`idOrden`),
  KEY `codigoClient` (`codigoClient`),
  KEY `idProducto` (`idProducto`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `productos`
--

DROP TABLE IF EXISTS `productos`;
CREATE TABLE IF NOT EXISTS `productos` (
  `idProducto` int NOT NULL AUTO_INCREMENT,
  `idCategoria` int NOT NULL,
  `nombre` varchar(100) NOT NULL,
  `descripcion` varchar(500) NOT NULL,
  `precio` double NOT NULL,
  `imagen` varchar(100) NOT NULL,
  `estado` varchar(25) NOT NULL,
  PRIMARY KEY (`idProducto`),
  KEY `idCategoria` (`idCategoria`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `clientes`
--
ALTER TABLE `clientes`
  ADD CONSTRAINT `clientes_ibfk_1` FOREIGN KEY (`idMesa`) REFERENCES `mesas` (`idMesa`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `orden`
--
ALTER TABLE `orden`
  ADD CONSTRAINT `orden_ibfk_1` FOREIGN KEY (`codigoClient`) REFERENCES `clientes` (`codigoClient`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `orden_ibfk_2` FOREIGN KEY (`idProducto`) REFERENCES `productos` (`idProducto`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `productos`
--
ALTER TABLE `productos`
  ADD CONSTRAINT `productos_ibfk_1` FOREIGN KEY (`idCategoria`) REFERENCES `categorias` (`idCategoria`) ON DELETE CASCADE ON UPDATE CASCADE;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
