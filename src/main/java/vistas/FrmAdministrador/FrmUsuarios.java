/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JPanel.java to edit this template
 */
package vistas.FrmAdministrador;

import controladores.RolController;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;
import controladores.UsuarioController;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import modelos.Rol;

/**
 *
 * @author Nehemias Valencia
 */
public class FrmUsuarios extends javax.swing.JPanel {

    private Usuario usuario;
    private FrmMenuPrincipal menuPrincipal;
    private UsuarioController controller;
    private RolController rolController;
    private Usuario usuarioSeleccionado;

    /**
     * Creates new form FrmUsuarios
     */
    public FrmUsuarios(Usuario usuario, FrmMenuPrincipal menuPrincipal) {
        this.usuario = usuario;
        this.menuPrincipal = menuPrincipal;
        initComponents();

        //para podes seleccionar filas
        tblUsuarios.setDefaultEditor(Object.class, null);
        controller = new UsuarioController();
        rolController = new RolController();

        cargarRoles();
        cargarUsuarios();
        initializeComponents();

    }

    private void initializeComponents() {

        // =========================================================================
        // 1. PANEL SUPERIOR: Encabezado
        // =========================================================================
        JPanel pnlHeader = new JPanel(new java.awt.BorderLayout());
        pnlHeader.setBackground(new Color(15, 23, 42)); // Slate 900
        pnlHeader.setPreferredSize(new java.awt.Dimension(1000, 65));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(0, 25, 0, 25));

