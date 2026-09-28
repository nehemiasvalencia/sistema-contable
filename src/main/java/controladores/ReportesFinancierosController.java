package controladores;

import dao.PartidaDao;
import dao.ProductoDao;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import modelos.MayorCuenta;

/**
 * Controlador de Estados Financieros.
 * Estado de Resultados analítico: inventario inicial + compras − inventario final
 * (inventario final = existencia × precio unitario de compra). Sin ISR ni reserva legal.
 * Balance General: Código 1 (Activo) = Código 2 (Pasivo) + Código 3 (Capital) + utilidad.
 */
public class ReportesFinancierosController {

    private final PartidaDao partidaDao;

    public ReportesFinancierosController() {
        this.partidaDao = new PartidaDao();
    }

    public static class LineaReporte {
        private String codigo;
        private String nombre;
        private double saldo;
        private String categoria;
        private double parcial;

        public LineaReporte(String codigo, String nombre, double saldo, String categoria) {
            this(codigo, nombre, saldo, categoria, 0.0);
        }

        public LineaReporte(String codigo, String nombre, double saldo, String categoria, double parcial) {
            this.codigo = codigo;
            this.nombre = nombre;
            this.saldo = saldo;
            this.categoria = categoria;
            this.parcial = parcial;
        }

        public String getCodigo() { return codigo; }
        public String getNombre() { return nombre; }
        public double getSaldo() { return saldo; }
        public String getCategoria() { return categoria; }
        public double getParcial() { return parcial; }
    }

    public static class BalanceGeneralResult {
        public List<LineaReporte> activos = new ArrayList<>();
        public List<LineaReporte> pasivos = new ArrayList<>();
        public List<LineaReporte> patrimonio = new ArrayList<>();
        public double totalActivo = 0.0;
        public double totalPasivo = 0.0;
        public double totalPatrimonio = 0.0;
        public double utilidadEjercicio = 0.0;
        public double totalPasivoMasPatrimonio = 0.0;
        public boolean balanceCuadrado = false;
        public double diferencia = 0.0;
    }

    public static class EstadoResultadosResult {
        public List<LineaReporte> ingresos = new ArrayList<>();
        public List<LineaReporte> costos = new ArrayList<>();
        public List<LineaReporte> gastos = new ArrayList<>();
        public List<LineaReporte> lineas = new ArrayList<>();
        public double ventas = 0.0;
        public double devolucionesVentas = 0.0;
        public double ventasNetas = 0.0;
        public double inventarioInicial = 0.0;
        public double compras = 0.0;
        public double devolucionesCompras = 0.0;
        public double comprasNetas = 0.0;
        public double mercaderiaDisponible = 0.0;
        public double inventarioFinal = 0.0;
        public double totalIngresos = 0.0;
        public double totalCostos = 0.0;
        public double totalGastos = 0.0;
        public double utilidadBruta = 0.0;
        public double utilidadAntesImpuestos = 0.0;
        public double reservaLegal = 0.0;
        public double utilidadNeta = 0.0;
        public boolean esUtilidad = true;
        public boolean aplicaIsr = false;
    }

    private static double r2(double v) {
        return Math.round(v * 100.0) / 100.0;
    }

    private static double saldoDeudor(MayorCuenta mc) {
        return mc.getTotalDebe() - mc.getTotalHaber();
    }

    private static double saldoAcreedor(MayorCuenta mc) {
        return mc.getTotalHaber() - mc.getTotalDebe();
    }

