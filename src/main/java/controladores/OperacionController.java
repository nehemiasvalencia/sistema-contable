package controladores;

import dao.OperacionDao;
import java.sql.SQLException;
import java.util.List;
import modelos.Operacion;

/**
 * Controlador para operaciones contables.
 *
 * @author Nehemias Valencia
 */
public class OperacionController {

    private final OperacionDao operacionDao;

    public OperacionController() throws SQLException {
        operacionDao = new OperacionDao();
    }

    public int guardar(Operacion operacion)
            throws SQLException {

        return operacionDao.insertar(operacion);
    }

    public void actualizar(Operacion operacion)
            throws SQLException {

        operacionDao.actualizar(operacion);
    }

    public void cambiarEstado(int idOperacion, String estado)
            throws SQLException {

        operacionDao.cambiarEstado(idOperacion, estado);
    }

    public Operacion buscarPorId(int idOperacion)
            throws SQLException {

        return operacionDao.buscarPorId(idOperacion);
    }

    public List<Operacion> listar()
            throws SQLException {

        return operacionDao.listar();
    }

    public List<Operacion> buscar(String texto)
            throws SQLException {

        return operacionDao.buscar(texto);
    }
}