package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.Encoder.WS.WSPrepDTOEncoder;
import com.goddrinksjava.prep.model.bean.dto.WS.WSPrepDTO;

import javax.enterprise.event.Observes;
import javax.websocket.OnClose;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.sql.SQLException;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(
        value = "/ws-prep",
        encoders = {WSPrepDTOEncoder.class}
)
public class WSEndpoint {
    private static final Set<Session> sessions = ConcurrentHashMap.newKeySet();

    private static WSPrepDTO cache;

    public static void setCache(WSPrepDTO WSPrepDTO) {
        System.out.println("Cache initialized: " + WSPrepDTO);
        WSEndpoint.cache = WSPrepDTO;
    }

    @OnOpen
    public void onOpen(Session session) throws SQLException {
        sessions.add(session);
        if (cache != null) {
            sendMessage(session, cache);
        }
        System.out.println("Sessions nya! " + sessions.size());
    }

    @OnClose
    public void onClose(Session session) {
        sessions.remove(session);
        System.out.println("Session was removed");
    }

    public void sendMessage(Session session, WSPrepDTO distritos) {
        try {
            session.getBasicRemote().sendObject(distritos);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void broadcast(@Observes WSPrepDTO event) {
        System.out.println("DistritoEvent received");

        cache = event;

        System.out.println(cache);

        System.out.println("Sessions kyaaa! " + sessions.size());

        for (Session session : sessions) {
            sendMessage(session, cache);
        }
    }
}
