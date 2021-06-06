package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Casilla;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CasillaDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public void insert(Casilla casilla) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into casilla (fk_seccional, fk_tipo_casilla) value (?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setInt(1, casilla.getFkSeccional());
            stmt.setInt(2, casilla.getFkTipoCasilla());
            stmt.executeQuery();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                casilla.setId(id);
            } else {
                throw new SQLException();
            }
        }
    }

    public List<Casilla> findAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from casilla")
        ) {
            ResultSet resultSet = stmt.executeQuery();
            return map(resultSet);
        }
    }

    public List<Integer> findAllIds() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select id from casilla order by id")
        ) {
            ResultSet resultSet = stmt.executeQuery();

            List<Integer> ids = new ArrayList<>();
            while (resultSet.next()) {
                ids.add(
                        resultSet.getInt(1)
                );
            }
            return ids;
        }
    }

    public List<Casilla> findByfkTipoCasillaAndfkSeccional(Integer fkTipoCasilla, Integer fkSeccional) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from casilla where fk_tipo_casilla=? and fk_seccional=?")
        ) {
            stmt.setInt(1, fkTipoCasilla);
            stmt.setInt(2, fkSeccional);
            ResultSet resultSet = stmt.executeQuery();
            return map(resultSet);
        }
    }

    public List<Casilla> map(ResultSet resultSet) throws SQLException {
        List<Casilla> casillaList = new ArrayList<>();
        while (resultSet.next()) {
            casillaList.add(
                    Casilla.builder()
                            .id(resultSet.getInt("id"))
                            .fkTipoCasilla(resultSet.getInt("fk_tipo_casilla"))
                            .fkSeccional(resultSet.getInt("fk_seccional"))
                            .build()
            );
        }
        return casillaList;
    }


    public Casilla findById(Integer casillaId) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from casilla where id=?")
        ) {
            stmt.setInt(1, casillaId);
            ResultSet resultSet = stmt.executeQuery();
            try {
                return map(resultSet).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
