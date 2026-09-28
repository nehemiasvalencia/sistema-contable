package dao;

import conexion.Conexion;
import modelos.Operacion;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nehemias Valencia
 */
public class OperacionDao {

    private final Connection conexion;

    public OperacionDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    /**
     * Insertar una nueva operación.
     */
    public int insertar(Operacion operacion) throws SQLException {

        String sql = """
            INSERT INTO operaciones
            (id_empresa, id_usuario, id_periodo, fecha, tipo, concepto, estado)
            VALUES (?, ?, ?, ?, ?, ?, ?)
            """;

        try (PreparedStatement ps = conexion.prepareStatement(
                sql, Statement.RETURN_GENERATED_KEYS)) {

            ps.setInt(1, operacion.getIdEmpresa());
            ps.setInt(2, operacion.getIdUsuario());
            ps.setInt(3, operacion.getIdPeriodo());
            ps.setDate(4, Date.valueOf(operacion.getFecha()));
            ps.setString(5, operacion.getTipo());
            ps.setString(6, operacion.getConcepto());
            ps.setString(7, operacion.getEstado());

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
     * Actualizar una operación.
     */
    public void actualizar(Operacion operacion) throws SQLException {

        String sql = """
            UPDATE operaciones
            SET id_empresa = ?,
                id_usuario = ?,
                id_periodo = ?,
                fecha = ?,
                tipo = ?,
                concepto = ?,
                estado = ?
            WHERE id_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, operacion.getIdEmpresa());
            ps.setInt(2, operacion.getIdUsuario());
            ps.setInt(3, operacion.getIdPeriodo());
            ps.setDate(4, Date.valueOf(operacion.getFecha()));
            ps.setString(5, operacion.getTipo());
            ps.setString(6, operacion.getConcepto());
            ps.setString(7, operacion.getEstado());
            ps.setInt(8, operacion.getIdOperacion());

            ps.executeUpdate();
        }
    }

    /**
     * Cambiar únicamente el estado de una operación.
     */
    public void cambiarEstado(int idOperacion, String estado)
            throws SQLException {

        String sql = """
            UPDATE operaciones
            SET estado = ?
            WHERE id_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idOperacion);

            ps.executeUpdate();
        }
    }

    /**
     * Buscar operación por ID.
     */
    public Operacion buscarPorId(int idOperacion) throws SQLException {

        String sql = """
            SELECT id_operacion,
                   id_empresa,
                   id_usuario,
                   id_periodo,
                   fecha,
                   tipo,
                   concepto,
                   estado
            FROM operaciones
            WHERE id_operacion = ?
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idOperacion);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return mapearOperacion(rs);
                }
            }
        }

        return null;
    }

    /**
     * Obtener todas las operaciones.
     */
    public List<Operacion> listar() throws SQLException {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
            SELECT id_operacion,
                   id_empresa,
                   id_usuario,
                   id_periodo,
                   fecha,
                   tipo,
                   concepto,
                   estado
            FROM operaciones
            ORDER BY fecha DESC, id_operacion DESC
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                lista.add(mapearOperacion(rs));
            }
        }

        return lista;
    }

    /**
     * Buscar por concepto o tipo.
     */
    public List<Operacion> buscar(String texto) throws SQLException {

        List<Operacion> lista = new ArrayList<>();

        String sql = """
            SELECT id_operacion,
                   id_empresa,
                   id_usuario,
                   id_periodo,
                   fecha,
                   tipo,
                   concepto,
                   estado
            FROM operaciones
            WHERE concepto LIKE ?
               OR tipo LIKE ?
            ORDER BY fecha DESC
            """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            String busqueda = "%" + texto + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    lista.add(mapearOperacion(rs));
                }
            }
        }

        return lista;
    }

    /**
     * Mapear ResultSet a modelo Operacion.
     */
    private Operacion mapearOperacion(ResultSet rs)
            throws SQLException {

        Operacion operacion = new Operacion();

        operacion.setIdOperacion(
                rs.getInt("id_operacion"));

        operacion.setIdEmpresa(
                rs.getInt("id_empresa"));

        operacion.setIdUsuario(
                rs.getInt("id_usuario"));

        operacion.setIdPeriodo(
                rs.getInt("id_periodo"));

        Date fecha = rs.getDate("fecha");

        if (fecha != null) {
            operacion.setFecha(fecha.toLocalDate());
        }

        operacion.setTipo(
                rs.getString("tipo"));

        operacion.setConcepto(
                rs.getString("concepto"));

        operacion.setEstado(
                rs.getString("estado"));

        return operacion;
    }
}