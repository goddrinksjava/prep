package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.AppConfig;
import com.goddrinksjava.prep.Email;
import com.goddrinksjava.prep.EmailService;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.sun.mail.smtp.SMTPTransport;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

@WebServlet(name = "EmailRecoverServlet", value = "/PasswordRecovery")
public class PasswordRecoveryServlet extends HttpServlet {
    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    AppConfig appConfig;

    @Inject
    EmailService emailService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("passwordRecovery.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email == null) {
            response.sendError(400);
        }

        Usuario usuario;
        try {
            usuario = usuarioDAO.findByEmail(email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        if (usuario == null) {
            response.sendError(400);
            return;
        }

        sendPassword(usuario.getPassword(), email);
    }

    private void sendPassword(String password, String toAddress) throws IOException {
        emailService.sendEmail(
                Email.builder()
                        .subject(toAddress)
                        .message(password)
                        .fromAddress(appConfig.getValue("mail.address.passwordRecovery"))
                        .toAdress(toAddress)
                        .build()
        );
    }
}
