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
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

@WebServlet(name = "servletPrenotazione", value = "/prenotazioni")
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
    * - elenco tutte le prenotazioni
    * - elenco (3) prenotazioni utente più imminenti
    * */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        HttpSession session = req.getSession(false);
        if(session == null) {
            //sessione scaduta o inesistente
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            String risposta = gson.toJson("no_session");
            out.print(risposta);
            return;
        }
        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        if(!(session.getAttribute("ruolo").equals("amministratore")) && !(session.getAttribute("ruolo").equals("studente"))) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
            return;
        }

        ArrayList<Prenotazione> elencoPrenotazioni = new ArrayList<>();
        String filtro = req.getParameter("utente");

        switch(tipoRichiesta) {
            case "ottieniPrenotazioniAttive":
                if(!(session.getAttribute("ruolo").equals("amministratore"))) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
                    return;
                }
                elencoPrenotazioni.addAll(dataModel.ottieniPrenotazioniAttive());
                break;

            case "ottieniTuttePrenotazioni":
                if(!(session.getAttribute("ruolo").equals("amministratore"))) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
                    return;
                }
                elencoPrenotazioni.addAll(dataModel.ottieniTuttePrenotazioni());
                break;

            case "ottieniPrenotazioniUtente":
                elencoPrenotazioni.addAll(dataModel.ottieniPrenotazioniUtente(filtro));
                break;

            case "ottieniPrenotazioniUtenteImminenti":
                elencoPrenotazioni.addAll(dataModel.ottieniPrenotazioniUtenteImminenti(filtro));
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
     * - imposta prenotazione come effettuata
     * - rimozione prenotazione
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        HttpSession session = req.getSession(false);
        if(session == null) {
            //sessione scaduta o inesistente
            PrintWriter out = resp.getWriter();
            Gson gson = new Gson();
            String risposta = gson.toJson("no_session");
            out.print(risposta);
            return;
        }
        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        if(!(session.getAttribute("ruolo").equals("amministratore")) && !(session.getAttribute("ruolo").equals("studente"))) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
            return;
        }

        String username, idCorso, emailDocente, data, fasciaOraria;

        username = req.getParameter("username");
        idCorso = req.getParameter("idCorso");
        emailDocente = req.getParameter("emailDocente");
        data = req.getParameter("data");
        fasciaOraria = req.getParameter("fasciaOraria");

        switch(tipoRichiesta) {
            case "aggiungiPrenotazione":
                if(!(session.getAttribute("ruolo").equals("studente"))) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
                    return;
                }
                dataModel.aggiungiPrenotazione(username, idCorso, emailDocente, data, fasciaOraria);
                break;

            case "impostaPrenotazioneEffettuata":
                if(!(session.getAttribute("ruolo").equals("studente"))) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
                    return;
                }
                dataModel.impostaPrenotazioneEffettuata(emailDocente, data, fasciaOraria);
                break;

            case "rimuoviPrenotazione":
                dataModel.rimuoviPrenotazioni(idCorso, emailDocente, data, fasciaOraria);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
