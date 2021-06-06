package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Privilegio;

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

    public String findNameById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select nombre from privilegio where id = ?")
        ) {
            stmt.setInt(1, id);

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();
            return resultSet.getString(1);
        }
    }

    public Integer findIdByName(String name) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select id from privilegio where nombre = ?")
        ) {
            stmt.setString(1, name);

            ResultSet resultSet = stmt.executeQuery();

            resultSet.next();
            return resultSet.getInt(1);
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
