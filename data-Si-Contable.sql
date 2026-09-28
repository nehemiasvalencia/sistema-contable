-- ==============================================================================
-- UNIVERSIDAD CATÓLICA DE EL SALVADOR (UNICAES)
-- MODULO CONTABLE DEL CICLO CONTABLE
-- DATA SEED (data.sql)
-- ==============================================================================

USE Sistema_contable;

-- 1. PAÍSES
INSERT INTO paises (id_pais, nombre, codigo_iso, moneda) VALUES
(1, 'El Salvador', 'SLV', 'USD ($)');

-- 2. EMPRESA (Ejercicios de la guía)
INSERT INTO empresa (id_empresa, id_pais, nombre, nit, nrc, direccion, telefono, correo, actividad_economica, estado) VALUES
(1, 1, 'La Vaquita, S.A. de C.V.', '0614-010190-101-1', '123456-7', 'Santa Ana, El Salvador', '2440-1234', 'contacto@lavaquita.com.sv', 'Comercialización de Lácteos y Alimentos', TRUE),
(2, 1, 'Distribuidora Electrónica, S.A. de C.V.', '0614-020295-102-2', '234567-8', 'San Salvador, El Salvador', '2250-9876', 'info@distribuidora.sv', 'Venta de Artículos Electrónicos', TRUE),
(3, 1, 'Romano S.A. de C.V.', '0614-030388-103-3', '345678-9', 'Santa Ana, El Salvador', '2445-5555', 'administracion@romano.sv', 'Comercio General', TRUE);

-- 3. ROLES DEL SISTEMA
INSERT INTO roles (id_rol, nombre, descripcion, estado) VALUES
(1, 'Administrador', 'Control total, gestión de usuarios, catálogos y configuración', TRUE),
(2, 'Contador', 'Gestión del ciclo contable: Libro Diario, Mayorización y Estados Financieros', TRUE);

-- 4. USUARIOS DEL SISTEMA
-- Contraseñas hasheadas con BCrypt (Costo 10):
-- admin: Admin123 -> $2a$10$L9S0DHbEmpF3k1IkQAMZtejCL1gdenOi3sW3zLu83kVIWQ.PRx7bK
-- contador: Contador123 -> $2a$10$7RuzbL.W1uK0pLshB8Qy3u3qL1a6L7w6d8uTsqsEkeq7sW5vFfKqy
INSERT INTO usuarios (id_usuario, id_rol, nombre, usuario, password_h, estado) VALUES
(1, 1, 'Hugo Emerson Gochez', 'admin', '$2a$10$L9S0DHbEmpF3k1IkQAMZtejCL1gdenOi3sW3zLu83kVIWQ.PRx7bK', TRUE),
(2, 2, 'Hermenegildo Antonio Herrera', 'contador', '$2a$10$L9S0DHbEmpF3k1IkQAMZtejCL1gdenOi3sW3zLu83kVIWQ.PRx7bK', TRUE);

-- 5. ROLES DE REPORTE CONTABLE
INSERT INTO roles_reporte (id_rol_reporte, codigo, nombre, descripcion, estado) VALUES
(1, 101, 'Activo Corriente', 'Cuentas líquidas realizables en el corto plazo', TRUE),
(2, 102, 'Activo No Corriente', 'Bienes duraderos y propiedad planta y equipo', TRUE),
(3, 201, 'Pasivo Corriente', 'Obligaciones exigibles a corto plazo', TRUE),
(4, 202, 'Pasivo No Corriente', 'Deudas y obligaciones a largo plazo', TRUE),
(5, 301, 'Patrimonio y Capital', 'Aportes de socios y reservas', TRUE),
(6, 401, 'Costos y Gastos', 'Costos de venta y gastos operativos', TRUE),
(7, 501, 'Ingresos de Operación', 'Ventas y rendimientos del negocio', TRUE);

