package vistas.menus;

import java.awt.Color;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JButton;
import modelos.Usuario;
import vistas.FrmAdministrador.FrmCuentas;
import vistas.FrmAdministrador.FrmEstadosFinancieros;
import vistas.FrmAdministrador.FrmLibroDiario;
import vistas.FrmAdministrador.FrmLibroMayor;
import vistas.FrmAdministrador.FrmProductos;
import vistas.FrmLogin;
import vistas.FrmMenuPrincipal;

/**
 * Menú lateral del rol Contador (Compatible al 100% con NetBeans GUI Builder).
 */
public class FrmMenuContador extends javax.swing.JPanel {

    private Usuario usuario;
    private FrmMenuPrincipal menuPrincipal;

    public FrmMenuContador() {
        initComponents();
    }

    public FrmMenuContador(Usuario usuario, FrmMenuPrincipal menuPrincipal) {
        this.usuario = usuario;
        this.menuPrincipal = menuPrincipal;
        initComponents();
        estilarBotones();
    }

    private void estilarBotones() {
        JButton[] botones = {btnCuentas, btnAsientos, btnOperaciones, btnReportes, btnProductos};
        for (JButton b : botones) {
            b.setOpaque(true);
            b.setContentAreaFilled(true);
            b.setBorderPainted(false);
            b.setFocusPainted(false);
            b.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }
        btnCerrarSesion.setOpaque(true);
        btnCerrarSesion.setContentAreaFilled(true);
        btnCerrarSesion.setBorderPainted(false);
        btnCerrarSesion.setFocusPainted(false);
        btnCerrarSesion.setCursor(new Cursor(Cursor.HAND_CURSOR));
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lblTitulo = new javax.swing.JLabel();
        lblBienvenida = new javax.swing.JLabel();
        btnCuentas = new javax.swing.JButton();
        btnAsientos = new javax.swing.JButton();
        btnOperaciones = new javax.swing.JButton();
        btnReportes = new javax.swing.JButton();
        btnProductos = new javax.swing.JButton();
        btnCerrarSesion = new javax.swing.JButton();

        setBackground(new java.awt.Color(248, 250, 252));
        setPreferredSize(new java.awt.Dimension(220, 700));

        lblTitulo.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        lblTitulo.setForeground(new java.awt.Color(15, 23, 42));
        lblTitulo.setText("SISTEMA CONTABLE");

        lblBienvenida.setFont(new java.awt.Font("Segoe UI", 0, 12)); // NOI18N
        lblBienvenida.setForeground(new java.awt.Color(100, 116, 139));
        lblBienvenida.setText("Bienvenido, Contador");

        btnCuentas.setBackground(new java.awt.Color(79, 70, 229));
        btnCuentas.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCuentas.setForeground(new java.awt.Color(255, 255, 255));
        btnCuentas.setText("Catálogo de Cuentas");
        btnCuentas.addActionListener(this::btnCuentasActionPerformed);

        btnAsientos.setBackground(new java.awt.Color(79, 70, 229));
        btnAsientos.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnAsientos.setForeground(new java.awt.Color(255, 255, 255));
        btnAsientos.setText("Libro Diario");
        btnAsientos.addActionListener(this::btnAsientosActionPerformed);

        btnOperaciones.setBackground(new java.awt.Color(79, 70, 229));
        btnOperaciones.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnOperaciones.setForeground(new java.awt.Color(255, 255, 255));
        btnOperaciones.setText("Libro Mayor");
        btnOperaciones.addActionListener(this::btnOperacionesActionPerformed);

        btnReportes.setBackground(new java.awt.Color(79, 70, 229));
        btnReportes.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnReportes.setForeground(new java.awt.Color(255, 255, 255));
        btnReportes.setText("Estados Financieros");
        btnReportes.addActionListener(this::btnReportesActionPerformed);

        btnProductos.setBackground(new java.awt.Color(79, 70, 229));
        btnProductos.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnProductos.setForeground(new java.awt.Color(255, 255, 255));
        btnProductos.setText("Productos");
        btnProductos.addActionListener(this::btnProductosActionPerformed);

        btnCerrarSesion.setBackground(new java.awt.Color(225, 29, 72));
        btnCerrarSesion.setFont(new java.awt.Font("Segoe UI", 1, 13)); // NOI18N
        btnCerrarSesion.setForeground(new java.awt.Color(255, 255, 255));
        btnCerrarSesion.setText("Cerrar Sesión");
        btnCerrarSesion.addActionListener(this::btnCerrarSesionActionPerformed);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(lblTitulo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(lblBienvenida, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnCuentas, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(btnAsientos, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(btnOperaciones, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(btnReportes, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(btnProductos, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE)
                    .addComponent(btnCerrarSesion, javax.swing.GroupLayout.DEFAULT_SIZE, 188, Short.MAX_VALUE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(lblTitulo)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblBienvenida)
                .addGap(28, 28, 28)
                .addComponent(btnCuentas, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnAsientos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnOperaciones, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnReportes, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(btnProductos, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 280, Short.MAX_VALUE)
                .addComponent(btnCerrarSesion, javax.swing.GroupLayout.PREFERRED_SIZE, 38, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(25, 25, 25))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void btnCuentasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCuentasActionPerformed
        if (menuPrincipal != null) {
            FrmCuentas frm = new FrmCuentas(usuario, menuPrincipal);
            menuPrincipal.mostrarVentana(frm);
        }
    }//GEN-LAST:event_btnCuentasActionPerformed

    private void btnAsientosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAsientosActionPerformed
        if (menuPrincipal != null) {
            FrmLibroDiario frm = new FrmLibroDiario(usuario, menuPrincipal);
            menuPrincipal.mostrarVentana(frm);
        }
    }//GEN-LAST:event_btnAsientosActionPerformed

    private void btnOperacionesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnOperacionesActionPerformed
        if (menuPrincipal != null) {
            FrmLibroMayor frm = new FrmLibroMayor(usuario, menuPrincipal);
            menuPrincipal.mostrarVentana(frm);
        }
    }//GEN-LAST:event_btnOperacionesActionPerformed

    private void btnReportesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnReportesActionPerformed
        if (menuPrincipal != null) {
            FrmEstadosFinancieros frm = new FrmEstadosFinancieros(usuario, menuPrincipal);
            menuPrincipal.mostrarVentana(frm);
        }
    }//GEN-LAST:event_btnReportesActionPerformed

    private void btnProductosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnProductosActionPerformed
        if (menuPrincipal != null) {
            FrmProductos frm = new FrmProductos(usuario, menuPrincipal);
            menuPrincipal.mostrarVentana(frm);
        }
    }//GEN-LAST:event_btnProductosActionPerformed

    private void btnCerrarSesionActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnCerrarSesionActionPerformed
        FrmLogin frmLogin = new FrmLogin();
        frmLogin.setVisible(true);
        if (menuPrincipal != null) {
            menuPrincipal.dispose();
        }
    }//GEN-LAST:event_btnCerrarSesionActionPerformed

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAsientos;
    private javax.swing.JButton btnCerrarSesion;
    private javax.swing.JButton btnCuentas;
    private javax.swing.JButton btnOperaciones;
    private javax.swing.JButton btnProductos;
    private javax.swing.JButton btnReportes;
    private javax.swing.JLabel lblBienvenida;
    private javax.swing.JLabel lblTitulo;
    // End of variables declaration//GEN-END:variables
}
