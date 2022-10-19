package businessLogic;
import dataModel.DAO;
import dataModel.Docente;
import dataModel.Utente;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DAO.registerDriver();

        DAO.aggiungiDocente("michele@gmail.com", "123456", "Michele", "Mariucci");
        DAO.aggiungiDocente("marco@gmail.com", "4312", "Marco", "Antonini");
        DAO.rimuoviDocente("michele@gmail.com");
        DAO.aggiungiDocente("michele@gmail.com", "123456", "Michele", "Mariucci");
        DAO.rimuoviDocente("michele@gmail.com");



    }
}
