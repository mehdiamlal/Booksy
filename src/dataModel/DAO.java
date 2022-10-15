package dataModel;
import javax.xml.transform.Result;
import java.awt.desktop.SystemEventListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.Objects;

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


    //metodi che gestiscono la tabella docente
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
                st.close();
                conn.close();
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
                st.close();
                conn.close();
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
                st.close();
                conn.close();
            } catch(SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        return list;
    }
}
