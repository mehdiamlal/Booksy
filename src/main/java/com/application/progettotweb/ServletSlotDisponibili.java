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

        HashMap<String, HashMap<String, ArrayList<String>>> slotDisponbili = new HashMap<>();

        switch (tipoRichiesta) {
            case "ottieniSlotDisponibili":
                slotDisponbili.putAll(dataModel.ottieniSlotDisponibili("12/12/2022", "16/12/2022"));
                break;

            default:
                resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Tipo di operazione non valida. Riprovare.");
                break;
        }

        PrintWriter writer = resp.getWriter();
        Gson gson = new Gson();

        String risposta = gson.toJson(slotDisponbili);
        writer.print(risposta);
    }
}
