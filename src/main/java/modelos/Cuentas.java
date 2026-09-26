/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Nehemias Valencia
 */
public class Cuentas {

    private int idCuenta;
    private Integer idCuentaPadre;
    private String codigo;
    private String nombre;
    private String tipo;
    private String clasificacion;
    private String naturaleza;
    private Integer idRolReporte;
    private byte nivel;
    private boolean permiteMovimientos;
    private boolean estado;

    public Cuentas() {
    }

    public Cuentas(int idCuenta, Integer idCuentaPadre, String codigo,
            String nombre, String tipo, String clasificacion,
            String naturaleza, Integer idRolReporte, byte nivel,
            boolean permiteMovimientos, boolean estado) {

        this.idCuenta = idCuenta;
        this.idCuentaPadre = idCuentaPadre;
        this.codigo = codigo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.clasificacion = clasificacion;
        this.naturaleza = naturaleza;
        this.idRolReporte = idRolReporte;
        this.nivel = nivel;
        this.permiteMovimientos = permiteMovimientos;
        this.estado = estado;
    }

    public int getIdCuenta() {
        return idCuenta;
    }

    public void setIdCuenta(int idCuenta) {
        this.idCuenta = idCuenta;
    }

    public Integer getIdCuentaPadre() {
        return idCuentaPadre;
    }

    public void setIdCuentaPadre(Integer idCuentaPadre) {
        this.idCuentaPadre = idCuentaPadre;
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

    public Integer getIdRolReporte() {
        return idRolReporte;
    }

    public void setIdRolReporte(Integer idRolReporte) {
        this.idRolReporte = idRolReporte;
    }

    public byte getNivel() {
        return nivel;
    }

    public void setNivel(byte nivel) {
        this.nivel = nivel;
    }

    public boolean isPermiteMovimientos() {
        return permiteMovimientos;
    }

    public void setPermiteMovimientos(boolean permiteMovimientos) {
        this.permiteMovimientos = permiteMovimientos;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }
}
