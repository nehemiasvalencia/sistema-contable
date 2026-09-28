package modelos;

import java.time.LocalDate;

/**
 * 
 *
 * @author Nehemias Valencia
 */
public class Operacion {

    private int idOperacion;
    private int idEmpresa;
    private int idUsuario;
    private int idPeriodo;

    private LocalDate fecha;
    private String tipo;
    private String concepto;
    private String estado;

    public Operacion() {
    }

    // Para insertar
    public Operacion(int idEmpresa, int idUsuario, int idPeriodo,
            LocalDate fecha, String tipo, String concepto, String estado) {

        this.idEmpresa = idEmpresa;
        this.idUsuario = idUsuario;
        this.idPeriodo = idPeriodo;
        this.fecha = fecha;
        this.tipo = tipo;
        this.concepto = concepto;
        this.estado = estado;
    }

    // Completo
    public Operacion(int idOperacion, int idEmpresa, int idUsuario,
            int idPeriodo, LocalDate fecha, String tipo,
            String concepto, String estado) {

        this.idOperacion = idOperacion;
        this.idEmpresa = idEmpresa;
        this.idUsuario = idUsuario;
        this.idPeriodo = idPeriodo;
        this.fecha = fecha;
        this.tipo = tipo;
        this.concepto = concepto;
        this.estado = estado;
    }

    public int getIdOperacion() {
        return idOperacion;
    }

    public void setIdOperacion(int idOperacion) {
        this.idOperacion = idOperacion;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(int idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }
}