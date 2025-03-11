-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Počítač: localhost
-- Vytvořeno: Úte 11. bře 2025, 20:15
-- Verze serveru: 10.4.32-MariaDB
-- Verze PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Databáze: `pg_skibidi_database`
--

-- --------------------------------------------------------

--
-- Struktura tabulky `category`
--

CREATE TABLE `category` (
  `id` int(11) UNSIGNED NOT NULL,
  `name` varchar(45) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `category`
--

INSERT INTO `category` (`id`, `name`) VALUES
(1, 'Chladivé'),
(2, 'Burgur'),
(3, 'Přílohy'),
(4, 'Nápoje');

-- --------------------------------------------------------

--
-- Struktura tabulky `food`
--

CREATE TABLE `food` (
  `id` int(11) UNSIGNED NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int(11) UNSIGNED NOT NULL,
  `id_category` int(11) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `food`
--

INSERT INTO `food` (`id`, `name`, `price`, `id_category`) VALUES
(1, 'Burgr', 30, 2),
(2, 'Čís Burgir', 35, 2),
(3, 'Rojal Burgir', 40, 2),
(4, 'Velký Syn', 45, 2),
(5, 'Čiken burgir', 35, 2),
(6, 'Hranlce', 25, 3),
(7, 'Salát', 20, 3),
(8, 'Ovocný box', 35, 3),
(9, 'Krokety', 30, 3),
(10, 'Kofola', 30, 4),
(11, 'Sprajt', 30, 4),
(12, 'Fanta', 30, 4),
(13, 'Braník ', 10, 4),
(14, 'Kozel 11', 35, 4),
(15, 'Zmrzlina', 35, 1),
(16, 'Syn Furry', 40, 1),
(17, 'Mléčný šejk', 45, 1),
(22, 'Burgir', 49, 20),
(23, 'Čízburgir', 59, 20),
(24, 'Rojal Burgir', 69, 20);

-- --------------------------------------------------------

--
-- Struktura tabulky `special_offer`
--

CREATE TABLE `special_offer` (
  `id` int(10) UNSIGNED NOT NULL,
  `name` varchar(45) NOT NULL,
  `price` int(11) NOT NULL,
  `id_category` int(10) UNSIGNED NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Vypisuji data pro tabulku `special_offer`
--

INSERT INTO `special_offer` (`id`, `name`, `price`, `id_category`) VALUES
(1, 'Bez okurky', 0, 2),
(2, 'Slanina navíc', 10, 2),
(3, 'Sýr navíc', 10, 2),
(4, 'Sušenkový posyp', 15, 1),
(5, 'sNICKERs posyp', 15, 1),
(6, 'Oreo posyp', 15, 1),
(7, 'Čoko poleva', 10, 1),
(8, 'Karamelová poleva', 10, 1),
(9, 'Jahodový posyp', 10, 1),
(13, 'Bez okurky', 0, 20),
(14, 'Sýr navíc', 10, 20),
(15, 'Slanina navíc', 10, 20);

--
-- Indexy pro exportované tabulky
--

--
-- Indexy pro tabulku `category`
--
ALTER TABLE `category`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `food`
--
ALTER TABLE `food`
  ADD PRIMARY KEY (`id`);

--
-- Indexy pro tabulku `special_offer`
--
ALTER TABLE `special_offer`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT pro tabulky
--

--
-- AUTO_INCREMENT pro tabulku `category`
--
ALTER TABLE `category`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=24;

--
-- AUTO_INCREMENT pro tabulku `food`
--
ALTER TABLE `food`
  MODIFY `id` int(11) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=26;

--
-- AUTO_INCREMENT pro tabulku `special_offer`
--
ALTER TABLE `special_offer`
  MODIFY `id` int(10) UNSIGNED NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
