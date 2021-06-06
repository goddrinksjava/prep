package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.AppConfig;
import com.goddrinksjava.prep.model.bean.database.Oauth;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.OauthSignupDTO;
import com.goddrinksjava.prep.model.dao.OauthDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;

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
import javax.ws.rs.client.Client;
import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.Entity;
import javax.ws.rs.client.WebTarget;
import javax.ws.rs.core.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Map;

import static javax.security.enterprise.authentication.mechanism.http.AuthenticationParameters.withParams;

@WebServlet(name = "GoogleOauthTokenServlet", value = "/Login/oauth2/google")
public class GoogleOauthTokenServlet extends HttpServlet {
    @SuppressWarnings("CdiInjectionPointsInspection")
    @Inject
    private SecurityContext securityContext;

    @Inject
    OauthDAO oauthDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    AppConfig config;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String localState = (String) request.getSession().getAttribute("CLIENT_LOCAL_STATE");
        request.getSession().removeAttribute("CLIENT_LOCAL_STATE");
        if (!localState.equals(request.getParameter("state"))) {
            response.sendError(400);
            return;
        }

        String error = request.getParameter("error");
        if (error != null) {
            System.out.println(error);
            response.sendError(400);
            return;
        }

        String code = request.getParameter("code");

        System.out.println("CODE: " + code);

        Client client = ClientBuilder.newClient();
        WebTarget target = client.target(config.getValue("provider.google.tokenUri"));

        Form form = new Form();
        form.param("client_id", config.getValue("client.google.clientId"));
        form.param("client_secret", config.getValue("client.google.clientSecret"));
        form.param("code", code);
        form.param("grant_type", "authorization_code");
        form.param("redirect_uri", config.getValue("client.google.redirectUri"));

        Response targetResponse = target.request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(form, MediaType.APPLICATION_FORM_URLENCODED_TYPE));


        Map<String, Object> json = targetResponse.readEntity(new GenericType<Map<String, Object>>() {
        });

        String accessToken = (String) json.get("access_token");

        System.out.println("ACCESS TOKEN: " + accessToken);


        client = ClientBuilder.newClient();
        WebTarget userInfoTarget = client.target(config.getValue("provider.google.userinfoUri"));
        Response userInfoResponse = userInfoTarget.request(MediaType.APPLICATION_JSON)
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .get();

        Map<String, Object> userInfoJson = userInfoResponse.readEntity(new GenericType<Map<String, Object>>() {
        });

        try {
            Oauth oauth = oauthDAO.getBySubAndFkOauthApplication((String) userInfoJson.get("id"), 1);
            if (oauth != null) {
                Usuario usuario = usuarioDAO.findById(oauth.getFkUsuario());
                Credential credential = new UsernamePasswordCredential(usuario.getEmail(), usuario.getPassword());
                AuthenticationStatus status =
                        securityContext
                                .authenticate(
                                        request,
                                        response,
                                        withParams().credential(credential)
                                );
                response.sendRedirect("/");
                return;
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        request.getSession().setAttribute("oauthSub", userInfoJson.get("id"));
        request.getSession().setAttribute("oauthApplicationId", 1);
        request.getSession().setAttribute(
                "oauthSignupDTO",
                OauthSignupDTO.builder()
                        .email((String) userInfoJson.get("email"))
                        .name((String) userInfoJson.get("name"))
                        .build()
        );

        response.sendRedirect("/FinishOauthSignup");
    }
}
