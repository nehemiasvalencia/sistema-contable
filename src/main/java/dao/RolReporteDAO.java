/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import modelos.RolReporte;

/**
 *
 * @author Nehemias Valencia
 */
public class RolReporteDAO {

    public List<RolReporte> listarActivos() {

        List<RolReporte> lista = new ArrayList<>();

        String sql = """
            SELECT id_rol_reporte, codigo, nombre, descripcion, estado
            FROM roles_reporte
            WHERE estado = TRUE
            ORDER BY codigo
        """;

        try (
                Connection con = Conexion.getConnection(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                RolReporte rol = new RolReporte();

                rol.setIdRolReporte(
                        rs.getInt("id_rol_reporte")
                );

                rol.setCodigo(
                        rs.getInt("codigo")
                );

                rol.setNombre(
                        rs.getString("nombre")
                );

                rol.setDescripcion(
                        rs.getString("descripcion")
                );

                rol.setEstado(
                        rs.getBoolean("estado")
                );

                lista.add(rol);
            }

        } catch (Exception e) {
            System.out.println(
                    "Error al listar roles de reporte: "
                    + e.getMessage()
            );
        }

        return lista;
    }
}
