package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.pojo.database.Usuario;
import com.sun.mail.smtp.SMTPTransport;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.Properties;

@WebServlet(name = "EmailRecoverServlet", value = "/EmailRecover")
public class EmailRecoverServlet extends HttpServlet {
    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("email_recover.jsp").forward(request, response);
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

    @SneakyThrows
    private void sendPassword(String password, String to) {
        Properties props = System.getProperties();
        props.put("mail.smtps.host", "smtp.mailgun.org");
        props.put("mail.smtps.auth", "true");

        Session session = Session.getInstance(props, null);
        Message msg = new MimeMessage(session);
        msg.setFrom(new InternetAddress("recover@goddrinksjava.codes"));

        InternetAddress[] addrs = InternetAddress.parse(to, false);

        msg.setRecipients(Message.RecipientType.TO, addrs);
        msg.setSubject("Password Recovery");
        msg.setText(password);
        msg.setSentDate(new Date());

        SMTPTransport t =
                (SMTPTransport) session.getTransport("smtps");

        t.connect("smtp.mailgun.org", "postmaster@goddrinksjava.codes", "1549f55ebbe4cb83b024d2015113e500-2a9a428a-a50b2936");
        t.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Response: " + t.getLastServerResponse());

        t.close();
    }
}
