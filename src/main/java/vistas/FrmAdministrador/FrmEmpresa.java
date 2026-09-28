/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas.FrmAdministrador;

import controladores.EmpresaController;
import controladores.PaisController;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.SQLException;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import modelos.Empresa;
import modelos.Pais;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;

/**
 *
 * @author maril
 */
public class FrmEmpresa extends javax.swing.JPanel {

    private EmpresaController controlador;
    private PaisController paisController;

    private int idEmpresaSeleccionada = 0;

    /**
     * Creates new form FrmEmpresa
     */
    public FrmEmpresa(Usuario usuario, FrmMenuPrincipal menuPrincipal) {

        initComponents();
        initializeComponents();

        try {

            controlador = new EmpresaController();
            paisController = new PaisController();

            configurarTabla();

            cargarPaises();

            cargarEmpresas();

            limpiarFormulario();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al inicializar el formulario:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void initializeComponents() {

        // =========================================================
        // FORMULARIO PRINCIPAL
        // =========================================================
        this.setLayout(new BorderLayout(0, 15));
        this.setBackground(new Color(241, 245, 249));

        // =========================================================
        // ENCABEZADO
        // =========================================================
        JPanel pnlHeader = new JPanel(new BorderLayout());

        pnlHeader.setBackground(
                new Color(15, 23, 42)
        );

        pnlHeader.setBorder(
                BorderFactory.createEmptyBorder(
                        18, 25, 18, 25
                )
        );

        jLabel1.setText("EMPRESAS");

        jLabel1.setFont(
                new Font("Segoe UI", Font.BOLD, 20)
        );

        jLabel1.setForeground(Color.WHITE);

        pnlHeader.add(
                jLabel1,
                BorderLayout.WEST
        );

        // =========================================================
        // PANEL PRINCIPAL
        // =========================================================
        JPanel pnlMain = new JPanel(
                new BorderLayout(0, 15)
        );

        pnlMain.setBackground(
                new Color(241, 245, 249)
        );

        pnlMain.setBorder(
                BorderFactory.createEmptyBorder(
                        0, 20, 20, 20
                )
        );

        // =========================================================
        // PANEL DE DATOS
        // =========================================================
        JPanel pnlDatos = new JPanel(
                new GridBagLayout()
        );

        pnlDatos.setBackground(Color.WHITE);

        pnlDatos.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        BorderFactory.createEmptyBorder(
                                20, 20, 20, 20
                        )
                )
        );

        GridBagConstraints gbc
                = new GridBagConstraints();

        gbc.insets
                = new Insets(7, 10, 7, 10);

        gbc.fill
                = GridBagConstraints.HORIZONTAL;

        gbc.weightx = 1.0;

        // =========================================================
        // ESTILO DE ETIQUETAS
        // =========================================================
        JLabel[] etiquetas = {
            lblNombre,
            lblPais,
            lblNIT,
            lblNRC,
            lblTelefono,
            lblCorreo,
            lblDireccion,
            lblActividad,
            jLabel10
        };

        for (JLabel etiqueta : etiquetas) {

            etiqueta.setFont(
                    new Font(
                            "Segoe UI",
                            Font.BOLD,
                            13
                    )
            );

            etiqueta.setForeground(
                    new Color(71, 85, 105)
            );
        }

        // =========================================================
        // CAMPOS DE TEXTO
        // =========================================================
        JTextField[] campos = {
            txtNombre,
            txtNIT,
            txtNRC,
            txtTelefono,
            txtCorreo,
            txtDireccion,
            txtActividad
        };

        for (JTextField campo : campos) {

            campo.setFont(
                    new Font(
                            "Segoe UI",
                            Font.PLAIN,
                            14
                    )
            );

            campo.setBackground(
                    new Color(248, 250, 252)
            );

            campo.setBorder(
                    BorderFactory.createCompoundBorder(
                            BorderFactory.createLineBorder(
                                    new Color(203, 213, 225)
                            ),
                            BorderFactory.createEmptyBorder(
                                    5, 8, 5, 8
                            )
                    )
            );

            campo.setPreferredSize(
                    new Dimension(220, 38)
            );
        }

        // =========================================================
        // COMBO PAÍS
        // =========================================================
        cmbPais.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        cmbPais.setBackground(Color.WHITE);

        cmbPais.setPreferredSize(
                new Dimension(220, 38)
        );

        // =========================================================
        // COMBO ESTADO
        // =========================================================
        cmbEstado.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        14
                )
        );

        cmbEstado.setBackground(Color.WHITE);

        cmbEstado.setPreferredSize(
                new Dimension(150, 38)
        );

        // =========================================================
        // FILA 1 - NOMBRE
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 0;

        pnlDatos.add(
                lblNombre,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        pnlDatos.add(
                txtNombre,
                gbc
        );

        gbc.gridwidth = 1;

        // =========================================================
        // FILA 2 - PAÍS / NIT
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 1;

        pnlDatos.add(
                lblPais,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                cmbPais,
                gbc
        );

        gbc.gridx = 2;

        pnlDatos.add(
                lblNIT,
                gbc
        );

        gbc.gridx = 3;

        pnlDatos.add(
                txtNIT,
                gbc
        );

        // =========================================================
        // FILA 3 - NRC / TELÉFONO
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 2;

        pnlDatos.add(
                lblNRC,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                txtNRC,
                gbc
        );

        gbc.gridx = 2;

        pnlDatos.add(
                lblTelefono,
                gbc
        );

        gbc.gridx = 3;

        pnlDatos.add(
                txtTelefono,
                gbc
        );

        // =========================================================
        // FILA 4 - CORREO
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 3;

        pnlDatos.add(
                lblCorreo,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        pnlDatos.add(
                txtCorreo,
                gbc
        );

        gbc.gridwidth = 1;

        // =========================================================
        // FILA 5 - DIRECCIÓN
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 4;

        pnlDatos.add(
                lblDireccion,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        pnlDatos.add(
                txtDireccion,
                gbc
        );

        gbc.gridwidth = 1;

        // =========================================================
        // FILA 6 - ACTIVIDAD ECONÓMICA
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 5;

        pnlDatos.add(
                lblActividad,
                gbc
        );

        gbc.gridx = 1;
        gbc.gridwidth = 3;

        pnlDatos.add(
                txtActividad,
                gbc
        );

        gbc.gridwidth = 1;

        // =========================================================
        // FILA 7 - ESTADO
        // =========================================================
        gbc.gridx = 0;
        gbc.gridy = 6;

        pnlDatos.add(
                jLabel10,
                gbc
        );

        gbc.gridx = 1;

        pnlDatos.add(
                cmbEstado,
                gbc
        );

        // =========================================================
        // BOTONES
        // =========================================================
        JPanel pnlBotones = new JPanel(
                new FlowLayout(
                        FlowLayout.LEFT,
                        10,
                        5
                )
        );

        pnlBotones.setBackground(Color.WHITE);

        estilarBoton(
                btnNuevo,
                "nuevo"
        );

        estilarBoton(
                btnGuardar,
                "guardar"
        );

        estilarBoton(
                btnEditar,
                "editar"
        );

        estilarBoton(
                btnLimpiar,
                "limpiar"
        );

        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnEditar);
        pnlBotones.add(btnLimpiar);

        // =========================================================
        // PANEL SUPERIOR
        // =========================================================
        JPanel pnlSuperior = new JPanel(
                new BorderLayout(0, 10)
        );

        pnlSuperior.setBackground(
                new Color(241, 245, 249)
        );

        pnlSuperior.add(
                pnlDatos,
                BorderLayout.CENTER
        );

        pnlSuperior.add(
                pnlBotones,
                BorderLayout.SOUTH
        );

        // =========================================================
        // TABLA
        // =========================================================
        tbEmpresas.setFont(
                new Font(
                        "Segoe UI",
                        Font.PLAIN,
                        13
                )
        );

        tbEmpresas.setRowHeight(36);

        tbEmpresas.setSelectionBackground(
                new Color(224, 242, 254)
        );

        tbEmpresas.setSelectionForeground(
                new Color(15, 23, 42)
        );

        tbEmpresas.setShowVerticalLines(false);

        tbEmpresas.setGridColor(
                new Color(226, 232, 240)
        );

        // =========================================================
        // CABECERA DE TABLA
        // =========================================================
        tbEmpresas.getTableHeader().setFont(
                new Font(
                        "Segoe UI",
                        Font.BOLD,
                        12
                )
        );

        tbEmpresas.getTableHeader().setBackground(
                new Color(241, 245, 249)
        );

        tbEmpresas.getTableHeader().setForeground(
                new Color(71, 85, 105)
        );

        tbEmpresas.getTableHeader().setPreferredSize(
                new Dimension(0, 38)
        );

        // =========================================================
        // SCROLLPANE
        // =========================================================
        jScrollPane1.setBorder(
                BorderFactory.createLineBorder(
                        new Color(226, 232, 240)
                )
        );

        jScrollPane1.getViewport().setBackground(
                Color.WHITE
        );

        // =========================================================
        // PANEL DE TABLA
        // =========================================================
        JPanel pnlTabla = new JPanel(
                new BorderLayout()
        );

        pnlTabla.setBackground(Color.WHITE);

        pnlTabla.setBorder(
                BorderFactory.createCompoundBorder(
                        BorderFactory.createLineBorder(
                                new Color(226, 232, 240)
                        ),
                        BorderFactory.createEmptyBorder(
                                10, 10, 10, 10
                        )
                )
        );

        pnlTabla.add(
                jScrollPane1,
                BorderLayout.CENTER
        );

        // =========================================================
        // AGREGAR AL FORMULARIO
        // =========================================================
        pnlMain.add(
                pnlSuperior,
                BorderLayout.NORTH
        );

        pnlMain.add(
                pnlTabla,
                BorderLayout.CENTER
        );

        this.add(
                pnlHeader,
                BorderLayout.NORTH
        );

        this.add(
                pnlMain,
                BorderLayout.CENTER
        );
    }
    
    private void estilarBoton(JButton boton, String tipo) {

    boton.setFont(
        new Font(
            "Segoe UI",
            Font.BOLD,
            13
        )
    );

    boton.setCursor(
        new Cursor(Cursor.HAND_CURSOR)
    );

    boton.setPreferredSize(
        new Dimension(110, 38)
    );

    switch (tipo) {

        case "nuevo":

            boton.setBackground(
                new Color(241, 245, 249)
            );

            boton.setForeground(
                new Color(30, 41, 59)
            );

            boton.setBorder(
                BorderFactory.createLineBorder(
                    new Color(203, 213, 225)
                )
            );

            break;

        case "guardar":

            boton.setBackground(
                new Color(37, 99, 235)
            );

            boton.setForeground(Color.WHITE);

            boton.setBorder(
                BorderFactory.createLineBorder(
                    new Color(29, 78, 216)
                )
            );

            break;

        case "editar":

            boton.setBackground(
                new Color(217, 119, 6)
            );

            boton.setForeground(Color.WHITE);

            boton.setBorder(
                BorderFactory.createLineBorder(
                    new Color(180, 83, 9)
                )
            );

            break;

        case "limpiar":

            boton.setBackground(
                new Color(100, 116, 139)
            );

            boton.setForeground(Color.WHITE);

            boton.setBorder(
                BorderFactory.createLineBorder(
                    new Color(71, 85, 105)
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

        jButton4 = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        lblNIT = new javax.swing.JLabel();
        cmbPais = new javax.swing.JComboBox<>();
        lblPais = new javax.swing.JLabel();
        txtNIT = new javax.swing.JTextField();
        lblNRC = new javax.swing.JLabel();
        txtNRC = new javax.swing.JTextField();
        lblCorreo = new javax.swing.JLabel();
        txtTelefono = new javax.swing.JTextField();
        lblTelefono = new javax.swing.JLabel();
        txtCorreo = new javax.swing.JTextField();
        lblDireccion = new javax.swing.JLabel();
        txtDireccion = new javax.swing.JTextField();
        lblActividad = new javax.swing.JLabel();
        txtActividad = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbEmpresas = new javax.swing.JTable();
        cmbEstado = new javax.swing.JComboBox<>();

        jButton4.setText("CERRAR PERÍODO");

        jLabel1.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jLabel1.setText("  EMPRESAS ");

        lblNombre.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNombre.setText("Nombre: ");

        lblNIT.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNIT.setText("NIT:");

        lblPais.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblPais.setText(" País: ");

        lblNRC.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblNRC.setText("NRC:");

        lblCorreo.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblCorreo.setText("Correo:   ");

        lblTelefono.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblTelefono.setText("Teléfono:  ");

        lblDireccion.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblDireccion.setText("Dirección: ");

        lblActividad.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        lblActividad.setText("Actividad económica:  ");

        jLabel10.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel10.setText("Estado:");

        btnNuevo.setText("NUEVO");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("GUARDAR");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnEditar.setText("MODIFICAR");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnLimpiar.setText("LIMPIAR");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        jScrollPane1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jScrollPane1MouseClicked(evt);
            }
        });

        tbEmpresas.setModel(new javax.swing.table.DefaultTableModel(
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
        tbEmpresas.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbEmpresasMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbEmpresas);

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(178, 178, 178)
                        .addComponent(jLabel1))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 426, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap(43, Short.MAX_VALUE)
                        .addComponent(btnNuevo)
                        .addGap(18, 18, 18)
                        .addComponent(btnGuardar)
                        .addGap(18, 18, 18)
                        .addComponent(btnEditar)
                        .addGap(18, 18, 18)
                        .addComponent(btnLimpiar))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addGap(38, 38, 38)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtNombre, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtCorreo, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblNRC)
                                    .addComponent(txtNRC, javax.swing.GroupLayout.PREFERRED_SIZE, 142, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lblCorreo))
                                .addGap(97, 97, 97)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtNIT)
                                    .addComponent(txtTelefono)
                                    .addGroup(layout.createSequentialGroup()
                                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lblNIT)
                                            .addComponent(lblTelefono))
                                        .addGap(0, 0, Short.MAX_VALUE))))
                            .addComponent(txtDireccion)
                            .addComponent(txtActividad)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblNombre)
                                    .addComponent(lblDireccion)
                                    .addComponent(lblActividad)
                                    .addComponent(lblPais)
                                    .addGroup(layout.createSequentialGroup()
                                        .addComponent(jLabel10)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                .addGap(0, 0, Short.MAX_VALUE)))))
                .addGap(40, 40, 40))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(jLabel1)
                .addGap(18, 18, 18)
                .addComponent(lblNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPais)
                    .addComponent(lblNIT))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(txtNIT, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblTelefono)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtTelefono, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(cmbPais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblNRC)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txtNRC, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblCorreo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtCorreo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblDireccion)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtDireccion, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblActividad)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtActividad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnGuardar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnLimpiar, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 147, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void cargarPaises() {

        try {

            List<Pais> paises = paisController.listar();

            cmbPais.removeAllItems();

            for (Pais pais : paises) {

                cmbPais.addItem(pais);
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar los países:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }
    
    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarFormulario();

        txtNombre.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre de la empresa es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            txtNombre.requestFocus();

            return;
        }

        try {

            Empresa empresa = new Empresa();

            /*
         * Por ahora El Salvador tiene id_pais = 1.
         * Después conectaremos cmbPais directamente
         * con la tabla paises.
             */
            empresa.setIdPais(1);

            empresa.setNombre(nombre);
            empresa.setNit(txtNIT.getText().trim());
            empresa.setNrc(txtNRC.getText().trim());
            empresa.setDireccion(txtDireccion.getText().trim());
            empresa.setTelefono(txtTelefono.getText().trim());
            empresa.setCorreo(txtCorreo.getText().trim());
            empresa.setActividadEconomica(
                    txtActividad.getText().trim()
            );

            empresa.setEstado(
                    cmbEstado.getSelectedItem()
                            .toString()
                            .equals("Activo")
            );

            controlador.insertar(empresa);

            JOptionPane.showMessageDialog(
                    this,
                    "Empresa registrada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();

            cargarEmpresas();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al registrar la empresa:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void jScrollPane1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jScrollPane1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_jScrollPane1MouseClicked

    private void tbEmpresasMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbEmpresasMouseClicked
        int fila = tbEmpresas.getSelectedRow();

        if (fila == -1) {
            return;
        }

        try {

            int idEmpresa = Integer.parseInt(
                    tbEmpresas.getValueAt(fila, 0).toString()
            );

            Empresa empresa = controlador.buscarPorId(idEmpresa);

            if (empresa == null) {
                return;
            }

            idEmpresaSeleccionada = empresa.getIdEmpresa();

            txtNombre.setText(empresa.getNombre());
            txtNIT.setText(empresa.getNit());
            txtNRC.setText(empresa.getNrc());
            txtTelefono.setText(empresa.getTelefono());
            txtCorreo.setText(empresa.getCorreo());
            txtDireccion.setText(empresa.getDireccion());
            txtActividad.setText(empresa.getActividadEconomica());

            cmbEstado.setSelectedItem(
                    empresa.isEstado()
                    ? "Activo"
                    : "Inactivo"
            );

            /*
         * Por ahora todos los registros utilizan
         * El Salvador.
             */
            if (empresa.getIdPais() == 1) {
                cmbPais.setSelectedItem("El Salvador");
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar la empresa:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_tbEmpresasMouseClicked

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (idEmpresaSeleccionada == 0) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una empresa de la tabla.",
                    "Aviso",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombre = txtNombre.getText().trim();

        if (nombre.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "El nombre de la empresa es obligatorio.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        try {

            Empresa empresa = new Empresa();

            empresa.setIdEmpresa(idEmpresaSeleccionada);

            // El Salvador
            empresa.setIdPais(1);

            empresa.setNombre(nombre);
            empresa.setNit(txtNIT.getText().trim());
            empresa.setNrc(txtNRC.getText().trim());
            empresa.setDireccion(txtDireccion.getText().trim());
            empresa.setTelefono(txtTelefono.getText().trim());
            empresa.setCorreo(txtCorreo.getText().trim());
            empresa.setActividadEconomica(
                    txtActividad.getText().trim()
            );

            empresa.setEstado(
                    cmbEstado.getSelectedItem()
                            .toString()
                            .equals("Activo")
            );

            controlador.actualizar(empresa);

            JOptionPane.showMessageDialog(
                    this,
                    "Empresa modificada correctamente.",
                    "Éxito",
                    JOptionPane.INFORMATION_MESSAGE
            );

            limpiarFormulario();

            cargarEmpresas();

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al modificar la empresa:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnEditarActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void configurarTabla() {

        DefaultTableModel modelo = new DefaultTableModel(
                new Object[][]{},
                new String[]{
                    "ID",
                    "Nombre",
                    "NIT",
                    "NRC",
                    "Teléfono",
                    "Correo",
                    "Estado"
                }
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        tbEmpresas.setModel(modelo);
    }

    private void cargarEmpresas() {

        try {

            List<Empresa> empresas = controlador.listar();

            DefaultTableModel modelo
                    = (DefaultTableModel) tbEmpresas.getModel();

            modelo.setRowCount(0);

            for (Empresa empresa : empresas) {

                modelo.addRow(new Object[]{
                    empresa.getIdEmpresa(),
                    empresa.getNombre(),
                    empresa.getNit(),
                    empresa.getNrc(),
                    empresa.getTelefono(),
                    empresa.getCorreo(),
                    empresa.isEstado() ? "Activo" : "Inactivo"
                });
            }

        } catch (SQLException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Error al cargar las empresas:\n"
                    + e.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }

    private void limpiarFormulario() {

        idEmpresaSeleccionada = 0;

        txtNombre.setText("");
        txtNIT.setText("");
        txtNRC.setText("");
        txtTelefono.setText("");
        txtCorreo.setText("");
        txtDireccion.setText("");
        txtActividad.setText("");

        if (cmbPais.getItemCount() > 0) {
            cmbPais.setSelectedIndex(0);
        }

        cmbEstado.setSelectedIndex(0);

        tbEmpresas.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<Pais> cmbPais;
    private javax.swing.JButton jButton4;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblActividad;
    private javax.swing.JLabel lblCorreo;
    private javax.swing.JLabel lblDireccion;
    private javax.swing.JLabel lblNIT;
    private javax.swing.JLabel lblNRC;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPais;
    private javax.swing.JLabel lblTelefono;
    private javax.swing.JTable tbEmpresas;
    private javax.swing.JTextField txtActividad;
    private javax.swing.JTextField txtCorreo;
    private javax.swing.JTextField txtDireccion;
    private javax.swing.JTextField txtNIT;
    private javax.swing.JTextField txtNRC;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JTextField txtTelefono;
    // End of variables declaration//GEN-END:variables
}
