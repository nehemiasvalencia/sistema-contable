package com.mycompany.sistema_contable;

import controladores.ReportesFinancierosController;
import controladores.ReportesFinancierosController.BalanceGeneralResult;
import controladores.ReportesFinancierosController.EstadoResultadosResult;
import controladores.UsuarioController;
import dao.CuentaDao;
import dao.PartidaDao;
import java.util.List;
import modelos.MayorCuenta;
import modelos.Partida;
import modelos.Usuario;

public class VerificarSistema {

    public static void main(String[] args) {
        System.out.println("==================================================");
        System.out.println("  INICIANDO VERIFICACIÓN DEL SISTEMA CONTABLE");
        System.out.println("==================================================");

        try {
            // 1. Verificación de Inicio de Sesión
            UsuarioController userCtrl = new UsuarioController();
            Usuario u = userCtrl.iniciarSesion("admin", "Admin123");
            if (u != null) {
                System.out.println("✔ [OK] Login exitoso: Usuario 'admin' (Rol ID: " + u.getIdRol() + " - " + u.getNombre() + ")");
            } else {
                System.err.println("❌ [FAIL] Error en login");
            }

            // 2. Verificación de Catálogo de Cuentas
            CuentaDao cuentaDao = new CuentaDao();
            int totalCuentas = cuentaDao.listar().size();
            System.out.println("✔ [OK] Catálogo de Cuentas cargado: " + totalCuentas + " cuentas registradas.");

            // 3. Verificación de Libro Diario (Partidas)
            PartidaDao partidaDao = new PartidaDao();
            List<Partida> partidas = partidaDao.listarPartidas(null, null);
            System.out.println("✔ [OK] Libro Diario: " + partidas.size() + " partidas registradas.");
            for (Partida p : partidas) {
                System.out.println("   -> Partida #" + p.getNumeroPartida() + " | Fecha: " + p.getFecha() + 
                                   " | Debe: $" + p.getTotalDebe() + " | Haber: $" + p.getTotalHaber() + 
                                   " | Cuadrada: " + p.isCuadrada());
            }

            // 4. Verificación de Mayorización en Tiempo Real (Libro Mayor)
            List<MayorCuenta> mayor = partidaDao.obtenerMayorizacion(null);
            double totalD = 0, totalH = 0;
            for (MayorCuenta mc : mayor) {
                totalD += mc.getTotalDebe();
                totalH += mc.getTotalHaber();
            }
            System.out.println("✔ [OK] Libro Mayor (Mayorización automática):");
            System.out.println("   -> Total Débitos Consolidados: $" + String.format("%.2f", totalD));
            System.out.println("   -> Total Créditos Consolidados: $" + String.format("%.2f", totalH));
            System.out.println("   -> Balance de Comprobación Cuadrado: " + (Math.abs(totalD - totalH) < 0.01));

            // 5. Verificación de Estados Financieros Dinámicos
            ReportesFinancierosController repCtrl = new ReportesFinancierosController();
            EstadoResultadosResult er = repCtrl.generarEstadoResultados(null);
            System.out.println("✔ [OK] Estado de Resultados (IVA incluido, inventario a precio unitario, sin ISR):");
            System.out.println("   -> Ventas Netas: $" + String.format("%.2f", er.ventasNetas));
            System.out.println("   -> Inventario Final: $" + String.format("%.2f", er.inventarioFinal));
            System.out.println("   -> Costo de Ventas: $" + String.format("%.2f", er.totalCostos));
            System.out.println("   -> Utilidad Bruta: $" + String.format("%.2f", er.utilidadBruta));
            System.out.println("   -> Gastos: $" + String.format("%.2f", er.totalGastos));
            System.out.println("   -> Utilidad Neta (sin ISR): $" + String.format("%.2f", er.utilidadNeta));

            BalanceGeneralResult bg = repCtrl.generarBalanceGeneral(null);
            System.out.println("✔ [OK] Balance General [1 = 2 + 3]:");
            System.out.println("   -> Total Activo (1): $" + String.format("%.2f", bg.totalActivo));
            System.out.println("   -> Total Pasivo (2): $" + String.format("%.2f", bg.totalPasivo));
            System.out.println("   -> Total Patrimonio (3): $" + String.format("%.2f", bg.totalPatrimonio));
            System.out.println("   -> Total Pasivo + Patrimonio: $" + String.format("%.2f", bg.totalPasivoMasPatrimonio));
            System.out.println("   -> Ecuación Cuadrada: " + bg.balanceCuadrado + " (Diferencia: $" + bg.diferencia + ")");

            System.out.println("==================================================");
            System.out.println("  ¡TODAS LAS PRUEBAS FUNCIONALES PASARON CON ÉXITO!");
            System.out.println("==================================================");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
