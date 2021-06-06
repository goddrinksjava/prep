package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.PrepDTOService;
import com.goddrinksjava.prep.model.bean.database.Candidato;
import com.goddrinksjava.prep.model.bean.database.Cargo;
import com.goddrinksjava.prep.model.bean.database.Votos;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidatoDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCandidaturaDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSCasillaDTO;
import com.goddrinksjava.prep.model.bean.dto.WS.WSPrepDTO;

import javax.annotation.Resource;
import javax.ejb.Asynchronous;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Event;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class VotosDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    @Inject
    private CandidatoDAO candidatoDAO;

    @Inject
    private CargoDAO cargoDAO;

    @EJB
    PrepDTOService prepDTOService;

    public List<Votos> getAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from votos"
                )
        ) {
            return map(stmt.executeQuery());
        }
    }

    public List<Integer> getDistinctFkCasillas() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select distinct fk_casilla from votos"
                )
        ) {
            List<Integer> fkCasillas = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                fkCasillas.add(
                        resultSet.getInt(1)
                );
            }
            return fkCasillas;
        }
    }

    public List<Votos> getByCasilla(Integer fk_casilla) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from votos where fk_casilla = ?"
                )
        ) {
            stmt.setInt(1, fk_casilla);
            return map(stmt.executeQuery());
        }
    }

    public void update(List<Votos> votosList) throws SQLException {
        if (votosList.size() == 0) {
            return;
        }

        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "update votos set cantidad=? where fk_casilla=? and fk_candidato=?"
                )
        ) {
            for (Votos votos : votosList) {
                System.out.println(votos);
                stmt.setInt(1, votos.getCantidad());
                stmt.setInt(2, votos.getFkCasilla());
                stmt.setInt(3, votos.getFkCandidato());
                stmt.executeUpdate();
            }

            fireEvent();
        }
    }

    public List<Votos> map(ResultSet resultSet) throws SQLException {

        List<Votos> votosList = new ArrayList<>();

        while (resultSet.next()) {
            votosList.add(
                    Votos.builder()
                            .cantidad(resultSet.getInt("cantidad"))
                            .fkCasilla(resultSet.getInt("fk_casilla"))
                            .fkCandidato(resultSet.getInt("fk_candidato"))
                            .build()
            );
        }

        return votosList;
    }

    public void fireEvent() throws SQLException {
        prepDTOService.fireEvent();
    }
}

