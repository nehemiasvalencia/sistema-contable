package controladores;

import dao.paisDao;
import java.sql.SQLException;
import java.util.List;
import modelos.Pais;

/**
 *
 * @author Nehemias Valencia
 */
public class PaisController {

    private final paisDao paisDao;

    public PaisController() throws SQLException {
        paisDao = new paisDao();
    }

    public List<Pais> listar() throws SQLException {
        return paisDao.listar();
    }

    public Pais buscarPorId(int idPais) throws SQLException {
        return paisDao.buscarPorId(idPais);
    }
}