package com.goddrinksjava.prep.Encoder.WS;

import com.goddrinksjava.prep.model.bean.dto.WS.WSPrepDTO;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.websocket.EncodeException;
import javax.websocket.Encoder;
import javax.websocket.EndpointConfig;

@ApplicationScoped
public class WSPrepDTOEncoder implements Encoder.Text<WSPrepDTO> {
    @Inject
    WSCasillaDTOEncoder WSCasillaDTOEncoder;

    @Override
    public String encode(WSPrepDTO object) throws EncodeException {
        return json(object).toString();
    }

    public JSONObject json(WSPrepDTO object) {
        JSONArray array = new JSONArray();

        object.getCasillas().stream().map(WSCasillaDTOEncoder::json).forEach(array::put);

        return new JSONObject().put("casillas", array);
    }

    @Override
    public void init(EndpointConfig config) {

    }

    @Override
    public void destroy() {

    }

}
