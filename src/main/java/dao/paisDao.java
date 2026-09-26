package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import modelos.Pais;

/**
 *
 * @author Nehemias Valencia
 */
public class paisDao {

    private final Connection conexion;

    public paisDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    public List<Pais> listar() throws SQLException {

        List<Pais> paises = new ArrayList<>();

        String sql = """
            SELECT
                id_pais,
                nombre,
                codigo_iso,
                moneda
            FROM paises
            ORDER BY nombre
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Pais pais = new Pais();

                pais.setIdPais(rs.getInt("id_pais"));
                pais.setNombre(rs.getString("nombre"));
                pais.setCodigoIso(rs.getString("codigo_iso"));
                pais.setMoneda(rs.getString("moneda"));

                paises.add(pais);
            }
        }

        return paises;
    }

    public Pais buscarPorId(int idPais) throws SQLException {

        String sql = """
            SELECT
                id_pais,
                nombre,
                codigo_iso,
                moneda
            FROM paises
            WHERE id_pais = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idPais);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Pais pais = new Pais();

                    pais.setIdPais(rs.getInt("id_pais"));
                    pais.setNombre(rs.getString("nombre"));
                    pais.setCodigoIso(rs.getString("codigo_iso"));
                    pais.setMoneda(rs.getString("moneda"));

                    return pais;
                }
            }
        }

        return null;
    }
}