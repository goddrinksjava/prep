package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.dao.CandidatoDAO;
import com.goddrinksjava.prep.model.dao.VotosDAO;
import com.goddrinksjava.prep.model.pojo.database.Candidato;
import com.goddrinksjava.prep.model.pojo.database.Votos;
import com.goddrinksjava.prep.model.pojo.dto.CapturaDTO;

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

import static com.goddrinksjava.prep.util.Validator.allEqual;


@ServletSecurity(
        value = @HttpConstraint(rolesAllowed = {"capturista", "administrador"})
)
@WebServlet(name = "CapturaDatosServlet", value = "/CapturaDatos/*")
public class CapturaDatosServlet extends HttpServlet {
    @Inject
    VotosDAO votosDAO;

    @Inject
    CandidatoDAO candidatoDAO;

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
            cargo = candidatoDAO.getCargoById(cargoId);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            response.sendError(404);
            return;
        }

        List<CapturaDTO> candidatos = new ArrayList<>();

        try {
            List<Votos> votosList = votosDAO.getByCasilla(1);
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
                                .nombre(candidato.getNombre())
                                .partido(partido)
                                .votos(votos.getCantidad())
                                .build()
                );
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
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

        if (!allEqual(idsStringValue.length, numeroVotosStringValue.length)) {
            response.sendError(400);
            return;
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

        List<Votos> votosList = new ArrayList<>();
        int n = idsStringValue.length;
        for (int i = 0; i < n; i++) {
            votosList.add(
                    Votos.builder()
                            .cantidad(numeroVotos.get(i))
                            .fkCandidato(ids.get(i))
                            .fkCasilla(1) //TODO
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
