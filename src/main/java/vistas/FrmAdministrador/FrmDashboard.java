/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas.FrmAdministrador;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;

/**
 *
 * @author maril
 */
public class FrmDashboard extends javax.swing.JPanel {

    /**
     * Creates new form FrmDashboard
     */

    private Usuario usuario;
    private FrmMenuPrincipal menuPrincipal;
    
    public FrmDashboard(Usuario usuario, FrmMenuPrincipal menuPrincipal) {
  
        this.usuario = usuario;
        this.menuPrincipal = menuPrincipal;

        initComponents();
        initializeComponents();
    }

    private void initializeComponents() {

        this.setLayout(new BorderLayout());
        this.setBackground(new Color(241, 245, 249));

        // ==========================================
        // ENCABEZADO
        // ==========================================
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(30, 41, 59));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(18, 25, 18, 25));

        JLabel lblTitulo = new JLabel("DASHBOARD");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Bienvenido al sistema contable");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(203, 213, 225));

        JPanel pnlTextos = new JPanel();
        pnlTextos.setLayout(new javax.swing.BoxLayout(
                pnlTextos,
                javax.swing.BoxLayout.Y_AXIS
        ));
        pnlTextos.setOpaque(false);

        pnlTextos.add(lblTitulo);
        pnlTextos.add(javax.swing.Box.createVerticalStrut(5));
        pnlTextos.add(lblSubtitulo);

        pnlHeader.add(pnlTextos, BorderLayout.WEST);

        // ==========================================
        // PANEL PRINCIPAL
        // ==========================================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 20));
        pnlMain.setBackground(new Color(241, 245, 249));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        // ==========================================
        // TARJETAS
        // ==========================================
        JPanel pnlTarjetas = new JPanel(new GridBagLayout());
        pnlTarjetas.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1;
        gbc.insets = new Insets(0, 8, 0, 8);

        JPanel pnlProductos = crearTarjeta(
                "PRODUCTOS",
                "25",
                "Productos registrados"
        );

        JPanel pnlEmpresas = crearTarjeta(
                "EMPRESAS",
                "3",
                "Empresas registradas"
        );

        JPanel pnlPeriodos = crearTarjeta(
                "PERIODOS",
                "2",
                "Periodos contables"
        );

        JPanel pnlUsuarios = crearTarjeta(
                "USUARIOS",
                "8",
                "Usuarios registrados"
        );

        gbc.gridx = 0;
        pnlTarjetas.add(pnlProductos, gbc);

        gbc.gridx = 1;
        pnlTarjetas.add(pnlEmpresas, gbc);

        gbc.gridx = 2;
        pnlTarjetas.add(pnlPeriodos, gbc);

        gbc.gridx = 3;
        pnlTarjetas.add(pnlUsuarios, gbc);

        // ==========================================
        // RESUMEN
        // ==========================================
        JPanel pnlResumen = new JPanel(new GridBagLayout());
        pnlResumen.setBackground(Color.WHITE);
        pnlResumen.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240)),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        GridBagConstraints gr = new GridBagConstraints();
        gr.gridx = 0;
        gr.anchor = GridBagConstraints.WEST;
        gr.fill = GridBagConstraints.HORIZONTAL;
        gr.weightx = 1;
        gr.insets = new Insets(5, 0, 5, 0);

        JLabel lblResumen = new JLabel("RESUMEN DEL SISTEMA");
        lblResumen.setFont(new Font("Segoe UI", Font.BOLD, 18));
        lblResumen.setForeground(new Color(30, 41, 59));

        gr.gridy = 0;
        pnlResumen.add(lblResumen, gr);

        JLabel lblLinea = new JLabel(
                "Periodo actual: 2026"
        );
        lblLinea.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblLinea.setForeground(new Color(71, 85, 105));

        gr.gridy = 1;
        pnlResumen.add(lblLinea, gr);

        JLabel lblEstado = new JLabel(
                "Estado del periodo: ABIERTO"
        );
        lblEstado.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEstado.setForeground(new Color(71, 85, 105));

        gr.gridy = 2;
        pnlResumen.add(lblEstado, gr);

        JLabel lblEmpresa = new JLabel(
                "Empresa activa: Colegio Gamaliel"
        );
        lblEmpresa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblEmpresa.setForeground(new Color(71, 85, 105));

        gr.gridy = 3;
        pnlResumen.add(lblEmpresa, gr);

        // ==========================================
        // AGREGAR AL PANEL PRINCIPAL
        // ==========================================
        pnlMain.add(pnlTarjetas, BorderLayout.NORTH);
        pnlMain.add(pnlResumen, BorderLayout.CENTER);

        // ==========================================
        // AGREGAR AL DASHBOARD
        // ==========================================
        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
    }

    private JPanel crearTarjeta(
            String titulo,
            String cantidad,
            String descripcion) {

        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setPreferredSize(new Dimension(220, 130));

        panel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(
                        15, 18, 15, 18
                )
        ));

        JLabel lblTitulo = new JLabel(titulo);
        lblTitulo.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                13
        ));
        lblTitulo.setForeground(
                new Color(71, 85, 105)
        );

        JLabel lblCantidad = new JLabel(cantidad);
        lblCantidad.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                30
        ));
        lblCantidad.setForeground(
                new Color(30, 41, 59)
        );

        JLabel lblDescripcion = new JLabel(descripcion);
        lblDescripcion.setFont(new Font(
                "Segoe UI",
                Font.PLAIN,
                12
        ));
        lblDescripcion.setForeground(
                new Color(100, 116, 139)
        );

        JPanel pnlCentro = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                0,
                0
        ));
        pnlCentro.setOpaque(false);
        pnlCentro.add(lblCantidad);

        JPanel pnlInferior = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                0,
                0
        ));
        pnlInferior.setOpaque(false);
        pnlInferior.add(lblDescripcion);

        panel.add(lblTitulo, BorderLayout.NORTH);
        panel.add(pnlCentro, BorderLayout.CENTER);
        panel.add(pnlInferior, BorderLayout.SOUTH);

        return panel;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        pnlTarjetas = new javax.swing.JPanel();
        pnlProductos = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        pnlProductos1 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        pnlProductos2 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        pnlProductos3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        pnlResumen = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("DASHBOARD");

        jLabel2.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jLabel2.setText("Bienvenido al sistema contable");

        pnlTarjetas.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pnlProductos.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel3.setText("PRODUCTOS  ");

        jLabel4.setText("25     ");

        jLabel5.setText("registrados");

        javax.swing.GroupLayout pnlProductosLayout = new javax.swing.GroupLayout(pnlProductos);
        pnlProductos.setLayout(pnlProductosLayout);
        pnlProductosLayout.setHorizontalGroup(
            pnlProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductosLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addGap(72, 72, 72))
            .addGroup(pnlProductosLayout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addGroup(pnlProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel5)
                    .addComponent(jLabel3))
                .addContainerGap(51, Short.MAX_VALUE))
        );
        pnlProductosLayout.setVerticalGroup(
            pnlProductosLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductosLayout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlProductos1.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel6.setText("EMPRESAS   ");

        jLabel7.setText("3     ");

        jLabel8.setText("registrados");

        javax.swing.GroupLayout pnlProductos1Layout = new javax.swing.GroupLayout(pnlProductos1);
        pnlProductos1.setLayout(pnlProductos1Layout);
        pnlProductos1Layout.setHorizontalGroup(
            pnlProductos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos1Layout.createSequentialGroup()
                .addContainerGap(60, Short.MAX_VALUE)
                .addGroup(pnlProductos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8)
                    .addGroup(pnlProductos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos1Layout.createSequentialGroup()
                            .addComponent(jLabel7)
                            .addGap(72, 72, 72))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos1Layout.createSequentialGroup()
                            .addComponent(jLabel6)
                            .addGap(53, 53, 53)))))
        );
        pnlProductos1Layout.setVerticalGroup(
            pnlProductos1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductos1Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel8)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pnlProductos2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel9.setText("PERIODOS   ");

        jLabel10.setText("2     ");

        jLabel11.setText("contables");

        javax.swing.GroupLayout pnlProductos2Layout = new javax.swing.GroupLayout(pnlProductos2);
        pnlProductos2.setLayout(pnlProductos2Layout);
        pnlProductos2Layout.setHorizontalGroup(
            pnlProductos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos2Layout.createSequentialGroup()
                .addContainerGap(65, Short.MAX_VALUE)
                .addGroup(pnlProductos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel11)
                    .addGroup(pnlProductos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos2Layout.createSequentialGroup()
                            .addComponent(jLabel10)
                            .addGap(72, 72, 72))
                        .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos2Layout.createSequentialGroup()
                            .addComponent(jLabel9)
                            .addGap(48, 48, 48)))))
        );
        pnlProductos2Layout.setVerticalGroup(
            pnlProductos2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductos2Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel11)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        pnlProductos3.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel12.setText("USUARIOS");

        jLabel13.setText("8");

        jLabel14.setText("activos");

        javax.swing.GroupLayout pnlProductos3Layout = new javax.swing.GroupLayout(pnlProductos3);
        pnlProductos3.setLayout(pnlProductos3Layout);
        pnlProductos3Layout.setHorizontalGroup(
            pnlProductos3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos3Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel12)
                .addGap(56, 56, 56))
            .addGroup(pnlProductos3Layout.createSequentialGroup()
                .addGap(77, 77, 77)
                .addGroup(pnlProductos3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlProductos3Layout.createSequentialGroup()
                        .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(12, 12, 12)))
                .addContainerGap(66, Short.MAX_VALUE))
        );
        pnlProductos3Layout.setVerticalGroup(
            pnlProductos3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlProductos3Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addComponent(jLabel12)
                .addGap(18, 18, 18)
                .addComponent(jLabel13)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel14)
                .addContainerGap(22, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout pnlTarjetasLayout = new javax.swing.GroupLayout(pnlTarjetas);
        pnlTarjetas.setLayout(pnlTarjetasLayout);
        pnlTarjetasLayout.setHorizontalGroup(
            pnlTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjetasLayout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(pnlProductos, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addComponent(pnlProductos1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(41, 41, 41)
                .addComponent(pnlProductos2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 40, Short.MAX_VALUE)
                .addComponent(pnlProductos3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        pnlTarjetasLayout.setVerticalGroup(
            pnlTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlTarjetasLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(pnlTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(pnlProductos3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(pnlTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                        .addComponent(pnlProductos2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlProductos1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(pnlProductos, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addContainerGap(41, Short.MAX_VALUE))
        );

        pnlResumen.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setText("RESUMEN DEL SISTEMA");

        jLabel16.setText("Periodo actual: 2026");

        jLabel17.setText("Estado del periodo: ABIERTO");

        jLabel18.setText("Empresa activa: Vaquita S.A de C.V");

        javax.swing.GroupLayout pnlResumenLayout = new javax.swing.GroupLayout(pnlResumen);
        pnlResumen.setLayout(pnlResumenLayout);
        pnlResumenLayout.setHorizontalGroup(
            pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pnlResumenLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel15)
                .addGap(354, 354, 354))
            .addGroup(pnlResumenLayout.createSequentialGroup()
                .addGap(152, 152, 152)
                .addGroup(pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel18)
                    .addComponent(jLabel17)
                    .addComponent(jLabel16))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pnlResumenLayout.setVerticalGroup(
            pnlResumenLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pnlResumenLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel15)
                .addGap(18, 18, 18)
                .addComponent(jLabel16)
                .addGap(18, 18, 18)
                .addComponent(jLabel17)
                .addGap(18, 18, 18)
                .addComponent(jLabel18)
                .addContainerGap(54, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(377, 377, 377)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(338, 338, 338)
                        .addComponent(jLabel2))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(37, 37, 37)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(pnlTarjetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pnlResumen, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel2)
                .addGap(24, 24, 24)
                .addComponent(pnlTarjetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(27, 27, 27)
                .addComponent(pnlResumen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(26, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel pnlProductos;
    private javax.swing.JPanel pnlProductos1;
    private javax.swing.JPanel pnlProductos2;
    private javax.swing.JPanel pnlProductos3;
    private javax.swing.JPanel pnlResumen;
    private javax.swing.JPanel pnlTarjetas;
    // End of variables declaration//GEN-END:variables
}
