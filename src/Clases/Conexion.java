/*
 * Trabajo Final - Construccion De Software I  TdeA.
 * Hecho por: Juan Guillermo Diosa Muñoz | Docente: Sofia Gallo
 * Cine Premier HD - Control de usuario para un cine.
 */
package Clases;

import com.mysql.jdbc.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class Conexion {     //Creacion de clase Conexion para conectar a la base de datos.

    Connection conexion = null;

    public Connection conexion() {  //Sintaxis de conexion a base de datos.
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conexion = (Connection) DriverManager.getConnection("jdbc:mysql://localhost/admincine", "root", ""); //Direccion de la base de datos. 
        } catch (ClassNotFoundException | SQLException e) {
            JOptionPane.showMessageDialog(null, "No se pudo conectar");   //Error si no se logra la conexion.
        }
        return conexion;
    }
}