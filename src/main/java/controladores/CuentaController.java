/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java
 */
package controladores;

import dao.CuentaDao;
import java.sql.SQLException;
import java.util.List;
import modelos.Cuentas;

/**
 *
 * @author Nehemias Valencia
 */
public class CuentaController {

    private final CuentaDao cuentasDao;

    public CuentaController() throws SQLException {
        cuentasDao = new CuentaDao();
    }

    // Add account
    public boolean guardar(Cuentas cuenta) {

        if (cuenta == null) {
            return false;
        }

        return cuentasDao.guardar(cuenta);
    }

    // Edit account
    public boolean editar(Cuentas cuenta) throws SQLException {

        if (cuenta == null || cuenta.getIdCuenta() <= 0) {
            return false;
        }

        return cuentasDao.editar(cuenta);
    }

    // List accounts
    public List<Cuentas> listar() {

        return cuentasDao.listar();
    }

    // Search accounts
    public List<Cuentas> buscar(String texto) {

        if (texto == null) {
            texto = "";
        }

        return cuentasDao.buscar(texto);
    }

    // Search account by code
    public Cuentas buscarPorCodigo(String codigo) {

        if (codigo == null || codigo.trim().isEmpty()) {
            return null;
        }

        return cuentasDao.buscarPorCodigo(codigo.trim());
    }
}