package com.goddrinksjava.prep.model.dao;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@ApplicationScoped
public class PartidoDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public String getNombreById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select NOMBRE from PARTIDO where ID = ?"
                )
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        }
    }
}
