package com.application.progettotweb;

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

import com.google.gson.Gson;

import dataModel.DAO;
import dataModel.Corso;

@WebServlet(name = "servletCorsi", value = "/corsi")
public class ServletCorso extends HttpServlet {
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

    /* Possibili richieste GET relative ai corsi:
    * - ottieni elenco corsi
    */
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
        System.out.println(session.getAttribute("ruolo"));
        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        if(!(session.getAttribute("ruolo").equals("amministratore")) && !(session.getAttribute("ruolo").equals("studente"))) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
            return;
        }

        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        switch(tipoRichiesta) {
            case "ottieniCorsiAttivi":
                elencoCorsi.addAll(dataModel.ottieniCorsiAttivi());
                break;

            case "ottieniCorsi":
                if(!(session.getAttribute("ruolo").equals("amministratore"))) {
                    resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
                    return;
                }
                elencoCorsi.addAll(dataModel.ottieniCorsi());
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                return;
        }

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(elencoCorsi);
        out.print(risposta);
    }

    /* Possibili richieste POST relative ai corsi:
    * - aggiunta corso
    * - cambio stato corso
    * */
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

        String corso = req.getParameter("corso");
        if(!(session.getAttribute("ruolo").equals("amministratore"))) {
            resp.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Operazione non autorizzata.");
            return;
        }

        switch(tipoRichiesta) {
            case "aggiungiCorso":
                dataModel.aggiungiCorso(corso);
                break;

            case "cambiaStatoCorso":
                dataModel.cambiaStatoCorso(corso);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
