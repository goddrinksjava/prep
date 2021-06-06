package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Oauth;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class OauthDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public void insert(Oauth oauth) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into oauth (sub, fk_usuario, fk_oauth_application) value (?, ?, ?)"
                )
        ) {
            stmt.setString(1, oauth.getSub());
            stmt.setInt(2, oauth.getFkUsuario());
            stmt.setInt(3, oauth.getFkOauthApplication());
            stmt.executeUpdate();
        }
    }

    public Oauth getBySubAndFkOauthApplication(String sub, Integer oauthApplicationId) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from oauth where sub=? and fk_oauth_application=?"
                )
        ) {
            stmt.setString(1, sub);
            stmt.setInt(2, oauthApplicationId);
            ResultSet resultSet = stmt.executeQuery();
            try {
                return map(resultSet).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }
        }
    }

    public List<Oauth> getByUsuario(Integer usuarioId) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select * from oauth where fk_usuario=?"
                )
        ) {
            stmt.setInt(1, usuarioId);
            ResultSet resultSet = stmt.executeQuery();
            return map(resultSet);
        }
    }

    private List<Oauth> map(ResultSet resultSet) throws SQLException {
        List<Oauth> oauthList = new ArrayList<>();

        while (resultSet.next()) {
            oauthList.add(
                    Oauth.builder()
                            .sub(resultSet.getString("sub"))
                            .fkUsuario(resultSet.getInt("fk_usuario"))
                            .fkOauthApplication(resultSet.getInt("fk_oauth_application"))
                            .build()
            );
        }

        return oauthList;
    }
}
