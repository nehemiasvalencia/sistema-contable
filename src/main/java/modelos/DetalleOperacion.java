package modelos;

import java.math.BigDecimal;

/**
 *
 * @author Nehemias Valencia
 */
public class DetalleOperacion {

    private int idDetalleOperacion;
    private int idOperacion;
    private Integer idProducto;
    private Integer idImpuesto;

    private String descripcion;

    private BigDecimal cantidad;
    private BigDecimal costoUnitario;
    private BigDecimal porcentajeIVA;

    public DetalleOperacion() {
    }

    // Para insertar
    public DetalleOperacion(int idOperacion, Integer idProducto,
            Integer idImpuesto, String descripcion,
            BigDecimal cantidad, BigDecimal costoUnitario,
            BigDecimal porcentajeIVA) {

        this.idOperacion = idOperacion;
        this.idProducto = idProducto;
        this.idImpuesto = idImpuesto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.porcentajeIVA = porcentajeIVA;
    }

    // Completo
    public DetalleOperacion(int idDetalleOperacion, int idOperacion,
            Integer idProducto, Integer idImpuesto,
            String descripcion, BigDecimal cantidad,
            BigDecimal costoUnitario, BigDecimal porcentajeIVA) {

        this.idDetalleOperacion = idDetalleOperacion;
        this.idOperacion = idOperacion;
        this.idProducto = idProducto;
        this.idImpuesto = idImpuesto;
        this.descripcion = descripcion;
        this.cantidad = cantidad;
        this.costoUnitario = costoUnitario;
        this.porcentajeIVA = porcentajeIVA;
    }

    public int getIdDetalleOperacion() {
        return idDetalleOperacion;
    }

    public void setIdDetalleOperacion(int idDetalleOperacion) {
        this.idDetalleOperacion = idDetalleOperacion;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public Integer getIdProducto() {
        return idProducto;
    }

    public void setIdProducto(Integer idProducto) {
        this.idProducto = idProducto;
    }

    public Integer getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(Integer idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getCantidad() {
        return cantidad;
    }

    public void setCantidad(BigDecimal cantidad) {
        this.cantidad = cantidad;
    }

    public BigDecimal getCostoUnitario() {
        return costoUnitario;
    }

    public void setCostoUnitario(BigDecimal costoUnitario) {
        this.costoUnitario = costoUnitario;
    }

    public BigDecimal getPorcentajeIVA() {
        return porcentajeIVA;
    }

    public void setPorcentajeIVA(BigDecimal porcentajeIVA) {
        this.porcentajeIVA = porcentajeIVA;
    }
}