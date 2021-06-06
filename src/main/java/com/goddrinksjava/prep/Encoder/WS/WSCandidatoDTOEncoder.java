package com.goddrinksjava.prep.Encoder.WS;

import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidatoDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class WSCandidatoDTOEncoder implements Encoder.Text<WSCandidatoDTO> {
    @Override
    public String encode(WSCandidatoDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(WSCandidatoDTO object) {
        JSONArray array = new JSONArray();
        object.getPartidos().forEach(array::put);

        return new JSONObject()
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
