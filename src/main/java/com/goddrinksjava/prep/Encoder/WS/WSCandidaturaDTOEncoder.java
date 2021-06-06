package com.goddrinksjava.prep.Encoder.WS;

import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidaturaDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class WSCandidaturaDTOEncoder implements Encoder.Text<WSCandidaturaDTO> {
    @Inject
    WSCandidatoDTOEncoder WSCandidatoDTOEncoder;

    @Override
    public String encode(WSCandidaturaDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(WSCandidaturaDTO object) {
        JSONArray array = new JSONArray();
        object.getCandidatos().stream().map(WSCandidatoDTOEncoder::json).forEach(array::put);

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
