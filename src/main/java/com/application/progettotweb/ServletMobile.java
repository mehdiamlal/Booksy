package com.application.progettotweb;

import dataModel.DAO;
import dataModel.Utente;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.servlet.annotation.WebServlet;
import java.io.IOException;

@WebServlet(name = "servletMobile", value = "/mobile")
public class ServletMobile extends HttpServlet {
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Utente daAutenticare = dataModel.autenticaUtente(username, password);

        HttpSession session = req.getSession();
        session.setAttribute("username", daAutenticare.getUsername());
        session.setAttribute("ruolo", daAutenticare.getRuolo());

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        ServletContext ctx = getServletContext();
        RequestDispatcher rd;

        switch(tipoRichiesta) {
            case "ottieniTuttePrenotazioni":
            case "ottieniStoricoPrenotazioniUtente":
            case "ottieniPrenotazioniUtenteImminenti":
                rd = ctx.getNamedDispatcher("servletPrenotazione");
                rd.include(req, resp);
                break;

            case "ottieniCorsiAttivi":
                rd = ctx.getNamedDispatcher("servletCorsi");
                rd.include(req, resp);
                break;

            case "ottieniDocentiLiberi":
            case "filtraDocentePerCorso":
                rd = ctx.getNamedDispatcher("servletDocente");
                rd.include(req, resp);
                break;

            case "ottieniSlotDisponibili":
            case "ottieniSlotDisponibiliCorso":
            case "ottieniSlotDisponibiliDocente":
            case "ottieniSlotDisponibiliDocenteData":
                rd = ctx.getNamedDispatcher("servletSlot");
                rd.include(req, resp);
                break;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String username = req.getParameter("username");
        String password = req.getParameter("password");
        Utente daAutenticare = dataModel.autenticaUtente(username, password);

        HttpSession session = req.getSession();
        session.setAttribute("username", daAutenticare.getUsername());
        session.setAttribute("ruolo", daAutenticare.getRuolo());

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        ServletContext ctx = getServletContext();
        RequestDispatcher rd;

        switch(tipoRichiesta) {
            case "aggiungiPrenotazione":
            case "impostaPrenotazioneEffettuata":
            case "rimuoviPrenotazione":
                rd = ctx.getNamedDispatcher("servletPrenotazione");
                rd.include(req, resp);
                break;
        }
    }
}
