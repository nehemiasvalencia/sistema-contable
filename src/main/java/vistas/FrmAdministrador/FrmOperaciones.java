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
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.table.DefaultTableModel;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;

/**
 *
 * @author maril
 */
public class FrmOperaciones extends javax.swing.JPanel {

    /**
     * Creates new form FrmOperaciones
     */
    private Usuario usuario;
    private FrmMenuPrincipal menuPrincipal;

    public FrmOperaciones(Usuario usuario, FrmMenuPrincipal menuPrincipal) {

        this.usuario = usuario;
        this.menuPrincipal = menuPrincipal;

        initComponents();
        initializeComponents();
    }

    private void initializeComponents() {

        // =========================================================
        // CONFIGURACIÓN PRINCIPAL
        // =========================================================
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(241, 245, 249));

        // =========================================================
        // PANEL ENCABEZADO
        // =========================================================
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(30, 41, 59));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        JLabel lblTitulo = new JLabel("OPERACIONES");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 24));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Gestión de operaciones contables");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        lblSubtitulo.setForeground(new Color(203, 213, 225));

        JPanel pnlTitulos = new JPanel();
        pnlTitulos.setLayout(new javax.swing.BoxLayout(
                pnlTitulos,
                javax.swing.BoxLayout.Y_AXIS
        ));
        pnlTitulos.setOpaque(false);

        pnlTitulos.add(lblTitulo);
        pnlTitulos.add(javax.swing.Box.createVerticalStrut(5));
        pnlTitulos.add(lblSubtitulo);

        pnlHeader.add(pnlTitulos, BorderLayout.WEST);

        // =========================================================
        // PANEL PRINCIPAL
        // =========================================================
        JPanel pnlMain = new JPanel(new BorderLayout(0, 15));
        pnlMain.setBackground(new Color(241, 245, 249));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));

        // =========================================================
        // PANEL DE DATOS
        // =========================================================
        JPanel pnlDatos = new JPanel(new GridBagLayout());
        pnlDatos.setBackground(Color.WHITE);
        pnlDatos.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // FECHA
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0;

        pnlDatos.add(lblFecha, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;

        dateFecha.setPreferredSize(new Dimension(200, 30));
        pnlDatos.add(dateFecha, gbc);

        // TIPO
        gbc.gridx = 2;
        gbc.weightx = 0;

        pnlDatos.add(lblTipo, gbc);

        gbc.gridx = 3;
        gbc.weightx = 0.5;

        cmbTipo.setPreferredSize(new Dimension(220, 30));
        pnlDatos.add(cmbTipo, gbc);

        // CONCEPTO
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0;

        pnlDatos.add(lblConcepto, gbc);

        gbc.gridx = 1;
        gbc.gridwidth = 3;
        gbc.weightx = 1;

        txtConcepto.setPreferredSize(new Dimension(200, 30));
        pnlDatos.add(txtConcepto, gbc);

        gbc.gridwidth = 1;

        // ESTADO
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0;

        pnlDatos.add(lblEstado, gbc);

        gbc.gridx = 1;
        gbc.weightx = 0.5;

        cmbEstado.setPreferredSize(new Dimension(200, 30));
        pnlDatos.add(cmbEstado, gbc);

        // =========================================================
        // PANEL DE BOTONES
        // =========================================================
        JPanel pnlBotones = new JPanel(new FlowLayout(
                FlowLayout.LEFT,
                10,
                10
        ));

        pnlBotones.setBackground(Color.WHITE);

        estilizarBoton(btnNuevo, "nuevo");
        estilizarBoton(btnGuardar, "guardar");
        estilizarBoton(btnModificar, "editar");
        estilizarBoton(btnAnular, "eliminar");

        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnAnular);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.weightx = 1;

        pnlDatos.add(pnlBotones, gbc);

        // =========================================================
        // PANEL TABLA
        // =========================================================
        JPanel pnlTabla = new JPanel(new BorderLayout());
        pnlTabla.setBackground(Color.WHITE);
        pnlTabla.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                ),
                BorderFactory.createEmptyBorder(15, 15, 15, 15)
        ));

        JLabel lblTabla = new JLabel("OPERACIONES REGISTRADAS");
        lblTabla.setFont(new Font("Segoe UI", Font.BOLD, 16));
        lblTabla.setForeground(new Color(30, 41, 59));
        lblTabla.setBorder(
                BorderFactory.createEmptyBorder(0, 0, 10, 0)
        );

        pnlTabla.add(lblTabla, BorderLayout.NORTH);

        // =========================================================
        // MODELO DE TABLA
        // =========================================================
        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{
                    {
                        "26/09/2026",
                        "COMPRA",
                        "Compra de mercadería",
                        "BORRADOR"
                    },
                    {
                        "25/09/2026",
                        "VENTA",
                        "Venta de productos",
                        "PROCESADA"
                    },
                    {
                        "24/09/2026",
                        "GASTO",
                        "Pago de servicio",
                        "PROCESADA"
                    }
                },
                new String[]{
                    "Fecha",
                    "Tipo",
                    "Concepto",
                    "Estado"
                }
        ) {

            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tblOperaciones.setModel(modelo);

        tblOperaciones.setRowHeight(30);
        tblOperaciones.setFont(new Font("Segoe UI", Font.PLAIN, 13));

        tblOperaciones.getTableHeader().setFont(
                new Font("Segoe UI", Font.BOLD, 13)
        );

        tblOperaciones.getTableHeader().setBackground(
                new Color(30, 41, 59)
        );

        tblOperaciones.getTableHeader().setForeground(Color.WHITE);

        tblOperaciones.setSelectionBackground(
                new Color(226, 232, 240)
        );

        tblOperaciones.setSelectionForeground(
                new Color(15, 23, 42)
        );

        tblOperaciones.setGridColor(
                new Color(226, 232, 240)
        );

        tblOperaciones.setShowVerticalLines(false);

        // Ancho de columnas
        tblOperaciones.getColumnModel()
                .getColumn(0).setPreferredWidth(100);

        tblOperaciones.getColumnModel()
                .getColumn(1).setPreferredWidth(150);

        tblOperaciones.getColumnModel()
                .getColumn(2).setPreferredWidth(350);

        tblOperaciones.getColumnModel()
                .getColumn(3).setPreferredWidth(150);

        JScrollPane scrollTabla = new JScrollPane(tblOperaciones);
        scrollTabla.setBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                )
        );

        pnlTabla.add(scrollTabla, BorderLayout.CENTER);

        // =========================================================
        // AGREGAR PANELES
        // =========================================================
        pnlMain.add(pnlDatos, BorderLayout.NORTH);
        pnlMain.add(pnlTabla, BorderLayout.CENTER);

        this.add(pnlHeader, BorderLayout.NORTH);
        this.add(pnlMain, BorderLayout.CENTER);
    }

    // =============================================================
    // ESTILO DE BOTONES
    // =============================================================
    private void estilizarBoton(JButton boton, String tipo) {

        boton.setFont(new Font(
                "Segoe UI",
                Font.BOLD,
                12
        ));

        boton.setFocusPainted(false);
        boton.setBorderPainted(false);
        boton.setOpaque(true);

        boton.setPreferredSize(
                new Dimension(120, 35)
        );

        if (tipo.equals("nuevo")) {

            boton.setBackground(
                    new Color(71, 85, 105)
            );

            boton.setForeground(Color.WHITE);

        } else if (tipo.equals("guardar")) {

            boton.setBackground(
                    new Color(34, 197, 94)
            );

            boton.setForeground(Color.WHITE);

        } else if (tipo.equals("editar")) {

            boton.setBackground(
                    new Color(59, 130, 246)
            );

            boton.setForeground(Color.WHITE);

        } else if (tipo.equals("eliminar")) {

            boton.setBackground(
                    new Color(220, 38, 38)
            );

            boton.setForeground(Color.WHITE);
        }
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
        lblFecha = new javax.swing.JLabel();
        dateFecha = new com.toedter.calendar.JDateChooser();
        lblTipo = new javax.swing.JLabel();
        cmbTipo = new javax.swing.JComboBox<>();
        lblConcepto = new javax.swing.JLabel();
        txtConcepto = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        cmbEstado = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnAnular = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblOperaciones = new javax.swing.JTable();

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 14)); // NOI18N
        jLabel1.setText("OPERACIONES  ");

        lblFecha.setText("Fecha:");

        lblTipo.setText("Tipo:");

        cmbTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "COMPRA", "VENTA", "DEVOLUCION_COMPRA", "DEVOLUCION_VENTA", "PAGO", "COBRO", "AJUSTE_INVENTARIO", "APORTE_CAPITAL", "PRESTAMO", "GASTO", "OTRA" }));

        lblConcepto.setText("Concepto:");

        lblEstado.setText("Estado:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "BORRADOR", "PROCESADA", "ANULADA" }));

        btnNuevo.setText("NUEVO");

        btnGuardar.setText("GUARDAR ");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnModificar.setText("MODIFICAR");

        btnAnular.setText("ANULAR ");
        btnAnular.addActionListener(this::btnAnularActionPerformed);

        tblOperaciones.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblOperaciones);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(64, 64, 64)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblFecha)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblConcepto)
                                    .addComponent(lblEstado))
                                .addGap(18, 18, 18)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 132, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGap(22, 22, 22)
                                        .addComponent(btnGuardar)
                                        .addGap(26, 26, 26)
                                        .addComponent(btnModificar)
                                        .addGap(18, 18, 18)
                                        .addComponent(btnAnular))
                                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                        .addGroup(layout.createSequentialGroup()
                                            .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                            .addComponent(lblTipo)
                                            .addGap(10, 10, 10)
                                            .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, 351, javax.swing.GroupLayout.PREFERRED_SIZE))))
                            .addComponent(btnNuevo)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(229, 229, 229)
                        .addComponent(jLabel1)))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jLabel1)
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(dateFecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lblTipo)
                        .addComponent(cmbTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(15, 15, 15)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblConcepto)
                    .addComponent(txtConcepto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(56, 56, 56)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnular, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 28, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 223, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnAnularActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnularActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnularActionPerformed


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAnular;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<String> cmbTipo;
    private com.toedter.calendar.JDateChooser dateFecha;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblConcepto;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblTipo;
    private javax.swing.JTable tblOperaciones;
    private javax.swing.JTextField txtConcepto;
    // End of variables declaration//GEN-END:variables
}
