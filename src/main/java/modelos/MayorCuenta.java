package modelos;

/**
 * Modelo para la mayorización consolidada de una cuenta (Libro Mayor / Cuenta T).
 */
public class MayorCuenta {

    private int idCuenta;
    private String codigo;
    private String nombre;
    private String tipo;
    private String clasificacion;
    private String naturaleza;
    private double totalDebe;
    private double totalHaber;
    private double saldoDeudor;
    private double saldoAcreedor;

    public MayorCuenta() {
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getClasificacion() {
        return clasificacion;
    }

    public void setClasificacion(String clasificacion) {
        this.clasificacion = clasificacion;
    }

    public String getNaturaleza() {
        return naturaleza;
    }

    public void setNaturaleza(String naturaleza) {
        this.naturaleza = naturaleza;
    }

    public double getTotalDebe() {
        return totalDebe;
    }

    public void setTotalDebe(double totalDebe) {
        this.totalDebe = totalDebe;
        calcularSaldos();
    }

    public double getTotalHaber() {
        return totalHaber;
    }

    public void setTotalHaber(double totalHaber) {
        this.totalHaber = totalHaber;
        calcularSaldos();
    }

    public double getSaldoDeudor() {
        return saldoDeudor;
    }

    public double getSaldoAcreedor() {
        return saldoAcreedor;
    }

    public void calcularSaldos() {
        double dif = this.totalDebe - this.totalHaber;
        if (dif >= 0) {
            this.saldoDeudor = Math.round(dif * 100.0) / 100.0;
            this.saldoAcreedor = 0.0;
        } else {
            this.saldoDeudor = 0.0;
            this.saldoAcreedor = Math.round(Math.abs(dif) * 100.0) / 100.0;
        }
    }

    public double getSaldoSegunNaturaleza() {
        if ("DEUDORA".equalsIgnoreCase(this.naturaleza)) {
            return this.totalDebe - this.totalHaber;
        } else {
            return this.totalHaber - this.totalDebe;
        }
    }
}
