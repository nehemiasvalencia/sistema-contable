/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

/**
 *
 * @author Nehemias Valencia
 */
public class RolReporte {

    private int idRolReporte;
    private int codigo;
    private String nombre;
    private String descripcion;
    private boolean estado;

    public RolReporte() {
    }

    public RolReporte(int idRolReporte, int codigo, String nombre,
            String descripcion, boolean estado) {
        this.idRolReporte = idRolReporte;
        this.codigo = codigo;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.estado = estado;
    }

    public int getIdRolReporte() {
        return idRolReporte;
    }

    public void setIdRolReporte(int idRolReporte) {
        this.idRolReporte = idRolReporte;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
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
