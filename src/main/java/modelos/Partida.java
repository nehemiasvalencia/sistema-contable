package modelos;

import java.sql.Date;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

/**
 * Modelo para la cabecera de la partida contable (Libro Diario).
 * Controla la condición de cuadratura (Partida Doble).
 */
public class Partida {

    private int idPartida;
    private Integer idPeriodo;
    private Integer idEmpresa;
    private int numeroPartida;
    private Date fecha;
    private String tipo;
    private String concepto;
    private double totalDebe;
    private double totalHaber;
    private boolean cuadrada;
    private String estado;
    private Integer idUsuario;
    private Timestamp creadoEn;

    private List<DetallePartida> detalles = new ArrayList<>();

    public Partida() {
        this.tipo = "DIARIO";
        this.estado = "PROCESADA";
        this.cuadrada = true;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public Integer getIdPeriodo() {
        return idPeriodo;
    }

    public void setIdPeriodo(Integer idPeriodo) {
        this.idPeriodo = idPeriodo;
    }

    public Integer getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(Integer idEmpresa) {
        this.idEmpresa = idEmpresa;
    }

    public int getNumeroPartida() {
        return numeroPartida;
    }

    public void setNumeroPartida(int numeroPartida) {
        this.numeroPartida = numeroPartida;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
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

    public double getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(double totalDebe) {
        this.totalDebe = totalDebe;
    }

    public double getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(double totalHaber) {
        this.totalHaber = totalHaber;
    }

    public boolean isCuadrada() {
        return Math.abs(totalDebe - totalHaber) < 0.001 && totalDebe > 0;
    }

    public void setCuadrada(boolean cuadrada) {
        this.cuadrada = cuadrada;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public Integer getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(Integer idUsuario) {
        this.idUsuario = idUsuario;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public List<DetallePartida> getDetalles() {
        return detalles;
    }

    public void setDetalles(List<DetallePartida> detalles) {
        this.detalles = detalles;
        calcularTotales();
    }

    public void agregarDetalle(DetallePartida detalle) {
        this.detalles.add(detalle);
        calcularTotales();
    }

    public void calcularTotales() {
        double debeSum = 0;
        double haberSum = 0;
        for (DetallePartida d : detalles) {
            debeSum += d.getDebe();
            haberSum += d.getHaber();
        }
        this.totalDebe = Math.round(debeSum * 100.0) / 100.0;
        this.totalHaber = Math.round(haberSum * 100.0) / 100.0;
        this.cuadrada = Math.abs(this.totalDebe - this.totalHaber) < 0.001 && this.totalDebe > 0;
    }
}
