package dao;

import conexion.Conexion;
import modelos.Impuesto;

import java.sql.Connection;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nehemias Valencia
 */
public class ImpuestoDao {

    private final Connection conexion;

    public ImpuestoDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    // =========================================
    // INSERTAR
    // =========================================
    public void insertar(Impuesto impuesto) throws SQLException {

        String sql = """
                INSERT INTO impuestos
                (nombre, codigo, descripcion, porcentaje, fecha_inicio, fecha_fin, estado)
                VALUES (?, ?, ?, ?, ?, ?, ?)
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, impuesto.getNombre());
            ps.setString(2, impuesto.getCodigo());
            ps.setString(3, impuesto.getDescripcion());
            ps.setBigDecimal(4, impuesto.getPorcentaje());
            
            ps.setDate(5, impuesto.getFechaInicio() != null ? Date.valueOf(impuesto.getFechaInicio()) : null);
            
            if (impuesto.getFechaFin() != null) {
                ps.setDate(6, Date.valueOf(impuesto.getFechaFin()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            
            ps.setBoolean(7, impuesto.isEstado());

            ps.executeUpdate();
        }
    }

    // =========================================
    // ACTUALIZAR
    // =========================================
    public void actualizar(Impuesto impuesto) throws SQLException {

        String sql = """
                UPDATE impuestos
                SET nombre = ?,
                    codigo = ?,
                    descripcion = ?,
                    porcentaje = ?,
                    fecha_inicio = ?,
                    fecha_fin = ?,
                    estado = ?
                WHERE id_impuesto = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, impuesto.getNombre());
            ps.setString(2, impuesto.getCodigo());
            ps.setString(3, impuesto.getDescripcion());
            ps.setBigDecimal(4, impuesto.getPorcentaje());
            
            ps.setDate(5, impuesto.getFechaInicio() != null ? Date.valueOf(impuesto.getFechaInicio()) : null);
            
            if (impuesto.getFechaFin() != null) {
                ps.setDate(6, Date.valueOf(impuesto.getFechaFin()));
            } else {
                ps.setNull(6, Types.DATE);
            }
            
            ps.setBoolean(7, impuesto.isEstado());
            ps.setInt(8, impuesto.getIdImpuesto());

            ps.executeUpdate();
        }
    }

    // =========================================
    // DESACTIVAR
    // =========================================
    public void desactivar(int idImpuesto) throws SQLException {

        String sql = """
                UPDATE impuestos
                SET estado = FALSE
                WHERE id_impuesto = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idImpuesto);

            ps.executeUpdate();
        }
    }

    // =========================================
    // LISTAR
    // =========================================
    public List<Impuesto> listar() throws SQLException {

        List<Impuesto> lista = new ArrayList<>();

        String sql = """
                SELECT id_impuesto,
                       nombre,
                       codigo,
                       descripcion,
                       porcentaje,
                       fecha_inicio,
                       fecha_fin,
                       estado
                FROM impuestos
                ORDER BY id_impuesto DESC
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                lista.add(convertirResultSet(rs));
            }
        }

        return lista;
    }

    // =========================================
    // BUSCAR
    // =========================================
    public List<Impuesto> buscar(String texto) throws SQLException {

        List<Impuesto> lista = new ArrayList<>();

        String sql = """
                SELECT id_impuesto,
                       nombre,
                       codigo,
                       descripcion,
                       porcentaje,
                       fecha_inicio,
                       fecha_fin,
                       estado
                FROM impuestos
                WHERE nombre LIKE ?
                   OR codigo LIKE ?
                   OR descripcion LIKE ?
                ORDER BY id_impuesto DESC
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            String busqueda = "%" + texto + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(convertirResultSet(rs));
                }
            }
        }

        return lista;
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    public Impuesto buscarPorId(int idImpuesto) throws SQLException {

        String sql = """
                SELECT id_impuesto,
                       nombre,
                       codigo,
                       descripcion,
                       porcentaje,
                       fecha_inicio,
                       fecha_fin,
                       estado
                FROM impuestos
                WHERE id_impuesto = ?
                """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idImpuesto);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirResultSet(rs);
                }
            }
        }

        return null;
    }

    // =========================================
    // CONVERTIR RESULTSET
    // =========================================
    private Impuesto convertirResultSet(ResultSet rs) throws SQLException {

        Impuesto impuesto = new Impuesto();

        impuesto.setIdImpuesto(rs.getInt("id_impuesto"));
        impuesto.setNombre(rs.getString("nombre"));
        impuesto.setCodigo(rs.getString("codigo"));
        impuesto.setDescripcion(rs.getString("descripcion"));
        impuesto.setPorcentaje(rs.getBigDecimal("porcentaje"));

        Date fInicio = rs.getDate("fecha_inicio");
        if (fInicio != null) {
            impuesto.setFechaInicio(fInicio.toLocalDate());
        }

        Date fFin = rs.getDate("fecha_fin");
        if (fFin != null) {
            impuesto.setFechaFin(fFin.toLocalDate());
        }

        impuesto.setEstado(rs.getBoolean("estado"));

        return impuesto;
    }
}