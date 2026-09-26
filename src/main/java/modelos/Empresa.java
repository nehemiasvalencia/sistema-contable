package modelos;

/**
 *
 * @author Nehemias Valencia
 */
public class Empresa {

    private int idEmpresa;
    private int idPais;
    private String nombre;
    private String nit;
    private String nrc;
    private String direccion;
    private String telefono;
    private String correo;
    private String actividadEconomica;
    private boolean estado;

    public Empresa() {
    }

    public Empresa(int idEmpresa, int idPais, String nombre,
            String nit, String nrc, String direccion,
            String telefono, String correo,
            String actividadEconomica, boolean estado) {

        this.idEmpresa = idEmpresa;
        this.idPais = idPais;
        this.nombre = nombre;
        this.nit = nit;
        this.nrc = nrc;
        this.direccion = direccion;
        this.telefono = telefono;
        this.correo = correo;
        this.actividadEconomica = actividadEconomica;
        this.estado = estado;
    }

    public int getIdEmpresa() {
        return idEmpresa;
    }

    public void setIdEmpresa(int idEmpresa) {
        this.idEmpresa = idEmpresa;
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

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getNrc() {
        return nrc;
    }

    public void setNrc(String nrc) {
        this.nrc = nrc;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getActividadEconomica() {
        return actividadEconomica;
    }

    public void setActividadEconomica(String actividadEconomica) {
        this.actividadEconomica = actividadEconomica;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    @Override
    public String toString() {
        return nombre;
    }
}
