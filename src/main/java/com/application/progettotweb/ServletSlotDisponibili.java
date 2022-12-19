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

@WebServlet(name = "servletSlot", value = "/slot-disponibili")
public class ServletSlotDisponibili extends HttpServlet {
    DAO dataModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext ctx = config.getServletContext();
        Object tmpDAO = ctx.getAttribute("datamodel");

        if(tmpDAO instanceof DAO) {
            dataModel = (DAO) tmpDAO;
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("application/json");
        resp.addHeader("Access-Control-Allow-Origin", "*");

        String tipoRichiesta = req.getParameter("action");
        if(tipoRichiesta == null) {
            tipoRichiesta = "";
        }

        String dataInizio, dataFine, emailDocente;

        dataInizio = req.getParameter("dataInizio");
        dataFine = req.getParameter("dataFine");
        emailDocente = req.getParameter("docente");

        Gson gson = new Gson();
        String risposta = "";

        switch (tipoRichiesta) {
            case "ottieniSlotDisponibili":
                HashMap<String, HashMap<String, ArrayList<String>>> slotDisponbili = new HashMap<>();
                slotDisponbili.putAll(dataModel.ottieniSlotDisponibili(dataInizio, dataFine));
                risposta = gson.toJson(slotDisponbili);
                break;

            case "ottieniSlotDisponibiliDocente":
                HashMap<String, ArrayList<String>> slotDisponibiliDocente = new HashMap<>();
                slotDisponibiliDocente.putAll(dataModel.ottieniSlotDisponibiliDocente(emailDocente, dataInizio, dataFine));
                risposta = gson.toJson(slotDisponibiliDocente);
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }

        PrintWriter writer = resp.getWriter();
        writer.print(risposta);
    }
}
