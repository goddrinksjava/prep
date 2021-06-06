package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.bean.database.Usuario;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.HttpConstraint;
import javax.servlet.annotation.ServletSecurity;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"administrador"})
)
@WebServlet(name = "AdminServlet", value = "/Admin")
public class AdminServlet extends HttpServlet {
    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        List<Usuario> usuarioList;
        try {
            usuarioList = usuarioDAO.findByEnabledAndEmailConfirmed(false, true);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        request.setAttribute(
                "capturistas",
                usuarioList
        );
        request.getRequestDispatcher("/admin.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String[] idsStringValue = request.getParameterValues("id");
        String[] statusList = request.getParameterValues("status");

        if (idsStringValue == null) {
            idsStringValue = new String[0];
        }

        if (statusList == null) {
            statusList = new String[0];
        }


        System.out.println("idsStringValue");
        Arrays.stream(idsStringValue).forEach(System.out::println);
        System.out.println("idsStringValue");
        Arrays.stream(statusList).forEach(System.out::println);

        List<Integer> ids;
        try {
            ids = Arrays.stream(idsStringValue).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            e.printStackTrace();
            response.sendError(400);
            return;
        }

        int n = ids.size();

        try {
            for (int i = 0; i < n; i++) {
                switch (statusList[i]) {
                    case "aceptado": {
                        Usuario usuario = usuarioDAO.findById(ids.get(i));
                        usuario.setEnabled(true);
                        usuarioDAO.update(usuario);
                        break;
                    }
                    case "rechazado": {
                        usuarioDAO.delete(ids.get(i));
                        break;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new ServletException();
        }

    }
}
