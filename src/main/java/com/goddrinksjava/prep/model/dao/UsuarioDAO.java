package com.goddrinksjava.prep.model.dao;

import com.goddrinksjava.prep.model.bean.database.Usuario;

import javax.annotation.Resource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class UsuarioDAO {
    @Inject
    PrivilegioDAO privilegioDAO;
    @Resource(lookup = "java:global/jdbc/prep")
    private DataSource dataSource;

    public Usuario findByEmail(String email) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from usuario where email = ?")
        ) {
            stmt.setString(1, email);

            try {
                return mapResultSet(stmt.executeQuery()).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }

        }
    }

    public Usuario findById(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from usuario where id = ?")
        ) {
            stmt.setInt(1, id);

            try {
                return mapResultSet(stmt.executeQuery()).get(0);
            } catch (IndexOutOfBoundsException e) {
                return null;
            }

        }
    }

    public List<String> getPrivileges(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select FK_PRIVILEGIO from USUARIO_PRIVILEGIOS where FK_USUARIO = ?")
        ) {
            stmt.setInt(1, id);
            ResultSet resultSet = stmt.executeQuery();

            List<String> privilegios = new ArrayList<>();
            while (resultSet.next()) {
                privilegios.add(
                        privilegioDAO.findNameById(
                                resultSet.getInt(1)
                        )
                );
            }

            return privilegios;
        }
    }

    public void insert(Usuario usuario) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "insert into usuario (nombre, calle, colonia, numero_casa, municipio, codigo_postal, telefono, email, password)" +
                                "values ( ?, ?, ?, ?, ?, ?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                )
        ) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCalle());
            stmt.setString(3, usuario.getColonia());
            stmt.setString(4, usuario.getNumeroCasa());
            stmt.setString(5, usuario.getMunicipio());
            stmt.setString(6, usuario.getCodigoPostal());
            stmt.setString(7, usuario.getTelefono());
            stmt.setString(8, usuario.getEmail());
            stmt.setString(9, usuario.getPassword());
            stmt.executeUpdate();

            ResultSet generatedKeys = stmt.getGeneratedKeys();
            if (generatedKeys.next()) {
                int id = generatedKeys.getInt(1);
                usuario.setEnabled(false);
                usuario.setId(id);
            } else {
                throw new SQLException();
            }
        }
    }

    public void update(Usuario usuario) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "update usuario set nombre=?, calle=?, colonia=?, numero_casa=?, " +
                                "municipio=?, codigo_postal=?, telefono=?, email=?, password=?, " +
                                "email_confirmed=?, enabled=?, fk_casilla=? where id=?"
                )
        ) {
            stmt.setString(1, usuario.getNombre());
            stmt.setString(2, usuario.getCalle());
            stmt.setString(3, usuario.getColonia());
            stmt.setString(4, usuario.getNumeroCasa());
            stmt.setString(5, usuario.getMunicipio());
            stmt.setString(6, usuario.getCodigoPostal());
            stmt.setString(7, usuario.getTelefono());
            stmt.setString(8, usuario.getEmail());
            stmt.setString(9, usuario.getPassword());
            stmt.setBoolean(10, usuario.getEmailConfirmed());
            stmt.setBoolean(11, usuario.getEnabled());

            if (usuario.getFkCasilla() != null) {
                stmt.setInt(12, usuario.getFkCasilla());
            } else {
                stmt.setNull(12, java.sql.Types.INTEGER);
            }

            stmt.setInt(13, usuario.getId());
            stmt.executeUpdate();
        }
    }

    private List<Usuario> mapResultSet(ResultSet resultSet) throws SQLException {
        List<Usuario> usuarios = new ArrayList<>();

        while (resultSet.next()) {
            Usuario usuario = Usuario.builder()
                    .id(resultSet.getInt("id"))
                    .nombre(resultSet.getString("nombre"))
                    .calle(resultSet.getString("calle"))
                    .colonia(resultSet.getString("colonia"))
                    .numeroCasa(resultSet.getString("numero_casa"))
                    .municipio(resultSet.getString("municipio"))
                    .codigoPostal(resultSet.getString("codigo_postal"))
                    .telefono(resultSet.getString("telefono"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .emailConfirmed(resultSet.getBoolean("email_confirmed"))
                    .enabled(resultSet.getBoolean("enabled"))
                    .fkCasilla(resultSet.getInt("fk_casilla"))
                    .build();

            resultSet.getInt("fk_casilla");
            if (resultSet.wasNull()) {
                usuario.setFkCasilla(null);
            }
            usuarios.add(usuario);
        }

        return usuarios;
    }

    public List<Usuario> findByEnabledAndEmailConfirmed(boolean enabled, boolean emailConfirmed) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement("select * from USUARIO where ENABLED = ? and email_confirmed=?")
        ) {
            stmt.setBoolean(1, enabled);
            stmt.setBoolean(2, emailConfirmed);

            return mapResultSet(stmt.executeQuery());
        }
    }

    public void delete(Integer id) throws SQLException {
        try (
                Connection conn = dataSource.getConnection();
                PreparedStatement stmt = conn.prepareStatement(
                        "delete from USUARIO where ID=?"
                )
        ) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
