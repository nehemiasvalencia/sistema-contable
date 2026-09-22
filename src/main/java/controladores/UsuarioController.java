/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;
import dao.usuarioDAO;
import java.util.List;
import modelos.Usuario;

/**
 *
 * @author Nehemias Valencia
 */


public class UsuarioController {

    private final usuarioDAO usuarioDAO;

    public UsuarioController() {
        usuarioDAO = new usuarioDAO();
    }

    public Usuario iniciarSesion(String usuario, String password) {
        return usuarioDAO.iniciarSesion(usuario, password);
    }

    public List<Usuario> listarUsuarios() {
        return usuarioDAO.listarUsuarios();
    }

    public boolean guardarUsuario(Usuario usuario, String password) {
        return usuarioDAO.insertarUsuario(usuario, password);
    }

    public boolean actualizarUsuario(Usuario usuario) {
        return usuarioDAO.actualizarUsuario(usuario);
    }

    public boolean actualizarPassword(int idUsuario, String nuevaPassword) {
        return usuarioDAO.actualizarPassword(idUsuario, nuevaPassword);
    }

    
}