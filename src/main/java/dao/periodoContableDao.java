package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.PeriodoContable;

/**
 *
 * @author maril
 */
public class periodoContableDao {

    private final Connection conexion;

    public periodoContableDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    public void insertar(PeriodoContable periodo) throws SQLException {

        String sql = """
            INSERT INTO periodos_contables (
                id_empresa,
                nombre,
                fecha_inicio,
                fecha_fin,
                estado
            )
            VALUES (?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, periodo.getIdEmpresa());
            ps.setString(2, periodo.getNombre());
            ps.setDate(3, Date.valueOf(periodo.getFechaInicio()));
            ps.setDate(4, Date.valueOf(periodo.getFechaFin()));
            ps.setString(5, periodo.getEstado());

            ps.executeUpdate();
        }
    }

    public void actualizar(PeriodoContable periodo) throws SQLException {

        String sql = """
            UPDATE periodos_contables SET
                id_empresa = ?,
                nombre = ?,
                fecha_inicio = ?,
                fecha_fin = ?,
                estado = ?
            WHERE id_periodo = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, periodo.getIdEmpresa());
            ps.setString(2, periodo.getNombre());
            ps.setDate(3, Date.valueOf(periodo.getFechaInicio()));
            ps.setDate(4, Date.valueOf(periodo.getFechaFin()));
            ps.setString(5, periodo.getEstado());
            ps.setInt(6, periodo.getIdPeriodo());

            ps.executeUpdate();
        }
    }

    public List<PeriodoContable> listar() throws SQLException {

        List<PeriodoContable> periodos = new ArrayList<>();

        String sql = """
            SELECT
                id_periodo,
                id_empresa,
                nombre,
                fecha_inicio,
                fecha_fin,
                estado
            FROM periodos_contables
            ORDER BY id_periodo
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                periodos.add(convertirPeriodo(rs));
            }
        }

        return periodos;
    }

    public PeriodoContable buscarPorId(int idPeriodo) throws SQLException {

        String sql = """
            SELECT
                id_periodo,
                id_empresa,
                nombre,
                fecha_inicio,
                fecha_fin,
                estado
            FROM periodos_contables
            WHERE id_periodo = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPeriodo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirPeriodo(rs);
                }
            }
        }

        return null;
    }

    public List<PeriodoContable> listarPorEmpresa(int idEmpresa) throws SQLException {

        List<PeriodoContable> periodos = new ArrayList<>();

        String sql = """
            SELECT
                id_periodo,
                id_empresa,
                nombre,
                fecha_inicio,
                fecha_fin,
                estado
            FROM periodos_contables
            WHERE id_empresa = ?
            ORDER BY fecha_inicio
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    periodos.add(convertirPeriodo(rs));
                }
            }
        }

        return periodos;
    }

    public List<PeriodoContable> buscar(String texto) throws SQLException {

        List<PeriodoContable> periodos = new ArrayList<>();

        String sql = """
            SELECT
                id_periodo,
                id_empresa,
                nombre,
                fecha_inicio,
                fecha_fin,
                estado
            FROM periodos_contables
            WHERE nombre LIKE ?
               OR estado LIKE ?
            ORDER BY id_periodo
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";

            ps.setString(1, filtro);
            ps.setString(2, filtro);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    periodos.add(convertirPeriodo(rs));
                }
            }
        }

        return periodos;
    }

    private PeriodoContable convertirPeriodo(ResultSet rs) throws SQLException {

        PeriodoContable periodo = new PeriodoContable();

        periodo.setIdPeriodo(rs.getInt("id_periodo"));
        periodo.setIdEmpresa(rs.getInt("id_empresa"));
        periodo.setNombre(rs.getString("nombre"));

        Date fechaInicio = rs.getDate("fecha_inicio");
        if (fechaInicio != null) {
            periodo.setFechaInicio(fechaInicio.toLocalDate());
        }

        Date fechaFin = rs.getDate("fecha_fin");
        if (fechaFin != null) {
            periodo.setFechaFin(fechaFin.toLocalDate());
        }

        periodo.setEstado(rs.getString("estado"));

        return periodo;
    }
}