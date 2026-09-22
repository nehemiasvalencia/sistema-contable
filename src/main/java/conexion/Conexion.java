/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Nehemias Valencia
 */
public class Conexion {
 
    // Datos de conexión
    private static final String SERVIDOR = "localhost";
    private static final String PUERTO = "3306";
    private static final String BASE_DATOS = "Sistema_contable";
    private static final String USUARIO = "root";
    private static final String PASSWORD = "";

    // Método para obtener la conexión
    public static Connection getConnection() throws SQLException {

        // Construir la URL de conexión
        String url = "jdbc:mysql://"
                + SERVIDOR
                + ":"
                + PUERTO
                + "/"
                + BASE_DATOS
                + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";

        // Validar los datos de conexión
        if (SERVIDOR == null || SERVIDOR.trim().isEmpty()) {
            throw new SQLException("El servidor no está configurado.");
        }

        if (PUERTO == null || PUERTO.trim().isEmpty()) {
            throw new SQLException("El puerto no está configurado.");
        }

        if (BASE_DATOS == null || BASE_DATOS.trim().isEmpty()) {
            throw new SQLException("El nombre de la base de datos no está configurado.");
        }

        if (USUARIO == null || USUARIO.trim().isEmpty()) {
            throw new SQLException("El usuario no está configurado.");
        }

        // Intentar establecer la conexión
        try {
            Connection conexion = DriverManager.getConnection(
                    url,
                    USUARIO,
                    PASSWORD
            );

            // Validar que la conexión se haya establecido
            if (conexion == null || conexion.isClosed()) {
                throw new SQLException("No se pudo establecer la conexión.");
            }

            return conexion;

        } catch (SQLException e) {
            throw new SQLException(
                    "Error al conectar con la base de datos: " + e.getMessage(),
                    e
            );
        }
    }
}
