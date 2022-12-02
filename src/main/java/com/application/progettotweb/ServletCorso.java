package com.application.progettotweb;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

import com.google.gson.Gson;

import dataModel.DAO;
import dataModel.Corso;

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

        String tipoRichiesta = req.getParameter("action");
        ArrayList<Corso> elencoCorsi = new ArrayList<>();

        switch(tipoRichiesta) {
            case "ottieniCorsi":
                elencoCorsi.addAll(dataModel.ottieniCorsi());
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
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
        String tipoRichiesta = req.getParameter("action");

        String corso = req.getParameter("corso");

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
