package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.Mapper;
import com.goddrinksjava.prep.model.bean.database.Casilla;
import com.goddrinksjava.prep.model.bean.database.Distrito;
import com.goddrinksjava.prep.model.bean.database.Seccional;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.dto.SeleccionCasilla.SeleccionCasillaDTO;
import com.goddrinksjava.prep.model.dao.*;

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
import java.util.List;

@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"capturista", "administrador"})
)
@WebServlet(name = "SeleccionCasillaServlet", value = "/SeleccionCasilla")
public class SeleccionCasillaServlet extends HttpServlet {
    @Inject
    Mapper mapper;

    @Inject
    TipoCasillaDAO tipoCasillaDAO;

    @Inject
    SeccionalDAO seccionalDAO;

    @Inject
    DistritoDAO distritoDAO;

    @Inject
    CasillaDAO casillaDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            request.setAttribute("tipoCasillaList", tipoCasillaDAO.findAll());
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }
        request.getRequestDispatcher("seleccionCasilla.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        SeleccionCasillaDTO seleccionCasillaDTO = mapper.getDTO(SeleccionCasillaDTO.class, request);

        if (seleccionCasillaDTO == null) {
            response.sendError(400);
            return;
        }
        Casilla casilla;
        try {
             casilla = casillaDAO.findById(
                    Integer.parseInt(seleccionCasillaDTO.getCasilla())
            );
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        if (casilla == null) {
            response.sendError(400);
            return;
        }

        String email = request.getUserPrincipal().getName();

        Usuario usuario;
        try {
            usuario = usuarioDAO.findByEmail(email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        usuario.setFkCasilla(casilla.getId());
        try {
            System.out.println(usuario);
            usuarioDAO.update(usuario);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
            throw new ServletException();
        }

        response.sendRedirect("/CapturaDatos/1");
    }
}
