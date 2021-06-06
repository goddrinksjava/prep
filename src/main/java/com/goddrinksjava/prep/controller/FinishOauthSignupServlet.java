package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.Mapper;
import com.goddrinksjava.prep.SignupException;
import com.goddrinksjava.prep.SignupService;
import com.goddrinksjava.prep.model.bean.database.Oauth;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.OauthSignupDTO;
import com.goddrinksjava.prep.model.bean.dto.SignupDTO;
import com.goddrinksjava.prep.model.dao.OauthDAO;
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
import java.sql.SQLException;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@WebServlet(name = "FinishOauthSignupServlet", value = "/FinishOauthSignup")
public class FinishOauthSignupServlet extends HttpServlet {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private SecurityContext securityContext;

    @Inject
    OauthDAO oauthDAO;

    @Inject
    SignupService signupService;

    @Inject
    Mapper mapper;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        OauthSignupDTO oauthSignupDTO = (OauthSignupDTO) request.getSession().getAttribute("oauthSignupDTO");

        if (request.getSession().getAttribute("oauthSub") == null || oauthSignupDTO == null) {
            response.sendRedirect("/");
            return;
        }

        request.setAttribute("oauthSignupDTO", oauthSignupDTO);
        request.getRequestDispatcher("finishOauthSignup.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String oauthSub = (String) request.getSession().getAttribute("oauthSub");
        Integer oauthApplicationId = (Integer) request.getSession().getAttribute("oauthApplicationId");

        if (oauthSub == null) {
            response.sendError(400);
            return;
        }

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

        try {
            oauthDAO.insert(
                    Oauth.builder()
                            .sub(oauthSub)
                            .fkUsuario(usuario.getId())
                            .fkOauthApplication(oauthApplicationId)
                            .build()
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        Credential credential = new UsernamePasswordCredential(usuario.getEmail(), usuario.getPassword());

        AuthenticationStatus status =
                securityContext
                        .authenticate(
                                request,
                                response,
                                withParams().credential(credential)
                        );

        request.getSession().removeAttribute("oauthSub");
        request.getSession().removeAttribute("oauthSignupDTO");

        response.sendRedirect("/ConfirmarEmail");
    }
}
