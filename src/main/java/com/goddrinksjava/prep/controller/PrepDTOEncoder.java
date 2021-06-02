package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.model.pojo.dto.PrepDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class PrepDTOEncoder implements Encoder.Text<PrepDTO> {
    @Inject
    CasillaDTOEncoder casillaDTOEncoder;

    @Override
    public String encode(PrepDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(PrepDTO object) {
        JSONArray array = new JSONArray();

        object.getCasillas().stream().map(casillaDTOEncoder::json).forEach(array::put);

        return new JSONObject().put("casillas", array);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

}
