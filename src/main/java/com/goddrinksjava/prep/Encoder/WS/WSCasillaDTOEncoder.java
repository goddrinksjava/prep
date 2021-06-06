package com.goddrinksjava.prep.Encoder.WS;

import com.goddrinksjava.prep.model.bean.dto.WS.WSCasillaDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class WSCasillaDTOEncoder implements Encoder.Text<WSCasillaDTO> {
    @Inject
    WSCandidaturaDTOEncoder WSCandidaturaDTOEncoder;

    @Override
    public String encode(WSCasillaDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(WSCasillaDTO object) {
        JSONArray array = new JSONArray();

        object.getCandidaturas()
                .stream()
                .map(WSCandidaturaDTOEncoder::json)
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
