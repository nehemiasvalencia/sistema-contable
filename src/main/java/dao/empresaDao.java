package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Empresa;

/**
 *
 * @author Nehemias Valencia
 */
public class empresaDao {

    private final Connection conexion;

    public empresaDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    public void insertar(Empresa empresa) throws SQLException {

        String sql = """
            INSERT INTO empresa (
                id_pais,
                nombre,
                nit,
                nrc,
                direccion,
                telefono,
                correo,
                actividad_economica,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empresa.getIdPais());
            ps.setString(2, empresa.getNombre());
            ps.setString(3, empresa.getNit());
            ps.setString(4, empresa.getNrc());
            ps.setString(5, empresa.getDireccion());
            ps.setString(6, empresa.getTelefono());
            ps.setString(7, empresa.getCorreo());
            ps.setString(8, empresa.getActividadEconomica());
            ps.setBoolean(9, empresa.isEstado());

            ps.executeUpdate();
        }
    }

    public void actualizar(Empresa empresa) throws SQLException {

        String sql = """
            UPDATE empresa SET
                id_pais = ?,
                nombre = ?,
                nit = ?,
                nrc = ?,
                direccion = ?,
                telefono = ?,
                correo = ?,
                actividad_economica = ?,
                estado = ?
            WHERE id_empresa = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, empresa.getIdPais());
            ps.setString(2, empresa.getNombre());
            ps.setString(3, empresa.getNit());
            ps.setString(4, empresa.getNrc());
            ps.setString(5, empresa.getDireccion());
            ps.setString(6, empresa.getTelefono());
            ps.setString(7, empresa.getCorreo());
            ps.setString(8, empresa.getActividadEconomica());
            ps.setBoolean(9, empresa.isEstado());
            ps.setInt(10, empresa.getIdEmpresa());

            ps.executeUpdate();
        }
    }

    public List<Empresa> listar() throws SQLException {

        List<Empresa> empresas = new ArrayList<>();

        String sql = """
            SELECT
                id_empresa,
                id_pais,
                nombre,
                nit,
                nrc,
                direccion,
                telefono,
                correo,
                actividad_economica,
                estado
            FROM empresa
            ORDER BY id_empresa
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                empresas.add(convertirEmpresa(rs));
            }
        }

        return empresas;
    }

    public Empresa buscarPorId(int idEmpresa) throws SQLException {

        String sql = """
            SELECT
                id_empresa,
                id_pais,
                nombre,
                nit,
                nrc,
                direccion,
                telefono,
                correo,
                actividad_economica,
                estado
            FROM empresa
            WHERE id_empresa = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idEmpresa);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirEmpresa(rs);
                }
            }
        }

        return null;
    }

    public List<Empresa> buscar(String texto) throws SQLException {

        List<Empresa> empresas = new ArrayList<>();

        String sql = """
            SELECT
                id_empresa,
                id_pais,
                nombre,
                nit,
                nrc,
                direccion,
                telefono,
                correo,
                actividad_economica,
                estado
            FROM empresa
            WHERE nombre LIKE ?
               OR nit LIKE ?
               OR nrc LIKE ?
               OR correo LIKE ?
            ORDER BY id_empresa
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            String filtro = "%" + texto + "%";

            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            ps.setString(4, filtro);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {
                    empresas.add(convertirEmpresa(rs));
                }
            }
        }

        return empresas;
    }

    private Empresa convertirEmpresa(ResultSet rs) throws SQLException {

        Empresa empresa = new Empresa();

        empresa.setIdEmpresa(rs.getInt("id_empresa"));
        empresa.setIdPais(rs.getInt("id_pais"));
        empresa.setNombre(rs.getString("nombre"));
        empresa.setNit(rs.getString("nit"));
        empresa.setNrc(rs.getString("nrc"));
        empresa.setDireccion(rs.getString("direccion"));
        empresa.setTelefono(rs.getString("telefono"));
        empresa.setCorreo(rs.getString("correo"));
        empresa.setActividadEconomica(
                rs.getString("actividad_economica")
        );
        empresa.setEstado(rs.getBoolean("estado"));

        return empresa;
    }
}