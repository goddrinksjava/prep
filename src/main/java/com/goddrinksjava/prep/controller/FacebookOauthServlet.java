package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.AppConfig;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@WebServlet(name = "FacebookOauthServlet", value = "/FacebookOauth")
public class FacebookOauthServlet extends HttpServlet {
    @Inject
    AppConfig config;

    @SneakyThrows
    private String urlEncoded(String str) {
        return URLEncoder.encode(str, StandardCharsets.UTF_8.toString());
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String state = UUID.randomUUID().toString();
        request.getSession().setAttribute("CLIENT_LOCAL_STATE", state);

        String authUri = config.getValue("provider.facebook.authorizationUri");
        String clientId = config.getValue("client.facebook.clientId");
        String redirectUri = config.getValue("client.facebook.redirectUri");
        String scope = config.getValue("client.facebook.scope");

        Map<String, String> requestParams = new HashMap<>();
        requestParams.put("scope", scope);
        requestParams.put("response_type", "code");
        requestParams.put("state", state);
        requestParams.put("redirect_uri", redirectUri);
        requestParams.put("client_id", clientId);

        String authLocation = requestParams.keySet().stream()
                .map(
                        key -> key + "=" + urlEncoded(requestParams.get(key))
                )
                .collect(Collectors.joining("&", authUri + "?", ""));

        response.sendRedirect(authLocation);
    }
}
