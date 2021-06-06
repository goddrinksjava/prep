package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.EmailVerification;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class EmailVerificationDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public void insert(EmailVerification emailVerification) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into email_verification (token, fk_usuario) value (?, ?)"
                )
        ) {
            stmt.setString(1, emailVerification.getToken());
            stmt.setInt(2, emailVerification.getFkUsuario());
            stmt.executeUpdate();
        }
    }

    public void delete(EmailVerification emailVerification) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "delete from email_verification where token=?"
                )
        ) {
            stmt.setString(1, emailVerification.getToken());
            stmt.executeUpdate();
        }
    }

    public String findTokenByUsuario(Integer usuarioId) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "select token from email_verification where fk_usuario=?"
                )
        ) {
            stmt.setInt(1, usuarioId);
            ResultSet resultSet = stmt.executeQuery();
            if (resultSet.next()) {
                return resultSet.getString(1);
            }
            return null;
        }
    }
}
