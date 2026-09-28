# Sistema Contable Automatizado
### Universidad Católica de El Salvador (UNICAES)
**Facultad de Ingeniería y Arquitectura / Ciencias Empresariales**  
**Actividad III del III Período - Módulo de Contabilidad**

---

## 👥 Integrantes del Equipo de Desarrollo
* Hugo Emerson Gochez Arevalo
* Hermenegildo Antonio Herrera Gonzalez
* Franklin Bladimir Hernandez Barrera
* Mayron Abraham Aguilar Lemus
* Daniel Enrique Mejia Martínez
* Denis Omar Rivera Monroy
* Stanley Edenilson Jacobo Arevalo
* William Alfredo Marroquin Barrera
* Julio Alexander Martinez Rodriguez
* Hugo Ernesto Bernal Ortiz
* Manuel Adaly Medina Aguirre
* Jose Antonio Martinez Rodriguez
* Cristian Omar Arriola

---

## 📋 Descripción del Sistema
Aplicación integral para la automatización del ciclo contable comercial y de servicios bajo la normativa y técnica contable de El Salvador. El sistema garantiza el cumplimiento del principio de **Partida Doble** ($\sum \text{Debe} = \sum \text{Haber}$), realiza la **Mayorización Automática en tiempo real** y genera dinámicamente los **Estados Financieros** mediante la clasificación por dígito de cuenta:
* **Balance General:** Código `1` (Activo) = Código `2` (Pasivo) + Código `3` (Capital / Patrimonio).
* **Estado de Resultados:** Código `5` (Ingresos) - Código `4` (Costos y Gastos) = Utilidad / Pérdida del Ejercicio.

---

## 🔑 Tabla de Accesos y Roles del Sistema

Las credenciales se encuentran encriptadas en la base de datos mediante el algoritmo criptográfico **BCrypt**:

| Usuario | Contraseña | Rol | Acceso y Permisos |
| :--- | :--- | :---: | :--- |
| `admin` | `Admin123` | **Administrador** (ID: 1) | Acceso total: Configuración, Usuarios, Períodos, Catálogo, Libro Diario, Libro Mayor, Estados Financieros y Mantenimientos. |
| `contador` | `Admin123` | **Contador** (ID: 2) | Acceso contable: Catálogo de Cuentas, Libro Diario, Libro Mayor, Estados Financieros y Productos. |

---

## 🚀 Requisitos del Entorno
* **Java Development Kit (JDK):** Versión 17 LTS o superior.
* **Motor de Base de Datos:** MySQL Server 8.0+ o MariaDB 10.4+ (incluido en Laragon, XAMPP o standalone).
* **Puerto:** `3306` (por defecto).
* **Librerías incluidas en `/lib`:**
  * `mysql-connector-j-8.4.0.jar`
  * `jbcrypt-0.4.jar`
  * `jcalendar-1.4.jar`
  * `AbsoluteLayout-RELEASE290.jar`

---

## 🛠 Manual de Instalación y Configuración

### 1. Importación de la Base de Datos
El proyecto incluye los dos scripts SQL oficiales en la raíz:
1. **`schema.sql`**: Crea la base de datos `Sistema_contable` y las 11 tablas relacionales (`cuentas`, `partidas`, `detalle_partidas`, `usuarios`, `roles`, `productos`, `impuestos`, `empresa`, `paises`, `periodos_contables`, `roles_reporte`).
2. **`data.sql`**: Carga el catálogo contable de 50 cuentas, roles, usuarios administradores, impuestos y las partidas del ejercicio práctico de demostración (*La Vaquita, S.A. de C.V.*).

#### Para importar desde Laragon / HeidiSQL / MySQL Workbench / CMD:
```bash
mysql -u root -p < schema.sql
mysql -u root -p < data.sql
```
*(Si MySQL no tiene contraseña, presione Enter).*

### 2. Configuración de Conexión
La clase `conexion/Conexion.java` viene preconfigurada para un entorno local estándar:
* **Servidor:** `localhost`
* **Puerto:** `3306`
* **Base de Datos:** `Sistema_contable`
* **Usuario:** `root`
* **Contraseña:** *(vacía por defecto)*

