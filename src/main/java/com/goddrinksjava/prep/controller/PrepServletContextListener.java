package com.goddrinksjava.prep.controller;

import com.goddrinksjava.prep.controller.WSEndpoint;
import com.goddrinksjava.prep.model.dao.VotosDAO;
import com.goddrinksjava.prep.model.pojo.dto.PrepDTO;
import lombok.SneakyThrows;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class PrepServletContextListener implements ServletContextListener {
    @Inject
    VotosDAO votosDAO;

    @SneakyThrows
    @Override
    public void contextInitialized(ServletContextEvent contextEvent) {
        PrepDTO prepDTO = votosDAO.generatePrepDTO();
        System.out.println("Context was initialized " + prepDTO);
        WSEndpoint.setCache(prepDTO);
    }
}