-- 6. IMPUESTOS
INSERT INTO impuestos (id_impuesto, nombre, codigo, descripcion, porcentaje, fecha_inicio, fecha_fin, estado) VALUES
(1, 'Impuesto al Valor Agregado', 'IVA', 'Tasa estándar del 13% El Salvador', 13.00, '2026-01-01', NULL, TRUE),
(2, 'Pago a Cuenta', 'PAC', 'Anticipo mensual de impuesto sobre la renta 1.75%', 1.75, '2026-01-01', NULL, TRUE);

-- 7. PERÍODO CONTABLE
INSERT INTO periodos_contables (id_periodo, id_empresa, nombre, fecha_inicio, fecha_fin, estado) VALUES
(1, 1, 'Ejercicio Contable 2026', '2026-01-01', '2026-12-31', 'ABIERTO');

-- 8. CATÁLOGO DE CUENTAS (Estructura estándar salvadoreña de 5 dígitos/rubros según la Guía UNICAES)
-- 1 = ACTIVO, 2 = PASIVO, 3 = CAPITAL / PATRIMONIO, 4 = COSTOS Y GASTOS, 5 = INGRESOS
INSERT INTO cuentas (id_cuenta, id_cuenta_padre, codigo, nombre, tipo, clasificacion, naturaleza, id_rol_reporte, nivel, permite_movimientos, estado) VALUES
-- CLASE 1: ACTIVO
(1, NULL, '1', 'ACTIVO', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 1, FALSE, TRUE),
(2, 1, '11', 'ACTIVO CORRIENTE', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 2, FALSE, TRUE),
(3, 2, '1101', 'EFECTIVO Y EQUIVALENTES', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 3, FALSE, TRUE),
(4, 3, '110101', 'Caja General', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 4, TRUE, TRUE),
(5, 3, '110102', 'Bancos (Cuentas Corrientes)', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 4, TRUE, TRUE),
(6, 2, '1102', 'CUENTAS POR COBRAR', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 3, FALSE, TRUE),
(7, 6, '110201', 'Clientes Locales', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 4, TRUE, TRUE),
(8, 2, '1103', 'INVENTARIOS', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 3, FALSE, TRUE),
(9, 8, '110301', 'Inventario de Mercaderías', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 4, TRUE, TRUE),
(10, 2, '1104', 'IVA CRÉDITO FISCAL', 'ACTIVO', 'CORRIENTE', 'DEUDORA', 1, 3, TRUE, TRUE),
(11, 1, '12', 'ACTIVO NO CORRIENTE', 'ACTIVO', 'NO_CORRIENTE', 'DEUDORA', 2, 2, FALSE, TRUE),
(12, 11, '1201', 'PROPIEDAD, PLANTA Y EQUIPO', 'ACTIVO', 'NO_CORRIENTE', 'DEUDORA', 2, 3, FALSE, TRUE),
(13, 12, '120101', 'Mobiliario y Equipo de Oficina', 'ACTIVO', 'NO_CORRIENTE', 'DEUDORA', 2, 4, TRUE, TRUE),
(14, 12, '120102', 'Equipo de Transporte', 'ACTIVO', 'NO_CORRIENTE', 'DEUDORA', 2, 4, TRUE, TRUE),
(15, 12, '120103', 'Equipo de Computación', 'ACTIVO', 'NO_CORRIENTE', 'DEUDORA', 2, 4, TRUE, TRUE),

-- CLASE 2: PASIVO
(16, NULL, '2', 'PASIVO', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 1, FALSE, TRUE),
(17, 16, '21', 'PASIVO CORRIENTE', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 2, FALSE, TRUE),
(18, 17, '2101', 'CUENTAS POR PAGAR', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 3, FALSE, TRUE),
(19, 18, '210101', 'Proveedores Locales', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 4, TRUE, TRUE),
(20, 17, '2102', 'IVA DÉBITO FISCAL', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 3, TRUE, TRUE),
(21, 17, '2103', 'IVA POR PAGAR', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 3, TRUE, TRUE),
(22, 17, '2104', 'PRÉSTAMOS BANCARIOS A CORTO PLAZO', 'PASIVO', 'CORRIENTE', 'ACREEDORA', 3, 3, TRUE, TRUE),
(23, 16, '22', 'PASIVO NO CORRIENTE', 'PASIVO', 'NO_CORRIENTE', 'ACREEDORA', 4, 2, FALSE, TRUE),
(24, 23, '2201', 'PRÉSTAMOS BANCARIOS A LARGO PLAZO', 'PASIVO', 'NO_CORRIENTE', 'ACREEDORA', 4, 3, TRUE, TRUE),