    // =========================================================
    // ESTADO DE RESULTADOS (IVA incluido en asientos, sin ISR)
    // =========================================================
    public EstadoResultadosResult generarEstadoResultados(Integer idPeriodo) throws SQLException {
        EstadoResultadosResult res = new EstadoResultadosResult();
        List<MayorCuenta> mayor = partidaDao.obtenerMayorizacion(idPeriodo);

        double otrosIngresos = 0.0;
        double gastosAdmin = 0.0;
        double gastosVenta = 0.0;
        double gastosFinancieros = 0.0;
        double otrosGastos = 0.0;

        for (MayorCuenta mc : mayor) {
            String cod = mc.getCodigo();

            if (cod.startsWith("5")) {
                if (cod.equals("5102") || cod.equals("5103")) {
                    double saldo = saldoDeudor(mc);
                    res.devolucionesVentas += saldo;
                    res.ingresos.add(new LineaReporte(cod, "(-) " + mc.getNombre(), saldo, "Deducción de Ventas"));
                } else if (cod.startsWith("51")) {
                    double saldo = saldoAcreedor(mc);
                    res.ventas += saldo;
                    res.ingresos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Ingresos de Operación"));
                } else {
                    double saldo = saldoAcreedor(mc);
                    otrosIngresos += saldo;
                    res.ingresos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Otros Ingresos"));
                }
            } else if (cod.equals("110301") || cod.equals("1103")) {
                res.inventarioInicial += saldoDeudor(mc);
            } else if (cod.equals("4102")) {
                res.compras += saldoDeudor(mc);
                res.costos.add(new LineaReporte(cod, mc.getNombre(), saldoDeudor(mc), "Compras"));
            } else if (cod.equals("4103")) {
                double saldo = saldoDeudor(mc);
                res.compras += saldo;
                res.costos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Gastos sobre Compras"));
            } else if (cod.equals("4104")) {
                double saldo = saldoAcreedor(mc);
                res.devolucionesCompras += saldo;
                res.costos.add(new LineaReporte(cod, "(-) " + mc.getNombre(), saldo, "Deducción de Compras"));
            } else if (cod.startsWith("42") || cod.startsWith("43")) {
                double saldo = saldoDeudor(mc);
                if (cod.startsWith("4201")) {
                    gastosAdmin += saldo;
                    res.gastos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Gastos de Administración"));
                } else if (cod.startsWith("4202")) {
                    gastosVenta += saldo;
                    res.gastos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Gastos de Venta"));
                } else if (cod.startsWith("4203")) {
                    gastosFinancieros += saldo;
                    res.gastos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Gastos Financieros"));
                } else {
                    otrosGastos += saldo;
                    res.gastos.add(new LineaReporte(cod, mc.getNombre(), saldo, "Otros Gastos"));
                }
            }
        }

        res.inventarioFinal = 0.0;
        try {
            ProductoDao productoDao = new ProductoDao();
            res.inventarioFinal = productoDao.calcularValorInventario();
        } catch (SQLException ex) {
            res.inventarioFinal = res.inventarioInicial;
        }

        res.ventas = r2(res.ventas);
        res.devolucionesVentas = r2(res.devolucionesVentas);
        res.ventasNetas = r2(res.ventas - res.devolucionesVentas);
        res.inventarioInicial = r2(res.inventarioInicial);
        res.compras = r2(res.compras);
        res.devolucionesCompras = r2(res.devolucionesCompras);
        res.comprasNetas = r2(res.compras - res.devolucionesCompras);
        res.mercaderiaDisponible = r2(res.inventarioInicial + res.comprasNetas);
        res.inventarioFinal = r2(res.inventarioFinal);
        res.totalCostos = r2(res.mercaderiaDisponible - res.inventarioFinal);

        res.totalIngresos = r2(res.ventasNetas + otrosIngresos);
        res.totalGastos = r2(gastosAdmin + gastosVenta + gastosFinancieros + otrosGastos);
        res.utilidadBruta = r2(res.ventasNetas - res.totalCostos);
        res.utilidadAntesImpuestos = r2(res.utilidadBruta - res.totalGastos + otrosIngresos);
        res.reservaLegal = 0.0;
        res.aplicaIsr = false;
        res.utilidadNeta = res.utilidadAntesImpuestos;
        res.esUtilidad = res.utilidadNeta >= 0;

        armarLineasEstadoResultados(res, gastosFinancieros);
        return res;
    }