        lblTitulo.setText("ADMINISTRACIÓN DE USUARIOS");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 20));
        lblTitulo.setForeground(Color.WHITE);
        lblTitulo.setVerticalAlignment(SwingConstants.CENTER);
        pnlHeader.add(lblTitulo, java.awt.BorderLayout.CENTER);

        add(pnlHeader, java.awt.BorderLayout.NORTH);

        // =========================================================================
        // 2. PANEL CONTENEDOR PRINCIPAL
        // =========================================================================
        JPanel pnlMain = new JPanel(new java.awt.BorderLayout(0, 20));
        pnlMain.setBackground(new Color(241, 245, 249));
        pnlMain.setBorder(BorderFactory.createEmptyBorder(20, 25, 20, 25));
        add(pnlMain, java.awt.BorderLayout.CENTER);

        // -------------------------------------------------------------------------
        // 2.1 PANEL FORMULARIO Y ACCIONES
        // -------------------------------------------------------------------------
        JPanel pnlForm = new JPanel(new java.awt.GridBagLayout());
        pnlForm.setBackground(Color.WHITE);
        pnlForm.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(226, 232, 240), 1),
                BorderFactory.createEmptyBorder(25, 25, 20, 25)
        ));

        java.awt.GridBagConstraints gbc = new java.awt.GridBagConstraints();
        gbc.insets = new java.awt.Insets(10, 12, 10, 12);
        gbc.fill = java.awt.GridBagConstraints.HORIZONTAL;

        // --- Estilos para Labels ---
        Font fontLabel = new Font("Segoe UI", Font.BOLD, 13);
        Color colorLabel = new Color(71, 85, 105); // Slate 600

        lblNombre.setText("Nombre:");
        lblUsuario.setText("Usuario:");
        lblPassword.setText("Contraseña:");
        lblRol.setText("Rol:");
        lblEstado.setText("Estado:");

        JLabel[] labels = {lblNombre, lblUsuario, lblPassword, lblRol, lblEstado};
        for (JLabel lbl : labels) {
            lbl.setFont(fontLabel);
            lbl.setForeground(colorLabel);
        }

        // --- Estilos para Campos de Texto más Grandes (Alto de 42px) ---
        Font fontInput = new Font("Segoe UI", Font.PLAIN, 14);
        Color colorBgInput = new Color(248, 250, 252); // Slate 50
        Dimension dimensionInput = new Dimension(220, 42); // Mayor altura y ancho

        // Borde elegante con padding interno cómodo
        javax.swing.border.Border borderInput = BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(203, 213, 225), 1),
                BorderFactory.createEmptyBorder(8, 12, 8, 12)
        );

        JComponent[] inputs = {txtNombre, txtUsuario, txtPassword, cmbRol, cmbEstado};
        for (JComponent input : inputs) {
            input.setFont(fontInput);
            input.setBackground(colorBgInput);
            input.setPreferredSize(dimensionInput);
            if (input instanceof JTextField || input instanceof JPasswordField) {
                input.setBorder(borderInput);
            }
        }

        // --- Distribución en Grid ---
        // Fila 0: Nombre y Usuario
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        pnlForm.add(lblNombre, gbc);
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        pnlForm.add(txtNombre, gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.weightx = 0.0;
        pnlForm.add(lblUsuario, gbc);
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.weightx = 0.5;
        pnlForm.add(txtUsuario, gbc);

        // Fila 1: Contraseña y Rol
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        pnlForm.add(lblPassword, gbc);
        gbc.gridx = 1;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        pnlForm.add(txtPassword, gbc);

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.weightx = 0.0;
        pnlForm.add(lblRol, gbc);
        gbc.gridx = 3;
        gbc.gridy = 1;
        gbc.weightx = 0.5;
        pnlForm.add(cmbRol, gbc);

        // Fila 2: Estado
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.weightx = 0.0;
        pnlForm.add(lblEstado, gbc);
        gbc.gridx = 1;
        gbc.gridy = 2;
        gbc.weightx = 0.5;
        pnlForm.add(cmbEstado, gbc);

        // --- Fila 3: Panel de Botones Estilizados ---
        JPanel pnlBotones = new JPanel(new java.awt.FlowLayout(java.awt.FlowLayout.RIGHT, 12, 0));
        pnlBotones.setOpaque(false);

        // Aplicar estilos modernos a los botones
        estilarBoton(btnNuevo, "Nuevo", new Color(241, 245, 249), new Color(30, 41, 59), new Color(203, 213, 225));
        estilarBoton(btnGuardar, "Guardar", new Color(37, 99, 235), Color.WHITE, new Color(29, 78, 216));
        estilarBoton(btnEditar, "Editar", new Color(217, 119, 6), Color.WHITE, new Color(180, 83, 9));
        estilarBoton(btnLimpiar, "Limpiar", new Color(100, 116, 139), Color.WHITE, new Color(71, 85, 105));

        pnlBotones.add(btnNuevo);
        pnlBotones.add(btnGuardar);
        pnlBotones.add(btnEditar);
        pnlBotones.add(btnLimpiar);

        gbc.gridx = 0;
        gbc.gridy = 3;
        gbc.gridwidth = 4;
        gbc.insets = new java.awt.Insets(25, 0, 0, 0);
        pnlForm.add(pnlBotones, gbc);

        pnlMain.add(pnlForm, java.awt.BorderLayout.NORTH);

        // -------------------------------------------------------------------------
        // 2.2 TABLA DE USUARIOS
        // -------------------------------------------------------------------------
        tblUsuarios.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        tblUsuarios.setRowHeight(38);
        tblUsuarios.setSelectionBackground(new Color(224, 242, 254));
        tblUsuarios.setSelectionForeground(new Color(15, 23, 42));
        tblUsuarios.setShowVerticalLines(false);
        tblUsuarios.setGridColor(new Color(226, 232, 240));

        javax.swing.table.JTableHeader header = tblUsuarios.getTableHeader();
        header.setFont(new Font("Segoe UI", Font.BOLD, 12));
        header.setBackground(new Color(241, 245, 249));
        header.setForeground(new Color(71, 85, 105));
        header.setPreferredSize(new java.awt.Dimension(0, 40));

        jScrollPane1.setViewportView(tblUsuarios);
        jScrollPane1.setBorder(BorderFactory.createLineBorder(new Color(226, 232, 240), 1));
        jScrollPane1.getViewport().setBackground(Color.WHITE);

        pnlMain.add(jScrollPane1, java.awt.BorderLayout.CENTER);
    }

