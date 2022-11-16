package com.application.progettotweb;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

import dataModel.DAO;

@WebServlet(name = "auth-servlet", value = "/auth-servlet")
public class InitServlet extends HttpServlet {
    DAO dataModel;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);

        ServletContext ctx = config.getServletContext();

        String URL = ctx.getInitParameter("DB-URL");
        String user = ctx.getInitParameter("user");
        String pwd = ctx.getInitParameter("pwd");
        dataModel = new DAO(URL, user, pwd);

        ctx.setAttribute("datamodel", dataModel);
    }
}
