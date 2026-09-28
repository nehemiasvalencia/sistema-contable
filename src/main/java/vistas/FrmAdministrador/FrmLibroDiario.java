package vistas.FrmAdministrador;

import dao.CuentaDao;
import dao.PartidaDao;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.sql.Date;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSplitPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import modelos.Cuentas;
import modelos.DetallePartida;
import modelos.Partida;
import modelos.Usuario;
import vistas.FrmMenuPrincipal;

/**
 * Módulo de Libro Diario (Registro de Asientos Contables).
 * Implementa la validación obligatoria de Partida Doble exigida en la Guía UNICAES:
 * "El sistema debe bloquear el guardado si el asiento no cumple la Partida Doble".
 */
public class FrmLibroDiario extends javax.swing.JPanel {

    private final Usuario usuario;
    private final FrmMenuPrincipal menuPrincipal;
    private final PartidaDao partidaDao;
    private final CuentaDao cuentaDao;

    private List<Cuentas> listaCuentasDisponibles = new ArrayList<>();
    private final List<DetallePartida> lineasActuales = new ArrayList<>();

    // Componentes visuales
    private JTextField txtNumeroPartida;
    private com.toedter.calendar.JDateChooser dateFecha;
    private JComboBox<String> cmbTipo;
    private JTextField txtConceptoGeneral;

    private JComboBox<String> cmbCuentas;
    private JTextField txtConceptoLinea;
    private JTextField txtParcial;
    private JTextField txtDebe;
    private JTextField txtHaber;

    private JTable tblDetalle;
    private DefaultTableModel modeloDetalle;

    private JLabel lblTotalDebe;
    private JLabel lblTotalHaber;
    private JLabel lblDiferencia;
    private JLabel lblEstadoCuadratura;

    private JButton btnGuardar;
    private JButton btnLimpiar;
    private JButton btnAgregarLinea;
    private JButton btnEliminarLinea;

    private JTable tblHistorial;
    private DefaultTableModel modeloHistorial;

    public FrmLibroDiario(Usuario usuario, FrmMenuPrincipal menuPrincipal) {
        this.usuario = usuario;
        this.menuPrincipal = menuPrincipal;
        this.partidaDao = new PartidaDao();
        
        CuentaDao tempDao = null;
        try {
            tempDao = new CuentaDao();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al conectar con el catálogo: " + e.getMessage());
        }
        this.cuentaDao = tempDao;

        initCustomComponents();
        cargarCuentasEnCombo();
        cargarSiguienteNumero();
        cargarHistorialPartidas();
        actualizarCuadratura();
    }

