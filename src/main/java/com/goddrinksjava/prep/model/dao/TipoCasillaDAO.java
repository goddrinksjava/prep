package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.TipoCasilla;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class TipoCasillaDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public List<TipoCasilla> findAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from tipo_casilla")
        ) {
            return map(stmt.executeQuery());
        }
    }

    public List<TipoCasilla> map(ResultSet resultSet) throws SQLException {
        List<TipoCasilla> tipoCasillaList = new ArrayList<>();

        while (resultSet.next()) {
            tipoCasillaList.add(
                    TipoCasilla.builder()
                            .id(resultSet.getInt("id"))
                            .tipo(resultSet.getString("tipo"))
                            .build()
            );
        }

        return tipoCasillaList;
    }

    public TipoCasilla findById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from tipo_casilla where id=?")
        ) {
            try {
                return map(stmt.executeQuery()).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }
}
