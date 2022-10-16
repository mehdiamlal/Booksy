package businessLogic;
import dataModel.DAO;
import dataModel.Docente;
import dataModel.Utente;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DAO.registerDriver();

        DAO.eliminaPrenotazione("mimmino", "3", "1", "24/02/2023");

    }
}
