package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.Oauth;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.OauthSignupDTO;
import com.goddrinksjava.prep.model.dao.OauthDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.security.Principal;

public class OauthPostprocess {
    @Inject
    UsuarioDAO usuarioDAO;

    @Inject
    OauthDAO oauthDAO;

    @SneakyThrows
    public void postProcess(HttpServletRequest request, HttpServletResponse response, String sub, Integer oauthApplicationId, OauthSignupDTO oauthPostprocessDTO) throws ServletException {
        Principal principal = request.getUserPrincipal();
        Usuario usuario;

        Oauth oauth = oauthDAO.getBySubAndFkOauthApplication(sub, oauthApplicationId);
        if (oauth != null) {

        }

        if (principal == null) {
            usuario = Usuario.builder()
                    .nombre(oauthPostprocessDTO.getName())
                    .email(oauthPostprocessDTO.getEmail())
                    .build();

            usuarioDAO.insert(usuario);
        } else {
            String email = principal.getName();
            usuario = usuarioDAO.findByEmail(email);
        }

        oauthDAO.insert(
                Oauth.builder()
                        .sub(sub)
                        .fkUsuario(usuario.getId())
                        .fkOauthApplication(oauthApplicationId)
                        .build()
        );

    }
}
