package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Candidato;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CandidatoDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    @Inject
    private CargoDAO cargoDAO;

    @Inject
    private PartidoDAO partidoDAO;

    public String getCargoById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select fk_cargo from candidato where id = ?"
                )
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return cargoDAO.getNombreById(resultSet.getInt(1));
        }
    }

    public List<String> getPartidosById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select fk_partido from candidato_partido where fk_candidato = ?"
                )
        ) {
            stmt.setInt(1, id);

            List<String> partidos = new ArrayList<>();
            ResultSet resultSet = stmt.executeQuery();
            while (resultSet.next()) {
                partidos.add(
                        partidoDAO.getNombreById(resultSet.getInt(1))
                );
            }
            return partidos;
        }
    }

    public List<Candidato> getByCargo(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from candidato where fk_cargo = ?"
                )
        ) {
            stmt.setInt(1, id);

            return map(stmt.executeQuery());
        }
    }

    public List<Candidato> findAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from candidato"
                )
        ) {
            return map(stmt.executeQuery());
        }
    }

    public List<Candidato> map(ResultSet resultSet) throws SQLException {
        List<Candidato> candidatoList = new ArrayList<>();

        while (resultSet.next()) {
            candidatoList.add(
                    Candidato.builder()
                            .id(resultSet.getInt("id"))
                            .fkCargo(resultSet.getInt("fk_cargo"))
                            .build()
            );
        }

        return candidatoList;
    }


}
