package dataModel;

import java.sql.*;
import java.util.ArrayList;

public class DAO {
    final static private String url = "jdbc:mysql://localhost:8889/DATABASE_NAME?useSSL=false"; // per MAMP (Mac OS)
              // = "jdbc:mysql://localhost:3306/test"; per XAMPP (Windows)

    final static private String user = "root";
    final static private String pw = "root";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void aggiungiUtente(Utente u) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            String sql = "INSERT INTO Utente(Account, Password, Ruolo)" +
                        "VALUES(?,?,?)";

            st = conn.prepareStatement(sql);
            st.setString(1, u.getNomeAccount());
            st.setString(2, u.getPassword());
            st.setString(3, u.getRuolo());

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

    public static void rimuoviUtente(Utente u) {

        Connection conn = null;
        PreparedStatement st = null;

        try {
            conn = DriverManager.getConnection(url, user, pw);
            System.out.println("Connesso al database locale.");

            /* RIDONDANZA: è sufficiente usare la chiave primaria per
            identificare la tupla da rimuovere */
            String sql = "DELETE FROM Utente " +
                    "WHERE Account = ? AND Password = ? AND Ruolo = ?";

            st = conn.prepareStatement(sql);
            st.setString(1, u.getNomeAccount());
            st.setString(2, u.getPassword());
            st.setString(3, u.getRuolo());

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
            ResultSet rs = st.executeQuery("SELECT * FROM Utente");
            ArrayList<Utente> elencoUtenti = new ArrayList<>();

            /* Iteriamo sul ResultSet ottenuto dalla query, aggiungendo
            ogni elemento all'ArrayList contentente utenti */
            while(rs.next()) {
                Utente u = new Utente(rs.getString("Account"),
                        rs.getString("Password"),
                        rs.getString("Ruolo"));
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
}