### 3. Compilación y Ejecución
Para compilar y ejecutar el sistema desde la terminal:
```powershell
# Compilación
javac -encoding UTF-8 -cp "lib/*" -d "target/classes" (Get-ChildItem -Path "src/main/java" -Recurse -Filter "*.java").FullName

# Ejecución de la aplicación
java -cp "target/classes;lib/*" com.mycompany.sistema_contable.Sistema_Contable

# Ejecución del test de verificación automatizada
java -cp "target/classes;lib/*" com.mycompany.sistema_contable.VerificarSistema
```

---

## 📖 Manual de Uso de los Módulos Contables

### 1. Módulo de Libro Diario (Registro de Asientos)
* **Acceso:** Menú Lateral $\rightarrow$ **Libro Diario**.
* **Funcionalidad:**
  1. El sistema asigna automáticamente el número correlativo de partida.
  2. Ingrese la Fecha, Tipo de Partida (Apertura, Compra, Venta, Gasto, Ajuste, Cierre) y el Concepto General.
  3. En la sección de detalle, seleccione la cuenta del catálogo, el concepto de línea, Parcial, Debe o Haber y presione **➕ Agregar al Asiento**.
  4. **Validación Obligatoria de Partida Doble:**
     * El sistema calcula en tiempo real: $\text{Total Debe}$, $\text{Total Haber}$ y la $\text{Diferencia}$.
     * Si el asiento no cumple la partida doble ($\text{Debe} \neq \text{Haber}$), el banner se torna rojo y **el botón Guardar se bloquea**.
     * Cuando la partida está exactamente cuadrada ($\sum \text{Debe} = \sum \text{Haber}$), el banner se torna verde y se desbloquea el botón **💾 Guardar Asiento**.
  5. En el panel lateral derecho se muestra el historial de partidas guardadas con opción de **Doble clic o 👁 Ver Detalle** para inspeccionar la partida completa.

### 2. Módulo de Libro Mayor (Mayorización en Tiempo Real)
* **Acceso:** Menú Lateral $\rightarrow$ **Libro Mayor**.
* **Funcionalidades:**
  * **Pestaña 1: Resumen Consolidado (Balance de Comprobación):**
    * Consolida automáticamente débitos y créditos de todas las cuentas con movimiento.
    * Calcula sin intervención manual el **Saldo Deudor** (si Debe > Haber) o **Saldo Acreedor** (si Haber > Debe).
    * Al pie valida las sumas iguales del Balance de Comprobación.
  * **Pestaña 2: Esquema Gráfico en Cuenta "T":**
    * Seleccione cualquier cuenta o haga doble clic en la tabla para ver su representación visual en **T**.
    * Columna izquierda: Desglose de débitos / cargos con fecha y referencia de partida.
    * Columna derecha: Desglose de créditos / abonos.
    * Pie de la T: Saldo neto determinado automáticamente.

### 3. Módulo de Estados Financieros Dinámicos
* **Acceso:** Menú Lateral $\rightarrow$ **Estados Financieros**.
* **Clasificación por dígito:**
  * **Pestaña 1: Balance General [$1 = 2 + 3$]:**
    * Clasifica automáticamente:
      * **Código 1:** Activo Corriente (11) y Activo No Corriente (12).
      * **Código 2:** Pasivo Corriente (21) y Pasivo No Corriente (22).
      * **Código 3:** Capital Social (3101), Reserva Legal (3102) y Resultado del Ejercicio (3104 / 3105).
    * Comprueba la Ecuación Fundamental: $\text{Activo} = \text{Pasivo} + \text{Patrimonio}$ con banner de confirmación en verde.
  * **Pestaña 2: Estado de Resultados [$5 - 4 = \text{Utilidad}$]:**
    * Clasifica automáticamente:
      * **Código 5:** Ingresos de Operación y Ventas Netas.
      * **Código 4:** Costos de Venta (41) y Gastos Operativos (42).
    * Estructura analítica: Ventas Netas - Costo de Ventas = Utilidad Bruta - Gastos = Utilidad antes de Reserva e ISR.
    * Determina automáticamente el **7% de Reserva Legal** y el **30% de Impuesto sobre la Renta (ISR)** tal como lo requiere la guía evaluativa.
  * **Pestaña 3: Verificador de Ejercicios del PDF:**
    * Permite consultar la resolución paso a paso de los ejercicios solicitados en la actividad:
      1. **Romano S.A. de C.V.:** Utilidad antes de ISR: **$203,700.00** | ISR (30%): $61,110.00 | Utilidad Neta: $142,590.00.
      2. **Real S.A. de C.V.:** Pérdida Neta comprobada: **-$8,865,100.00**.
      3. **Federación y Asociados S.A.:** Utilidad Neta: **$1,014,000.00**.
      4. **La Vaquita, S.A. de C.V.:** Inventario final valorado en **$6,487.05**, Costo de Ventas de **$8,362.51** y Liquidación de IVA.

