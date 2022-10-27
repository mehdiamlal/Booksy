package dataModel;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.ArrayList;

public class DAO {
    static String url = "jdbc:mysql://localhost:8889/test?useSSL=false"; //per MAMP (Mac OS)
    // inserire alternativamente l'url per XAMPP (Windows)
    static String user = "root";
    static String pw = "root";

    //metodo di utility per generare la date odierna in formato dd/MM/yyyy
    //NB: le lettere dei mesi devono essere in maiuscolo per evitare errori
    private static String getDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        return dateFormat.format(new Date());
    }

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodi per la gestione delle operazioni sulla tabella utente
    public static void aggiungiUtente(String username, String password, String nome, String cognome, String ruolo) {

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

    public static void rimuoviUtente(String username) {

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

    public static ArrayList<Utente> ottieniElencoUtenti() {

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
            ResultSet rs = st.executeQuery("SELECT * FROM utente");

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
    public static void aggiungiCorso(String nome) {

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

    public static void rimuoviCorso(String nome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "DELETE FROM corso " +
                    "WHERE nome = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, nome);

            st.executeUpdate();

            System.out.println("Corso rimosso con successo");
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

    public static ArrayList<Corso> ottieniElencoCorsi() {

        Connection conn = null;
        Statement st = null;
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            st = conn.createStatement();

            ResultSet rs = st.executeQuery("SELECT * FROM utente");

            while(rs.next()) {
                Corso c = new Corso(rs.getString("nome"));
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
    public static void aggiungiDocente(String email, String password, String nome, String cognome) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO docente (email, password, nome, cognome, dataCreazione) VALUES (?,?,?,?,?);";

            st = conn.prepareStatement(sql);
            st.setString(1, email.toLowerCase());
            st.setString(2, password);
            st.setString(3, nome);
            st.setString(4, cognome);
            st.setString(5, getDate());

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

    public static void rimuoviDocente(String email) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE docente SET attivo = 0, dataCancellazione = ? WHERE email = ? AND attivo = 1";

            st = conn.prepareStatement(sql);
            st.setString(1, getDate());
            st.setString(2, email);

            st.executeUpdate();

            System.out.println("Docente rimosso con successo.");
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

    public static ArrayList<Docente> ottieniElencoDocenti() {

        Connection conn = null;
        PreparedStatement st = null;
        ArrayList<Docente> elencoDocenti = new ArrayList<>();

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM docente";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                elencoDocenti.add(new Docente(rs.getString("email"),
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
    public static void aggiungiInsegnamento(String docente, String corso) {
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

    public static void rimuoviInsegnamento(String emailDocente, String nomeCorso) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "DELETE FROM Corso" +
                    "WHERE docente = ? AND corso = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, emailDocente);
            st.setString(2, nomeCorso);

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
    public static void aggiungiPrenotazione(String username, String idCorso, String data, String fasciaOraria) {

        //si presuppone che ciascuna prenotazione abbia un flag "attiva"
        //che viene settato a 1 di default quando viene creata
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO prenotazione (utente, corso, data, fasciaOraria) VALUES (?, ?, ?, ?);";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, idCorso);
            st.setString(3, data);
            st.setString(4, fasciaOraria);

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

    public static void rimuoviPrenotazione(String idCorso, String data, String fasciaOraria) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE prenotazione SET attiva = 0 WHERE corso = ? AND data = ? AND fasciaOraria = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, idCorso);
            st.setString(2, data);
            st.setString(3, fasciaOraria);

            st.executeUpdate();

            System.out.println("Prenotazione eliminata con successo.");
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
}