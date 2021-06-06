package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.Distrito;
import com.goddrinksjava.prep.model.bean.dto.SeleccionCasilla.DistritoDTO;
import com.goddrinksjava.prep.model.bean.dto.SeleccionCasilla.SeccionalDTO;
import com.goddrinksjava.prep.model.dao.DistritoDAO;
import com.goddrinksjava.prep.model.dao.SeccionalDAO;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Response;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/Distritos")
public class DistritosResource {
    @Inject
    DistritoDAO distritoDAO;

    @Inject
    SeccionalDAO seccionalDAO;

    @SneakyThrows
    @GET
    @Produces({APPLICATION_JSON})
    public Response getDistritos() {
        List<Distrito> distritoList = distritoDAO.findAll();
        List<DistritoDTO> distritoDTOList = new ArrayList<>();

        for (Distrito distrito : distritoList) {
            int id = distrito.getId();

            List<SeccionalDTO> seccionalDTOList =
                    seccionalDAO.findByDistrito(id).stream()
                            .map(seccional -> new SeccionalDTO(seccional.getId()))
                            .collect(Collectors.toList());

            distritoDTOList.add(
                    DistritoDTO.builder()
                            .id(id)
                            .seccionalDTOList(seccionalDTOList)
                            .build()
            );
        }

        return Response.status(Response.Status.OK).entity(distritoDTOList).build();
    }
}
