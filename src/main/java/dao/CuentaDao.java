/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package dao;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;
import java.util.List;
import modelos.Cuentas;

/**
 *
 * @author Nehemias Valencia
 */
public class CuentaDao {

    private Connection connection;

    public CuentaDao() throws SQLException {
        connection = Conexion.getConnection();
    }

    // =========================================================
    // GUARDAR CUENTA
    // =========================================================
    public boolean guardar(Cuentas cuenta) {

        String sql = "INSERT INTO cuentas "
                + "(id_cuenta_padre, codigo, nombre, tipo, clasificacion, "
                + "naturaleza, id_rol_reporte, nivel, permite_movimientos, estado) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // Cuenta padre
            if (cuenta.getIdCuentaPadre() != null) {
                ps.setInt(1, cuenta.getIdCuentaPadre());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, cuenta.getCodigo());
            ps.setString(3, cuenta.getNombre());
            ps.setString(4, cuenta.getTipo());
            ps.setString(5, cuenta.getClasificacion());
            ps.setString(6, cuenta.getNaturaleza());

            // Rol de reporte
            if (cuenta.getIdRolReporte() != null) {
                ps.setInt(7, cuenta.getIdRolReporte());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            // Nivel
            ps.setByte(8, cuenta.getNivel());

            /*
             * Una cuenta nueva permite movimientos inicialmente.
             * Si posteriormente se le crea una cuenta hija,
             * este valor se cambiará automáticamente a FALSE.
             */
            ps.setBoolean(9, cuenta.isPermiteMovimientos());

            ps.setBoolean(10, cuenta.isEstado());

            boolean guardado = ps.executeUpdate() > 0;

            if (guardado && cuenta.getIdCuentaPadre() != null) {

                // El padre ya tiene una cuenta hija,
                // por lo tanto no debe recibir movimientos.
                actualizarPermiteMovimientos(
                        cuenta.getIdCuentaPadre(),
                        false
                );
            }

            return guardado;

        } catch (SQLException e) {

            System.out.println(
                    "Error al guardar cuenta: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // EDITAR CUENTA
    // =========================================================
    public boolean editar(Cuentas cuenta) throws SQLException {

        Integer padreAnterior = obtenerIdCuentaPadre(
                cuenta.getIdCuenta()
        );

        String sql = "UPDATE cuentas SET "
                + "id_cuenta_padre = ?, "
                + "codigo = ?, "
                + "nombre = ?, "
                + "tipo = ?, "
                + "clasificacion = ?, "
                + "naturaleza = ?, "
                + "id_rol_reporte = ?, "
                + "nivel = ?, "
                + "permite_movimientos = ?, "
                + "estado = ? "
                + "WHERE id_cuenta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            // Cuenta padre
            if (cuenta.getIdCuentaPadre() != null) {
                ps.setInt(1, cuenta.getIdCuentaPadre());
            } else {
                ps.setNull(1, Types.INTEGER);
            }

            ps.setString(2, cuenta.getCodigo());
            ps.setString(3, cuenta.getNombre());
            ps.setString(4, cuenta.getTipo());
            ps.setString(5, cuenta.getClasificacion());
            ps.setString(6, cuenta.getNaturaleza());

            // Rol de reporte
            if (cuenta.getIdRolReporte() != null) {
                ps.setInt(7, cuenta.getIdRolReporte());
            } else {
                ps.setNull(7, Types.INTEGER);
            }

            ps.setByte(8, cuenta.getNivel());

            /*
             * El valor se recibe desde el controlador.
             * No se modifica manualmente desde la vista.
             */
            ps.setBoolean(9, cuenta.isPermiteMovimientos());

            ps.setBoolean(10, cuenta.isEstado());

            ps.setInt(11, cuenta.getIdCuenta());

            boolean actualizado = ps.executeUpdate() > 0;

            if (actualizado) {

                // Si cambió de padre, revisar el padre anterior.
                if (padreAnterior != null
                        && !padreAnterior.equals(
                                cuenta.getIdCuentaPadre())) {

                    actualizarEstadoMovimientoPadre(
                            padreAnterior
                    );
                }

                // El nuevo padre ya tiene un hijo.
                if (cuenta.getIdCuentaPadre() != null) {

                    actualizarPermiteMovimientos(
                            cuenta.getIdCuentaPadre(),
                            false
                    );
                }
            }

            return actualizado;

        } catch (SQLException e) {

            System.out.println(
                    "Error al editar cuenta: "
                    + e.getMessage()
            );

            return false;
        }
    }

    // =========================================================
    // BUSCAR CUENTA POR CODIGO
    // =========================================================
    public Cuentas buscarPorCodigo(String codigo) {

        String sql = "SELECT * FROM cuentas WHERE codigo = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setString(1, codigo);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirResultSet(rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar cuenta por código: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // BUSCAR CUENTA POR ID
    // =========================================================
    public Cuentas buscarPorId(int idCuenta) {

        String sql = "SELECT * FROM cuentas WHERE id_cuenta = ?";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {
                    return convertirResultSet(rs);
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar cuenta por ID: "
                    + e.getMessage()
            );
        }

        return null;
    }

    // =========================================================
    // LISTAR CUENTAS
    // =========================================================
    public List<Cuentas> listar() {

        List<Cuentas> lista = new ArrayList<>();

        String sql = "SELECT * FROM cuentas ORDER BY codigo";

        try (
                PreparedStatement ps =
                        connection.prepareStatement(sql);
                ResultSet rs =
                        ps.executeQuery()
        ) {

            while (rs.next()) {

                lista.add(
                        convertirResultSet(rs)
                );
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al listar cuentas: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    // =========================================================
    // BUSCAR CUENTAS
    // =========================================================
    public List<Cuentas> buscar(String texto) {

        List<Cuentas> lista = new ArrayList<>();

        String sql = "SELECT * FROM cuentas "
                + "WHERE codigo LIKE ? "
                + "OR nombre LIKE ? "
                + "ORDER BY codigo";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            String busqueda = "%" + texto + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    lista.add(
                            convertirResultSet(rs)
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al buscar cuentas: "
                    + e.getMessage()
            );
        }

        return lista;
    }

    // =========================================================
    // OBTENER ID DEL PADRE ACTUAL
    // =========================================================
    private Integer obtenerIdCuentaPadre(int idCuenta)
            throws SQLException {

        String sql =
                "SELECT id_cuenta_padre "
                + "FROM cuentas "
                + "WHERE id_cuenta = ?";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setInt(1, idCuenta);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int idPadre =
                            rs.getInt("id_cuenta_padre");

                    if (rs.wasNull()) {
                        return null;
                    }

                    return idPadre;
                }
            }
        }

        return null;
    }

    // =========================================================
    // ACTUALIZAR PERMITE MOVIMIENTOS
    // =========================================================
    private void actualizarPermiteMovimientos(
            int idCuenta,
            boolean permite) {

        String sql =
                "UPDATE cuentas "
                + "SET permite_movimientos = ? "
                + "WHERE id_cuenta = ?";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setBoolean(1, permite);
            ps.setInt(2, idCuenta);

            ps.executeUpdate();

        } catch (SQLException e) {

            System.out.println(
                    "Error al actualizar permite movimientos: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // REVISAR SI EL PADRE TODAVIA TIENE HIJOS
    // =========================================================
    private void actualizarEstadoMovimientoPadre(
            int idPadre) {

        String sql =
                "SELECT COUNT(*) "
                + "FROM cuentas "
                + "WHERE id_cuenta_padre = ?";

        try (PreparedStatement ps =
                connection.prepareStatement(sql)) {

            ps.setInt(1, idPadre);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    int cantidadHijos =
                            rs.getInt(1);

                    /*
                     * Si todavía tiene hijos:
                     * FALSE
                     *
                     * Si ya no tiene hijos:
                     * TRUE
                     */
                    boolean permite =
                            cantidadHijos == 0;

                    actualizarPermiteMovimientos(
                            idPadre,
                            permite
                    );
                }
            }

        } catch (SQLException e) {

            System.out.println(
                    "Error al revisar hijos de cuenta: "
                    + e.getMessage()
            );
        }
    }

    // =========================================================
    // CONVERTIR RESULTSET A OBJETO CUENTAS
    // =========================================================
    private Cuentas convertirResultSet(
            ResultSet rs) throws SQLException {

        Cuentas cuenta = new Cuentas();

        cuenta.setIdCuenta(
                rs.getInt("id_cuenta")
        );

        int idPadre =
                rs.getInt("id_cuenta_padre");

        if (rs.wasNull()) {

            cuenta.setIdCuentaPadre(null);

        } else {

            cuenta.setIdCuentaPadre(idPadre);
        }

        cuenta.setCodigo(
                rs.getString("codigo")
        );

        cuenta.setNombre(
                rs.getString("nombre")
        );

        cuenta.setTipo(
                rs.getString("tipo")
        );

        cuenta.setClasificacion(
                rs.getString("clasificacion")
        );

        cuenta.setNaturaleza(
                rs.getString("naturaleza")
        );

        int idRol =
                rs.getInt("id_rol_reporte");

        if (rs.wasNull()) {

            cuenta.setIdRolReporte(null);

        } else {

            cuenta.setIdRolReporte(idRol);
        }

        cuenta.setNivel(
                rs.getByte("nivel")
        );

        cuenta.setPermiteMovimientos(
                rs.getBoolean("permite_movimientos")
        );

        cuenta.setEstado(
                rs.getBoolean("estado")
        );

        return cuenta;
    }
}