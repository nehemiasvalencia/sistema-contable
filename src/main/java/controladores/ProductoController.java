/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.ProductoDao;
import java.sql.SQLException;
import java.util.List;
import modelos.Producto;

/**
 *
 * @author maril
 */
public class ProductoController {

    private final ProductoDao productoDao;

    public ProductoController() throws SQLException {
        productoDao = new ProductoDao();
    }

    public void insertar(Producto producto) throws SQLException {
        productoDao.insertar(producto);
    }

    public void actualizar(Producto producto) throws SQLException {
        productoDao.actualizar(producto);
    }

    public void desactivar(int idProducto) throws SQLException {
        productoDao.desactivar(idProducto);
    }

    public List<Producto> listar() throws SQLException {
        return productoDao.listar();
    }

    public Producto buscarPorId(int idProducto) throws SQLException {
        return productoDao.buscarPorId(idProducto);
    }

    public List<Producto> buscar(String texto) throws SQLException {
        return productoDao.buscar(texto);
    }
}
