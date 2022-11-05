package com.application.progettotweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

import dataModel.DAO;

@WebServlet(name = "auth-servlet", value = "/auth-servlet")
public class AuthServlet extends HttpServlet {
    DAO dataModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext ctx = config.getServletContext();
        Object res = ctx.getAttribute("datamodel");
        if(res instanceof DAO) {
            dataModel = (DAO) res;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