    private void initCustomComponents() {
        this.setLayout(new BorderLayout());
        this.setBackground(new Color(241, 245, 249));

        // 1. ENCABEZADO
        JPanel pnlHeader = new JPanel(new BorderLayout());
        pnlHeader.setBackground(new Color(30, 41, 59));
        pnlHeader.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        JLabel lblTitulo = new JLabel("LIBRO DIARIO (REGISTRO DE ASIENTOS)");
        lblTitulo.setFont(new Font("Segoe UI", Font.BOLD, 22));
        lblTitulo.setForeground(Color.WHITE);

        JLabel lblSubtitulo = new JLabel("Registro contable con validación en tiempo real del principio de Partida Doble");
        lblSubtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 13));
        lblSubtitulo.setForeground(new Color(203, 213, 225));

        JPanel pnlTitulos = new JPanel();
        pnlTitulos.setLayout(new javax.swing.BoxLayout(pnlTitulos, javax.swing.BoxLayout.Y_AXIS));
        pnlTitulos.setOpaque(false);
        pnlTitulos.add(lblTitulo);
        pnlTitulos.add(javax.swing.Box.createVerticalStrut(4));
        pnlTitulos.add(lblSubtitulo);

        pnlHeader.add(pnlTitulos, BorderLayout.WEST);
        this.add(pnlHeader, BorderLayout.NORTH);

        // 2. PANEL PRINCIPAL CON SPLIT
        JPanel pnlIzquierdo = new JPanel(new BorderLayout(0, 10));
        pnlIzquierdo.setBackground(new Color(241, 245, 249));
        pnlIzquierdo.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));

        // CABECERA DEL ASIENTO
        JPanel pnlCabecera = new JPanel(new GridBagLayout());
        pnlCabecera.setBackground(Color.WHITE);
        pnlCabecera.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)), "Datos del Asiento / Partida", 0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(30, 41, 59)),
            BorderFactory.createEmptyBorder(8, 12, 8, 12)
        ));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(4, 6, 4, 6);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        txtNumeroPartida = new JTextField("1", 6);
        txtNumeroPartida.setEditable(false);
        txtNumeroPartida.setFont(new Font("Segoe UI", Font.BOLD, 14));
        txtNumeroPartida.setHorizontalAlignment(JTextField.CENTER);

        dateFecha = new com.toedter.calendar.JDateChooser();
        dateFecha.setDate(new java.util.Date());
        dateFecha.setPreferredSize(new Dimension(140, 28));

        cmbTipo = new JComboBox<>(new String[]{"DIARIO", "APERTURA", "COMPRA", "VENTA", "GASTO", "AJUSTE", "CIERRE", "OTRA"});
        cmbTipo.setPreferredSize(new Dimension(130, 28));

        txtConceptoGeneral = new JTextField();
        txtConceptoGeneral.setPreferredSize(new Dimension(350, 28));

        // Fila 0
        gbc.gridx = 0; gbc.gridy = 0; gbc.weightx = 0;
        pnlCabecera.add(new JLabel("Partida Nº:"), gbc);
        gbc.gridx = 1; gbc.weightx = 0.2;
        pnlCabecera.add(txtNumeroPartida, gbc);

        gbc.gridx = 2; gbc.weightx = 0;
        pnlCabecera.add(new JLabel("Fecha:"), gbc);
        gbc.gridx = 3; gbc.weightx = 0.3;
        pnlCabecera.add(dateFecha, gbc);

        gbc.gridx = 4; gbc.weightx = 0;
        pnlCabecera.add(new JLabel("Tipo:"), gbc);
        gbc.gridx = 5; gbc.weightx = 0.3;
        pnlCabecera.add(cmbTipo, gbc);

        // Fila 1
        gbc.gridx = 0; gbc.gridy = 1; gbc.weightx = 0;
        pnlCabecera.add(new JLabel("Concepto:"), gbc);
        gbc.gridx = 1; gbc.gridwidth = 5; gbc.weightx = 1.0;
        pnlCabecera.add(txtConceptoGeneral, gbc);
        gbc.gridwidth = 1;

        // INGRESO DE LÍNEAS DE DETALLE
        JPanel pnlIngresoLinea = new JPanel(new GridBagLayout());
        pnlIngresoLinea.setBackground(Color.WHITE);
        pnlIngresoLinea.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)), "Movimiento Contable (Código/Cuenta, Debe y Haber)", 0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(79, 70, 229)),
            BorderFactory.createEmptyBorder(6, 12, 8, 12)
        ));

        cmbCuentas = new JComboBox<>();
        cmbCuentas.setPreferredSize(new Dimension(280, 28));

        txtConceptoLinea = new JTextField("Movimiento de cuenta", 15);
        txtConceptoLinea.setPreferredSize(new Dimension(150, 28));

        txtParcial = new JTextField("0.00", 7);
        txtParcial.setHorizontalAlignment(JTextField.RIGHT);
        txtParcial.setPreferredSize(new Dimension(80, 28));

        txtDebe = new JTextField("0.00", 7);
        txtDebe.setHorizontalAlignment(JTextField.RIGHT);
        txtDebe.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtDebe.setPreferredSize(new Dimension(90, 28));

        txtHaber = new JTextField("0.00", 7);
        txtHaber.setHorizontalAlignment(JTextField.RIGHT);
        txtHaber.setFont(new Font("Segoe UI", Font.BOLD, 12));
        txtHaber.setPreferredSize(new Dimension(90, 28));

        btnAgregarLinea = new JButton("➕ Agregar al Asiento");
        btnAgregarLinea.setBackground(new Color(79, 70, 229));
        btnAgregarLinea.setForeground(Color.WHITE);
        btnAgregarLinea.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnAgregarLinea.setFocusPainted(false);
        btnAgregarLinea.addActionListener(e -> agregarLineaADetalle());

        btnEliminarLinea = new JButton("❌ Quitar Línea");
        btnEliminarLinea.setBackground(new Color(239, 68, 68));
        btnEliminarLinea.setForeground(Color.WHITE);
        btnEliminarLinea.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnEliminarLinea.setFocusPainted(false);
        btnEliminarLinea.addActionListener(e -> eliminarLineaSeleccionada());

        // Fila Línea 0
        GridBagConstraints gbcL = new GridBagConstraints();
        gbcL.insets = new Insets(4, 5, 4, 5);
        gbcL.fill = GridBagConstraints.HORIZONTAL;

        gbcL.gridx = 0; gbcL.gridy = 0;
        pnlIngresoLinea.add(new JLabel("Cuenta Contable:"), gbcL);
        gbcL.gridx = 1; gbcL.gridwidth = 2;
        pnlIngresoLinea.add(cmbCuentas, gbcL);
        gbcL.gridwidth = 1;

        gbcL.gridx = 3;
        pnlIngresoLinea.add(new JLabel("Ref/Concepto:"), gbcL);
        gbcL.gridx = 4; gbcL.gridwidth = 2;
        pnlIngresoLinea.add(txtConceptoLinea, gbcL);
        gbcL.gridwidth = 1;

        // Fila Línea 1: Parcial, Debe, Haber, Botones
        gbcL.gridx = 0; gbcL.gridy = 1;
        pnlIngresoLinea.add(new JLabel("Parcial ($):"), gbcL);
        gbcL.gridx = 1;
        pnlIngresoLinea.add(txtParcial, gbcL);

        gbcL.gridx = 2;
        pnlIngresoLinea.add(new JLabel("Debe ($):"), gbcL);
        gbcL.gridx = 3;
        pnlIngresoLinea.add(txtDebe, gbcL);

        gbcL.gridx = 4;
        pnlIngresoLinea.add(new JLabel("Haber ($):"), gbcL);
        gbcL.gridx = 5;
        pnlIngresoLinea.add(txtHaber, gbcL);

        gbcL.gridx = 6;
        pnlIngresoLinea.add(btnAgregarLinea, gbcL);
        gbcL.gridx = 7;
        pnlIngresoLinea.add(btnEliminarLinea, gbcL);

        // TABLA DE DETALLE
        modeloDetalle = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Línea", "Código", "Cuenta", "Concepto/Referencia", "Parcial ($)", "Debe ($)", "Haber ($)"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblDetalle = new JTable(modeloDetalle);
        tblDetalle.setRowHeight(26);
        tblDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        tblDetalle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 12));
        tblDetalle.getTableHeader().setBackground(new Color(30, 41, 59));
        tblDetalle.getTableHeader().setForeground(Color.WHITE);

        // Alineación derecha para números
        DefaultTableCellRenderer rightRenderer = new DefaultTableCellRenderer();
        rightRenderer.setHorizontalAlignment(SwingConstants.RIGHT);
        tblDetalle.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);
        tblDetalle.getColumnModel().getColumn(5).setCellRenderer(rightRenderer);
        tblDetalle.getColumnModel().getColumn(6).setCellRenderer(rightRenderer);

        JScrollPane scrollDetalle = new JScrollPane(tblDetalle);
        scrollDetalle.setPreferredSize(new Dimension(650, 180));
        scrollDetalle.setBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)));

        // PANEL INFERIOR: TOTALES, VALIDACIÓN Y BOTONES
        JPanel pnlTotalesYControl = new JPanel(new BorderLayout(10, 10));
        pnlTotalesYControl.setBackground(Color.WHITE);
        pnlTotalesYControl.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(new Color(203, 213, 225)),
            BorderFactory.createEmptyBorder(10, 15, 10, 15)
        ));

        // Subpanel de totales
        JPanel pnlSuma = new JPanel(new FlowLayout(FlowLayout.RIGHT, 20, 5));
        pnlSuma.setOpaque(false);

        lblTotalDebe = new JLabel("Total Debe: $0.00");
        lblTotalDebe.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalDebe.setForeground(new Color(30, 41, 59));

        lblTotalHaber = new JLabel("Total Haber: $0.00");
        lblTotalHaber.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblTotalHaber.setForeground(new Color(30, 41, 59));

        lblDiferencia = new JLabel("Diferencia: $0.00");
        lblDiferencia.setFont(new Font("Segoe UI", Font.BOLD, 14));
        lblDiferencia.setForeground(new Color(239, 68, 68));

        pnlSuma.add(lblTotalDebe);
        pnlSuma.add(lblTotalHaber);
        pnlSuma.add(lblDiferencia);

        // BANNER DE VALIDACIÓN OBLIGATORIA
        lblEstadoCuadratura = new JLabel("ESTADO DE PARTIDA DOBLE: INICIAL", SwingConstants.CENTER);
        lblEstadoCuadratura.setOpaque(true);
        lblEstadoCuadratura.setFont(new Font("Segoe UI", Font.BOLD, 13));
        lblEstadoCuadratura.setPreferredSize(new Dimension(600, 32));

        // Botones de acción
        JPanel pnlAcciones = new JPanel(new FlowLayout(FlowLayout.RIGHT, 15, 5));
        pnlAcciones.setOpaque(false);

        btnLimpiar = new JButton("🧹 Limpiar Asiento");
        btnLimpiar.setFont(new Font("Segoe UI", Font.BOLD, 12));
        btnLimpiar.setBackground(new Color(100, 116, 139));
        btnLimpiar.setForeground(Color.WHITE);
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        btnGuardar = new JButton("💾 Guardar Asiento Contable");
        btnGuardar.setFont(new Font("Segoe UI", Font.BOLD, 13));
        btnGuardar.setBackground(new Color(34, 197, 94));
        btnGuardar.setForeground(Color.WHITE);
        btnGuardar.setEnabled(false); // BLOQUEADO INICIALMENTE
        btnGuardar.addActionListener(e -> guardarAsiento());

        pnlAcciones.add(btnLimpiar);
        pnlAcciones.add(btnGuardar);

        pnlTotalesYControl.add(pnlSuma, BorderLayout.NORTH);
        pnlTotalesYControl.add(lblEstadoCuadratura, BorderLayout.CENTER);
        pnlTotalesYControl.add(pnlAcciones, BorderLayout.SOUTH);

        // Ensamblar panel izquierdo
        JPanel pnlTopForms = new JPanel(new BorderLayout(0, 8));
        pnlTopForms.setOpaque(false);
        pnlTopForms.add(pnlCabecera, BorderLayout.NORTH);
        pnlTopForms.add(pnlIngresoLinea, BorderLayout.CENTER);

        pnlIzquierdo.add(pnlTopForms, BorderLayout.NORTH);
        pnlIzquierdo.add(scrollDetalle, BorderLayout.CENTER);
        pnlIzquierdo.add(pnlTotalesYControl, BorderLayout.SOUTH);

        // 3. PANEL DERECHO: HISTORIAL DE PARTIDAS
        JPanel pnlDerecho = new JPanel(new BorderLayout(0, 10));
        pnlDerecho.setBackground(Color.WHITE);
        pnlDerecho.setBorder(BorderFactory.createCompoundBorder(
            BorderFactory.createTitledBorder(BorderFactory.createLineBorder(new Color(203, 213, 225)), "Historial de Partidas Registradas", 0, 0, new Font("Segoe UI", Font.BOLD, 12), new Color(30, 41, 59)),
            BorderFactory.createEmptyBorder(8, 8, 8, 8)
        ));

        modeloHistorial = new DefaultTableModel(
            new Object[][]{},
            new String[]{"Partida #", "Fecha", "Tipo", "Concepto", "Total ($)", "Estado"}
        ) {
            @Override
            public boolean isCellEditable(int row, int col) { return false; }
        };

        tblHistorial = new JTable(modeloHistorial);
        tblHistorial.setRowHeight(24);
        tblHistorial.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        tblHistorial.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 11));
        tblHistorial.getTableHeader().setBackground(new Color(30, 41, 59));
        tblHistorial.getTableHeader().setForeground(Color.WHITE);
        tblHistorial.getColumnModel().getColumn(4).setCellRenderer(rightRenderer);

        tblHistorial.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                if (evt.getClickCount() == 2) {
                    cargarDetallesDePartidaHistorial();
                }
            }
        });

        JScrollPane scrollHistorial = new JScrollPane(tblHistorial);
        scrollHistorial.setPreferredSize(new Dimension(380, 400));

        JPanel pnlBarraHistorial = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 5));
        pnlBarraHistorial.setOpaque(false);
        JButton btnVerDetalle = new JButton("👁 Ver Detalle");
        btnVerDetalle.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnVerDetalle.addActionListener(e -> cargarDetallesDePartidaHistorial());

        JButton btnRefrescar = new JButton("🔄 Actualizar");
        btnRefrescar.setFont(new Font("Segoe UI", Font.PLAIN, 11));
        btnRefrescar.addActionListener(e -> cargarHistorialPartidas());

        pnlBarraHistorial.add(btnVerDetalle);
        pnlBarraHistorial.add(btnRefrescar);

        pnlDerecho.add(pnlBarraHistorial, BorderLayout.NORTH);
        pnlDerecho.add(scrollHistorial, BorderLayout.CENTER);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, pnlIzquierdo, pnlDerecho);
        splitPane.setResizeWeight(0.68);
        this.add(splitPane, BorderLayout.CENTER);
    }

    private void cargarCuentasEnCombo() {
        if (cuentaDao == null) return;
        cmbCuentas.removeAllItems();
        listaCuentasDisponibles = cuentaDao.listar();
        for (Cuentas c : listaCuentasDisponibles) {
            if (c.isPermiteMovimientos() && c.isEstado()) {
                cmbCuentas.addItem(c.getCodigo() + " - " + c.getNombre());
            }
        }
    }

    private void cargarSiguienteNumero() {
        try {
            int sig = partidaDao.obtenerSiguienteNumero(1);
            txtNumeroPartida.setText(String.valueOf(sig));
        } catch (SQLException e) {
            txtNumeroPartida.setText("1");
        }
    }

    private void agregarLineaADetalle() {
        int idx = cmbCuentas.getSelectedIndex();
        if (idx < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una cuenta contable.");
            return;
        }

        // Buscar cuenta
        String item = (String) cmbCuentas.getSelectedItem();
        String codigo = item.split(" - ")[0];

        Cuentas cuentaSel = null;
        for (Cuentas c : listaCuentasDisponibles) {
            if (c.getCodigo().equals(codigo)) {
                cuentaSel = c;
                break;
            }
        }

        if (cuentaSel == null) return;

        double parcial = 0.0;
        double debe = 0.0;
        double haber = 0.0;

        try {
            parcial = Double.parseDouble(txtParcial.getText().trim());
        } catch (NumberFormatException ignored) {}

        try {
            debe = Double.parseDouble(txtDebe.getText().trim());
        } catch (NumberFormatException ignored) {}

        try {
            haber = Double.parseDouble(txtHaber.getText().trim());
        } catch (NumberFormatException ignored) {}

        if (debe == 0 && haber == 0 && parcial == 0) {
            JOptionPane.showMessageDialog(this, "Debe ingresar un monto en Debe, Haber o Parcial.", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        if (debe > 0 && haber > 0) {
            JOptionPane.showMessageDialog(this, "Una línea contable individual no puede tener montos en el Debe y en el Haber simultáneamente.", "Regla Contable", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int lineaNum = lineasActuales.size() + 1;
        String concepto = txtConceptoLinea.getText().trim();
        if (concepto.isEmpty()) concepto = txtConceptoGeneral.getText().trim();

        DetallePartida det = new DetallePartida(
            cuentaSel.getIdCuenta(),
            cuentaSel.getCodigo(),
            cuentaSel.getNombre(),
            lineaNum,
            concepto,
            parcial,
            debe,
            haber
        );

        lineasActuales.add(det);
        modeloDetalle.addRow(new Object[]{
            lineaNum,
            cuentaSel.getCodigo(),
            cuentaSel.getNombre(),
            concepto,
            String.format("%.2f", parcial),
            String.format("%.2f", debe),
            String.format("%.2f", haber)
        });

        // Limpiar inputs
        txtParcial.setText("0.00");
        txtDebe.setText("0.00");
        txtHaber.setText("0.00");
        txtDebe.requestFocus();

        actualizarCuadratura();
    }

    private void eliminarLineaSeleccionada() {
        int fila = tblDetalle.getSelectedRow();
        if (fila >= 0) {
            lineasActuales.remove(fila);
            modeloDetalle.removeRow(fila);
            // Renumerar
            for (int i = 0; i < lineasActuales.size(); i++) {
                lineasActuales.get(i).setLinea(i + 1);
                modeloDetalle.setValueAt(i + 1, i, 0);
            }
            actualizarCuadratura();
        } else {
            JOptionPane.showMessageDialog(this, "Seleccione la línea que desea eliminar.");
        }
    }

    /**
     * Validación obligatoria de Partida Doble en tiempo real:
     * Si Total Debe == Total Haber (y > 0) -> Desbloquea botón Guardar.
     * Si no -> Bloquea el botón Guardar.
     */
    private void actualizarCuadratura() {
        double totalDebe = 0.0;
        double totalHaber = 0.0;

        for (DetallePartida d : lineasActuales) {
            totalDebe += d.getDebe();
            totalHaber += d.getHaber();
        }

        totalDebe = Math.round(totalDebe * 100.0) / 100.0;
        totalHaber = Math.round(totalHaber * 100.0) / 100.0;
        double diferencia = Math.round(Math.abs(totalDebe - totalHaber) * 100.0) / 100.0;

        lblTotalDebe.setText("Total Debe: $" + String.format("%.2f", totalDebe));
        lblTotalHaber.setText("Total Haber: $" + String.format("%.2f", totalHaber));
        lblDiferencia.setText("Diferencia: $" + String.format("%.2f", diferencia));

        if (totalDebe > 0 && diferencia < 0.001) {
            lblEstadoCuadratura.setText("✔ PARTIDA CUADRADA: PARTIDA DOBLE CUMPLIDA (DEBE = HABER)");
            lblEstadoCuadratura.setBackground(new Color(220, 252, 231)); // Verde claro
            lblEstadoCuadratura.setForeground(new Color(22, 101, 52));   // Verde oscuro
            lblDiferencia.setForeground(new Color(22, 101, 52));
            btnGuardar.setEnabled(true);
            btnGuardar.setToolTipText("Haga clic para guardar la partida");
        } else {
            if (totalDebe == 0 && totalHaber == 0) {
                lblEstadoCuadratura.setText("⚠ INGRESE LÍNEAS AL ASIENTO PARA VERIFICAR LA PARTIDA DOBLE");
                lblEstadoCuadratura.setBackground(new Color(241, 245, 249));
                lblEstadoCuadratura.setForeground(new Color(71, 85, 105));
            } else {
                lblEstadoCuadratura.setText("✖ BLOQUEO: EL ASIENTO NO CUMPLE LA PARTIDA DOBLE (DIFERENCIA: $" + String.format("%.2f", diferencia) + ")");
                lblEstadoCuadratura.setBackground(new Color(254, 226, 226)); // Rojo claro
                lblEstadoCuadratura.setForeground(new Color(153, 27, 27));   // Rojo oscuro
            }
            lblDiferencia.setForeground(new Color(220, 38, 38));
            btnGuardar.setEnabled(false);
            btnGuardar.setToolTipText("El sistema bloquea el guardado hasta que el asiento cumpla la Partida Doble");
        }
    }

    private void guardarAsiento() {
        if (txtConceptoGeneral.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Ingrese el concepto general del asiento.", "Validación", JOptionPane.WARNING_MESSAGE);
            txtConceptoGeneral.requestFocus();
            return;
        }

        if (dateFecha.getDate() == null) {
            JOptionPane.showMessageDialog(this, "Seleccione una fecha válida.", "Validación", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Partida partida = new Partida();
        partida.setIdPeriodo(1);
        partida.setIdEmpresa(1);
        partida.setNumeroPartida(Integer.parseInt(txtNumeroPartida.getText().trim()));
        partida.setFecha(new Date(dateFecha.getDate().getTime()));
        partida.setTipo((String) cmbTipo.getSelectedItem());
        partida.setConcepto(txtConceptoGeneral.getText().trim());
        partida.setIdUsuario(usuario != null ? usuario.getIdUsuario() : 1);

        for (DetallePartida d : lineasActuales) {
            partida.agregarDetalle(d);
        }

        try {
            boolean exito = partidaDao.guardar(partida);
            if (exito) {
                JOptionPane.showMessageDialog(this, "¡Asiento Contable Nº " + partida.getNumeroPartida() + " guardado exitosamente!", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                limpiarFormulario();
                cargarSiguienteNumero();
                cargarHistorialPartidas();
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, ex.getMessage(), "Error de Validación", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarFormulario() {
        lineasActuales.clear();
        modeloDetalle.setRowCount(0);
        txtConceptoGeneral.setText("");
        txtConceptoLinea.setText("Movimiento de cuenta");
        txtParcial.setText("0.00");
        txtDebe.setText("0.00");
        txtHaber.setText("0.00");
        actualizarCuadratura();
    }

    private void cargarHistorialPartidas() {
        modeloHistorial.setRowCount(0);
        try {
            List<Partida> lista = partidaDao.listarPartidas(null, null);
            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
            for (Partida p : lista) {
                modeloHistorial.addRow(new Object[]{
                    "#" + p.getNumeroPartida(),
                    sdf.format(p.getFecha()),
                    p.getTipo(),
                    p.getConcepto(),
                    String.format("%.2f", p.getTotalDebe()),
                    p.getEstado()
                });
            }
        } catch (SQLException e) {
            System.err.println("Error al cargar historial: " + e.getMessage());
        }
    }

    private void cargarDetallesDePartidaHistorial() {
        int fila = tblHistorial.getSelectedRow();
        if (fila < 0) {
            JOptionPane.showMessageDialog(this, "Seleccione una partida de la lista para ver su detalle.");
            return;
        }

        String numStr = (String) modeloHistorial.getValueAt(fila, 0);
        int num = Integer.parseInt(numStr.replace("#", ""));

        try {
            List<Partida> lista = partidaDao.listarPartidas(null, null);
            int idPartida = 0;
            String concepto = "";
            for (Partida p : lista) {
                if (p.getNumeroPartida() == num) {
                    idPartida = p.getIdPartida();
                    concepto = p.getConcepto();
                    break;
                }
            }

            if (idPartida > 0) {
                List<DetallePartida> dets = partidaDao.obtenerDetallesPartida(idPartida);
                StringBuilder sb = new StringBuilder();
                sb.append("DETALLE DE LA PARTIDA Nº ").append(num).append("\n");
                sb.append("Concepto: ").append(concepto).append("\n\n");
                sb.append(String.format("%-10s %-25s %-12s %-12s %-12s\n", "CÓDIGO", "CUENTA", "PARCIAL", "DEBE", "HABER"));
                sb.append("---------------------------------------------------------------------------\n");
                for (DetallePartida d : dets) {
                    sb.append(String.format("%-10s %-25s %10.2f %10.2f %10.2f\n",
                        d.getCodigoCuenta(),
                        d.getNombreCuenta().length() > 24 ? d.getNombreCuenta().substring(0, 24) : d.getNombreCuenta(),
                        d.getParcial(),
                        d.getDebe(),
                        d.getHaber()
                    ));
                }
                javax.swing.JTextArea ta = new javax.swing.JTextArea(sb.toString());
                ta.setFont(new Font("Monospaced", Font.PLAIN, 12));
                ta.setEditable(false);
                JOptionPane.showMessageDialog(this, new JScrollPane(ta), "Detalle Partida #" + num, JOptionPane.PLAIN_MESSAGE);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al consultar detalles: " + e.getMessage());
        }
    }
}
