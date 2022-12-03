package com.application.progettotweb;

import com.google.gson.Gson;
import dataModel.DAO;
import dataModel.Docente;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

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

        String tipoRichiesta = req.getParameter("action");
        ArrayList<Docente> elencoDocenti = new ArrayList<>();

        switch(tipoRichiesta) {
            case "ottieniElencoDocenti":
                elencoDocenti.addAll(dataModel.ottieniDocenti());
                break;

            case "filtraDocentePerCorso":
                String filtro = req.getParameter("docente");
                elencoDocenti.addAll(dataModel.filtraDocentePerCorso(filtro));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(elencoDocenti);
        out.print(risposta);
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
