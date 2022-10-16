package dataModel;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    static String url = "jdbc:mysql://localhost:8889/test?useSSL=false"; //per MAMP (Mac OS)
    // inserire alternativamente l'url per XAMPP (Windows)
    static String user = "root";
    static String pw = "root";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    //Metodi per la gestione delle operazioni sulla tabella utente
    public static void aggiungiUtente(String username, String password, String ruolo) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "INSERT INTO Utente(username, password, ruolo)" +
                    "VALUES(?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, password);
            st.setString(3, ruolo);

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

    public static void rimuoviUtente(String username) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            /* RIDONDANZA: è sufficiente usare la chiave primaria per
            identificare la tupla da rimuovere */
            String sql = "DELETE FROM Utente " +
                    "WHERE username = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, username);

            st.execute();
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

        try {
            /* Stabiliamo la connessione col database */
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            /* Creiamo lo statement SQL da eseguire */
            st = conn.createStatement();

            /* Creiamo un ResultSet per contenere il risultato della query
             * e un ArrayList per averlo in una struttura dati più approcciabile */
            ResultSet rs = st.executeQuery("SELECT * FROM utente");
            ArrayList<Utente> elencoUtenti = new ArrayList<>();

            /* Iteriamo sul ResultSet ottenuto dalla query, aggiungendo
            ogni elemento all'ArrayList contentente utenti */
            while(rs.next()) {
                Utente u = new Utente(rs.getString("username"),
                        rs.getString("password"),
                        rs.getString("ruolo"));
                elencoUtenti.add(u);
            }

            return elencoUtenti;

        } catch(SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if(conn != null && st != null) {conn.close(); st.close();}
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        return null;
    }


    //Metodi per la gestione delle operazioni sulla tabella docente
    public static void aggiungiDocente(String nome, String cognome) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO docente (nome, cognome) VALUES (?, ?);";

            st = conn.prepareStatement(sql);
            st.setString(1, nome);
            st.setString(2, cognome);

            st.executeUpdate();

            System.out.println("Docente aggiunto con successo.");
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

    public static void rimuoviDocente(String id) {
        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "UPDATE docente SET attivo = 0 WHERE idDocente = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, id);

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
        ArrayList<Docente> list = new ArrayList<>();
        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "SELECT * FROM docente";

            st = conn.prepareStatement(sql);

            ResultSet rs = st.executeQuery();

            while(rs.next()) {
                list.add(new Docente(rs.getString("nome"),
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
        return list;
    }

    //Metodi per la gestione delle operazioni sulla tabella prenotazione
    public static void aggiungiPrenotazione(String username, String idDocente, String idCorso, String data) {
        Connection conn = null;
        PreparedStatement st = null;
        try {
            conn = DriverManager.getConnection(url, user, pw);
            String sql = "INSERT INTO prenotazione (utente, docente, corso, data) VALUES (?, ?, ?, ?);";

            st = conn.prepareStatement(sql);
            st.setString(1, username);
            st.setString(2, idDocente);
            st.setString(3, idCorso);
            st.setString(4, data);

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

}
