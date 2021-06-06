package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.EmailVerification;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.dao.EmailVerificationDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"usuario"})
)
@WebServlet(name = "ConfirmarEmailServlet", value = "/ConfirmarEmail")
public class ConfirmarEmailServlet extends HttpServlet {
    @Inject
    EmailVerificationDAO emailVerificationDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Yahallo!");
        request.getRequestDispatcher("confirmarEmail.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Usuario usuario;
        try {
            usuario = usuarioDAO.findByEmail(request.getUserPrincipal().getName());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        String requestVerificationToken = request.getParameter("verificationToken");

        try {
            String dbVerificationToken = emailVerificationDAO.findTokenByUsuario(
                    usuario.getId()
            );

            if (requestVerificationToken.equals(dbVerificationToken)) {

                System.out.println(usuario);
                usuario.setEmailConfirmed(true);
                usuarioDAO.update(usuario);

                emailVerificationDAO.delete(
                        EmailVerification.builder()
                                .token(dbVerificationToken)
                                .fkUsuario(usuario.getId())
                                .build()
                );
            }
            else {
                request.setAttribute("error", "Codigo de verificacion invalido");
                request.getRequestDispatcher("confirmarEmail.jsp").forward(request, response);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
            throw new ServletException();
        }

    }
}
