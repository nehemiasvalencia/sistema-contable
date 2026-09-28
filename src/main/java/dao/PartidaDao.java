package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelos.DetallePartida;
import modelos.MayorCuenta;
import modelos.Partida;

/**
 * Data Access Object para el Libro Diario (Partidas) y cálculos del Libro Mayor.
 */
public class PartidaDao {

    public PartidaDao() {
    }

    // =========================================================
    // GUARDAR PARTIDA CON VALIDACIÓN ESTRICTA DE PARTIDA DOBLE
    // =========================================================
    public boolean guardar(Partida partida) throws SQLException {

        if (partida.getDetalles() == null || partida.getDetalles().size() < 2) {
            throw new SQLException("El asiento debe tener al menos dos registros contables.");
        }

        partida.calcularTotales();

        // VALIDACIÓN OBLIGATORIA: El sistema debe bloquear el guardado si no cumple la Partida Doble
        if (!partida.isCuadrada()) {
            throw new SQLException(
                "BLOQUEO DE GUARDADO: El asiento no cumple el principio de Partida Doble.\n"
                + "Total Debe: $" + String.format("%.2f", partida.getTotalDebe()) + "\n"
                + "Total Haber: $" + String.format("%.2f", partida.getTotalHaber()) + "\n"
                + "Diferencia: $" + String.format("%.2f", Math.abs(partida.getTotalDebe() - partida.getTotalHaber()))
            );
        }

        String sqlPartida = """
            INSERT INTO partidas (
                id_periodo, id_empresa, numero_partida, fecha, tipo,
                concepto, total_debe, total_haber, cuadrada, estado, id_usuario
            ) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        String sqlDetalle = """
            INSERT INTO detalle_partidas (
                id_partida, id_cuenta, linea, concepto, parcial, debe, haber
            ) VALUES (?, ?, ?, ?, ?, ?, ?)
        """;

        Connection con = Conexion.getConnection();
        boolean autoCommitAnterior = con.getAutoCommit();

        try {
            con.setAutoCommit(false);

            int idPartidaGenerado = 0;

            try (PreparedStatement psPartida = con.prepareStatement(sqlPartida, Statement.RETURN_GENERATED_KEYS)) {
                if (partida.getIdPeriodo() != null) {
                    psPartida.setInt(1, partida.getIdPeriodo());
                } else {
                    psPartida.setNull(1, Types.INTEGER);
                }

                if (partida.getIdEmpresa() != null) {
                    psPartida.setInt(2, partida.getIdEmpresa());
                } else {
                    psPartida.setNull(2, Types.INTEGER);
                }

                psPartida.setInt(3, partida.getNumeroPartida());
                psPartida.setDate(4, partida.getFecha());
                psPartida.setString(5, partida.getTipo() != null ? partida.getTipo() : "DIARIO");
                psPartida.setString(6, partida.getConcepto());
                psPartida.setDouble(7, partida.getTotalDebe());
                psPartida.setDouble(8, partida.getTotalHaber());
                psPartida.setBoolean(9, true);
                psPartida.setString(10, partida.getEstado() != null ? partida.getEstado() : "PROCESADA");

                if (partida.getIdUsuario() != null) {
                    psPartida.setInt(11, partida.getIdUsuario());
                } else {
                    psPartida.setNull(11, Types.INTEGER);
                }

                psPartida.executeUpdate();

                try (ResultSet rs = psPartida.getGeneratedKeys()) {
                    if (rs.next()) {
                        idPartidaGenerado = rs.getInt(1);
                        partida.setIdPartida(idPartidaGenerado);
                    }
                }
            }

            if (idPartidaGenerado <= 0) {
                throw new SQLException("No se pudo obtener el identificador de la partida.");
            }

            try (PreparedStatement psDetalle = con.prepareStatement(sqlDetalle)) {
                int linea = 1;
                for (DetallePartida det : partida.getDetalles()) {
                    psDetalle.setInt(1, idPartidaGenerado);
                    psDetalle.setInt(2, det.getIdCuenta());
                    psDetalle.setInt(3, linea++);
                    psDetalle.setString(4, det.getConcepto());
                    psDetalle.setDouble(5, det.getParcial());
                    psDetalle.setDouble(6, det.getDebe());
                    psDetalle.setDouble(7, det.getHaber());
                    psDetalle.addBatch();
                }
                psDetalle.executeBatch();
            }

            con.commit();
            return true;

        } catch (SQLException ex) {
            con.rollback();
            throw ex;
        } finally {
            con.setAutoCommit(autoCommitAnterior);
            con.close();
        }
    }

    // =========================================================
    // OBTENER SIGUIENTE NÚMERO DE PARTIDA
    // =========================================================
    public int obtenerSiguienteNumero(Integer idPeriodo) throws SQLException {
        String sql = "SELECT COALESCE(MAX(numero_partida), 0) + 1 AS siguiente FROM partidas";
        if (idPeriodo != null) {
            sql += " WHERE id_periodo = " + idPeriodo;
        }

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                return rs.getInt("siguiente");
            }
        }
        return 1;
    }

    // =========================================================
    // LISTAR PARTIDAS REGISTRADAS
    // =========================================================
    public List<Partida> listarPartidas(Integer idPeriodo, String criterio) throws SQLException {
        List<Partida> lista = new ArrayList<>();

        StringBuilder sql = new StringBuilder("""
            SELECT id_partida, id_periodo, id_empresa, numero_partida, fecha,
                   tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario, creado_en
            FROM partidas
            WHERE 1=1
        """);

        if (idPeriodo != null) {
            sql.append(" AND id_periodo = ").append(idPeriodo);
        }

        if (criterio != null && !criterio.trim().isEmpty()) {
            sql.append(" AND (concepto LIKE ? OR tipo LIKE ?)");
        }

        sql.append(" ORDER BY numero_partida DESC, fecha DESC");

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql.toString())) {

            if (criterio != null && !criterio.trim().isEmpty()) {
                String search = "%" + criterio.trim() + "%";
                ps.setString(1, search);
                ps.setString(2, search);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    Partida p = new Partida();
                    p.setIdPartida(rs.getInt("id_partida"));
                    p.setIdPeriodo((Integer) rs.getObject("id_periodo"));
                    p.setIdEmpresa((Integer) rs.getObject("id_empresa"));
                    p.setNumeroPartida(rs.getInt("numero_partida"));
                    p.setFecha(rs.getDate("fecha"));
                    p.setTipo(rs.getString("tipo"));
                    p.setConcepto(rs.getString("concepto"));
                    p.setTotalDebe(rs.getDouble("total_debe"));
                    p.setTotalHaber(rs.getDouble("total_haber"));
                    p.setCuadrada(rs.getBoolean("cuadrada"));
                    p.setEstado(rs.getString("estado"));
                    p.setIdUsuario((Integer) rs.getObject("id_usuario"));
                    p.setCreadoEn(rs.getTimestamp("creado_en"));

                    lista.add(p);
                }
            }
        }

        return lista;
    }

    // =========================================================
    // OBTENER DETALLES DE UNA PARTIDA
    // =========================================================
    public List<DetallePartida> obtenerDetallesPartida(int idPartida) throws SQLException {
        List<DetallePartida> lista = new ArrayList<>();

        String sql = """
            SELECT dp.id_detalle, dp.id_partida, dp.id_cuenta, dp.linea,
                   dp.concepto, dp.parcial, dp.debe, dp.haber,
                   c.codigo, c.nombre
            FROM detalle_partidas dp
            INNER JOIN cuentas c ON dp.id_cuenta = c.id_cuenta
            WHERE dp.id_partida = ?
            ORDER BY dp.linea ASC
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePartida d = new DetallePartida();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdPartida(rs.getInt("id_partida"));
                    d.setIdCuenta(rs.getInt("id_cuenta"));
                    d.setLinea(rs.getInt("linea"));
                    d.setConcepto(rs.getString("concepto"));
                    d.setParcial(rs.getDouble("parcial"));
                    d.setDebe(rs.getDouble("debe"));
                    d.setHaber(rs.getDouble("haber"));
                    d.setCodigoCuenta(rs.getString("codigo"));
                    d.setNombreCuenta(rs.getString("nombre"));

                    lista.add(d);
                }
            }
        }

        return lista;
    }

    // =========================================================
    // ANULAR PARTIDA
    // =========================================================
    public boolean anularPartida(int idPartida) throws SQLException {
        String sql = "UPDATE partidas SET estado = 'ANULADA' WHERE id_partida = ?";
        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idPartida);
            return ps.executeUpdate() > 0;
        }
    }

    // =========================================================
    // 2. MAYORIZACIÓN AUTOMÁTICA EN TIEMPO REAL
    // Consolidación de débitos y créditos con saldos deudor/acreedor
    // =========================================================
    public List<MayorCuenta> obtenerMayorizacion(Integer idPeriodo) throws SQLException {
        List<MayorCuenta> lista = new ArrayList<>();

        String sql = """
            SELECT c.id_cuenta, c.codigo, c.nombre, c.tipo, c.clasificacion, c.naturaleza,
                   COALESCE(SUM(dp.debe), 0) AS total_debe,
                   COALESCE(SUM(dp.haber), 0) AS total_haber
            FROM cuentas c
            LEFT JOIN detalle_partidas dp ON c.id_cuenta = dp.id_cuenta
            LEFT JOIN partidas p ON dp.id_partida = p.id_partida AND p.estado = 'PROCESADA'
        """;

        if (idPeriodo != null) {
            sql += " AND (p.id_periodo = " + idPeriodo + " OR p.id_periodo IS NULL)";
        }

        sql += """
            WHERE c.permite_movimientos = TRUE
            GROUP BY c.id_cuenta, c.codigo, c.nombre, c.tipo, c.clasificacion, c.naturaleza
            HAVING total_debe > 0 OR total_haber > 0
            ORDER BY c.codigo ASC
        """;

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                MayorCuenta mc = new MayorCuenta();
                mc.setIdCuenta(rs.getInt("id_cuenta"));
                mc.setCodigo(rs.getString("codigo"));
                mc.setNombre(rs.getString("nombre"));
                mc.setTipo(rs.getString("tipo"));
                mc.setClasificacion(rs.getString("clasificacion"));
                mc.setNaturaleza(rs.getString("naturaleza"));
                mc.setTotalDebe(rs.getDouble("total_debe"));
                mc.setTotalHaber(rs.getDouble("total_haber"));

                lista.add(mc);
            }
        }

        return lista;
    }

    // =========================================================
    // MOVIMIENTOS INDIVIDUALES PARA CUENTA T
    // =========================================================
    public List<DetallePartida> obtenerMovimientosCuenta(int idCuenta, Integer idPeriodo) throws SQLException {
        List<DetallePartida> lista = new ArrayList<>();

        String sql = """
            SELECT dp.id_detalle, dp.id_partida, dp.id_cuenta, dp.linea,
                   dp.concepto, dp.parcial, dp.debe, dp.haber,
                   p.numero_partida, p.fecha, p.concepto AS concepto_partida
            FROM detalle_partidas dp
            INNER JOIN partidas p ON dp.id_partida = p.id_partida
            WHERE dp.id_cuenta = ? AND p.estado = 'PROCESADA'
        """;

        if (idPeriodo != null) {
            sql += " AND p.id_periodo = " + idPeriodo;
        }

        sql += " ORDER BY p.fecha ASC, p.numero_partida ASC, dp.linea ASC";

        try (Connection con = Conexion.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    DetallePartida d = new DetallePartida();
                    d.setIdDetalle(rs.getInt("id_detalle"));
                    d.setIdPartida(rs.getInt("id_partida"));
                    d.setIdCuenta(rs.getInt("id_cuenta"));
                    d.setLinea(rs.getInt("linea"));
                    d.setConcepto("Ptda #" + rs.getInt("numero_partida") + " - " + rs.getString("concepto_partida"));
                    d.setParcial(rs.getDouble("parcial"));
                    d.setDebe(rs.getDouble("debe"));
                    d.setHaber(rs.getDouble("haber"));

                    lista.add(d);
                }
            }
        }

        return lista;
    }
}
