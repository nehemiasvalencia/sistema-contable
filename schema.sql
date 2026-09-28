-- ==============================================================================
-- UNIVERSIDAD CATÓLICA DE EL SALVADOR (UNICAES)
-- MODULO CONTABLE DEL CICLO CONTABLE
-- SCHEMA DEFINITION (schema.sql)
-- ==============================================================================

CREATE DATABASE IF NOT EXISTS Sistema_contable 
CHARACTER SET utf8mb4 
COLLATE utf8mb4_unicode_ci;

USE Sistema_contable;

-- Desactivar llaves foráneas para recreación limpia
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS detalle_partidas;
DROP TABLE IF EXISTS partidas;
DROP TABLE IF EXISTS productos;
DROP TABLE IF EXISTS periodos_contables;
DROP TABLE IF EXISTS impuestos;
DROP TABLE IF EXISTS empresa;
DROP TABLE IF EXISTS cuentas;
DROP TABLE IF EXISTS roles_reporte;
DROP TABLE IF EXISTS usuarios;
DROP TABLE IF EXISTS roles;
DROP TABLE IF EXISTS paises;

SET FOREIGN_KEY_CHECKS = 1;

-- 1. PAÍSES
CREATE TABLE paises (
    id_pais INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo_iso VARCHAR(10) NOT NULL,
    moneda VARCHAR(10) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 2. EMPRESA
CREATE TABLE empresa (
    id_empresa INT AUTO_INCREMENT PRIMARY KEY,
    id_pais INT NOT NULL,
    nombre VARCHAR(150) NOT NULL,
    nit VARCHAR(30),
    nrc VARCHAR(30),
    direccion TEXT,
    telefono VARCHAR(30),
    correo VARCHAR(100),
    actividad_economica VARCHAR(150),
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_pais) REFERENCES paises(id_pais)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 3. ROLES DE USUARIO
CREATE TABLE roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(50) NOT NULL,
    descripcion TEXT,
    estado BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 4. USUARIOS DEL SISTEMA
CREATE TABLE usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    usuario VARCHAR(50) NOT NULL UNIQUE,
    password_h VARCHAR(255) NOT NULL,
    estado BOOLEAN DEFAULT TRUE,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ultima_cone TIMESTAMP NULL,
    FOREIGN KEY (id_rol) REFERENCES roles(id_rol)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 5. ROLES DE REPORTE CONTABLE
CREATE TABLE roles_reporte (
    id_rol_reporte INT AUTO_INCREMENT PRIMARY KEY,
    codigo INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    descripcion TEXT,
    estado BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 6. CATÁLOGO DE CUENTAS
-- Clasificación según la guía:
-- 1 = ACTIVO, 2 = PASIVO, 3 = CAPITAL CONTABLE / PATRIMONIO
-- 4 = COSTOS Y GASTOS, 5 = INGRESOS
CREATE TABLE cuentas (
    id_cuenta INT AUTO_INCREMENT PRIMARY KEY,
    id_cuenta_padre INT NULL,
    codigo VARCHAR(30) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    tipo ENUM('ACTIVO','PASIVO','CAPITAL','INGRESO','COSTO','GASTO') NOT NULL,
    clasificacion ENUM('CORRIENTE','NO_CORRIENTE','PATRIMONIO','RESULTADO') NOT NULL,
    naturaleza ENUM('DEUDORA','ACREEDORA') NOT NULL,
    id_rol_reporte INT NULL,
    nivel TINYINT NOT NULL DEFAULT 1,
    permite_movimientos BOOLEAN NOT NULL DEFAULT TRUE,
    estado BOOLEAN DEFAULT TRUE,
    FOREIGN KEY (id_cuenta_padre) REFERENCES cuentas(id_cuenta),
    FOREIGN KEY (id_rol_reporte) REFERENCES roles_reporte(id_rol_reporte)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 7. IMPUESTOS
CREATE TABLE impuestos (
    id_impuesto INT AUTO_INCREMENT PRIMARY KEY,
    nombre VARCHAR(100) NOT NULL,
    codigo VARCHAR(20) NOT NULL,
    descripcion TEXT,
    porcentaje DECIMAL(5,2) NOT NULL,
    fecha_inicio DATE,
    fecha_fin DATE,
    estado BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 8. PERÍODOS CONTABLES
CREATE TABLE periodos_contables (
    id_periodo INT AUTO_INCREMENT PRIMARY KEY,
    id_empresa INT NOT NULL,
    nombre VARCHAR(100) NOT NULL,
    fecha_inicio DATE NOT NULL,
    fecha_fin DATE NOT NULL,
    estado VARCHAR(30) DEFAULT 'ABIERTO',
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 9. PRODUCTOS E INVENTARIO
CREATE TABLE productos (
    id_producto INT AUTO_INCREMENT PRIMARY KEY,
    codigo VARCHAR(50) NOT NULL UNIQUE,
    nombre VARCHAR(150) NOT NULL,
    descripcion TEXT,
    unidad_medida VARCHAR(30) DEFAULT 'UNIDAD',
    precio_compra DECIMAL(12,2) DEFAULT 0.00,
    precio_venta DECIMAL(12,2) DEFAULT 0.00,
    existencia DECIMAL(14,4) DEFAULT 0.0000,
    stock_minimo DECIMAL(12,2) DEFAULT 0.00,
    estado BOOLEAN DEFAULT TRUE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 10. PARTIDAS / ASIENTOS (LIBRO DIARIO)
CREATE TABLE partidas (
    id_partida INT AUTO_INCREMENT PRIMARY KEY,
    id_periodo INT NULL,
    id_empresa INT NULL,
    numero_partida INT NOT NULL,
    fecha DATE NOT NULL,
    tipo VARCHAR(50) DEFAULT 'DIARIO',
    concepto TEXT NOT NULL,
    total_debe DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    total_haber DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    cuadrada BOOLEAN NOT NULL DEFAULT TRUE,
    estado VARCHAR(30) NOT NULL DEFAULT 'PROCESADA',
    id_usuario INT NULL,
    creado_en TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (id_periodo) REFERENCES periodos_contables(id_periodo),
    FOREIGN KEY (id_empresa) REFERENCES empresa(id_empresa),
    FOREIGN KEY (id_usuario) REFERENCES usuarios(id_usuario)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- 11. DETALLE DE PARTIDAS (CUENTAS, PARCIAL, DEBE, HABER)
CREATE TABLE detalle_partidas (
    id_detalle INT AUTO_INCREMENT PRIMARY KEY,
    id_partida INT NOT NULL,
    id_cuenta INT NOT NULL,
    linea INT NOT NULL DEFAULT 1,
    concepto VARCHAR(255) NULL,
    parcial DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    debe DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    haber DECIMAL(14,2) NOT NULL DEFAULT 0.00,
    FOREIGN KEY (id_partida) REFERENCES partidas(id_partida) ON DELETE CASCADE,
    FOREIGN KEY (id_cuenta) REFERENCES cuentas(id_cuenta)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- Vistas para compatibilidad conceptual de Asientos
CREATE OR REPLACE VIEW asientos AS SELECT * FROM partidas;
CREATE OR REPLACE VIEW detalle_asientos AS SELECT * FROM detalle_partidas;
