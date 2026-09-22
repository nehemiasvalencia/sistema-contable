/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelos;

import java.sql.Timestamp;

/**
 *
 * @author Nehemias Valencia
 */
public class Usuario {

    private int idUsuario;
    private int idRol;
    private String nombre;
    private String usuario;
    private String passwordHash;
    private boolean estado;
    private Timestamp creadoEn;
    private Timestamp ultimaConexion;
    private String nombreRol;

    public Usuario() {
    }

    public Usuario(int idUsuario, int idRol, String nombre,
            String usuario, String passwordHash,
            boolean estado, Timestamp creadoEn,
            Timestamp ultimaConexion) {
        this.idUsuario = idUsuario;
        this.idRol = idRol;
        this.nombre = nombre;
        this.usuario = usuario;
        this.passwordHash = passwordHash;
        this.estado = estado;
        this.creadoEn = creadoEn;
        this.ultimaConexion = ultimaConexion;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPasswordHash() {
        return passwordHash;
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public boolean isEstado() {
        return estado;
    }

    public void setEstado(boolean estado) {
        this.estado = estado;
    }

    public Timestamp getCreadoEn() {
        return creadoEn;
    }

    public void setCreadoEn(Timestamp creadoEn) {
        this.creadoEn = creadoEn;
    }

    public Timestamp getUltimaConexion() {
        return ultimaConexion;
    }

    public void setUltimaConexion(Timestamp ultimaConexion) {
        this.ultimaConexion = ultimaConexion;
    }

    public String getNombreRol() {
        return nombreRol;
    }

    public void setNombreRol(String nombreRol) {
        this.nombreRol = nombreRol;
    }
}
