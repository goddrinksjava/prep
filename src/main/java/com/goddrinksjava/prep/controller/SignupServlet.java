package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.Mapper;
import com.goddrinksjava.prep.SignupException;
import com.goddrinksjava.prep.SignupService;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.SignupDTO;
import org.apache.commons.beanutils.BeanUtils;

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
import java.lang.reflect.InvocationTargetException;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@WebServlet(name = "SignupServlet", value = "/Signup")
public class SignupServlet extends HttpServlet {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private SecurityContext securityContext;

    @Inject
    Mapper mapper;

    @Inject
    SignupService signupService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SignupDTO signupDTO = mapper.getDTO(SignupDTO.class, request);

        if (signupDTO == null) {
            response.sendError(400);
            return;
        }

        Usuario usuario = new Usuario();
        try {
            BeanUtils.copyProperties(usuario, signupDTO);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new ServletException();
        }

        try {
            signupService.signup(request, response, usuario);
        } catch (SignupException e) {
            request.setAttribute("error", "Email ya ha sido registrado");
            request.getRequestDispatcher("signup.jsp").forward(request, response);
        }

        Credential credential = new UsernamePasswordCredential(usuario.getEmail(), usuario.getPassword());

        AuthenticationStatus status =
                securityContext
                        .authenticate(
                                request,
                                response,
                                withParams().credential(credential)
                        );

        request.getRequestDispatcher("/ConfirmarEmail").forward(request, response);

    }
}
