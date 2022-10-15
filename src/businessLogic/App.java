package businessLogic;
import dataModel.DAO;

public class App {
    public static void main(String[] args) {
        DAO.registerDriver();
        DAO.aggiungiDocente("Paolo", "Bianchi");
        DAO.aggiungiDocente("Michele", "Santoro");
        DAO.aggiungiDocente("Giovanni", "Antonucci");
        DAO.rimuoviDocente("2");
    }
}
