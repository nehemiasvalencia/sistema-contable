package modelos;

/**
 *
 * @author Nehemias Valencia
 */
public class Pais {

    private int idPais;
    private String nombre;
    private String codigoIso;
    private String moneda;

    public Pais() {
    }

    public Pais(int idPais, String nombre, String codigoIso, String moneda) {
        this.idPais = idPais;
        this.nombre = nombre;
        this.codigoIso = codigoIso;
        this.moneda = moneda;
    }

    public int getIdPais() {
        return idPais;
    }

    public void setIdPais(int idPais) {
        this.idPais = idPais;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCodigoIso() {
        return codigoIso;
    }

    public void setCodigoIso(String codigoIso) {
        this.codigoIso = codigoIso;
    }

    public String getMoneda() {
        return moneda;
    }

    public void setMoneda(String moneda) {
        this.moneda = moneda;
    }

    @Override
    public String toString() {
        return nombre;
    }
}