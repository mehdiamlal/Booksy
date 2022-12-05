package com.application.progettotweb;

import com.google.gson.Gson;
import dataModel.DAO;
import dataModel.Prenotazione;

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

@WebServlet(name = "servletPrenotazione", value = "/servlet-prenotazione")
public class ServletPrenotazione extends HttpServlet {
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

    /* Possibili richieste GET relative ai prenotazioni
    * - elenco prenotazioni attive
    * - elenco prenotazioni utente
    * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();

        switch(tipoRichiesta) {
            case "ottieniPrenotazioniAttive":
                elencoPrenotazioni.addAll(dataModel.ottieniPrenotazioniAttive());
                break;

            case "ottieniPrenotazioniUtente":
                String filtro = req.getParameter("utente");
                elencoPrenotazioni.addAll(dataModel.ottieniPrenotazioniUtente(filtro));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                return;
        }

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(elencoPrenotazioni);
        out.print(risposta);
    }

    /* Possibili richieste POST relative ai prenotazioni
     * - aggiunta prenotazione
     * - rimozione prenotazione
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        String username, idCorso, emailDocente, data, fasciaOraria;

        username = req.getParameter("username");
        emailDocente = req.getParameter("emailDocente");
        idCorso = req.getParameter("idCorso");
        data = req.getParameter("data");
        fasciaOraria = req.getParameter("fasciaOraria");

        switch(tipoRichiesta) {
            case "aggiungiPrenotazione":
                dataModel.aggiungiPrenotazione(username, emailDocente, idCorso, data, fasciaOraria);
                break;

            case "rimuoviPrenotazione":
                dataModel.rimuoviPrenotazioni(emailDocente, idCorso, data, fasciaOraria);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
