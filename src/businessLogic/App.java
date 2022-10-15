package businessLogic;
import dataModel.DAO;
import dataModel.Docente;

import java.util.ArrayList;

public class App {
    public static void main(String[] args) {
        DAO.registerDriver();

        ArrayList<Docente> listaDocenti = DAO.ottieniElencoDocenti();

        for(Docente d : listaDocenti) {
            System.out.println(d);
        }
    }
}
