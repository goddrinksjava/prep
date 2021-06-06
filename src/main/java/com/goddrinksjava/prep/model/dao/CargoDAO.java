package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Cargo;

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
public class CargoDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public String getNombreById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select nombre from cargo where id = ?"
                )
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getString(1);
        }
    }

    public List<Cargo> findAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from cargo"
                )
        ) {
            return map(stmt.executeQuery());
        }
    }

    private List<Cargo> map(ResultSet resultSet) throws SQLException {
        List<Cargo> cargoList = new ArrayList<>();

        while (resultSet.next()) {
            cargoList.add(
                    Cargo.builder()
                            .id(resultSet.getInt("id"))
                            .nombre(resultSet.getString("nombre"))
                            .build()
            );
        }

        return cargoList;
    }
}
