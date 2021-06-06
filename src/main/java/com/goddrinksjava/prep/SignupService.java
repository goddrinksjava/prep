package com.goddrinksjava.prep;

import com.goddrinksjava.prep.model.bean.database.EmailVerification;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.SignupDTO;
import com.goddrinksjava.prep.model.dao.EmailVerificationDAO;
import com.goddrinksjava.prep.model.dao.PrivilegioDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.dao.UsuarioPrivilegioDAO;
import org.apache.commons.beanutils.BeanUtils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.sql.SQLException;
import java.util.Map;
import java.util.UUID;

@ApplicationScoped
public class SignupService {

    @Inject
    Mapper mapper;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    UsuarioPrivilegioDAO usuarioPrivilegioDAO;

    @Inject
    EmailVerificationDAO emailVerificationDAO;

    @Inject
    EmailService emailService;

    @Inject
    AppConfig appConfig;

    public void signup(HttpServletRequest request, HttpServletResponse response, Usuario usuario) throws ServletException, IOException, SignupException {
        try {
            usuarioDAO.insert(usuario);
            String verificationToken = UUID.randomUUID().toString();
            emailVerificationDAO.insert(
                    EmailVerification.builder()
                            .token(verificationToken)
                            .fkUsuario(usuario.getId())
                            .build()
            );
            sendEmailVerificationToken(verificationToken, usuario.getEmail());
        } catch (SQLException e) {
            if (e.getErrorCode() == 1062) {
                throw new SignupException();
            } else {
                e.printStackTrace();
                throw new ServletException();
            }
        }


    }

    private void sendEmailVerificationToken(String verificationToken, String toAddress) throws IOException {
        emailService.sendEmail(
                Email.builder()
                        .subject("Prep Email Verification")
                        .message(verificationToken)
                        .fromAddress(appConfig.getValue("mail.address.passwordRecovery"))
                        .toAdress(toAddress)
                        .build()
        );
    }
}