---

## 🗂 Estructura del Proyecto
```text
sistema-contable-main/
│
├── schema.sql                         # Script DDL de base de datos
├── data.sql                           # Script DML con catálogo y datos semilla
├── README.md                          # Manual técnico y de usuario
├── pom.xml                            # Configuración Maven (Java 17 LTS)
│
├── lib/                               # Librerías binarias autocontenidas
│   ├── mysql-connector-j-8.4.0.jar
│   ├── jbcrypt-0.4.jar
│   ├── jcalendar-1.4.jar
│   └── AbsoluteLayout-RELEASE290.jar
│
└── src/main/java/
    ├── com/mycompany/sistema_contable/
    │   ├── Sistema_Contable.java      # Main Launcher (Inicia FrmLogin)
    │   └── VerificarSistema.java      # Suite de pruebas automatizadas
    ├── conexion/
    │   └── Conexion.java              # Conexión JDBC a MySQL
    ├── modelos/
    │   ├── Partida.java               # Modelo de Partida / Asiento
    │   ├── DetallePartida.java        # Líneas de asiento (Debe, Haber, Parcial)
    │   ├── MayorCuenta.java           # Modelo de mayorización y saldos
    │   ├── Cuentas.java               # Catálogo de cuentas
    │   ├── Usuario.java               # Usuarios y autenticación
    │   └── ...
    ├── dao/
    │   ├── PartidaDao.java            # Transacciones, validación partida doble y mayor
    │   ├── CuentaDao.java             # CRUD y reglas de cuentas
    │   └── usuarioDAO.java            # Autenticación y roles
    ├── controladores/
    │   ├── ReportesFinancierosController.java # Motor de Balance y Estado de Resultados
    │   ├── CuentaController.java
    │   └── UsuarioController.java
    └── vistas/
        ├── FrmLogin.java              # Inicio de sesión con BCrypt
        ├── FrmMenuPrincipal.java      # Enrutador por rol
        ├── menus/
        │   ├── FrmMenuAdmin.java      # Navegación Administrador
        │   └── FrmMenuContador.java   # Navegación Contador
        └── FrmAdministrador/
            ├── FrmLibroDiario.java    # Formulario de Asientos con bloqueo de cuadratura
            ├── FrmLibroMayor.java     # Mayorización y Cuentas T
            ├── FrmEstadosFinancieros.java # Balance General y Estado de Resultados
            ├── FrmCuentas.java        # Mantenimiento de Catálogo
            └── ...
```

---

## 📜 Historial de Versiones (Git Log Resumido)
* `v1.0.0` - **Implementación Inicial:** Estructura base de vistas Swing, configuración de conexión JDBC y login seguro con BCrypt.
* `v1.1.0` - **Módulos Maestros:** CRUD de Cuentas, Usuarios, Períodos Contables, Impuestos y Empresas.
* `v1.2.0` - **Núcleo Contable y Partida Doble:** Implementación de `PartidaDao`, `FrmLibroDiario` con bloqueo de cuadratura en tiempo real.
* `v1.3.0` - **Mayorización y Cuentas T:** Consolidación automática de débitos/créditos en `FrmLibroMayor` y visor gráfico en Cuenta T.
* `v1.4.0` - **Estados Financieros Dinámicos:** Clasificación automática de Balance General ($1 = 2 + 3$) y Estado de Resultados ($5 - 4$), con cálculo de Reserva Legal e ISR.
* `v1.5.0` - **Entrega Final:** Creación de `schema.sql`, `data.sql`, verificador de ejercicios del PDF y manual completo `README.md`.