    private void armarLineasEstadoResultados(EstadoResultadosResult res, double gastosFinancieros) {
        res.lineas.add(new LineaReporte("5101", "Ventas", res.ventas, "TOTAL"));
        res.lineas.add(new LineaReporte("5102", "(-) Devolución sobre ventas", res.devolucionesVentas, "TOTAL"));
        res.lineas.add(new LineaReporte("", "= Ventas Netas", res.ventasNetas, "TOTAL"));
        res.lineas.add(new LineaReporte("110301", "Inventario Inicial", res.inventarioInicial, "TOTAL"));
        res.lineas.add(new LineaReporte("4102", "(+) Compras", res.compras, "PARCIAL"));
        res.lineas.add(new LineaReporte("4104", "(-) Devolución sobre compras", res.devolucionesCompras, "PARCIAL"));
        res.lineas.add(new LineaReporte("", "= Compras Netas", res.comprasNetas, "TOTAL"));
        res.lineas.add(new LineaReporte("", "(+) Inventario Inicial", res.inventarioInicial, "TOTAL"));
        res.lineas.add(new LineaReporte("", "= Mercadería Disponible", res.mercaderiaDisponible, "TOTAL"));
        res.lineas.add(new LineaReporte("110301F", "(-) Inventario Final", res.inventarioFinal, "TOTAL"));
        res.lineas.add(new LineaReporte("4101", "= Costo de Ventas", res.totalCostos, "TOTAL"));
        res.lineas.add(new LineaReporte("", "= Utilidad Bruta", res.utilidadBruta, "TOTAL"));
        res.lineas.add(new LineaReporte("4203", "(-) Gastos Financieros", gastosFinancieros, "TOTAL"));
        for (LineaReporte g : res.gastos) {
            if (!"Gastos Financieros".equals(g.getCategoria())) {
                res.lineas.add(new LineaReporte(g.getCodigo(), "(-) " + g.getNombre(), g.getSaldo(), "TOTAL"));
            }
        }
        res.lineas.add(new LineaReporte("3104", "= Utilidad Neta", res.utilidadNeta, "TOTAL"));
    }

    // =========================================================
    // BALANCE GENERAL (1 = 2 + 3), inventario a precio unitario
    // =========================================================
    public BalanceGeneralResult generarBalanceGeneral(Integer idPeriodo) throws SQLException {
        BalanceGeneralResult res = new BalanceGeneralResult();
        EstadoResultadosResult estRes = generarEstadoResultados(idPeriodo);
        res.utilidadEjercicio = estRes.utilidadNeta;

        List<MayorCuenta> mayor = partidaDao.obtenerMayorizacion(idPeriodo);
        Map<String, LineaReporte> activosAgrupados = new LinkedHashMap<>();
        Map<String, LineaReporte> pasivosAgrupados = new LinkedHashMap<>();

        for (MayorCuenta mc : mayor) {
            String cod = mc.getCodigo();

            if (cod.startsWith("1")) {
                if (cod.startsWith("1103")) {
                    continue;
                }
                double saldo = saldoDeudor(mc);
                if (Math.abs(saldo) <= 0.001) {
                    continue;
                }
                acumular(activosAgrupados, agruparActivo(cod), saldo);
            } else if (cod.startsWith("2")) {
                double saldo = saldoAcreedor(mc);
                if (Math.abs(saldo) <= 0.001) {
                    continue;
                }
                acumular(pasivosAgrupados, agruparPasivo(cod), saldo);
            } else if (cod.startsWith("3")) {
                double saldo = saldoAcreedor(mc);
                if (Math.abs(saldo) > 0.001) {
                    res.patrimonio.add(new LineaReporte(cod, nombrePatrimonio(cod, mc.getNombre()), saldo, "Patrimonio"));
                    res.totalPatrimonio += saldo;
                }
            }
        }

        if (estRes.inventarioFinal > 0.001) {
            activosAgrupados.put("1103", new LineaReporte("1103", "Inventarios", estRes.inventarioFinal, "Activo Corriente"));
        }

        String[] ordenActivo = {"1101", "1102", "1104", "1103", "1201ME", "120102"};
        for (String clave : ordenActivo) {
            LineaReporte a = activosAgrupados.remove(clave);
            if (a != null) {
                res.activos.add(a);
                res.totalActivo += a.getSaldo();
            }
        }
        for (LineaReporte a : activosAgrupados.values()) {
            res.activos.add(a);
            res.totalActivo += a.getSaldo();
        }
        String[] ordenPasivo = {"2101", "2102", "2104", "2201"};
        for (String clave : ordenPasivo) {
            LineaReporte p = pasivosAgrupados.remove(clave);
            if (p != null) {
                res.pasivos.add(p);
                res.totalPasivo += p.getSaldo();
            }
        }
        for (LineaReporte p : pasivosAgrupados.values()) {
            res.pasivos.add(p);
            res.totalPasivo += p.getSaldo();
        }

        if (Math.abs(res.utilidadEjercicio) > 0.001) {
            String nombre = estRes.esUtilidad ? "Utilidad del Ejercicio" : "Pérdida del Ejercicio";
            String codigo = estRes.esUtilidad ? "3104" : "3105";
            res.patrimonio.add(new LineaReporte(codigo, nombre, res.utilidadEjercicio, "Resultado del Ejercicio"));
            res.totalPatrimonio += res.utilidadEjercicio;
        }

        res.totalActivo = r2(res.totalActivo);
        res.totalPasivo = r2(res.totalPasivo);
        res.totalPatrimonio = r2(res.totalPatrimonio);
        res.totalPasivoMasPatrimonio = r2(res.totalPasivo + res.totalPatrimonio);
        res.diferencia = r2(Math.abs(res.totalActivo - res.totalPasivoMasPatrimonio));
        res.balanceCuadrado = res.diferencia < 0.01;

        return res;
    }

