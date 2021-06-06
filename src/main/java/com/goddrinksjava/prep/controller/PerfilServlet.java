package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.Oauth;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.dao.OauthDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;

import javax.inject.Inject;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet(name = "PerfilServlet", value = "/Perfil")
public class PerfilServlet extends HttpServlet {
    @Inject
    OauthDAO oauthDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Usuario usuario = (Usuario) request.getAttribute("usuario");

        if (usuario == null) {
            response.sendRedirect("/Login");
            return;
        }

        List<Oauth> oauthList;
        try {
            oauthList = oauthDAO.getByUsuario(usuario.getId());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        oauthList.forEach(System.out::println);

        boolean a = oauthList.stream().anyMatch(oauth -> oauth.getFkOauthApplication().equals(1));

        List<Integer> oauthIds = oauthList.stream().map(Oauth::getFkOauthApplication).collect(Collectors.toList());

        oauthIds.forEach(System.out::println);

        System.out.println(oauthIds.contains(1));

        request.setAttribute("userOauthList", oauthIds);
        request.setAttribute("facebookEnabled", true);
        request.getRequestDispatcher("/perfil.jsp").forward(request, response);
    }
}
