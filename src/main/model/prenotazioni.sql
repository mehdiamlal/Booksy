-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Creato il: Feb 08, 2023 alle 22:56
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
('Analisi', 1),
('Chimica', 1),
('Economia', 1),
('Filosofia', 1),
('Fisica', 0),
('Informatica', 0),
('Inglese', 1),
('Italiano', 1),
('Matematica', 1),
('Storia', 1);

-- --------------------------------------------------------

--
-- Struttura della tabella `docente`
--

CREATE TABLE `docente` (
  `email` varchar(100) NOT NULL,
  `nome` varchar(50) NOT NULL,
  `cognome` varchar(50) NOT NULL,
  `attivo` tinyint(1) NOT NULL DEFAULT 1,
  `dataCreazione` varchar(10) NOT NULL,
  `dataCancellazione` varchar(10) NOT NULL DEFAULT 'none'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `docente`
--

INSERT INTO `docente` (`email`, `nome`, `cognome`, `attivo`, `dataCreazione`, `dataCancellazione`) VALUES
('albi77@gmail.com', 'Alberto', 'Caccia', 1, '09/12/2022', 'none'),
('ciruzz79@libero.it', 'Ciro', 'Sarracino', 1, '21/10/2022', 'none'),
('edoardo@gmail.com', 'Edoardo', 'Gilda', 1, '08/12/2022', 'none'),
('eladio@gmail.com', 'Eladio', 'Vasquez', 1, '08/12/2022', 'none'),
('giovanni3@gmail.com', 'Giovanni', 'Tiredio', 1, '08/12/2022', 'none'),
('marco@gmail.com', 'Marco', 'Antonini', 1, '19/10/2022', 'none'),
('marini@libero.it', 'Alberto', 'Marini', 1, '09/12/2022', 'none'),
('mario.nizza@gmail.com', 'Mario', 'Nizza', 1, '08/12/2022', 'none'),
('marypi@gmail.com', 'Maria', 'Pistoia', 1, '09/12/2022', 'none'),
('mehdi@gmail.com', 'Mehdi', 'Amlal', 1, '09/12/2022', 'none'),
('michele.molteni@gmail.com', 'Michele', 'Molteni', 1, '08/12/2022', 'none'),
('michele@gmail.com', 'Michele', 'Mariucci', 1, '19/10/2022', 'none'),
('monga01@yahoo.com', 'Marco', 'Ongaro', 1, '09/12/2022', 'none'),
('nino@gmail.com', 'Antonino', 'Pirro', 1, '08/12/2022', 'none'),
('paolooo@gmail.com', 'Paolo', 'Melani', 1, '08/12/2022', 'none'),
('paulie@gmail.com', 'Paolo', 'Gualtieri', 1, '08/12/2022', 'none'),
('pirconazzi@gmail.com', 'Saverio', 'Pirconazzi', 1, '08/12/2022', 'none'),
('raffy@gmail.com', 'Raffaele', 'Milone', 1, '08/12/2022', 'none'),
('tony.sop@gmail.com', 'Antonio', 'Soprano', 1, '08/12/2022', 'none');

-- --------------------------------------------------------

--
-- Struttura della tabella `insegnamento`
--

CREATE TABLE `insegnamento` (
  `docente` varchar(100) NOT NULL,
  `corso` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dump dei dati per la tabella `insegnamento`
--

INSERT INTO `insegnamento` (`docente`, `corso`) VALUES
('edoardo@gmail.com', 'Italiano'),
('eladio@gmail.com', 'Inglese'),
('marco@gmail.com', 'Economia'),
('marini@libero.it', 'Storia'),
('mario.nizza@gmail.com', 'Analisi'),
('mario.nizza@gmail.com', 'Informatica');

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

-- --------------------------------------------------------

--
-- Struttura della tabella `utente`
--

CREATE TABLE `utente` (
  `username` varchar(30) NOT NULL,
  `password` varchar(300) NOT NULL,
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
('heymehdi', '2CF24DBA5FB0A30E26E83B2AC5B9E29E1B161E5C1FA7425E73043362938B9824', 'Mehdi', 'Amlal', 'studente', 1, '09/12/2022', 'none'),
('root', '4813494D137E1631BBA301D5ACAB6E7BB7AA74CE1185D456565EF51D737677B2', 'Pasquale', 'Lipari', 'amministratore', 1, '16/12/2022', 'none');

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
  ADD PRIMARY KEY (`email`,`attivo`,`dataCancellazione`);

--
-- Indici per le tabelle `insegnamento`
--
ALTER TABLE `insegnamento`
  ADD PRIMARY KEY (`docente`,`corso`),
  ADD KEY `corso` (`corso`);

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
