package controladores;

import dao.periodoContableDao;
import java.sql.SQLException;
import java.util.List;
import modelos.PeriodoContable;

/**
 *
 * @author maril
 */
public class PeriodoContableController {

    private final periodoContableDao periodoContableDao;

    public PeriodoContableController() throws SQLException {
        periodoContableDao = new periodoContableDao();
    }

    public void insertar(PeriodoContable periodo) throws SQLException {
        periodoContableDao.insertar(periodo);
    }

    public void actualizar(PeriodoContable periodo) throws SQLException {
        periodoContableDao.actualizar(periodo);
    }

    public List<PeriodoContable> listar() throws SQLException {
        return periodoContableDao.listar();
    }

    public PeriodoContable buscarPorId(int idPeriodo) throws SQLException {
        return periodoContableDao.buscarPorId(idPeriodo);
    }

    public List<PeriodoContable> buscar(String texto) throws SQLException {
        return periodoContableDao.buscar(texto);
    }

    public List<PeriodoContable> listarPorEmpresa(int idEmpresa) throws SQLException {
        return periodoContableDao.listarPorEmpresa(idEmpresa);
    }
}