package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.pojo.dto.CandidaturaDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class CandidaturaDTOEncoder implements Encoder.Text<CandidaturaDTO> {
    @Inject
    CandidatoDTOEncoder candidatoDTOEncoder;

    @Override
    public String encode(CandidaturaDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(CandidaturaDTO object) {
        JSONArray array = new JSONArray();
        object.getCandidatos().stream().map(candidatoDTOEncoder::json).forEach(array::put);

        return new JSONObject()
                .put("nombre", object.getNombre())
                .put("candidatos", array);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }
}