-- CLASE 3: CAPITAL CONTABLE / PATRIMONIO
(25, NULL, '3', 'CAPITAL CONTABLE', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 1, FALSE, TRUE),
(26, 25, '31', 'PATRIMONIO', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 2, FALSE, TRUE),
(27, 26, '3101', 'Capital Social', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 3, TRUE, TRUE),
(28, 26, '3102', 'Reserva Legal', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 3, TRUE, TRUE),
(29, 26, '3103', 'Utilidades Acumuladas', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 3, TRUE, TRUE),
(30, 26, '3104', 'Utilidad del Ejercicio', 'CAPITAL', 'PATRIMONIO', 'ACREEDORA', 5, 3, TRUE, TRUE),
(31, 26, '3105', 'Pérdida del Ejercicio', 'CAPITAL', 'PATRIMONIO', 'DEUDORA', 5, 3, TRUE, TRUE),

-- CLASE 4: COSTOS Y GASTOS
(32, NULL, '4', 'COSTOS Y GASTOS', 'COSTO', 'RESULTADO', 'DEUDORA', 6, 1, FALSE, TRUE),
(33, 32, '41', 'COSTOS', 'COSTO', 'RESULTADO', 'DEUDORA', 6, 2, FALSE, TRUE),
(34, 33, '4101', 'Costo de Ventas', 'COSTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(35, 33, '4102', 'Compras', 'COSTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(36, 33, '4103', 'Gastos sobre Compras', 'COSTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(37, 33, '4104', 'Devoluciones y Rebajas sobre Compras', 'COSTO', 'RESULTADO', 'ACREEDORA', 6, 3, TRUE, TRUE),
(38, 32, '42', 'GASTOS DE OPERACIÓN', 'GASTO', 'RESULTADO', 'DEUDORA', 6, 2, FALSE, TRUE),
(39, 38, '4201', 'Gastos de Administración', 'GASTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(40, 38, '4202', 'Gastos de Venta', 'GASTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(41, 38, '4203', 'Gastos Financieros', 'GASTO', 'RESULTADO', 'DEUDORA', 6, 3, TRUE, TRUE),
(42, 32, '43', 'OTROS GASTOS', 'GASTO', 'RESULTADO', 'DEUDORA', 6, 2, TRUE, TRUE),

-- CLASE 5: INGRESOS
(43, NULL, '5', 'INGRESOS', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 1, FALSE, TRUE),
(44, 43, '51', 'INGRESOS DE OPERACIÓN', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 2, FALSE, TRUE),
(45, 44, '5101', 'Ventas', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 3, TRUE, TRUE),
(46, 44, '5102', 'Devoluciones sobre Ventas', 'INGRESO', 'RESULTADO', 'DEUDORA', 7, 3, TRUE, TRUE),
(47, 44, '5103', 'Descuentos sobre Ventas', 'INGRESO', 'RESULTADO', 'DEUDORA', 7, 3, TRUE, TRUE),
(48, 43, '52', 'OTROS INGRESOS', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 2, FALSE, TRUE),
(49, 48, '5201', 'Productos Financieros', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 3, TRUE, TRUE),
(50, 48, '5202', 'Otros Productos', 'INGRESO', 'RESULTADO', 'ACREEDORA', 7, 3, TRUE, TRUE);

-- 9. PRODUCTOS E INVENTARIO (Queso a precio de compra $10.00, inventario final $6,487.05)
INSERT INTO productos (id_producto, codigo, nombre, descripcion, unidad_medida, precio_compra, precio_venta, existencia, stock_minimo, estado) VALUES
(1, 'PROD001', 'Queso Lácteo', 'Queso comercial a precio unitario de compra', 'UNIDAD', 10.00, 20.00, 648.705, 10.00, TRUE);

-- 10. PARTIDAS DE LA GUÍA #1 "LA VAQUITA, S.A. DE C.V." (IVA INCLUIDO)
-- El inventario final de estados financieros se valúa con precio unitario de compra. Sin ISR.

-- Partida 1: Apertura (1 ene)
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(1, 1, 1, 1, '2026-01-01', 'APERTURA', 'Inicio de actividades. Aporte de socios: efectivo $30,000.00 e inventarios $6,000.00.', 36000.00, 36000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(1, 4, 1, 'Dinero en efectivo aportado por socios', 30000.00, 30000.00, 0.00),
(1, 9, 2, 'Inventario inicial de mercaderías', 6000.00, 6000.00, 0.00),
(1, 27, 3, 'Capital social aportado por socios', 36000.00, 0.00, 36000.00);

-- Partida 2: Apertura de cuenta corriente (3 ene)
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(2, 1, 1, 2, '2026-01-03', 'DIARIO', 'Apertura de cuenta corriente Banco Cuscatlán No. 0001 por $20,000.00.', 20000.00, 20000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(2, 5, 1, 'Depósito en Banco Cuscatlán cuenta No. 0001', 20000.00, 20000.00, 0.00),
(2, 4, 2, 'Salida de efectivo para apertura de cuenta bancaria', 20000.00, 0.00, 20000.00);

-- Partida 3: Compra al crédito IVA incluido $10,000 (5 ene). Base 8849.56 + IVA CF 1150.44
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(3, 1, 1, 3, '2026-01-05', 'COMPRA', 'Compra de queso al crédito simple, pagadera en 30 días. IVA incluido $10,000.00.', 10000.00, 10000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(3, 35, 1, 'Compra de mercadería (base gravada)', 8849.56, 8849.56, 0.00),
(3, 10, 2, '13% IVA Crédito Fiscal', 1150.44, 1150.44, 0.00),
(3, 19, 3, 'Proveedor Lácteos Metapan, crédito 30 días', 10000.00, 0.00, 10000.00);

-- Partida 4: Venta al crédito CCF 001 IVA incluido $12,000 (10 ene). Base 10619.47 + IVA DF 1380.53
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(4, 1, 1, 4, '2026-01-10', 'VENTA', 'Venta de queso a Lácteos, S.A. de C.V. CCF No. 001. IVA incluido $12,000.00, dos cuotas.', 12000.00, 12000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(4, 7, 1, 'Cliente Lácteos, S.A. de C.V. (dos cuotas)', 12000.00, 12000.00, 0.00),
(4, 45, 2, 'Venta gravada CCF No. 001', 10619.47, 0.00, 10619.47),
(4, 20, 3, '13% IVA Débito Fiscal', 1380.53, 0.00, 1380.53);

-- Partida 5: Pago de la compra del 5 ene (15 ene)
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(5, 1, 1, 5, '2026-01-15', 'PAGO', 'Pago de compra del 5 de enero a Lácteos Metapan mediante transferencia. CCF No. 1644.', 10000.00, 10000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(5, 19, 1, 'Cancelación de cuenta por pagar Lácteos Metapan', 10000.00, 10000.00, 0.00),
(5, 5, 2, 'Transferencia bancaria', 10000.00, 0.00, 10000.00);

-- Partida 6: Escritorios Office Depot IVA incluido $300 (31 ene). Base 265.49 + IVA CF 34.51
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(6, 1, 1, 6, '2026-01-31', 'COMPRA', 'Compra de 2 escritorios a Office Depot (CCF No. 123), $150.00 c/u IVA incluido. Pago en efectivo.', 300.00, 300.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(6, 13, 1, 'Mobiliario: 2 escritorios para oficinas administrativas', 265.49, 265.49, 0.00),
(6, 10, 2, '13% IVA Crédito Fiscal CCF No. 123', 34.51, 34.51, 0.00),
(6, 4, 3, 'Pago en efectivo', 300.00, 0.00, 300.00);

-- Partida 7: Cobro primera cuota (10 feb)
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(7, 1, 1, 7, '2026-02-10', 'COBRO', 'Cobro de primera cuota de venta del 10 de enero. Transferencia a Banco Cuscatlán.', 6000.00, 6000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(7, 5, 1, 'Transferencia recibida Banco Cuscatlán', 6000.00, 6000.00, 0.00),
(7, 7, 2, 'Abono a cliente Lácteos, S.A. de C.V. (1ra cuota)', 6000.00, 0.00, 6000.00);

-- Partida 8: Laptop SIMAN IVA incluido $580 (15 feb). Base 513.27 + IVA CF 66.73
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(8, 1, 1, 8, '2026-02-15', 'COMPRA', 'Compra de laptop a SIMAN para uso de la empresa. CCF No. 001. Pago con cheque. IVA incluido $580.00.', 580.00, 580.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(8, 15, 1, 'Equipo de computación: laptop', 513.27, 513.27, 0.00),
(8, 10, 2, '13% IVA Crédito Fiscal CCF No. 001 SIMAN', 66.73, 66.73, 0.00),
(8, 5, 3, 'Pago con cheque Banco Cuscatlán', 580.00, 0.00, 580.00);

-- Partida 9: Venta de contado CCF 002 IVA incluido $5,000 (16 feb). Base 4424.78 + IVA DF 575.22
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(9, 1, 1, 9, '2026-02-16', 'VENTA', 'Venta de productos a Lácteos, S.A. de C.V. CCF No. 002 cobrada en efectivo. IVA incluido $5,000.00.', 5000.00, 5000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(9, 4, 1, 'Cobro en efectivo', 5000.00, 5000.00, 0.00),
(9, 45, 2, 'Venta gravada CCF No. 002', 4424.78, 0.00, 4424.78),
(9, 20, 3, '13% IVA Débito Fiscal', 575.22, 0.00, 575.22);

-- Partida 10: Vehículo Grupo Q IVA incluido $15,000 (20 feb). Base 13274.34 + IVA CF 1725.66. Enganche 10% = 1500
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(10, 1, 1, 10, '2026-02-20', 'COMPRA', 'Adquisición de vehículo en Grupo Q. IVA incluido $15,000.00. Enganche 10% por transferencia y el resto a deber.', 15000.00, 15000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(10, 14, 1, 'Equipo de transporte: vehículo Grupo Q', 13274.34, 13274.34, 0.00),
(10, 10, 2, '13% IVA Crédito Fiscal', 1725.66, 1725.66, 0.00),
(10, 5, 3, 'Enganche 10% por transferencia bancaria', 1500.00, 0.00, 1500.00),
(10, 19, 4, 'Saldo a deber a Grupo Q', 13500.00, 0.00, 13500.00);

-- Partida 11: Préstamo Daviviend`usuarios`a $20,000, comisión 5% + IVA (20 feb). Neto 18870. Porción C/P 1/8 = 2500
INSERT INTO partidas (id_partida, id_periodo, id_empresa, numero_partida, fecha, tipo, concepto, total_debe, total_haber, cuadrada, estado, id_usuario) VALUES
(11, 1, 1, 11, '2026-02-20', 'FINANCIAMIENTO', 'Préstamo Banco Davivienda $20,000.00 a 8 años. Comisión 5% más IVA. Porción corriente $2,500.00.', 20000.00, 20000.00, TRUE, 'PROCESADA', 1);
INSERT INTO detalle_partidas (id_partida, id_cuenta, linea, concepto, parcial, debe, haber) VALUES
(11, 5, 1, 'Desembolso neto recibido en Banco Davivienda', 18870.00, 18870.00, 0.00),
(11, 41, 2, 'Comisión 5% sobre préstamo', 1000.00, 1000.00, 0.00),
(11, 10, 3, '13% IVA sobre comisión bancaria', 130.00, 130.00, 0.00),
(11, 22, 4, 'Préstamos bancarios (c/p) — porción corriente', 2500.00, 0.00, 2500.00),
(11, 24, 5, 'Préstamos bancarios (l/p)', 17500.00, 0.00, 17500.00);
