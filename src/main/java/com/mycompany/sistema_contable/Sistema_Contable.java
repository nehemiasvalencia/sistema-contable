package com.mycompany.sistema_contable;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;
import vistas.FrmLogin;

/**
 * Punto de entrada principal para el Sistema Contable.
 * Universidad Católica de El Salvador (UNICAES).
 */
public class Sistema_Contable {

    public static void main(String[] args) {
        // Configurar apariencia visual moderna
        try {
            for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (Exception ignored) {
            // Mantener Look and Feel por defecto del sistema operativo
        }

        // Iniciar interfaz gráfica de Login en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            FrmLogin frmLogin = new FrmLogin();
            frmLogin.setLocationRelativeTo(null);
            frmLogin.setVisible(true);
        });
    }
}
