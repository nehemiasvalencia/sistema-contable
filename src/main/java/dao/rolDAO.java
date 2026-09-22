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
import modelos.Rol;

/**
 *
 * @author Nehemias Valencia
 */
public class rolDAO {

    public List<Rol> listarRolesActivos() {

        List<Rol> lista = new ArrayList<>();

        String sql = """
            SELECT
                id_rol,
                nombre,
                descripcion,
                estado
            FROM roles
            WHERE estado = TRUE
            ORDER BY nombre
        """;

        try (
                Connection conexion = Conexion.getConnection(); PreparedStatement statement = conexion.prepareStatement(sql); ResultSet resultado = statement.executeQuery()) {

            while (resultado.next()) {

                Rol rol = new Rol();

                rol.setIdRol(resultado.getInt("id_rol"));
                rol.setNombre(resultado.getString("nombre"));
                rol.setDescripcion(resultado.getString("descripcion"));
                rol.setEstado(resultado.getBoolean("estado"));

                lista.add(rol);
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar roles: " + e.getMessage()
            );
        }

        return lista;
    }

}
