/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;
import java.sql.*;
import javax.swing.JOptionPane;
/**
 *
 * @author JOSE
 */
public class Conexion {
    
    private static String url = "jdbc:mysql://localhost/dbflores?";
    private static String usuario = "root";
    private static String contrasenia = "";
    private static Connection cn = null;
    
    public static Connection conectar() throws ClassNotFoundException , SQLException{
        if (cn == null){
            try{
                Class.forName("com.mysql.jdbc.Driver");
                cn = DriverManager.getConnection( "jdbc:mysql://localhost/dbflores?"
                    + "user="+usuario+"&password="+contrasenia);
                
                
            }
            catch(SQLException ex){
                ex.printStackTrace();
            }
            catch(ClassNotFoundException ce){
              ce.printStackTrace();
            }
        }
        return cn;
    }
    
    
   
    
    public static void cerrar() throws SQLException{
        if (cn != null){
            cn.close();
        }
    }
}
