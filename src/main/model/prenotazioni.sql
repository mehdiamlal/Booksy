-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Dic 17, 2022 alle 17:50
-- Versione del server: 10.4.25-MariaDB
-- Versione PHP: 8.1.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `prenotazioni`
--

-- --------------------------------------------------------

--
-- Struttura della tabella `corso`
--

CREATE TABLE `corso` (
  `nome` varchar(100) NOT NULL,
  `attivo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `corso`
--

INSERT INTO `corso` (`nome`, `attivo`) VALUES
('Fisica', 1),
('Informatica', 1),
('Matematica', 1),
('Musica', 0),
('Scienze', 0);

-- --------------------------------------------------------

--
-- Struttura della tabella `docente`
--

CREATE TABLE `docente` (
  `email` varchar(100) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `attivo` tinyint(1) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `docente`
--

INSERT INTO `docente` (`email`, `nome`, `cognome`, `attivo`) VALUES
('antonello@yahoo.com', 'Antonello', 'Perdonò', 0),
('ciruzz79@libero.it', 'Ciro', 'Sarracino', 1),
('marco@gmail.com', 'Marco', 'Antonini', 0),
('michele@gmail.com', 'Michele', 'Mariucci', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnamento`
--

CREATE TABLE `insegnamento` (
  `corso` varchar(100) CHARACTER SET utf8 NOT NULL,
  `docente` varchar(100) CHARACTER SET utf8 NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Dump dei dati per la tabella `insegnamento`
--

INSERT INTO `insegnamento` (`corso`, `docente`) VALUES
('Matematica', 'marco@gmail.com'),
('Fisica', 'marco@gmail.com'),
('Informatica', 'ciruzz79@libero.it'),
('Matematica', 'antonello@yahoo.com');

-- --------------------------------------------------------

--
-- Struttura della tabella `prenotazione`
--

CREATE TABLE `prenotazione` (
  `utente` varchar(30) NOT NULL,
  `corso` varchar(100) NOT NULL,
  `docente` varchar(200) NOT NULL,
  `data` varchar(10) NOT NULL,
  `fasciaOraria` enum('15.00 - 16.00','16.00 - 17.00','17.00 - 18.00','18.00 - 19.00') NOT NULL,
  `attiva` tinyint(1) NOT NULL DEFAULT 1,
  `effettuata` tinyint(1) NOT NULL DEFAULT 0,
  `dataCancellazione` varchar(10) NOT NULL DEFAULT 'none'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `prenotazione`
--

INSERT INTO `prenotazione` (`utente`, `corso`, `docente`, `data`, `fasciaOraria`, `attiva`, `effettuata`, `dataCancellazione`) VALUES
('heymehdi', 'Fisica', 'antonello@yahoo.com', '17/12/2022', '15.00 - 16.00', 1, 0, 'none'),
('heymehdi', 'Musica', 'antonello@yahoo.com', '22/12/2022', '18.00 - 19.00', 0, 0, '15/11/2022'),
('fplfrancesco', 'Informatica', 'ciruzz79@libero.it', '09/01/2023', '16.00 - 17.00', 1, 0, 'none'),
('ilConoscitore', 'Matematica', 'marco@gmail.com', '16/01/2023', '18.00 - 19.00', 1, 0, 'none');

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `username` varchar(30) NOT NULL,
  `password` char(64) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `ruolo` enum('studente','ospite','amministratore') NOT NULL,
  `attivo` tinyint(1) NOT NULL DEFAULT 1,
  `dataCreazione` varchar(10) NOT NULL,
  `dataCancellazione` varchar(10) NOT NULL DEFAULT 'none'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `utente`
--

INSERT INTO `utente` (`username`, `password`, `nome`, `cognome`, `ruolo`, `attivo`, `dataCreazione`, `dataCancellazione`) VALUES
('fplfrancesco', '5E884898DA28047151D0E56F8DC6292773603D0D6AABBDD62A11EF721D1542D8', 'Francesco', 'Francis', 'studente', 1, '17/12/2022', 'none'),
('heymehdi', '5994471ABB01112AFCC18159F6CC74B4F511B99806DA59B3CAF5A9C173CACFC5', 'Mehdi', 'Amlal', 'studente', 1, '17/12/2022', 'none'),
('ilConoscitore', '95BF20C96D834A2A13C14E753A51494827CF607F24D14E82FE6D224A57A42AD8', 'Mauro', 'Chiesa', 'studente', 1, '17/12/2022', 'none'),
('root', '4813494D137E1631BBA301D5ACAB6E7BB7AA74CE1185D456565EF51D737677B2', 'Michele', 'Sciacarri', 'amministratore', 1, '17/12/2022', 'none');

--
-- Indici per le tabelle scaricate
--

--
-- Indici per le tabelle `corso`
--
ALTER TABLE `corso`
  ADD PRIMARY KEY (`nome`);

--
-- Indici per le tabelle `docente`
--
ALTER TABLE `docente`
  ADD PRIMARY KEY (`email`,`attivo`) USING BTREE;

--
-- Indici per le tabelle `insegnamento`
--
ALTER TABLE `insegnamento`
  ADD KEY `corso` (`corso`),
  ADD KEY `docente` (`docente`);

--
-- Indici per le tabelle `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD PRIMARY KEY (`docente`,`data`,`fasciaOraria`,`attiva`,`dataCancellazione`),
  ADD KEY `utente` (`utente`),
  ADD KEY `corso` (`corso`);

--
-- Indici per le tabelle `utente`
--
ALTER TABLE `utente`
  ADD PRIMARY KEY (`username`,`attivo`,`dataCancellazione`);

--
-- Limiti per le tabelle scaricate
--

--
-- Limiti per la tabella `insegnamento`
--
ALTER TABLE `insegnamento`
  ADD CONSTRAINT `insegnamento_ibfk_1` FOREIGN KEY (`corso`) REFERENCES `corso` (`nome`),
  ADD CONSTRAINT `insegnamento_ibfk_2` FOREIGN KEY (`docente`) REFERENCES `docente` (`email`);

--
-- Limiti per la tabella `prenotazione`
--
ALTER TABLE `prenotazione`
  ADD CONSTRAINT `prenotazione_ibfk_1` FOREIGN KEY (`utente`) REFERENCES `utente` (`username`),
  ADD CONSTRAINT `prenotazione_ibfk_3` FOREIGN KEY (`docente`) REFERENCES `docente` (`email`),
  ADD CONSTRAINT `prenotazione_ibfk_4` FOREIGN KEY (`corso`) REFERENCES `corso` (`nome`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
