package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.UsuarioPrivilegios;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.sql.DataSource;
import java.sql.*;

@ApplicationScoped
public class UsuarioPrivilegioDAO {
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public void persist(UsuarioPrivilegios usuarioPrivilegios) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into usuario_privilegios (fk_usuario, fk_privilegio) VALUES ( ?, ? )",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setInt(1, usuarioPrivilegios.getFkUsuario());
            stmt.setInt(2, usuarioPrivilegios.getFkPrivilegio());
            stmt.executeUpdate();
        }
    }
}
