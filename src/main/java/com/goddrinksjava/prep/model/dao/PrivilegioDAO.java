package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.pojo.database.Privilegio;

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
public class PrivilegioDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public Privilegio findById(Long id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from PRIVILEGIO where ID = ?")
        ) {
            stmt.setLong(1, id);

            try {
                return mapResultSet(stmt.executeQuery()).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }

        }
    }

    private List<Privilegio> mapResultSet(ResultSet resultSet) throws SQLException {
        List<Privilegio> privilegios = new ArrayList<>();

        while (resultSet.next()) {
            Privilegio privilegio = Privilegio.builder()
                    .id(resultSet.getInt("id"))
                    .nombre(resultSet.getString("nombre"))
                    .build();

            privilegios.add(privilegio);
        }

        return privilegios;
    }
}
