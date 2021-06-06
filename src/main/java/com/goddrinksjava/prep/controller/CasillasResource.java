package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.bean.database.Casilla;
import com.goddrinksjava.prep.model.dao.CasillaDAO;
import lombok.SneakyThrows;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.util.List;

import static javax.ws.rs.core.MediaType.APPLICATION_JSON;

@ApplicationScoped
@Path("/Casillas")
public class CasillasResource {
    @Inject
    CasillaDAO casillaDAO;

    @SneakyThrows
    @GET
    @Produces({APPLICATION_JSON})
    public Response getCasillas(
            @QueryParam("tipoCasilla") Integer tipoCasilla,
            @QueryParam("seccional") Integer seccional
    ) {
        if (tipoCasilla == null || seccional == null) {
            List<Casilla> casillaList = casillaDAO.findAll();
            return Response.status(Response.Status.OK).entity(casillaList).build();
        }

        List<Casilla> casillaList = casillaDAO.findByfkTipoCasillaAndfkSeccional(tipoCasilla, seccional);
        return Response.status(Response.Status.OK).entity(casillaList).build();
    }
}
