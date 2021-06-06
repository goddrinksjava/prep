package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.Candidato;
import com.goddrinksjava.prep.model.bean.database.Usuario;
import com.goddrinksjava.prep.model.bean.database.Votos;
import com.goddrinksjava.prep.model.bean.dto.CapturaDTO;
import com.goddrinksjava.prep.model.dao.CandidatoDAO;
import com.goddrinksjava.prep.model.dao.CargoDAO;
import com.goddrinksjava.prep.model.dao.UsuarioDAO;
import com.goddrinksjava.prep.model.dao.VotosDAO;

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
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"capturista", "administrador"})
)
@WebServlet(name = "CapturaDatosServlet", value = "/CapturaDatos/*")
public class CapturaDatosServlet extends HttpServlet {
    @Inject
    VotosDAO votosDAO;

    @Inject
    CandidatoDAO candidatoDAO;

    @Inject
    CargoDAO cargoDAO;

    @Inject
    UsuarioDAO usuarioDAO;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int cargoId;
        try {
            cargoId = Integer.parseInt(request.getPathInfo().substring(1));
        } catch (NumberFormatException | NullPointerException e) {
            response.sendError(404);
            return;
        }

        System.out.println("CargoId:" + cargoId);

        String cargo;
        try {
            cargo = cargoDAO.getNombreById(cargoId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            response.sendError(404);
            return;
        }

        System.out.println("Cargo: " + cargo);

        String email = request.getUserPrincipal().getName();

        Usuario usuario;
        try {
            usuario = usuarioDAO.findByEmail(email);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            throw new ServletException();
        }

        if (usuario.getFkCasilla() == null) {
            response.sendRedirect("/SeleccionCasilla");
            return;
        }

        List<CapturaDTO> candidatos = new ArrayList<>();

        try {
            List<Votos> votosList = votosDAO.getByCasilla(usuario.getFkCasilla());
            List<Candidato> candidatoList = candidatoDAO.getByCargo(cargoId);

            for (Votos votos : votosList) {
                Candidato candidato =
                        candidatoList.stream()
                                .filter(
                                        c -> c.getId().equals(votos.getFkCandidato())
                                )
                                .findFirst().orElse(null);

                if (candidato == null) {
                    continue;
                }


                String partido = String.join(", ", candidatoDAO.getPartidosById(candidato.getId()));

                candidatos.add(
                        CapturaDTO.builder()
                                .id(candidato.getId())
                                .partido(partido)
                                .votos(votos.getCantidad())
                                .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            System.out.println(throwables.getMessage());
            throw new ServletException();
        }

        request.setAttribute("cargo", cargo);
        request.setAttribute("candidatos", candidatos);
        request.getRequestDispatcher("/capturaDatos.jsp").forward(request, response);
    }

    @Override
    protected void doPost(
            HttpServletRequest request,
            HttpServletResponse response
    ) throws ServletException, IOException {
        String[] idsStringValue = request.getParameterValues("id");
        String[] numeroVotosStringValue = request.getParameterValues("numero_votos");

        if (idsStringValue == null) {
            idsStringValue = new String[0];
        }

        if (numeroVotosStringValue == null) {
            numeroVotosStringValue = new String[0];
        }

        List<Integer> ids;
        List<Integer> numeroVotos;

        try {
            ids = Arrays.stream(idsStringValue).map(Integer::parseInt).collect(Collectors.toList());
            numeroVotos = Arrays.stream(numeroVotosStringValue).map(Integer::parseInt).collect(Collectors.toList());
        } catch (NumberFormatException e) {
            e.printStackTrace();
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

        if (usuario.getFkCasilla() == null) {
            response.sendRedirect("/SeleccionCasilla");
        }

        List<Votos> votosList = new ArrayList<>();
        int n = idsStringValue.length;
        for (int i = 0; i < n; i++) {
            votosList.add(
                    Votos.builder()
                            .cantidad(numeroVotos.get(i))
                            .fkCandidato(ids.get(i))
                            .fkCasilla(usuario.getFkCasilla())
                            .build()
            );
        }

        try {
            votosDAO.update(votosList);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