    private static void acumular(Map<String, LineaReporte> map, LineaReporte plantilla, double saldo) {
        LineaReporte actual = map.get(plantilla.getCodigo());
        if (actual == null) {
            map.put(plantilla.getCodigo(), new LineaReporte(
                plantilla.getCodigo(), plantilla.getNombre(), r2(saldo), plantilla.getCategoria()));
        } else {
            map.put(plantilla.getCodigo(), new LineaReporte(
                actual.getCodigo(), actual.getNombre(), r2(actual.getSaldo() + saldo), actual.getCategoria()));
        }
    }

    private static LineaReporte agruparActivo(String cod) {
        if (cod.startsWith("1101")) {
            return new LineaReporte("1101", "Efectivo y Equivalente", 0, "Activo Corriente");
        }
        if (cod.startsWith("1102")) {
            return new LineaReporte("1102", "Cuentas y Documentos por Cobrar", 0, "Activo Corriente");
        }
        if (cod.startsWith("1104")) {
            return new LineaReporte("1104", "IVA Crédito Fiscal", 0, "Activo Corriente");
        }
        if (cod.equals("120101") || cod.equals("120103")) {
            return new LineaReporte("1201ME", "Mobiliario y Equipo", 0, "Activo No Corriente");
        }
        if (cod.equals("120102")) {
            return new LineaReporte("120102", "Equipo de Transporte", 0, "Activo No Corriente");
        }
        if (cod.startsWith("11")) {
            return new LineaReporte(cod, "Otras cuentas de activo corriente", 0, "Activo Corriente");
        }
        return new LineaReporte(cod, "Otras cuentas de activo no corriente", 0, "Activo No Corriente");
    }

    private static LineaReporte agruparPasivo(String cod) {
        if (cod.startsWith("2101")) {
            return new LineaReporte("2101", "Cuentas Comerciales por Pagar", 0, "Pasivo Corriente");
        }
        if (cod.startsWith("2102")) {
            return new LineaReporte("2102", "IVA Débito Fiscal", 0, "Pasivo Corriente");
        }
        if (cod.startsWith("2104")) {
            return new LineaReporte("2104", "Préstamos por Pagar C/P", 0, "Pasivo Corriente");
        }
        if (cod.startsWith("2201") || cod.startsWith("22")) {
            return new LineaReporte("2201", "Préstamos por Pagar L/P", 0, "Pasivo No Corriente");
        }
        if (cod.startsWith("21")) {
            return new LineaReporte(cod, "Otras cuentas de pasivo corriente", 0, "Pasivo Corriente");
        }
        return new LineaReporte(cod, "Otras cuentas de pasivo no corriente", 0, "Pasivo No Corriente");
    }

    private static String nombrePatrimonio(String cod, String nombre) {
        if ("3101".equals(cod)) {
            return "Capital Social";
        }
        return nombre;
    }
}
