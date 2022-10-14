package dataModel;
import java.sql.*;

public class DAO {
    String url = "jdbc:mysql://localhost:8889/DATABASE_NAME?useSSL=false"; //per MAMP (Mac OS)
    // inserire alternativamente l'url per XAMPP (Windows)
    String user = "root";
    String pw = "root";

    public static void registerDriver() {
        try {
            DriverManager.registerDriver(new com.mysql.jdbc.Driver());
            System.out.println("Driver registrato con successo.");
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
    }
}
