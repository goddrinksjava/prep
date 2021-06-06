package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Distrito;
import com.goddrinksjava.prep.model.bean.database.Seccional;

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
public class SeccionalDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public List<Seccional> findByDistrito(Integer distritoId) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from seccional where fk_distrito=?")
        ) {
            stmt.setInt(1, distritoId);
            ResultSet resultSet = stmt.executeQuery();
            return map(resultSet);
        }
    }

    public Seccional findByIdAndDistrito(Integer id, Integer distrito) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from seccional where id=? and fk_distrito=?")
        ) {
            stmt.setInt(1, id);
            stmt.setInt(1, distrito);
            ResultSet resultSet = stmt.executeQuery();

            try {
                return map(resultSet).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }

    public List<Seccional> map(ResultSet resultSet) throws SQLException {
        List<Seccional> seccionalList = new ArrayList<>();
        while (resultSet.next()) {
            seccionalList.add(
                    Seccional.builder()
                            .id(resultSet.getInt("id"))
                            .fkDistrito(resultSet.getInt("fk_distrito"))
                            .build()
            );
        }
        return seccionalList;
    }
}
