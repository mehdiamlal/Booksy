package com.application.progettotweb;

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
import java.util.HashMap;

import com.google.gson.Gson;
import dataModel.DAO;

@WebServlet(name = "servletInsegnamento", value = "/insegnamenti")
public class ServletInsegnamento extends HttpServlet {
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

    /* Possibili richieste GET relative agli insegnamenti:
    * - ottenimento insegnamenti
    */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        HashMap<String, ArrayList<String>> elencoInsegnamenti = new HashMap<>();

        switch (tipoRichiesta) {
            case "ottieniInsegnamenti":
                elencoInsegnamenti.putAll(dataModel.ottieniInsegnamenti());
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                return;
        }

        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(elencoInsegnamenti);
        out.print(risposta);
    }

    /* Possibili richieste POST relative agli insegnamenti:
    - aggiungi insegnamento
    - rimuovi insegnamento
    */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");
        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        String corso, docente;

        corso = req.getParameter("corso");
        docente = req.getParameter("docente");

        switch (tipoRichiesta) {
            case "aggiungiInsegnamento":
                dataModel.aggiungiInsegnamento(corso, docente);
                break;

            case "rimuoviInsegnamenti":
                dataModel.rimuoviInsegnamenti(corso, docente);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
