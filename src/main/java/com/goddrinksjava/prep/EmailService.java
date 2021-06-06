package com.goddrinksjava.prep;

import com.sun.mail.smtp.SMTPTransport;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Date;
import java.util.Properties;

@ApplicationScoped
public class EmailService {
    @Inject
    AppConfig appConfig;

    @SneakyThrows
    public void sendEmail(Email email) {
        Properties properties = new Properties();

        properties.put(
                "mail.smtps.host",
                appConfig.getValue("mail.smtps.host")
        );

        properties.put(
                "mail.smtps.auth",
                appConfig.getValue("mail.smtps.auth")
        );

        Session session = Session.getInstance(properties, null);

        Message msg = new MimeMessage(session);
        msg.setFrom(
                new InternetAddress(
                        email.getFromAddress()
                )
        );

        InternetAddress[] addrs = InternetAddress.parse(email.getToAdress(), false);

        msg.setRecipients(Message.RecipientType.TO, addrs);
        msg.setSubject(email.getSubject());
        msg.setText(email.getMessage());
        msg.setSentDate(new Date());

        SMTPTransport smtpTransport = (SMTPTransport) session.getTransport("smtps");

        smtpTransport.connect(
                appConfig.getValue("mail.smtps.host"),
                appConfig.getValue("mail.address.postmaster"),
                appConfig.getValue("mail.smtps.password")
        );

        smtpTransport.sendMessage(msg, msg.getAllRecipients());

        System.out.println("Respuesta: " + smtpTransport.getLastServerResponse());

        smtpTransport.close();
    }
}
