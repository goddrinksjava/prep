package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.pojo.dto.CandidatoDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class CandidatoDTOEncoder implements Encoder.Text<CandidatoDTO> {
    @Override
    public String encode(CandidatoDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(CandidatoDTO object) {
        JSONArray array = new JSONArray();
        object.getPartidos().forEach(array::put);

        return new JSONObject()
                .put("nombre", object.getNombre())
                .put("partidos", array)
                .put("votos", object.getVotos());
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
