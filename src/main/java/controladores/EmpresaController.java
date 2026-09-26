package controladores;

import dao.empresaDao;
import java.sql.SQLException;
import java.util.List;
import modelos.Empresa;

/**
 *
 * @author Nehemias Valencia
 */
public class EmpresaController {

    private final empresaDao empresaDao;

    public EmpresaController() throws SQLException {
        empresaDao = new empresaDao();
    }

    public void insertar(Empresa empresa) throws SQLException {
        empresaDao.insertar(empresa);
    }

    public void actualizar(Empresa empresa) throws SQLException {
        empresaDao.actualizar(empresa);
    }

    public List<Empresa> listar() throws SQLException {
        return empresaDao.listar();
    }

    public Empresa buscarPorId(int idEmpresa) throws SQLException {
        return empresaDao.buscarPorId(idEmpresa);
    }

    public List<Empresa> buscar(String texto) throws SQLException {
        return empresaDao.buscar(texto);
    }
}