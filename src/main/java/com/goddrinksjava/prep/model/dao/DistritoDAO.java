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
public class DistritoDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public List<Distrito> findAll() throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from distrito")
        ) {
            ResultSet resultSet = stmt.executeQuery();
            return map(resultSet);
        }
    }

    public boolean isInDatabase(Distrito distrito) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select count(*) from distrito where id=?")
        ) {
            stmt.setInt(1, distrito.getId());
            ResultSet resultSet = stmt.executeQuery();
            resultSet.next();
            return resultSet.getInt(1) != 0;
        }
    }

    public List<Distrito> map(ResultSet resultSet) throws SQLException {
        List<Distrito> seccionalList = new ArrayList<>();
        while (resultSet.next()) {
            seccionalList.add(
                    Distrito.builder()
                            .id(resultSet.getInt("id"))
                            .build()
            );
        }
        return seccionalList;
    }
}
