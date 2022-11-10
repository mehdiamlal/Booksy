package dataModel;


import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

import javax.xml.transform.Result;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;
import java.util.HashMap;

public class DAO {
    String url;
    String user;
    String pw;

    public DAO(String url, String user, String pw) {
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

    public boolean allNotNull(Object... args) {
        for(Object obj : args) {
            if(obj == null) {
                return false;
            }
        }

        return true;
    }

    public void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Utente autenticaUtente(String username, String password) {
        //se il metodo ritorna un null, vuol dire che l'autenticazione è fallita
        Connection conn = null;
        PreparedStatement st = null;
        ResultSet rs = null;
        Utente res = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM utente WHERE username = ? AND password = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);

            rs = st.executeQuery();

            if(rs.next()) {  //se la query ha restituito un risultato...
                res = new Utente(rs.getString("username"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("ruolo"));
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

            String sql = "INSERT INTO utente(username, password, nome, cognome, ruolo, dataCreazione)" +
                        "VALUES(?,?,?,?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, username.toLowerCase());
            st.setString(2, password);
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

    public ArrayList<Utente> ottieniElencoUtenti() {

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
            String sql = "INSERT INTO corso (nome) VALUES (?);";

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

            String sql = "UPDATE corso " +
                        "SET attivo = ?" +
                        "WHERE nome = ? AND ATTIVO = ?";

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

    public ArrayList<Corso> ottieniElencoCorsi() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "SELECT * FROM CORSO";
            st = conn.createStatement();
            ResultSet rs = st.executeQuery(sql);

            while(rs.next()) {
                Corso c = new Corso(rs.getString("nome"), rs.getString("attivo").equals("1"));
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
    public void aggiungiDocente(String email, String password, String nome, String cognome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO docente (email, nome, cognome, dataCreazione) VALUES (?,?,?,?);";

            st = conn.prepareStatement(sql);
            st.setString(1, email.toLowerCase());
            st.setString(2, nome);
            st.setString(3, cognome);
            st.setString(4, getDate());

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

    public void rimuoviDocente(String docente) {
        Connection conn = null;
        PreparedStatement st = null;

        rimuoviInsegnamenti(docente, null);
        rimuoviPrenotazioni(docente, null, null, null);

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE docente " +
                        "SET attivo = 0, dataCancellazione = ? " +
                        "WHERE email = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, docente);

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

    public ArrayList<Docente> ottieniElencoDocenti() {

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
                        rs.getString("password"),
                        rs.getString("nome"),
                        rs.getString("cognome")));
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
    public void aggiungiInsegnamento(String docente, String corso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO insegnamento (docente, corso) VALUES (?,?);";

            st = conn.prepareStatement(sql);
            st.setString(1, docente);
            st.setString(2, corso);

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

    public void rimuoviInsegnamenti(String docente, String corso) {
        Connection conn = null;
        PreparedStatement st = null;
        String sql = "DELETE FROM insegnamento";

        /* Tutti i parametri sono null */
        if(allNotNull(docente, corso) == false) {
            return;
        }

        /* Rimozione di tutti gli insegnamenti tenuti da un certo docente */
        if(docente != null && corso == null) {
            sql.concat("WHERE docente = " + docente);
        }

        /* Rimozione di tutti gli insegnamenti di un certo corso */
        if(docente == null && corso != null) {
            sql.concat("WHERE corso = " + corso);
        }

        /* Rimozione dell'insegnamento di un certo corso tenuto da un certo docente */
        sql.concat("WHERE docente = " + docente + " AND corso " + corso);

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

    //Metodi per la gestione delle operazioni sulla tabella prenotazione
    public void aggiungiPrenotazione(String username, String idCorso, String emailDocente, String data, String fasciaOraria) {

        //si presuppone che ciascuna prenotazione abbia un flag "attiva"
        //che viene settato a 1 di default quando viene creata
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO prenotazione (utente, corso, docente, data, fasciaOraria) VALUES (?, ?, ?, ?, ?);";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, idCorso);
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

    public void impostaPrenotazioneEffettuata(String docente, String data, String fasciaOraria) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE prenotazione " +
                        "SET attiva = 1 " +
                        "WHERE docente = ? AND data = ? AND fasciaOraria = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, docente);
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

    public void rimuoviPrenotazioni(String docente, String data, String fasciaOraria, String corso) {

        Connection conn = null;
        PreparedStatement st = null;
        String sql = "UPDATE prenotazione" +
                    "SET attiva = 0, dataCancellazione = " + getDate();

        if(docente != null && data == null && fasciaOraria == null && corso == null) {
            sql.concat("WHERE docente = " + docente + " AND attivo = 1");
        }

        if(corso != null && docente == null && data == null && fasciaOraria == null) {
            sql.concat("WHERE corso = " + corso + " AND attivo = 1");
        }

        sql.concat("WHERE docente = ? AND data = ? AND fasciaOraria = ? AND corso = ?");

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

    public ArrayList<Prenotazione> ottieniPrenotazioniUtente(String utente) {
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Prenotazione> prenotazioniUtente = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM prenotazione WHERE utente = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, utente);

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

    public ArrayList<Prenotazione> ottieniElencoPrenotazioniAttive() {
        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM prenotazione";

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

    private String getDateSlotPair(String date, String slot) {
        return "(" + date + ", " + slot +")";
    }

    //ritorna un'arraylist di slot nel formato (gg/MM/aaa, oraInizio - oraFine)
    //per tutte le date tra star e end (estremi compresi)
    private ArrayList<String> getTotalSlots(DateTime start, DateTime end) {
        ArrayList<String> ret = new ArrayList<>();
        int nOfDays = 0;

        // calcola range di date e le mette in lista
        DateTime tmp = start;
        while(tmp.isBefore(end) || tmp.equals(end)) {
            //formatta la data nel formato specificato
            DateTimeFormatter dtfOut = DateTimeFormat.forPattern("dd/MM/yyyy");
            ret.add(dtfOut.print(tmp));
            tmp = tmp.plusDays(1);
        }

        nOfDays = ret.size();

        //associa le fosce orarie alle date
        for(int i = 0; i < nOfDays; i++) {
            String slot1 = getDateSlotPair(ret.get(i), "15.00 - 16.00");
            ret.add(slot1);
            String slot2 = getDateSlotPair(ret.get(i), "16.00 - 17.00");
            ret.add(slot2);
            String slot3 = getDateSlotPair(ret.get(i), "17.00 - 18.00");
            ret.add(slot3);
            String slot4 = getDateSlotPair(ret.get(i), "18.00 - 19.00");
            ret.add(slot4);
        }

        //togli le date presenti nelle prime posizioni
        for(int i = 0; i < nOfDays; i++) {
            ret.remove(0);
        }

        return ret;
    }

    public HashMap<String, ArrayList<String>> ottieniSlotDisponibili(String dataInizio, String dataFine) {
        ArrayList<Prenotazione> listaPrenotazioni = ottieniElencoPrenotazioniAttive();
        ArrayList<Docente> listaDocenti = ottieniElencoDocenti();  //prendo i docenti attivi


        //Si presuppone che il formato delle date che vengono passate sia dd/MM/yyyy
        DateTime inizio = new DateTime(Integer.parseInt(dataInizio.substring(6)),
                                        Integer.parseInt(dataInizio.substring(3, 4)),
                                        Integer.parseInt(dataInizio.substring(0, 1)), 0, 0);
        DateTime fine = new DateTime(Integer.parseInt(dataFine.substring(6)),
                                    Integer.parseInt(dataFine.substring(3, 4)),
                                    Integer.parseInt(dataFine.substring(0, 1)), 0, 0);

        ArrayList<String> totalSlots = getTotalSlots(inizio, fine);
        HashMap<String, ArrayList<String>> ris = new HashMap<>();

        for(Docente d : listaDocenti) {
            ris.put(d.getEmail(), totalSlots);
        }

        for(Prenotazione p : listaPrenotazioni) {
            ris.get(p.getDocente()).remove(getDateSlotPair(p.getData(), p.getFasciaOraria()));
        }

        return ris;
    }
}