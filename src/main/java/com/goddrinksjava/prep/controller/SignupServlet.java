package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.pojo.database.Usuario;
import com.goddrinksjava.prep.util.Validator;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

//TODO set user privileges
//TODO require email confirmation
@WebServlet(name = "SignupServlet", value = "/Signup")
public class SignupServlet extends HttpServlet {
    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getRequestDispatcher("signup.jsp").forward(request, response);
    }


    //TODO Validate user data
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nombre = request.getParameter("nombre");
        String calle = request.getParameter("calle");
        String colonia = request.getParameter("colonia");
        String numero_casa = request.getParameter("numero_casa");
        String municipio = request.getParameter("municipio");
        String codigo_postal = request.getParameter("codigo_postal");
        String telefono = request.getParameter("telefono");
        String email = request.getParameter("email");
        String password = request.getParameter("password");

        boolean anynull =
                Validator.anyNull(
                        nombre,
                        calle,
                        colonia,
                        numero_casa,
                        municipio,
                        codigo_postal,
                        telefono,
                        email,
                        password
                );

        if (anynull) {
            throw new ServletException();
        }

        Usuario usuario = Usuario.builder()
                .nombre(nombre)
                .calle(calle)
                .colonia(colonia)
                .numeroCasa(numero_casa)
                .municipio(municipio)
                .codigoPostal(codigo_postal)
                .telefono(telefono)
                .email(email)
                .password(password)
                .build();

        try {
            usuarioDAO.persist(usuario);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }
    }
}