// Método auxiliar para dar acabado moderno a los botones
    private void estilarBoton(JButton btn, String texto, Color bg, Color fg, Color borderColor) {
        btn.setText(texto);
        btn.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btn.setBackground(bg);
        btn.setForeground(fg);
        btn.setFocusPainted(false);
        btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
        btn.setPreferredSize(new Dimension(115, 40)); // Un poco más amplios
        btn.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(borderColor, 1, true),
                BorderFactory.createEmptyBorder(5, 12, 5, 12)
        ));
    }

    private void cargarRoles() {

        cmbRol.removeAllItems();

        List<Rol> roles = rolController.listarRoles();

        for (Rol rol : roles) {
            cmbRol.addItem(rol);
        }
    }

    private void cargarUsuarios() {

        DefaultTableModel modelo
                = (DefaultTableModel) tblUsuarios.getModel();

        modelo.setRowCount(0);

        List<Usuario> usuarios = controller.listarUsuarios();

        for (Usuario usuario : usuarios) {

            modelo.addRow(new Object[]{
                usuario.getIdUsuario(),
                usuario.getNombre(),
                usuario.getUsuario(),
                usuario.getNombreRol(),
                usuario.isEstado() ? "Activo" : "Inactivo",
                usuario.getCreadoEn(),
                usuario.getUltimaConexion()
            });
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

        lblTitulo = new javax.swing.JLabel();
        lblNombre = new javax.swing.JLabel();
        lblUsuario = new javax.swing.JLabel();
        lblPassword = new javax.swing.JLabel();
        lblRol = new javax.swing.JLabel();
        lblEstado = new javax.swing.JLabel();
        txtNombre = new javax.swing.JTextField();
        txtUsuario = new javax.swing.JTextField();
        txtPassword = new javax.swing.JPasswordField();
        cmbRol = new javax.swing.JComboBox<>();
        cmbEstado = new javax.swing.JComboBox<>();
        btnNuevo = new javax.swing.JButton();
        btnGuardar = new javax.swing.JButton();
        btnEditar = new javax.swing.JButton();
        btnLimpiar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();

        lblTitulo.setText("ADMINISTRACIÓN DE USUARIOS");

        lblNombre.setText("Nombre:");

        lblUsuario.setText("Usuario:");

        lblPassword.setText("Contraseña:");

        lblRol.setText("Rol:");

        lblEstado.setText("Estado:");

        cmbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Activo", "Inactivo" }));

        btnNuevo.setText("Nuevo");
        btnNuevo.addActionListener(this::btnNuevoActionPerformed);

        btnGuardar.setText("Guardar");
        btnGuardar.addActionListener(this::btnGuardarActionPerformed);

        btnEditar.setText("Editar");
        btnEditar.addActionListener(this::btnEditarActionPerformed);

        btnLimpiar.setText("Limpiar");
        btnLimpiar.addActionListener(this::btnLimpiarActionPerformed);

        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nombre", "Usuario", "Rol", "Estado", "Creado", "Última conexión"
            }
        ));
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btnNuevo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnGuardar)
                .addGap(18, 18, 18)
                .addComponent(btnEditar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btnLimpiar)
                .addGap(354, 354, 354))
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(333, 333, 333)
                        .addComponent(lblTitulo))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(73, 73, 73)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblUsuario)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGroup(layout.createSequentialGroup()
                                    .addComponent(lblNombre)
                                    .addGap(75, 75, 75)
                                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lblPassword)
                                    .addComponent(lblRol)
                                    .addComponent(lblEstado))
                                .addGap(59, 59, 59)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(15, 15, 15)
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 707, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(79, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblTitulo)
                .addGap(45, 45, 45)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblNombre)
                    .addComponent(txtNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblPassword)
                    .addComponent(txtPassword, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblRol)
                    .addComponent(cmbRol, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblEstado)
                    .addComponent(cmbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnNuevo)
                    .addComponent(btnGuardar)
                    .addComponent(btnEditar)
                    .addComponent(btnLimpiar))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnNuevoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNuevoActionPerformed
        limpiarFormulario();
        txtNombre.requestFocus();
    }//GEN-LAST:event_btnNuevoActionPerformed

    private void btnLimpiarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimpiarActionPerformed
        limpiarFormulario();
    }//GEN-LAST:event_btnLimpiarActionPerformed

    private void btnGuardarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnGuardarActionPerformed
        String nombre = txtNombre.getText().trim();
        String nombreUsuario = txtUsuario.getText().trim();
        String password = new String(txtPassword.getPassword());

        if (nombre.isEmpty()
                || nombreUsuario.isEmpty()
                || password.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Complete todos los campos.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        Rol rolSeleccionado
                = (Rol) cmbRol.getSelectedItem();

        if (rolSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un rol.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        boolean estado
                = cmbEstado.getSelectedItem()
                        .toString()
                        .equals("Activo");

        Usuario nuevoUsuario = new Usuario();

        nuevoUsuario.setNombre(nombre);
        nuevoUsuario.setUsuario(nombreUsuario);
        nuevoUsuario.setIdRol(rolSeleccionado.getIdRol());
        nuevoUsuario.setEstado(estado);

        boolean guardado
                = controller.guardarUsuario(
                        nuevoUsuario,
                        password
                );

        if (guardado) {

            JOptionPane.showMessageDialog(
                    this,
                    "Usuario guardado correctamente.",
                    "Usuarios",
                    JOptionPane.INFORMATION_MESSAGE
            );

            cargarUsuarios();
            limpiarFormulario();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo guardar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
        }
    }//GEN-LAST:event_btnGuardarActionPerformed

    private void btnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEditarActionPerformed
        if (usuarioSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un usuario de la tabla.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        String nombre = txtNombre.getText().trim();
        String nombreUsuario = txtUsuario.getText().trim();
        String nuevaPassword = new String(txtPassword.getPassword());

        // Validar datos obligatorios
        if (nombre.isEmpty() || nombreUsuario.isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Nombre y usuario son obligatorios.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Obtener rol
        Rol rolSeleccionado
                = (Rol) cmbRol.getSelectedItem();

        if (rolSeleccionado == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un rol.",
                    "Validación",
                    JOptionPane.WARNING_MESSAGE
            );

            return;
        }

        // Obtener estado
        boolean estado
                = cmbEstado.getSelectedItem()
                        .toString()
                        .equals("Activo");

        // Actualizar datos del usuario
        usuarioSeleccionado.setNombre(nombre);
        usuarioSeleccionado.setUsuario(nombreUsuario);
        usuarioSeleccionado.setIdRol(
                rolSeleccionado.getIdRol()
        );
        usuarioSeleccionado.setEstado(estado);

        boolean actualizado
                = controller.actualizarUsuario(
                        usuarioSeleccionado
                );

        if (!actualizado) {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar el usuario.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        // Actualizar contraseña solamente si se escribió una nueva
        if (!nuevaPassword.isBlank()) {

            boolean passwordActualizada
                    = controller.actualizarPassword(
                            usuarioSeleccionado.getIdUsuario(),
                            nuevaPassword
                    );

            if (!passwordActualizada) {

                JOptionPane.showMessageDialog(
                        this,
                        "Los datos fueron actualizados, "
                        + "pero no se pudo actualizar la contraseña.",
                        "Advertencia",
                        JOptionPane.WARNING_MESSAGE
                );

                cargarUsuarios();
                limpiarFormulario();

                return;
            }
        }

        JOptionPane.showMessageDialog(
                this,
                "Usuario actualizado correctamente.",
                "Usuarios",
                JOptionPane.INFORMATION_MESSAGE
        );

        cargarUsuarios();
        limpiarFormulario();
    }//GEN-LAST:event_btnEditarActionPerformed

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        int fila = tblUsuarios.getSelectedRow();

        if (fila == -1) {
            return;
        }

        int idUsuario = Integer.parseInt(
                tblUsuarios.getValueAt(fila, 0).toString()
        );

        String nombre = tblUsuarios.getValueAt(fila, 1).toString();
        String nombreUsuario = tblUsuarios.getValueAt(fila, 2).toString();
        String nombreRol = tblUsuarios.getValueAt(fila, 3).toString();
        String estado = tblUsuarios.getValueAt(fila, 4).toString();

        usuarioSeleccionado = new Usuario();

        usuarioSeleccionado.setIdUsuario(idUsuario);
        usuarioSeleccionado.setNombre(nombre);
        usuarioSeleccionado.setUsuario(nombreUsuario);

        txtNombre.setText(nombre);
        txtUsuario.setText(nombreUsuario);

        // La contraseña no se recupera de la base de datos
        txtPassword.setText("");

        // Seleccionar rol
        for (int i = 0; i < cmbRol.getItemCount(); i++) {

            Rol rol = cmbRol.getItemAt(i);

            if (rol.getNombre().equals(nombreRol)) {
                cmbRol.setSelectedIndex(i);
                break;
            }
        }

        cmbEstado.setSelectedItem(estado);
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void limpiarFormulario() {

        usuarioSeleccionado = null;

        txtNombre.setText("");
        txtUsuario.setText("");
        txtPassword.setText("");

        if (cmbRol.getItemCount() > 0) {
            cmbRol.setSelectedIndex(0);
        }

        if (cmbEstado.getItemCount() > 0) {
            cmbEstado.setSelectedIndex(0);
        }

        tblUsuarios.clearSelection();
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnEditar;
    private javax.swing.JButton btnGuardar;
    private javax.swing.JButton btnLimpiar;
    private javax.swing.JButton btnNuevo;
    private javax.swing.JComboBox<String> cmbEstado;
    private javax.swing.JComboBox<Rol> cmbRol;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblEstado;
    private javax.swing.JLabel lblNombre;
    private javax.swing.JLabel lblPassword;
    private javax.swing.JLabel lblRol;
    private javax.swing.JLabel lblTitulo;
    private javax.swing.JLabel lblUsuario;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtNombre;
    private javax.swing.JPasswordField txtPassword;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
