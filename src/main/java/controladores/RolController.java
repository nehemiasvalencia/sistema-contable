/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.rolDAO;
import java.util.List;
import modelos.Rol;

/**
 *
 * @author Nehemias Valencia
 */
public class RolController {

    private final rolDAO rolDAO;

    public RolController() {
        rolDAO = new rolDAO();
    }

    public List<Rol> listarRoles() {
        return rolDAO.listarRolesActivos();
    }
}
