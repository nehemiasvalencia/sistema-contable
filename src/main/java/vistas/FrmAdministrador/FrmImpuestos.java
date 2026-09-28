/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas.FrmAdministrador;

import controladores.ImpuestoController;
import java.math.BigDecimal;
import modelos.Impuesto;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;

/**
 *
 * @author maril
 */
public class FrmImpuestos extends javax.swing.JPanel {

    private ImpuestoController controlador;

    private int idImpuestoSeleccionado = 0;

    /**
     * Creates new form FrmImpuestos
     */
    public FrmImpuestos(Usuario usuario, FrmMenuPrincipal menuPrincipal) {
        initComponents();
        initializeComponents();

        try {

            controlador = new ImpuestoController();

        } catch (SQLException ex) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al conectar con la base de datos:\n"
                    + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        cargarTabla();
        limpiarCampos();
    }

    private void initializeComponents() {

        // =========================================================
        // PANEL PRINCIPAL
        // =========================================================
        this.removeAll();
        this.setLayout(new java.awt.BorderLayout(0, 15));
        this.setBackground(new java.awt.Color(241, 245, 249));

        // =========================================================
        // ENCABEZADO
        // =========================================================
        javax.swing.JPanel pnlHeader = new javax.swing.JPanel(
                new java.awt.BorderLayout()
        );

        pnlHeader.setBackground(new java.awt.Color(15, 23, 42));
        pnlHeader.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        15, 20, 15, 20
                )
        );

        jLabel1.setText("IMPUESTOS");
        jLabel1.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        20
                )
        );
        jLabel1.setForeground(java.awt.Color.WHITE);

        pnlHeader.add(
                jLabel1,
                java.awt.BorderLayout.WEST
        );

        // =========================================================
        // PANEL CENTRAL
        // =========================================================
        javax.swing.JPanel pnlMain = new javax.swing.JPanel(
                new java.awt.BorderLayout(0, 15)
        );

        pnlMain.setBackground(
                new java.awt.Color(241, 245, 249)
        );

        pnlMain.setBorder(
                javax.swing.BorderFactory.createEmptyBorder(
                        0, 15, 15, 15
                )
        );

        // =========================================================
        // TARJETA DE DATOS
        // =========================================================
        javax.swing.JPanel pnlDatos = new javax.swing.JPanel(
                new java.awt.GridBagLayout()
        );

        pnlDatos.setBackground(java.awt.Color.WHITE);

        pnlDatos.setBorder(
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(226, 232, 240)
                        ),
                        javax.swing.BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        // =========================================================
        // ESTILO DE LABELS
        // =========================================================
        javax.swing.JLabel[] labels = {
            lblNombre,
            lblCodigo,
            lblEstado,
            jLabel2,
            lblFechaInicio,
            lblFechaFin,
            lblDescripcion
        };

        for (javax.swing.JLabel label : labels) {

            label.setFont(
                    new java.awt.Font(
                            "Segoe UI",
                            java.awt.Font.BOLD,
                            13
                    )
            );

            label.setForeground(
                    new java.awt.Color(71, 85, 105)
            );
        }

        lblNombre.setText("Nombre:");
        lblCodigo.setText("Código:");
        lblEstado.setText("Estado:");
        jLabel2.setText("Porcentaje:");
        lblFechaInicio.setText("Fecha de inicio:");
        lblFechaFin.setText("Fecha de finalización:");
        lblDescripcion.setText("Descripción:");

        // =========================================================
        // ESTILO DE CAMPOS
        // =========================================================
        javax.swing.JTextField[] campos = {
            txtNombre,
            txtCodigo,
            txtPorcentaje,
            txtDescripcion
        };

        for (javax.swing.JTextField campo : campos) {

            campo.setFont(
                    new java.awt.Font(
                            "Segoe UI",
                            java.awt.Font.PLAIN,
                            14
                    )
            );

            campo.setBackground(
                    new java.awt.Color(248, 250, 252)
            );

            campo.setBorder(
                    javax.swing.BorderFactory.createCompoundBorder(
                            javax.swing.BorderFactory.createLineBorder(
                                    new java.awt.Color(203, 213, 225)
                            ),
                            javax.swing.BorderFactory.createEmptyBorder(
                                    5, 10, 5, 10
                            )
                    )
            );

            campo.setPreferredSize(
                    new java.awt.Dimension(220, 38)
            );
        }

        // =========================================================
        // PORCENTAJE
        // =========================================================
        txtPorcentaje.setPreferredSize(
                new java.awt.Dimension(150, 38)
        );

        // =========================================================
        // COMBO ESTADO
        // =========================================================
        cmbEstado.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.PLAIN,
                        14
                )
        );

        cmbEstado.setBackground(
                new java.awt.Color(248, 250, 252)
        );

        cmbEstado.setPreferredSize(
                new java.awt.Dimension(220, 38)
        );

        // =========================================================
        // JDATECHOOSER
        // =========================================================
        dcFechaInicio.setPreferredSize(
                new java.awt.Dimension(220, 38)
        );

        dcFechaFin.setPreferredSize(
                new java.awt.Dimension(220, 38)
        );

        // =========================================================
        // GRIDBAG CONSTRAINTS
        // =========================================================
        java.awt.GridBagConstraints gbc
                = new java.awt.GridBagConstraints();

        gbc.insets = new java.awt.Insets(
                6, 8, 6, 8
        );

        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        gbc.anchor = java.awt.GridBagConstraints.WEST;

        // =========================================================
        // FILA 1 - NOMBRE
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        gbc.weightx = 1.0;

        pnlDatos.add(
                lblNombre,
                gbc
        );

        gbc.gridy = 1;

        pnlDatos.add(
                txtNombre,
                gbc
        );

        // =========================================================
        // FILA 2 - CÓDIGO / ESTADO
        // =========================================================
        gbc.gridwidth = 1;

        gbc.gridx = 0;
        gbc.gridy = 2;

        pnlDatos.add(
                lblCodigo,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                lblEstado,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 3;

        pnlDatos.add(
                txtCodigo,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                cmbEstado,
                gbc
        );

        // =========================================================
        // FILA 3 - PORCENTAJE / FECHA INICIO
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 4;

        pnlDatos.add(
                jLabel2,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                lblFechaInicio,
                gbc
        );

        gbc.gridx = 0;
        gbc.gridy = 5;

        pnlDatos.add(
                txtPorcentaje,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                dcFechaInicio,
                gbc
        );

        // =========================================================
        // FILA 4 - FECHA FIN
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 6;

        pnlDatos.add(
                lblFechaFin,
                gbc
        );

        gbc.gridy = 7;

        pnlDatos.add(
                dcFechaFin,
                gbc
        );

        // =========================================================
        // FILA 5 - DESCRIPCIÓN
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 8;
        gbc.gridwidth = 2;

        pnlDatos.add(
                lblDescripcion,
                gbc
        );

        gbc.gridy = 9;

        pnlDatos.add(
                txtDescripcion,
                gbc
        );

        // =========================================================
        // BOTONES
        // =========================================================
        javax.swing.JPanel pnlBotones
                = new javax.swing.JPanel(
                        new java.awt.FlowLayout(
                                java.awt.FlowLayout.RIGHT,
                                10,
                                5
                        )
                );

        pnlBotones.setBackground(
                java.awt.Color.WHITE
        );

        estilarBoton(btnNuevo, "nuevo");
        estilarBoton(btnGuardar, "guardar");
        estilarBoton(btnModificar, "editar");
        estilarBoton(btnLimpiar, "limpiar");

        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnModificar);
        pnlBotones.add(btnLimpiar);

        // =========================================================
        // AGREGAR BOTONES A LA TARJETA
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 10;
        gbc.gridwidth = 2;

        pnlDatos.add(
                pnlBotones,
                gbc
        );

        // =========================================================
        // TABLA
        // =========================================================
        tblImpuestos.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.PLAIN,
                        13
                )
        );

        tblImpuestos.setRowHeight(36);

        tblImpuestos.setSelectionBackground(
                new java.awt.Color(224, 242, 254)
        );

        tblImpuestos.setSelectionForeground(
                new java.awt.Color(15, 23, 42)
        );

        tblImpuestos.setShowVerticalLines(false);

        tblImpuestos.setGridColor(
                new java.awt.Color(226, 232, 240)
        );

        // =========================================================
        // ENCABEZADO DE TABLA
        // =========================================================
        javax.swing.table.JTableHeader header
                = tblImpuestos.getTableHeader();

        header.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        12
                )
        );

        header.setBackground(
                new java.awt.Color(241, 245, 249)
        );

        header.setForeground(
                new java.awt.Color(71, 85, 105)
        );

        header.setPreferredSize(
                new java.awt.Dimension(0, 38)
        );

        // =========================================================
        // SCROLL
        // =========================================================
        jScrollPane1.setBorder(
                javax.swing.BorderFactory.createLineBorder(
                        new java.awt.Color(226, 232, 240)
                )
        );

        jScrollPane1.getViewport().setBackground(
                java.awt.Color.WHITE
        );

        // =========================================================
        // PANEL TABLA
        // =========================================================
        javax.swing.JPanel pnlTabla
                = new javax.swing.JPanel(
                        new java.awt.BorderLayout()
                );

        pnlTabla.setBackground(
                java.awt.Color.WHITE
        );

        pnlTabla.setBorder(
                javax.swing.BorderFactory.createCompoundBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(226, 232, 240)
                        ),
                        javax.swing.BorderFactory.createEmptyBorder(
                                10, 10, 10, 10
                        )
                )
        );

        pnlTabla.add(
                jScrollPane1,
                java.awt.BorderLayout.CENTER
        );

        // =========================================================
        // AGREGAR TODO
        // =========================================================
        pnlMain.add(
                pnlDatos,
                java.awt.BorderLayout.NORTH
        );

        pnlMain.add(
                pnlTabla,
                java.awt.BorderLayout.CENTER
        );

        this.add(
                pnlHeader,
                java.awt.BorderLayout.NORTH
        );

        this.add(
                pnlMain,
                java.awt.BorderLayout.CENTER
        );

        this.revalidate();
        this.repaint();
    }

    private void estilarBoton(
            javax.swing.JButton boton,
            String tipo
    ) {

        boton.setFont(
                new java.awt.Font(
                        "Segoe UI",
                        java.awt.Font.BOLD,
                        13
                )
        );

        boton.setCursor(
                new java.awt.Cursor(
                        java.awt.Cursor.HAND_CURSOR
                )
        );

        boton.setPreferredSize(
                new java.awt.Dimension(110, 38)
        );

        boton.setFocusPainted(false);

        switch (tipo) {

            case "nuevo":
                boton.setBackground(
                        new java.awt.Color(241, 245, 249)
                );

                boton.setForeground(
                        new java.awt.Color(30, 41, 59)
                );

                boton.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(203, 213, 225)
                        )
                );
                break;

            case "guardar":
                boton.setBackground(
                        new java.awt.Color(37, 99, 235)
                );

                boton.setForeground(
                        java.awt.Color.WHITE
                );

                boton.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(29, 78, 216)
                        )
                );
                break;

            case "editar":
                boton.setBackground(
                        new java.awt.Color(217, 119, 6)
                );

                boton.setForeground(
                        java.awt.Color.WHITE
                );

                boton.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(180, 83, 9)
                        )
                );
                break;

            case "limpiar":
                boton.setBackground(
                        new java.awt.Color(100, 116, 139)
                );

                boton.setForeground(
                        java.awt.Color.WHITE
                );

                boton.setBorder(
                        javax.swing.BorderFactory.createLineBorder(
                                new java.awt.Color(71, 85, 105)
                        )
                );
                break;
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

        jButton1 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton5 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblCodigo = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lblEstado = new javax.swing.JLabel();
        lblDescripcion = new javax.swing.JLabel();
        txtDescripcion = new javax.swing.JTextField();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnModificar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblImpuestos = new javax.swing.JTable();
        cmbEstado = new javax.swing.JComboBox<>();
        jLabel2 = new javax.swing.JLabel();
        txtPorcentaje = new javax.swing.JTextField();
        lblFechaFin = new javax.swing.JLabel();
        dcFechaInicio = new com.toedter.calendar.JDateChooser();
        dcFechaFin = new com.toedter.calendar.JDateChooser();
        lblFechaInicio = new javax.swing.JLabel();

        jButton1.setText("NUEVO");

        jButton3.setText("GUARDAR");

        jButton5.setText("MODIFICAR");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("IMPUESTOS  ");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre: ");

        lblCodigo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCodigo.setText(" Código:");

        lblEstado.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblEstado.setText("Estado:");

        lblDescripcion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDescripcion.setText("Descripción:");

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnModificar.setText("MODIFICAR");
        btnModificar.addActionListener(this::btnModificarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tblImpuestos.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Codigo", "Nombre", "Porcentaje", "Fecha inicio", "Fecha fin ", "Descripcion", "Estado"
            }
        ));
        tblImpuestos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblImpuestosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblImpuestos);

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        jLabel2.setText("Porcentaje");

        lblFechaFin.setText("Fecha Fin:");

        lblFechaInicio.setText("Fecha Inicio:");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblCodigo)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 175, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(72, 72, 72)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblEstado)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addGap(6, 6, 6)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblFechaFin)
                                            .addComponent(lblFechaInicio))
                                        .addGap(28, 28, 28)
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addComponent(lblDescripcion)))
                            .addComponent(jLabel2)
                            .addComponent(lblNombre)
                            .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, 400, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, 151, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 554, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 41, Short.MAX_VALUE))))
            .addGroup(layout.createSequentialGroup()
                .addGap(190, 190, 190)
                .addComponent(jLabel1)
                .addContainerGap(318, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(31, 31, 31)
                        .addComponent(txtDescripcion))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(btnNuevo)
                        .addGap(28, 28, 28)
                        .addComponent(btnGuardar)
                        .addGap(31, 31, 31)
                        .addComponent(btnModificar)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimpiar)))
                .addGap(49, 49, 49))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addGap(31, 31, 31)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblNombre)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lblCodigo)
                            .addComponent(lblEstado))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtPorcentaje, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(22, 22, 22)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(dcFechaInicio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblFechaInicio))
                        .addGap(18, 18, 18)
                        .addComponent(dcFechaFin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(lblFechaFin))
                .addGap(38, 38, 38)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(lblDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtDescripcion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(52, 52, 52))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(151, 151, 151)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnModificar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(293, 293, 293))))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();
        String codigo = txtCodigo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String porcentajeTexto = txtPorcentaje.getText().trim(); // Campo nuevo

        if (nombre.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el nombre del impuesto.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtNombre.requestFocus();
            return;
        }

        if (codigo.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el código del impuesto.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtCodigo.requestFocus();
            return;
        }

        // Validar Porcentaje
        if (porcentajeTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el porcentaje del impuesto.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPorcentaje.requestFocus();
            return;
        }

        BigDecimal porcentaje;
        try {
            porcentaje = new BigDecimal(porcentajeTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un porcentaje válido (ej. 13.00).", "Validación", JOptionPane.WARNING_MESSAGE);
            txtPorcentaje.requestFocus();
            return;
        }

        // Validar Fecha Inicio (Ejemplo usando java.time.LocalDate)
        LocalDate fechaInicio;
        if (dcFechaInicio.getDate() == null) { // Si usas JDateChooser
            JOptionPane.showMessageDialog(this, "Seleccione la fecha de inicio.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            fechaInicio = dcFechaInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        // Fecha Fin (Opcional)
        LocalDate fechaFin = null;
        if (dcFechaFin.getDate() != null) {
            fechaFin = dcFechaFin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        }

        boolean estado = cmbEstado.getSelectedItem().toString().equals("Activo");

        Impuesto impuesto = new Impuesto(
                nombre,
                codigo,
                descripcion,
                porcentaje,
                fechaInicio,
                fechaFin,
                estado
        );

        try {
            controlador.insertar(impuesto);
            JOptionPane.showMessageDialog(this, "Impuesto guardado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarCampos();
        } catch (IllegalArgumentException ex) {
            // Captura las validaciones lanzadas por el ImpuestoController
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo guardar el impuesto.\n\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void tblImpuestosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblImpuestosMouseClicked

        int fila = tblImpuestos.getSelectedRow();

        if (fila == -1) {
            return;
        }

        idImpuestoSeleccionado = Integer.parseInt(tblImpuestos.getValueAt(fila, 0).toString());
        txtNombre.setText(tblImpuestos.getValueAt(fila, 1).toString());
        txtCodigo.setText(tblImpuestos.getValueAt(fila, 2).toString());
        txtPorcentaje.setText(tblImpuestos.getValueAt(fila, 3).toString());

        Object fInicio = tblImpuestos.getValueAt(fila, 4);
        if (fInicio != null) {
            dcFechaInicio.setDate(java.sql.Date.valueOf(fInicio.toString()));
        }

        Object fFin = tblImpuestos.getValueAt(fila, 5);
        dcFechaFin.setDate((fFin != null && !fFin.toString().isEmpty()) ? java.sql.Date.valueOf(fFin.toString()) : null);

        Object descripcion = tblImpuestos.getValueAt(fila, 6);
        txtDescripcion.setText(descripcion == null ? "" : descripcion.toString());

        cmbEstado.setSelectedItem(tblImpuestos.getValueAt(fila, 7).toString());

    }//GEN-LAST:event_tblImpuestosMouseClicked

    private void btnModificarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnModificarActionPerformed
        if (idImpuestoSeleccionado <= 0) {
            JOptionPane.showMessageDialog(this, "Seleccione un impuesto de la tabla.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String nombre = txtNombre.getText().trim();
        String codigo = txtCodigo.getText().trim();
        String descripcion = txtDescripcion.getText().trim();
        String porcentajeTexto = txtPorcentaje.getText().trim();

        if (nombre.isEmpty() || codigo.isEmpty() || porcentajeTexto.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Complete los campos obligatorios.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        BigDecimal porcentaje;
        try {
            porcentaje = new BigDecimal(porcentajeTexto);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Ingrese un porcentaje válido.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        LocalDate fechaInicio = dcFechaInicio.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fechaFin = (dcFechaFin.getDate() != null)
                ? dcFechaFin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate()
                : null;

        boolean estado = cmbEstado.getSelectedItem().toString().equals("Activo");

        Impuesto impuesto = new Impuesto(
                idImpuestoSeleccionado,
                nombre,
                codigo,
                descripcion,
                porcentaje,
                fechaInicio,
                fechaFin,
                estado
        );

        try {
            controlador.actualizar(impuesto);
            JOptionPane.showMessageDialog(this, "Impuesto modificado correctamente.", "Información", JOptionPane.INFORMATION_MESSAGE);
            cargarTabla();
            limpiarCampos();
        } catch (IllegalArgumentException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Validación de Negocio", JOptionPane.WARNING_MESSAGE);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "No se pudo modificar el impuesto.\n\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }//GEN-LAST:event_btnModificarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarCampos();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void cargarTabla() {

        try {
            List<Impuesto> lista = controlador.listar();
            DefaultTableModel modelo = (DefaultTableModel) tblImpuestos.getModel();
            modelo.setRowCount(0);

            for (Impuesto impuesto : lista) {
                modelo.addRow(new Object[]{
                    impuesto.getIdImpuesto(),
                    impuesto.getNombre(),
                    impuesto.getCodigo(),
                    impuesto.getPorcentaje(),
                    impuesto.getFechaInicio(),
                    impuesto.getFechaFin() != null ? impuesto.getFechaFin() : "",
                    impuesto.getDescripcion(),
                    impuesto.isEstado() ? "Activo" : "Inactivo"
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar los impuestos:\n" + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {

        txtNombre.setText("");
        txtCodigo.setText("");
        txtDescripcion.setText("");

        cmbEstado.setSelectedItem("Activo");

        idImpuestoSeleccionado = 0;

        txtNombre.requestFocus();
    }


    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnModificar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbEstado;
    private com.toedter.calendar.JDateChooser dcFechaFin;
    private com.toedter.calendar.JDateChooser dcFechaInicio;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton5;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblCodigo;
    private javax.swing.JLabel lblDescripcion;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblFechaFin;
    private javax.swing.JLabel lblFechaInicio;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JTable tblImpuestos;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtDescripcion;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtPorcentaje;
    // End of variables declaration//GEN-END:variables
}
