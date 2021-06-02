package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.pojo.dto.CasillaDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class CasillaDTOEncoder implements Encoder.Text<CasillaDTO> {
    @Inject
    CandidaturaDTOEncoder candidaturaDTOEncoder;

    @Override
    public String encode(CasillaDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(CasillaDTO object) {
        JSONArray array = new JSONArray();

        object.getCandidaturas()
                .stream()
                .map(candidaturaDTOEncoder::json)
                .forEach(array::put);

        return new JSONObject()
                .put("id", object.getId())
                .put("candidaturas", array);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
