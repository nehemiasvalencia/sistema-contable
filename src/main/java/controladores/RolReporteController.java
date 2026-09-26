/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladores;

import dao.RolReporteDAO;
import java.util.List;
import modelos.RolReporte;

/**
 *
 * @author Nehemias Valencia
 */
public class RolReporteController {

    private RolReporteDAO rolReporteDAO;

    public RolReporteController() {
        rolReporteDAO = new RolReporteDAO();
    }

    public List<RolReporte> listarRolesReporte() {
        return rolReporteDAO.listarActivos();
    }
}
