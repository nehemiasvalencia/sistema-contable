/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Usuario;
import utilidades.PasswordUtil;

/**
 *
 * @author Nehemias Valencia
 */
public class usuarioDAO {

    // =========================================
    // LISTAR USUARIOS
    // =========================================
    public List<Usuario> listarUsuarios() {

        List<Usuario> lista = new ArrayList<>();

        String sql = """
            SELECT
                u.id_usuario,
                u.id_rol,
                u.nombre,
                u.usuario,
                u.password_h,
                u.estado,
                u.creado_en,
                u.ultima_cone,
                r.nombre AS nombre_rol
            FROM usuarios u
            INNER JOIN roles r ON u.id_rol = r.id_rol
            ORDER BY u.id_usuario
        """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement statement = conexion.prepareStatement(sql); ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Usuario usuario = new Usuario();

                usuario.setIdUsuario(
                        resultado.getInt("id_usuario")
                );

                usuario.setIdRol(
                        resultado.getInt("id_rol")
                );

                usuario.setNombre(
                        resultado.getString("nombre")
                );

                usuario.setUsuario(
                        resultado.getString("usuario")
                );

                usuario.setEstado(
                        resultado.getBoolean("estado")
                );

                usuario.setCreadoEn(
                        resultado.getTimestamp("creado_en")
                );

                usuario.setUltimaConexion(
                        resultado.getTimestamp("ultima_cone")
                );

                usuario.setNombreRol(
                        resultado.getString("nombre_rol")
                );

                lista.add(usuario);
            }

        } catch (SQLException e) {
            System.out.println(
                    "Error al listar usuarios: " + e.getMessage()
            );
        }

        return lista;
    }

    // =========================================
    // INSERTAR USUARIO
    // =========================================
    public boolean insertarUsuario(Usuario usuario, String password) {

        String sql = """
            INSERT INTO usuarios
            (
                id_rol,
                nombre,
                usuario,
                password_h,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            String passwordHash = PasswordUtil.encriptar(password);

            statement.setInt(1, usuario.getIdRol());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getUsuario());
            statement.setString(4, passwordHash);
            statement.setBoolean(5, usuario.isEstado());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al insertar usuario: " + e.getMessage()
            );

            return false;
        }
    }

    // =========================================
    // ACTUALIZAR USUARIO SIN CAMBIAR PASSWORD
    // =========================================
    public boolean actualizarUsuario(Usuario usuario) {

        String sql = """
            UPDATE usuarios
            SET
                id_rol = ?,
                nombre = ?,
                usuario = ?,
                estado = ?
            WHERE id_usuario = ?
        """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            statement.setInt(1, usuario.getIdRol());
            statement.setString(2, usuario.getNombre());
            statement.setString(3, usuario.getUsuario());
            statement.setBoolean(4, usuario.isEstado());
            statement.setInt(5, usuario.getIdUsuario());

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar usuario: " + e.getMessage()
            );

            return false;
        }
    }

    // =========================================
    // ACTUALIZAR PASSWORD
    // =========================================
    public boolean actualizarPassword(
            int idUsuario,
            String nuevaPassword
    ) {

        String sql = """
            UPDATE usuarios
            SET password_h = ?
            WHERE id_usuario = ?
        """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement statement = conexion.prepareStatement(sql)) {

            String passwordHash
                    = PasswordUtil.encriptar(nuevaPassword);

            statement.setString(1, passwordHash);
            statement.setInt(2, idUsuario);

            return statement.executeUpdate() > 0;

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar contraseña: "
                    + e.getMessage()
            );

            return false;
        }
    }

    

    // Método para validar el usuario y la contraseña
    public Usuario iniciarSesion(String nombreUsuario, String passwordIngresada) {

        String sqlSelect = """
        SELECT
            u.id_usuario,
            u.id_rol,
            u.nombre,
            u.usuario,
            u.password_h,
            u.estado,
            u.creado_en,
            u.ultima_cone
        FROM usuarios u
        INNER JOIN roles r ON u.id_rol = r.id_rol
        WHERE u.usuario = ?
          AND u.estado = TRUE
          AND r.estado = TRUE
    """;

        String sqlUpdateUltimaConexion = """
        UPDATE usuarios
        SET ultima_cone = CURRENT_TIMESTAMP
        WHERE id_usuario = ?
    """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement stmtSelect = conexion.prepareStatement(sqlSelect)) {

            stmtSelect.setString(1, nombreUsuario);

            try (ResultSet resultado = stmtSelect.executeQuery()) {

                if (resultado.next()) {

                    String passwordHash = resultado.getString("password_h");

                    // Verificar contraseña
                    if (PasswordUtil.verificar(passwordIngresada, passwordHash)) {

                        int idUsuario = resultado.getInt("id_usuario");

                        // 1. Actualizar "ultima_cone" en la Base de Datos
                        try (PreparedStatement stmtUpdate = conexion.prepareStatement(sqlUpdateUltimaConexion)) {
                            stmtUpdate.setInt(1, idUsuario);
                            stmtUpdate.executeUpdate();
                        }

                        // 2. Mapear objeto Usuario
                        Usuario usuario = new Usuario();
                        usuario.setIdUsuario(idUsuario);
                        usuario.setIdRol(resultado.getInt("id_rol"));
                        usuario.setNombre(resultado.getString("nombre"));
                        usuario.setUsuario(resultado.getString("usuario"));
                        usuario.setPasswordHash(passwordHash);
                        usuario.setEstado(resultado.getBoolean("estado"));
                        usuario.setCreadoEn(resultado.getTimestamp("creado_en"));

                        // Se asigna el momento exacto del login en el objeto en memoria
                        usuario.setUltimaConexion(new java.sql.Timestamp(System.currentTimeMillis()));

                        return usuario;
                    }
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al iniciar sesión: " + e.getMessage());
        }

        return null;
    }
}
