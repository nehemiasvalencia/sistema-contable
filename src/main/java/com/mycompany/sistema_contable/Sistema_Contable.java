/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.sistema_contable;

import conexion.Conexion;
import java.sql.Connection;
import java.sql.SQLException;
import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author Nehemias Valencia
 */
public class Sistema_Contable {

    public static void main(String[] args) {
        String password = "Admin123";

        String hash = BCrypt.hashpw(
                password,
                BCrypt.gensalt(10)
        );

        System.out.println("Password: " + password);
        System.out.println("Hash: " + hash);
    }
}
