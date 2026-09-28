package modelos;

/**
 * Modelo para las líneas de detalle de una partida o asiento contable.
 * Contempla el código de cuenta, parcial, debe y haber.
 */
public class DetallePartida {

    private int idDetalle;
    private int idPartida;
    private int idCuenta;
    private String codigoCuenta;
    private String nombreCuenta;
    private int linea;
    private String concepto;
    private double parcial;
    private double debe;
    private double haber;

    public DetallePartida() {
    }

    public DetallePartida(int idCuenta, String codigoCuenta, String nombreCuenta, 
                          int linea, String concepto, double parcial, double debe, double haber) {
        this.idCuenta = idCuenta;
        this.codigoCuenta = codigoCuenta;
        this.nombreCuenta = nombreCuenta;
        this.linea = linea;
        this.concepto = concepto;
        this.parcial = parcial;
        this.debe = debe;
        this.haber = haber;
    }

    public int getIdDetalle() {
        return idDetalle;
    }

    public void setIdDetalle(int idDetalle) {
        this.idDetalle = idDetalle;
    }

    public int getIdPartida() {
        return idPartida;
    }

    public void setIdPartida(int idPartida) {
        this.idPartida = idPartida;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCodigoCuenta() {
        return codigoCuenta;
    }

    public void setCodigoCuenta(String codigoCuenta) {
        this.codigoCuenta = codigoCuenta;
    }

    public String getNombreCuenta() {
        return nombreCuenta;
    }

    public void setNombreCuenta(String nombreCuenta) {
        this.nombreCuenta = nombreCuenta;
    }

    public int getLinea() {
        return linea;
    }

    public void setLinea(int linea) {
        this.linea = linea;
    }

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public double getParcial() {
        return parcial;
    }

    public void setParcial(double parcial) {
        this.parcial = parcial;
    }

    public double getDebe() {
        return debe;
    }

    public void setDebe(double debe) {
        this.debe = debe;
    }

    public double getHaber() {
        return haber;
    }

    public void setHaber(double haber) {
        this.haber = haber;
    }
}
