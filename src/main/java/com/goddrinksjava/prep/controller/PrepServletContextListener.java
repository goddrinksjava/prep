package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.PrepDTOService;
import com.goddrinksjava.prep.model.dao.VotosDAO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSPrepDTO;
import lombok.SneakyThrows;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrepServletContextListener implements ServletContextListener {
    @EJB
    PrepDTOService prepDTOService;

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        WSPrepDTO WSPrepDTO = prepDTOService.generatePrepDTO();
        System.out.println("Context was initialized " + WSPrepDTO);
        WSEndpoint.setCache(WSPrepDTO);
    }
}
