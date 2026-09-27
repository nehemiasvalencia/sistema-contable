package modelos;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 *
 * @author Nehemias Valencia
 */
public class Impuesto {

    private int idImpuesto;
    private String nombre;
    private String codigo;
    private String descripcion;
    private BigDecimal porcentaje;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean estado;

    public Impuesto() {
    }

    // Constructor completo (con ID, ideal para consultas SELECT)
    public Impuesto(int idImpuesto, String nombre, String codigo, String descripcion, 
                    BigDecimal porcentaje, LocalDate fechaInicio, LocalDate fechaFin, boolean estado) {
        this.idImpuesto = idImpuesto;
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    // Constructor sin ID (ideal para registros INSERT)
    public Impuesto(String nombre, String codigo, String descripcion, 
                    BigDecimal porcentaje, LocalDate fechaInicio, LocalDate fechaFin, boolean estado) {
        this.nombre = nombre;
        this.codigo = codigo;
        this.descripcion = descripcion;
        this.porcentaje = porcentaje;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.estado = estado;
    }

    public int getIdImpuesto() {
        return idImpuesto;
    }

    public void setIdImpuesto(int idImpuesto) {
        this.idImpuesto = idImpuesto;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public BigDecimal getPorcentaje() {
        return porcentaje;
    }

    public void setPorcentaje(BigDecimal porcentaje) {
        this.porcentaje = porcentaje;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}