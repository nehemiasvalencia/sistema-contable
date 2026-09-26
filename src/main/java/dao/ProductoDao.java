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
import modelos.Producto;

/**
 *
 * @author maril
 */
public class ProductoDao {

    private final Connection conexion;

    public ProductoDao() throws SQLException {
        conexion = Conexion.getConnection();
    }

    public void insertar(Producto producto) throws SQLException {

        String sql = """
            INSERT INTO productos (
                codigo,
                nombre,
                descripcion,
                unidad_medida,
                precio_compra,
                precio_venta,
                existencia,
                stock_minimo,
                estado
            )
            VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getUnidadMedida());
            ps.setDouble(5, producto.getPrecioCompra());
            ps.setDouble(6, producto.getPrecioVenta());
            ps.setDouble(7, producto.getExistencia());
            ps.setDouble(8, producto.getStockMinimo());
            ps.setBoolean(9, producto.isEstado());

            ps.executeUpdate();
        }
    }

    public void actualizar(Producto producto) throws SQLException {

        String sql = """
            UPDATE productos
            SET codigo = ?,
                nombre = ?,
                descripcion = ?,
                unidad_medida = ?,
                precio_compra = ?,
                precio_venta = ?,
                existencia = ?,
                stock_minimo = ?,
                estado = ?
            WHERE id_producto = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, producto.getCodigo());
            ps.setString(2, producto.getNombre());
            ps.setString(3, producto.getDescripcion());
            ps.setString(4, producto.getUnidadMedida());
            ps.setDouble(5, producto.getPrecioCompra());
            ps.setDouble(6, producto.getPrecioVenta());
            ps.setDouble(7, producto.getExistencia());
            ps.setDouble(8, producto.getStockMinimo());
            ps.setBoolean(9, producto.isEstado());
            ps.setInt(10, producto.getIdProducto());

            ps.executeUpdate();
        }
    }

    public void desactivar(int idProducto) throws SQLException {

        String sql = """
            UPDATE productos
            SET estado = 0
            WHERE id_producto = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);
            ps.executeUpdate();
        }
    }

    public List<Producto> listar() throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = """
            SELECT
                id_producto,
                codigo,
                nombre,
                descripcion,
                unidad_medida,
                precio_compra,
                precio_venta,
                existencia,
                stock_minimo,
                estado
            FROM productos
            ORDER BY id_producto DESC
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                Producto producto = new Producto();

                producto.setIdProducto(rs.getInt("id_producto"));
                producto.setCodigo(rs.getString("codigo"));
                producto.setNombre(rs.getString("nombre"));
                producto.setDescripcion(rs.getString("descripcion"));
                producto.setUnidadMedida(rs.getString("unidad_medida"));
                producto.setPrecioCompra(rs.getDouble("precio_compra"));
                producto.setPrecioVenta(rs.getDouble("precio_venta"));
                producto.setExistencia(rs.getDouble("existencia"));
                producto.setStockMinimo(rs.getDouble("stock_minimo"));
                producto.setEstado(rs.getBoolean("estado"));

                productos.add(producto);
            }
        }

        return productos;
    }

    public Producto buscarPorId(int idProducto) throws SQLException {

        String sql = """
            SELECT
                id_producto,
                codigo,
                nombre,
                descripcion,
                unidad_medida,
                precio_compra,
                precio_venta,
                existencia,
                stock_minimo,
                estado
            FROM productos
            WHERE id_producto = ?
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idProducto);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    Producto producto = new Producto();

                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setCodigo(rs.getString("codigo"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setUnidadMedida(rs.getString("unidad_medida"));
                    producto.setPrecioCompra(rs.getDouble("precio_compra"));
                    producto.setPrecioVenta(rs.getDouble("precio_venta"));
                    producto.setExistencia(rs.getDouble("existencia"));
                    producto.setStockMinimo(rs.getDouble("stock_minimo"));
                    producto.setEstado(rs.getBoolean("estado"));

                    return producto;
                }
            }
        }

        return null;
    }

    public List<Producto> buscar(String texto) throws SQLException {

        List<Producto> productos = new ArrayList<>();

        String sql = """
            SELECT
                id_producto,
                codigo,
                nombre,
                descripcion,
                unidad_medida,
                precio_compra,
                precio_venta,
                existencia,
                stock_minimo,
                estado
            FROM productos
            WHERE codigo LIKE ?
               OR nombre LIKE ?
               OR descripcion LIKE ?
            ORDER BY id_producto DESC
        """;

        try (PreparedStatement ps = conexion.prepareStatement(sql)) {

            String busqueda = "%" + texto + "%";

            ps.setString(1, busqueda);
            ps.setString(2, busqueda);
            ps.setString(3, busqueda);

            try (ResultSet rs = ps.executeQuery()) {

                while (rs.next()) {

                    Producto producto = new Producto();

                    producto.setIdProducto(rs.getInt("id_producto"));
                    producto.setCodigo(rs.getString("codigo"));
                    producto.setNombre(rs.getString("nombre"));
                    producto.setDescripcion(rs.getString("descripcion"));
                    producto.setUnidadMedida(rs.getString("unidad_medida"));
                    producto.setPrecioCompra(rs.getDouble("precio_compra"));
                    producto.setPrecioVenta(rs.getDouble("precio_venta"));
                    producto.setExistencia(rs.getDouble("existencia"));
                    producto.setStockMinimo(rs.getDouble("stock_minimo"));
                    producto.setEstado(rs.getBoolean("estado"));

                    productos.add(producto);
                }
            }
        }

        return productos;
    }
}
