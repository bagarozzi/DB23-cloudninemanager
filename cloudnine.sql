-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Ago 12, 2024 alle 12:00
-- Versione del server: 10.4.32-MariaDB
-- Versione PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `cloudnine`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `account`
--

CREATE TABLE `account` (
  `Password` varchar(16) NOT NULL,
  `Nome_Utente` varchar(16) NOT NULL,
  `CodFiscale` char(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `account`
--

INSERT INTO `account` (`Password`, `Nome_Utente`, `CodFiscale`) VALUES
('1', 'bag', 'BGTFRC03M18D704C'),
('sdf', 'fd', 'ADSDGAEWEFK');

-- --------------------------------------------------------

--
-- Struttura della tabella `categoria`
--

CREATE TABLE `categoria` (
  `Nome_Categoria` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `categoria`
--

INSERT INTO `categoria` (`Nome_Categoria`) VALUES
('Primi'),
('Secondi');

-- --------------------------------------------------------

--
-- Struttura della tabella `comanda`
--

CREATE TABLE `comanda` (
  `Conto_finale` float DEFAULT NULL,
  `Cod_Comanda` int(8) NOT NULL,
  `Modalita_d_odine` enum('AYCE','CARTA') NOT NULL,
  `Coperti` int(8) NOT NULL,
  `Data` date NOT NULL DEFAULT current_timestamp(),
  `Ora` time NOT NULL DEFAULT curtime(),
  `Nome_Menu` varchar(16) NOT NULL,
  `Num_Tavolo` int(8) DEFAULT NULL,
  `CodFiscale` char(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `comanda`
--

INSERT INTO `comanda` (`Conto_finale`, `Cod_Comanda`, `Modalita_d_odine`, `Coperti`, `Data`, `Ora`, `Nome_Menu`, `Num_Tavolo`, `CodFiscale`) VALUES
(NULL, 2, 'CARTA', 3, '2024-07-20', '10:10:22', 'Menù elegante', 14, 'BGTFRC03M18D704C'),
(NULL, 3, 'AYCE', 4, '2024-07-20', '18:15:01', 'Menù elegante', 14, 'BGTFRC03M18D704C');

-- --------------------------------------------------------

--
-- Struttura della tabella `ingrediente`
--

CREATE TABLE `ingrediente` (
  `Soglia_critica` float NOT NULL,
  `Costo_al_kg` float NOT NULL,
  `Nome_Ingrediente` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ingrediente`
--

INSERT INTO `ingrediente` (`Soglia_critica`, `Costo_al_kg`, `Nome_Ingrediente`) VALUES
(10, 6, 'Aglio'),
(5, 7.5, 'Carne di maiale'),
(3, 20, 'Costata manzo'),
(50, 15, 'Olio EVO'),
(10, 5, 'Salsa pomdoro');

-- --------------------------------------------------------

--
-- Struttura della tabella `materia_prima`
--

CREATE TABLE `materia_prima` (
  `Data_scadenza` date NOT NULL,
  `Nome_Ingrediente` varchar(16) NOT NULL,
  `Quantita` float NOT NULL,
  `Data_d_acquisto` date NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `materia_prima`
--

INSERT INTO `materia_prima` (`Data_scadenza`, `Nome_Ingrediente`, `Quantita`, `Data_d_acquisto`) VALUES
('2024-07-31', 'Aglio', 20, '2024-07-20'),
('2024-07-23', 'Carne di maiale', 0.2, '2024-07-20'),
('2024-07-18', 'Costata manzo', 4, '2024-07-14'),
('2024-07-22', 'Costata manzo', 4, '2024-07-20'),
('2024-07-24', 'Costata manzo', 5, '2024-07-20'),
('2025-07-23', 'Costata Manzo', 13, '2030-07-23'),
('2024-07-31', 'Olio EVO', 20, '2024-07-27'),
('2024-07-31', 'Salsa pomdoro', 15, '2024-07-20');

-- --------------------------------------------------------

--
-- Struttura della tabella `membro_del_personale`
--

CREATE TABLE `membro_del_personale` (
  `CodFiscale` char(16) NOT NULL,
  `Ruolo_cuoco` varchar(16) DEFAULT NULL,
  `Professione` varchar(16) NOT NULL,
  `Nome` varchar(16) NOT NULL,
  `Cognome` varchar(16) NOT NULL,
  `Numero_Telefono` varchar(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `membro_del_personale`
--

INSERT INTO `membro_del_personale` (`CodFiscale`, `Ruolo_cuoco`, `Professione`, `Nome`, `Cognome`, `Numero_Telefono`) VALUES
('ADSDGAEWEFK', 'NULL', 'Amministratore', 'Luca', 'Venturini', '3348759134'),
('BGTFRC03M18D704C', NULL, 'Amministratore', 'Federico', 'Bagattoni', '3803797762');

-- --------------------------------------------------------

--
-- Struttura della tabella `menu`
--

CREATE TABLE `menu` (
  `Nome_Menu` varchar(16) NOT NULL,
  `Costo_menu_AYCE` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `menu`
--

INSERT INTO `menu` (`Nome_Menu`, `Costo_menu_AYCE`) VALUES
('Menù del cazzo', 12.32),
('Menù elegante', 30.23);

-- --------------------------------------------------------

--
-- Struttura della tabella `necessita`
--

CREATE TABLE `necessita` (
  `Nome_Ingrediente` varchar(16) NOT NULL,
  `Cod_vivanda` int(8) NOT NULL,
  `Quantita_usata` float NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `necessita`
--

INSERT INTO `necessita` (`Nome_Ingrediente`, `Cod_vivanda`, `Quantita_usata`) VALUES
('Carne di maiale', 12, 0.5),
('Salsa pomdoro', 12, 1000);

-- --------------------------------------------------------

--
-- Struttura della tabella `ordine`
--

CREATE TABLE `ordine` (
  `Cod_Comanda` int(8) NOT NULL,
  `Stato` varchar(16) NOT NULL,
  `N_Ordine` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `ordine`
--

INSERT INTO `ordine` (`Cod_Comanda`, `Stato`, `N_Ordine`) VALUES
(2, 'Cucinato', 2);

-- --------------------------------------------------------

--
-- Struttura della tabella `prenoatazione`
--

CREATE TABLE `prenoatazione` (
  `Data` date NOT NULL DEFAULT current_timestamp(),
  `Ora` time NOT NULL,
  `Nominativo` char(16) NOT NULL,
  `Coperti` int(8) NOT NULL,
  `Telefono` char(10) NOT NULL,
  `CodFiscale` char(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `prenoatazione`
--

INSERT INTO `prenoatazione` (`Data`, `Ora`, `Nominativo`, `Coperti`, `Telefono`, `CodFiscale`) VALUES
('2024-07-20', '10:26:57', 'Mario', 3, '3354433211', 'BGTFRC03M18D704C'),
('2024-07-23', '13:00:00', 'luca', 2, '1234567898', 'BGTFRC03M18D704C'),
('2024-07-23', '13:00:00', 'luca', 2, '1234567899', 'BGTFRC03M18D704C');

-- --------------------------------------------------------

--
-- Struttura della tabella `proposta`
--

CREATE TABLE `proposta` (
  `Nome_Menu` varchar(16) NOT NULL,
  `Cod_vivanda` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `proposta`
--

INSERT INTO `proposta` (`Nome_Menu`, `Cod_vivanda`) VALUES
('Menù del cazzo', 15),
('Menù elegante', 12);

-- --------------------------------------------------------

--
-- Struttura della tabella `richiede`
--

CREATE TABLE `richiede` (
  `Cod_Comanda` int(8) NOT NULL,
  `N_Ordine` int(8) NOT NULL,
  `Cod_vivanda` int(8) NOT NULL,
  `N_vivande` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `richiede`
--

INSERT INTO `richiede` (`Cod_Comanda`, `N_Ordine`, `Cod_vivanda`, `N_vivande`) VALUES
(2, 2, 12, 3);

-- --------------------------------------------------------

--
-- Struttura della tabella `servizio_di_diponibilita`
--

CREATE TABLE `servizio_di_diponibilita` (
  `Servizio` enum('Pranzo','Cena') NOT NULL,
  `Giorno` enum('Lunedì','Martedì','Mercoledì','Giovedì','Venerdì','Sabato','Domenica') NOT NULL,
  `Nome_Menu` varchar(16) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `servizio_di_diponibilita`
--

INSERT INTO `servizio_di_diponibilita` (`Servizio`, `Giorno`, `Nome_Menu`) VALUES
('Pranzo', 'Martedì', 'Menù elegante'),
('Pranzo', 'Mercoledì', 'Menù elegante');

-- --------------------------------------------------------

--
-- Struttura della tabella `tavolo`
--

CREATE TABLE `tavolo` (
  `Num_Tavolo` int(8) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `tavolo`
--

INSERT INTO `tavolo` (`Num_Tavolo`) VALUES
(14);

-- --------------------------------------------------------

--
-- Struttura della tabella `vivanda`
--

CREATE TABLE `vivanda` (
  `Cod_vivanda` int(8) NOT NULL,
  `prezzo` float NOT NULL,
  `tipologia` enum('Piatto','Bevanda') NOT NULL,
  `Nome_Vivanda` varchar(16) NOT NULL,
  `Nome_Categoria` varchar(16) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dump dei dati per la tabella `vivanda`
--

INSERT INTO `vivanda` (`Cod_vivanda`, `prezzo`, `tipologia`, `Nome_Vivanda`, `Nome_Categoria`) VALUES
(12, 12, 'Piatto', 'Polpette al sug', 'Secondi'),
(15, 15, 'Piatto', 'Insalata', 'Secondi'),
(16, 20, 'Piatto', 'Bistecca', 'Secondi');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `account`
--
ALTER TABLE `account`
  ADD PRIMARY KEY (`Nome_Utente`),
  ADD UNIQUE KEY `FKAccesso_ID` (`CodFiscale`),
  ADD UNIQUE KEY `ID_Account_IND` (`Nome_Utente`),
  ADD UNIQUE KEY `FKAccesso_IND` (`CodFiscale`);

--
-- Indici per le tabelle `categoria`
--
ALTER TABLE `categoria`
  ADD PRIMARY KEY (`Nome_Categoria`),
  ADD UNIQUE KEY `ID_Categoria_IND` (`Nome_Categoria`);

--
-- Indici per le tabelle `comanda`
--
ALTER TABLE `comanda`
  ADD PRIMARY KEY (`Cod_Comanda`),
  ADD UNIQUE KEY `ID_Comanda_IND` (`Cod_Comanda`),
  ADD KEY `FKRiferimento_IND` (`Nome_Menu`),
  ADD KEY `FKApertura_IND` (`CodFiscale`),
  ADD KEY `FKTavolo_FK` (`Num_Tavolo`);

--
-- Indici per le tabelle `ingrediente`
--
ALTER TABLE `ingrediente`
  ADD PRIMARY KEY (`Nome_Ingrediente`),
  ADD UNIQUE KEY `ID_Ingrediente_IND` (`Nome_Ingrediente`);

--
-- Indici per le tabelle `materia_prima`
--
ALTER TABLE `materia_prima`
  ADD UNIQUE KEY `SID_Materia_Prima_ID` (`Nome_Ingrediente`,`Data_scadenza`),
  ADD UNIQUE KEY `SID_Materia_Prima_IND` (`Nome_Ingrediente`,`Data_scadenza`);

--
-- Indici per le tabelle `membro_del_personale`
--
ALTER TABLE `membro_del_personale`
  ADD PRIMARY KEY (`CodFiscale`),
  ADD UNIQUE KEY `ID_Membro_del_Personale_IND` (`CodFiscale`);

--
-- Indici per le tabelle `menu`
--
ALTER TABLE `menu`
  ADD PRIMARY KEY (`Nome_Menu`),
  ADD UNIQUE KEY `ID_Menu_IND` (`Nome_Menu`);

--
-- Indici per le tabelle `necessita`
--
ALTER TABLE `necessita`
  ADD PRIMARY KEY (`Nome_Ingrediente`,`Cod_vivanda`),
  ADD UNIQUE KEY `ID_Necessita_IND` (`Nome_Ingrediente`,`Cod_vivanda`),
  ADD KEY `FKNec_Viv_IND` (`Cod_vivanda`);

--
-- Indici per le tabelle `ordine`
--
ALTER TABLE `ordine`
  ADD PRIMARY KEY (`Cod_Comanda`,`N_Ordine`),
  ADD UNIQUE KEY `ID_Ordine_IND` (`Cod_Comanda`,`N_Ordine`);

--
-- Indici per le tabelle `prenoatazione`
--
ALTER TABLE `prenoatazione`
  ADD PRIMARY KEY (`Data`,`Ora`,`Telefono`),
  ADD UNIQUE KEY `ID_Prenoatazione_IND` (`Data`,`Ora`,`Telefono`),
  ADD KEY `FKAcquisizione_IND` (`CodFiscale`);

--
-- Indici per le tabelle `proposta`
--
ALTER TABLE `proposta`
  ADD PRIMARY KEY (`Nome_Menu`,`Cod_vivanda`),
  ADD UNIQUE KEY `ID_Proposta_IND` (`Nome_Menu`,`Cod_vivanda`),
  ADD KEY `FKPro_Viv_IND` (`Cod_vivanda`);

--
-- Indici per le tabelle `richiede`
--
ALTER TABLE `richiede`
  ADD PRIMARY KEY (`Cod_Comanda`,`N_Ordine`,`Cod_vivanda`),
  ADD UNIQUE KEY `ID_Richiede_IND` (`Cod_Comanda`,`N_Ordine`,`Cod_vivanda`),
  ADD KEY `FKRic_Viv_IND` (`Cod_vivanda`);

--
-- Indici per le tabelle `servizio_di_diponibilita`
--
ALTER TABLE `servizio_di_diponibilita`
  ADD PRIMARY KEY (`Servizio`,`Giorno`),
  ADD UNIQUE KEY `ID_Servizio_di_diponibilita_IND` (`Servizio`,`Giorno`),
  ADD KEY `FKDurata_IND` (`Nome_Menu`);

--
-- Indici per le tabelle `tavolo`
--
ALTER TABLE `tavolo`
  ADD PRIMARY KEY (`Num_Tavolo`),
  ADD UNIQUE KEY `ID_Tavolo_IND` (`Num_Tavolo`);

--
-- Indici per le tabelle `vivanda`
--
ALTER TABLE `vivanda`
  ADD PRIMARY KEY (`Cod_vivanda`),
  ADD UNIQUE KEY `ID_Vivanda_IND` (`Cod_vivanda`),
  ADD KEY `FKAppartenenza_IND` (`Nome_Categoria`);

--
-- AUTO_INCREMENT per le tabelle scaricate
--

--
-- AUTO_INCREMENT per la tabella `comanda`
--
ALTER TABLE `comanda`
  MODIFY `Cod_Comanda` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT per la tabella `vivanda`
--
ALTER TABLE `vivanda`
  MODIFY `Cod_vivanda` int(8) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=17;

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `account`
--
ALTER TABLE `account`
  ADD CONSTRAINT `FKAccesso_FK` FOREIGN KEY (`CodFiscale`) REFERENCES `membro_del_personale` (`CodFiscale`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `comanda`
--
ALTER TABLE `comanda`
  ADD CONSTRAINT `FKApertura_FK` FOREIGN KEY (`CodFiscale`) REFERENCES `membro_del_personale` (`CodFiscale`) ON DELETE SET NULL ON UPDATE SET NULL,
  ADD CONSTRAINT `FKRelativa_FK` FOREIGN KEY (`Nome_Menu`) REFERENCES `menu` (`Nome_Menu`) ON UPDATE CASCADE,
  ADD CONSTRAINT `FKTavolo_FK` FOREIGN KEY (`Num_Tavolo`) REFERENCES `tavolo` (`Num_Tavolo`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Limiti per la tabella `materia_prima`
--
ALTER TABLE `materia_prima`
  ADD CONSTRAINT `FKLotto` FOREIGN KEY (`Nome_Ingrediente`) REFERENCES `ingrediente` (`Nome_Ingrediente`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `necessita`
--
ALTER TABLE `necessita`
  ADD CONSTRAINT `FKNec_Ing` FOREIGN KEY (`Nome_Ingrediente`) REFERENCES `ingrediente` (`Nome_Ingrediente`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKNec_Viv_FK` FOREIGN KEY (`Cod_vivanda`) REFERENCES `vivanda` (`Cod_vivanda`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `ordine`
--
ALTER TABLE `ordine`
  ADD CONSTRAINT `FKComposto` FOREIGN KEY (`Cod_Comanda`) REFERENCES `comanda` (`Cod_Comanda`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `prenoatazione`
--
ALTER TABLE `prenoatazione`
  ADD CONSTRAINT `FK_AcquisizioneFK` FOREIGN KEY (`CodFiscale`) REFERENCES `membro_del_personale` (`CodFiscale`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Limiti per la tabella `proposta`
--
ALTER TABLE `proposta`
  ADD CONSTRAINT `FKPro_Men` FOREIGN KEY (`Nome_Menu`) REFERENCES `menu` (`Nome_Menu`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKPro_Viv_FK` FOREIGN KEY (`Cod_vivanda`) REFERENCES `vivanda` (`Cod_vivanda`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `richiede`
--
ALTER TABLE `richiede`
  ADD CONSTRAINT `FKRic_Ord` FOREIGN KEY (`Cod_Comanda`,`N_Ordine`) REFERENCES `ordine` (`Cod_Comanda`, `N_Ordine`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `FKRic_Viv_FK` FOREIGN KEY (`Cod_vivanda`) REFERENCES `vivanda` (`Cod_vivanda`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Limiti per la tabella `servizio_di_diponibilita`
--
ALTER TABLE `servizio_di_diponibilita`
  ADD CONSTRAINT `FKDurata_FK` FOREIGN KEY (`Nome_Menu`) REFERENCES `menu` (`Nome_Menu`) ON DELETE SET NULL ON UPDATE SET NULL;

--
-- Limiti per la tabella `vivanda`
--
ALTER TABLE `vivanda`
  ADD CONSTRAINT `FKAppartenenza_FK` FOREIGN KEY (`Nome_Categoria`) REFERENCES `categoria` (`Nome_Categoria`) ON UPDATE CASCADE;

DELIMITER $$
--
-- Eventi
--
CREATE DEFINER=`root`@`localhost` EVENT `REMOVE_EMPTY_MAT_PRIME_` ON SCHEDULE EVERY 1 DAY STARTS '2024-07-22 00:00:00' ENDS '2024-07-31 00:00:00' ON COMPLETION PRESERVE ENABLE COMMENT 'rimuove i lotti di materie prime con quantità 0' DO DELETE FROM Materia_Prima
WHERE Materia_Prima.Quantita=0$$

DELIMITER ;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
