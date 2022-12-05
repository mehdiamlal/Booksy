package com.application.progettotweb;

import com.google.gson.Gson;
import dataModel.DAO;
import dataModel.Utente;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet(name = "servletCorso", value = "/servlet-corso")
public class ServletAutenticazione extends HttpServlet {
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

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        String username, password;
        username = req.getParameter("username");
        password = req.getParameter("password");

        switch(tipoRichiesta) {
            case "aggiungiUtente":
                String nome, cognome, ruolo;
                nome = req.getParameter("nome");
                cognome = req.getParameter("cognome");
                ruolo = req.getParameter("ruolo");
                dataModel.aggiungiUtente(username, password, nome, cognome, ruolo);
                break;

            case "autenticaUtente":
                Utente utente;
                utente = dataModel.autenticaUtente(username, password);

                Gson gson = new Gson();
                String risposta = gson.toJson(utente);

                PrintWriter out = resp.getWriter();
                out.print(risposta);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }
    }
}
