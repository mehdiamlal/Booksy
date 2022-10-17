package businessLogic;
import dataModel.DAO;
import dataModel.Docente;
import dataModel.Utente;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DAO.registerDriver();

        DAO.aggiungiPrenotazione("blur89", "3", "28/09/2022", "15.00 - 16.00");
        DAO.aggiungiPrenotazione("heymehdi", "3", "28/09/2022", "16.00 - 17.00");

    }
}
