package com.application.progettotweb;

import dataModel.DAO;

import java.io.*;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "helloServlet", value = "/hello-servlet")
public class HelloServlet extends HttpServlet {
    private DAO dataModel;

    public void init(ServletConfig config) {
        ServletContext ctx = config.getServletContext();
        Object tmpDAO = ctx.getAttribute("datamodel");

        if(tmpDAO instanceof DAO) {
            dataModel = (DAO) ctx.getAttribute("datamodel");
        }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html");

        // Hello
        PrintWriter out = response.getWriter();
        out.println("<html><body>");
        out.println("<h1>" + "prova" + "</h1>");
        out.println("</body></html>");

        System.out.println(dataModel.ottieniStoricoPrenotazioni());
    }

    public void destroy() {
    }
}