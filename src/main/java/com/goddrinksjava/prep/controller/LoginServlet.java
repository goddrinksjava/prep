package com.goddrinksjava.prep.controller;

import javax.inject.Inject;
import javax.security.enterprise.AuthenticationStatus;
import javax.security.enterprise.SecurityContext;
import javax.security.enterprise.credential.Credential;
import javax.security.enterprise.credential.UsernamePasswordCredential;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@WebServlet(name = "LoginServlet", value = "/Login")
public class LoginServlet extends HttpServlet {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private SecurityContext securityContext;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("login.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        System.out.println(email + ": " + password);

        Credential credential = new UsernamePasswordCredential(email, password);

        AuthenticationStatus status =
                securityContext
                        .authenticate(
                                request,
                                response,
                                withParams().credential(credential)
                        );

        PrintWriter out = response.getWriter();
        out.println(status);
    }
}
