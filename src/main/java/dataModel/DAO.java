package dataModel;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {
    String url;
    String user;
    String pw;

    private void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public DAO(String url, String user, String pw) {
        registerDriver();
        this.url = url;
        this.user = user;
        this.pw = pw;
    }

    //metodo di utility per generare la date odierna in formato dd/MM/yyyy
    //NB: le lettere dei mesi devono essere in maiuscolo per evitare errori
    private String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    private boolean allNull(Object... args) {
        for(Object obj : args) {
            if(obj != null) {
                return false;
            }
        }

        return true;
    }

    public Utente autenticaUtente(String username, String password) {
        //se il metodo ritorna un null, vuol dire che l'autenticazione è fallita
        Connection conn = null;
        PreparedStatement st = null;
        Utente res = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM utente WHERE username = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            if(rs.next()) {  //se la query ha restituito un risultato...
                if(Service.checkSHA2(rs.getString("password"), password)) { //se la password è corretta
                    res = new Utente(rs.getString("username"),
                            rs.getString("nome"),
                            rs.getString("cognome"),
                            rs.getString("ruolo"));
                }
            }

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return res;
    }

    //Metodi per la gestione delle operazioni sulla tabella utente
    public void aggiungiUtente(String username, String password, String nome, String cognome, String ruolo) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "INSERT INTO utente(username, password, nome, cognome, ruolo, dataCreazione) VALUES(?,?,?,?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, username.toLowerCase());
            st.setString(2, Service.encryptSHA2(password));
            st.setString(3, nome);
            st.setString(4, cognome);
            st.setString(5, ruolo);
            st.setString(6, getDate());

            st.executeUpdate();

            System.out.println("Utente aggiunto con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviUtente(String username) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "UPDATE utente SET attivo = 0, dataCancellazione = ? WHERE username = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, username);

            st.execute();

            System.out.println("Utente rimosso con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Utente> ottieniUtenti() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Utente> elencoUtenti = new ArrayList<>();

        try {
            /* Stabiliamo la connessione col database */
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            /* Creiamo lo statement SQL da eseguire */
            st = conn.createStatement();

            /* Creiamo un ResultSet per contenere il risultato della query
             * e un ArrayList per averlo in una struttura dati più approcciabile */
            ResultSet rs = st.executeQuery("SELECT * FROM utente WHERE attivo = 1");

            /* Iteriamo sul ResultSet ottenuto dalla query, aggiungendo
            ogni elemento all'ArrayList contentente utenti */
            while(rs.next()) {
                Utente u = new Utente(rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("ruolo"));
                elencoUtenti.add(u);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoUtenti;
    }

    //Metodi per gestione delle operazioni sulla tabella corso
    public void aggiungiCorso(String nome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO corso (nome) VALUES (?)";

            st = conn.prepareStatement(sql);
            st.setString(1, nome);

            st.executeUpdate();

            System.out.println("Corso aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void cambiaStatoCorso(String nome) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "UPDATE corso SET attiva = ? WHERE nome = ? AND attiva = ?";

            /* Provo ad eseguire la query sui corsi con nome "nome" inattivi */
            String setActive, whereActive;

            st = conn.prepareStatement(sql);
            setActive = "1"; st.setString(1, setActive);
            st.setString(2, nome);
            whereActive = "0"; st.setString(3, whereActive);

            /* Se non trovo corsi con titolo "nome" inattivi, faccio la query inversa
            * impostando come inattivi i corsi con titolo "nome" attivi */
            if(st.executeUpdate() == 0) {
                rimuoviPrenotazioni(null, null, null, nome);
                rimuoviInsegnamenti(null, nome);

                setActive = "0"; st.setString(1, setActive);
                whereActive = "1"; st.setString(3, whereActive);
                st.executeUpdate();
            }

            System.out.println("Stato del corso aggiornato.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Corso> ottieniCorsiAttivi() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM corso WHERE attiva = 1";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Corso c = new Corso(rs.getString("nome"), rs.getString("attiva").equals("1"));
                elencoCorsi.add(c);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoCorsi;
    }

    public ArrayList<Corso> ottieniCorsi() {
        Connection conn = null;
        Statement st = null;
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM corso";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Corso c = new Corso(rs.getString("nome"), rs.getString("attiva").equals("1"));
                elencoCorsi.add(c);
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoCorsi;
    }

    //Metodi per la gestione delle operazioni sulla tabella docente
    public void aggiungiDocente(String email, String nome, String cognome) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO docente (email, nome, cognome) VALUES (?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, email.toLowerCase());
            st.setString(2, nome);
            st.setString(3, cognome);

            st.executeUpdate();

            System.out.println("Docente aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null) {conn.close();}
                if(st != null) {st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviDocente(String email) {
        Connection conn = null;
        PreparedStatement st = null;

        rimuoviInsegnamenti(email, null);
        rimuoviPrenotazioni(email, null, null, null);

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE docente SET attivo = 0 WHERE email = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, email);

            st.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Docente> ottieniDocenti() {

        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Docente> elencoDocenti = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM docente WHERE attivo = 1";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                elencoDocenti.add(new Docente(rs.getString("email"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getBoolean("attivo")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoDocenti;
    }

    //Metodi per la gestione delle operazioni sulla tabella insegnamento
    public void aggiungiInsegnamento(String nomeCorso, String emailDocente) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO insegnamento (corso, docente) VALUES (?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, nomeCorso);
            st.setString(2, emailDocente);

            st.executeUpdate();

            System.out.println("Insegnamento aggiunto con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviInsegnamenti(String nomeCorso, String emailDocente) {
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "DELETE FROM insegnamento ";

        /* Tutti i parametri sono null */
        if(allNull(emailDocente, nomeCorso)) {
            return;
        }

        /* Rimozione di tutti gli insegnamenti tenuti da un certo docente */
        if(emailDocente != null && nomeCorso == null) {
            sql = sql.concat("WHERE docente = '" + emailDocente + "'");
        }

        /* Rimozione di tutti gli insegnamenti di un certo corso */
        if(nomeCorso != null && emailDocente == null) {
            sql = sql.concat("WHERE corso = '" + nomeCorso + "'");
        }

        /* Rimozione dell'insegnamento di un certo corso tenuto da un certo docente */
        sql = sql.concat("WHERE docente = '" + emailDocente + "' AND corso = '" + nomeCorso + "'");

        try {
            conn = DriverManager.getConnection(url, user, pw);
            st = conn.prepareStatement(sql);
            st.executeUpdate();

            System.out.println("Insegnamento rimosso con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public HashMap<String, ArrayList<String>> ottieniInsegnamenti() {
        Connection conn = null;
        PreparedStatement st = null;
        HashMap<String, ArrayList<String>> elencoInsegnamenti = new HashMap<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM insegnamento";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                if(!elencoInsegnamenti.containsKey(rs.getString("docente"))) {
                    elencoInsegnamenti.put(rs.getString("docente"), new ArrayList<>());
                    elencoInsegnamenti.get(rs.getString("docente")).add(rs.getString("corso"));
                } else {
                    elencoInsegnamenti.get(rs.getString("docente")).add(rs.getString("corso"));
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoInsegnamenti;
    }

    public ArrayList<Docente> filtraDocentePerCorso(String corso) {
        //ritorna la lista di docenti (attivi) che insegnano corso
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Docente> listaDocenti = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM insegnamento WHERE corso = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, corso);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                ArrayList<Docente> elencoTuttiDocenti = ottieniDocenti(); //tutti docenti attivi
                for(Docente d : elencoTuttiDocenti) {
                    if(d.getEmail().equals(rs.getString("docente"))) {
                        listaDocenti.add(d);
                    }
                }
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return listaDocenti;
    }

    public ArrayList<Docente> ottieniDocentiLiberi(String corso) {
        //ritorna la lista di docenti (attivi) che non insegnano corso
        ArrayList<Docente> listaDocentiAttivi = this.ottieniDocenti();
        ArrayList<Docente> listaDocentiCheInsegnano = this.filtraDocentePerCorso(corso);

        ArrayList<Docente> duplicates = new ArrayList<>();
        for(Docente d1 : listaDocentiAttivi) {
            for(Docente d2 : listaDocentiCheInsegnano) {
                if(d1.getEmail().equals(d2.getEmail())) {
                    duplicates.add(d1);
                }
            }
        }

        for(Docente d : duplicates) {
            listaDocentiAttivi.remove(d);
        }

        return listaDocentiAttivi;
    }

    //Metodi per la gestione delle operazioni sulla tabella prenotazione
    public void aggiungiPrenotazione(String username, String nomeCorso, String emailDocente, String data, String fasciaOraria) {
        //si presuppone che ciascuna prenotazione abbia un flag "attiva" e uno "effettuata"
        //che vengono settati rispettivamente a 1 e 0 di default quando viene creata
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO prenotazione (utente, corso, docente, data, fasciaOraria) VALUES (?, ?, ?, ?, ?)";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, nomeCorso);
            st.setString(3, emailDocente);
            st.setString(4, data);
            st.setString(5, fasciaOraria);

            st.executeUpdate();

            System.out.println("Prenotazione aggiunta con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void impostaPrenotazioneEffettuata(String emailDocente, String data, String fasciaOraria) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE prenotazione " +
                        "SET effettuata = 1 " +
                        "WHERE docente = ? AND data = ? AND fasciaOraria = ? AND attiva = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, emailDocente);
            st.setString(2, data);
            st.setString(3, fasciaOraria);

            st.executeUpdate();

            System.out.println("Prenotazione impostata come effettuata.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public void rimuoviPrenotazioni(String emailDocente, String nomeCorso, String data, String fasciaOraria) {
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "UPDATE prenotazione SET attiva = 0, dataCancellazione = '" + getDate() + "'";

        if(emailDocente != null && allNull(data, fasciaOraria, nomeCorso)) {
            sql = sql.concat("WHERE docente = '" + emailDocente + "' AND attivo = 1");
        }

        if(nomeCorso != null && allNull(emailDocente, data, fasciaOraria)) {
            sql = sql.concat("WHERE corso = '" + nomeCorso + "' AND attivo = 1");
        }

        sql = sql.concat("WHERE docente = '" + emailDocente + "' AND data = '" + data + "' AND fasciaOraria = '" + fasciaOraria + "' AND corso = '" + nomeCorso + "'");

        try {
            conn = DriverManager.getConnection(url, user, pw);
            st = conn.prepareStatement(sql);
            st.executeUpdate();

            System.out.println("Prenotazioni eliminate con successo.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (conn != null && st != null) {
                    conn.close();
                    st.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public ArrayList<Prenotazione> ottieniPrenotazioniUtente(String username) {
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM prenotazione WHERE utente = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, username);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                prenotazioniUtente.add(new Prenotazione(rs.getString("utente"),
                        rs.getString("corso"),
                        rs.getString("docente"),
                        rs.getString("data"),
                        rs.getString("fasciaOraria"),
                        rs.getString("attiva").equals("1")));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return prenotazioniUtente;
    }

    public ArrayList<Prenotazione> ottieniPrenotazioniAttive() {
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM prenotazione WHERE attiva = 1";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                elencoPrenotazioni.add(new Prenotazione(rs.getString("utente"),
                        rs.getString("corso"),
                        rs.getString("docente"),
                        rs.getString("data"),
                        rs.getString("fasciaOraria"),
                        true));
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return elencoPrenotazioni;
    }

    //ritorna un'arraylist di slot nel formato (gg/MM/aaa, oraInizio - oraFine)
    //per tutte le date tra star e end (estremi compresi)
    private HashMap<String, ArrayList<String>> getTotalSlots(DateTime start, DateTime end) {
        HashMap<String, ArrayList<String>> ret = new HashMap<>();

        // calcola range di date e le mette in lista
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            //formatta la data nel formato specificato
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
            ret.put(dtfOut.print(tmp), new ArrayList<>());
            tmp = tmp.plusDays(1);
        }

        ret.forEach((k, v) -> {
            v.add("15.00 - 16.00");
            v.add("16.00 - 17.00");
            v.add("17.00 - 18.00");
            v.add("18.00 - 19.00");
        });

        return ret;
    }

    public HashMap<String, HashMap<String, ArrayList<String>>> ottieniSlotDisponibili(String dataInizio, String dataFine) {
        ArrayList<Prenotazione> listaPrenotazioni = ottieniPrenotazioniAttive();
        ArrayList<Docente> listaDocenti = ottieniDocenti();  //prendo i docenti attivi


        //Si presuppone che il formato delle date che vengono passate sia dd/MM/yyyy
        DateTime inizio = new DateTime(Integer.parseInt(dataInizio.substring(6)),
                                        Integer.parseInt(dataInizio.substring(3, 5)),
                                        Integer.parseInt(dataInizio.substring(0, 2)), 0, 0);
        DateTime fine = new DateTime(Integer.parseInt(dataFine.substring(6)),
                                    Integer.parseInt(dataFine.substring(3, 5)),
                                    Integer.parseInt(dataFine.substring(0, 2)), 0, 0);

//        HashMap<String, ArrayList<String>> totalSlots = getTotalSlots(inizio, fine);
        HashMap<String, HashMap<String, ArrayList<String>>> ris = new HashMap<>();

        for(Docente d : listaDocenti) {
            ris.put(d.getEmail(), getTotalSlots(inizio, fine));
        }

        for(Prenotazione p : listaPrenotazioni) {
            if(ris.get(p.getDocente()).get(p.getData()) != null) {
                ris.get(p.getDocente()).get(p.getData()).remove(p.getFasciaOraria());
                System.out.println("Ho tolto a " + p.getDocente() + " la fascia delle: " + p.getFasciaOraria());
            }
        }

        return ris;
    }
}