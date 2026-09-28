package dao;

import conexion.Conexion;
import modelos.DetalleOperacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nehemias Valencia
 */
public class DetalleOperacionDao {

    private final Connection conexion;

    public DetalleOperacionDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    /**
     * Insertar un detalle.
     */
    public int insertar(DetalleOperacion detalle)
            throws SQLException {

        String sql = """
            INSERT INTO detalle_operacion
            (
                id_operacion,
                id_producto,
                id_impuesto,
                descripcion,
                cantidad,
                costo_unitario,
                porcentaje_IVA
            )
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, detalle.getIdOperacion());

            if (detalle.getIdProducto() != null) {
                ps.setInt(2, detalle.getIdProducto());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            if (detalle.getIdImpuesto() != null) {
                ps.setInt(3, detalle.getIdImpuesto());
            } else {
                ps.setNull(3, Types.INTEGER);
            }

            ps.setString(4, detalle.getDescripcion());

            ps.setBigDecimal(5, detalle.getCantidad());

            ps.setBigDecimal(6, detalle.getCostoUnitario());

            ps.setBigDecimal(7, detalle.getPorcentajeIVA());

            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        }

        return 0;
    }

    /**
     * Actualizar un detalle.
     */
    public void actualizar(DetalleOperacion detalle)
            throws SQLException {

        String sql = """
            UPDATE detalle_operacion
            SET id_producto = ?,
                id_impuesto = ?,
                descripcion = ?,
                cantidad = ?,
                costo_unitario = ?,
                porcentaje_IVA = ?
            WHERE id_detalle_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            if (detalle.getIdProducto() != null) {
                ps.setInt(1, detalle.getIdProducto());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            if (detalle.getIdImpuesto() != null) {
                ps.setInt(2, detalle.getIdImpuesto());
            } else {
                ps.setNull(2, Types.INTEGER);
            }

            ps.setString(3, detalle.getDescripcion());

            ps.setBigDecimal(4, detalle.getCantidad());

            ps.setBigDecimal(5, detalle.getCostoUnitario());

            ps.setBigDecimal(6, detalle.getPorcentajeIVA());

            ps.setInt(7, detalle.getIdDetalleOperacion());

            ps.executeUpdate();
        }
    }

    /**
     * Eliminar un detalle.
     */
    public void eliminar(int idDetalleOperacion)
            throws SQLException {

        String sql = """
            DELETE FROM detalle_operacion
            WHERE id_detalle_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idDetalleOperacion);

            ps.executeUpdate();
        }
    }

    /**
     * Eliminar todos los detalles de una operación.
     */
    public void eliminarPorOperacion(int idOperacion)
            throws SQLException {

        String sql = """
            DELETE FROM detalle_operacion
            WHERE id_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idOperacion);

            ps.executeUpdate();
        }
    }

    /**
     * Obtener todos los detalles de una operación.
     */
    public List<DetalleOperacion> listarPorOperacion(
            int idOperacion) throws SQLException {

        List<DetalleOperacion> lista = new ArrayList<>();

        String sql = """
            SELECT id_detalle_operacion,
                   id_operacion,
                   id_producto,
                   id_impuesto,
                   descripcion,
                   cantidad,
                   costo_unitario,
                   porcentaje_IVA
            FROM detalle_operacion
            WHERE id_operacion = ?
            ORDER BY id_detalle_operacion
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idOperacion);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    DetalleOperacion detalle
                            = new DetalleOperacion();

                    detalle.setIdDetalleOperacion(
                            rs.getInt("id_detalle_operacion"));

                    detalle.setIdOperacion(
                            rs.getInt("id_operacion"));

                    int idProducto =
                            rs.getInt("id_producto");

                    if (!rs.wasNull()) {
                        detalle.setIdProducto(idProducto);
                    }

                    int idImpuesto =
                            rs.getInt("id_impuesto");

                    if (!rs.wasNull()) {
                        detalle.setIdImpuesto(idImpuesto);
                    }

                    detalle.setDescripcion(
                            rs.getString("descripcion"));

                    detalle.setCantidad(
                            rs.getBigDecimal("cantidad"));

                    detalle.setCostoUnitario(
                            rs.getBigDecimal("costo_unitario"));

                    detalle.setPorcentajeIVA(
                            rs.getBigDecimal("porcentaje_IVA"));

                    lista.add(detalle);
                }
            }
        }

        return lista;
    }
}