package com.application.progettotweb;

import com.google.gson.Gson;
import dataModel.DAO;
import dataModel.Docente;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.regex.Pattern;

@WebServlet(name = "servletDocente", value = "/docenti")
public class ServletDocente extends HttpServlet {
    DAO dataModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext ctx = config.getServletContext();
        Object tmpDAO = ctx.getAttribute("datamodel");

        if (tmpDAO instanceof DAO) {
            dataModel = (DAO) tmpDAO;
        }
    }

    /* Possibili richieste GET relative ai docenti:
    * - elenco docenti
    * - elenco docenti che insegnano un determinato corso
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        ArrayList<Docente> elencoDocenti = new ArrayList<>();

        switch(tipoRichiesta) {
            case "ottieniElencoDocenti":
                elencoDocenti.addAll(dataModel.ottieniDocenti());
                break;

            case "filtraDocentePerCorso":
                String filtro = req.getParameter("corso");
                elencoDocenti.addAll(dataModel.filtraDocentePerCorso(filtro));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo , operazione non valida. Riprovare.");
                return;
        }

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(elencoDocenti);
        out.print(risposta);
    }

    private boolean controllaMail(String indirizzoMail) {
        String regex = "^[\\w!#$%&'*+/=?`{|}~^-]+(?:\\.[\\w!#$%&'*+/=?`{|}~^-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,6}";
        Pattern pattern = Pattern.compile(regex);

        return pattern.matcher(indirizzoMail).matches();
    }

    /* Possibili richieste POST relative ai docenti:
     * - aggiunta docente
     * - rimozione docente
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tipoRichiesta = req.getParameter("action");

        String mail, nome, cognome;

        mail = req.getParameter("mail");
        nome = req.getParameter("nome");
        cognome = req.getParameter("cognome");

        boolean richiestaNonValida = tipoRichiesta == null || !controllaMail(mail);
        if(richiestaNonValida) {
            tipoRichiesta = "";
        }

        switch(tipoRichiesta) {
            case "aggiungiDocente":
                dataModel.aggiungiDocente(mail, nome, cognome);
                break;

            case "rimuoviDocente":
                dataModel.rimuoviDocente(mail);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
