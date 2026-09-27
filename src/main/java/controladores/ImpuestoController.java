package controladores;

import dao.ImpuestoDao;
import modelos.Impuesto;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

/**
 *
 * @author Nehemias Valencia
 */
public class ImpuestoController {

    private final ImpuestoDao impuestoDao;

    public ImpuestoController() throws SQLException {
        impuestoDao = new ImpuestoDao();
    }

    // =========================================
    // INSERTAR
    // =========================================
    public void insertar(Impuesto impuesto) throws SQLException {

        validarImpuesto(impuesto);

        impuestoDao.insertar(impuesto);
    }

    // =========================================
    // ACTUALIZAR
    // =========================================
    public void actualizar(Impuesto impuesto) throws SQLException {

        validarImpuesto(impuesto);

        if (impuesto.getIdImpuesto() <= 0) {
            throw new IllegalArgumentException(
                    "El impuesto seleccionado no es válido."
            );
        }

        impuestoDao.actualizar(impuesto);
    }

    // =========================================
    // DESACTIVAR
    // =========================================
    public void desactivar(int idImpuesto) throws SQLException {

        if (idImpuesto <= 0) {
            throw new IllegalArgumentException(
                    "El impuesto seleccionado no es válido."
            );
        }

        impuestoDao.desactivar(idImpuesto);
    }

    // =========================================
    // LISTAR
    // =========================================
    public List<Impuesto> listar() throws SQLException {

        return impuestoDao.listar();
    }

    // =========================================
    // BUSCAR
    // =========================================
    public List<Impuesto> buscar(String texto) throws SQLException {

        return impuestoDao.buscar(texto);
    }

    // =========================================
    // BUSCAR POR ID
    // =========================================
    public Impuesto buscarPorId(int idImpuesto) throws SQLException {

        return impuestoDao.buscarPorId(idImpuesto);
    }

    // =========================================
    // VALIDACIONES DE NEGOCIO
    // =========================================
    private void validarImpuesto(Impuesto impuesto) {

        if (impuesto == null) {
            throw new IllegalArgumentException(
                    "El impuesto no puede ser nulo."
            );
        }

        if (impuesto.getNombre() == null || impuesto.getNombre().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El nombre del impuesto es obligatorio."
            );
        }

        if (impuesto.getCodigo() == null || impuesto.getCodigo().trim().isEmpty()) {
            throw new IllegalArgumentException(
                    "El código del impuesto es obligatorio."
            );
        }

        if (impuesto.getPorcentaje() == null || impuesto.getPorcentaje().compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException(
                    "El porcentaje del impuesto no puede ser nulo ni negativo."
            );
        }

        if (impuesto.getFechaInicio() == null) {
            throw new IllegalArgumentException(
                    "La fecha de inicio es obligatoria."
            );
        }

        if (impuesto.getFechaFin() != null && impuesto.getFechaFin().isBefore(impuesto.getFechaInicio())) {
            throw new IllegalArgumentException(
                    "La fecha de fin no puede ser anterior a la fecha de inicio."
            );
        }
    }
}