/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nehemias Valencia
 */
public class PasswordUtil {

    // Generar el hash de una contraseña
    public static String encriptar(String password) {

        return BCrypt.hashpw(
                password,
                BCrypt.gensalt()
        );
    }

    // Verificar una contraseña contra el hash almacenado
    public static boolean verificar(
            String passwordIngresada,
            String passwordHash
    ) {

        if (passwordIngresada == null || passwordHash == null) {
            return false;
        }

       

        boolean resultado = BCrypt.checkpw(
                passwordIngresada,
                passwordHash
        );


        return resultado;
    }
}
