/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class Imagen {
    
     // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================
    
    private static int id_imagen;
    
    private static String ruta;
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================

    
    public static int getid_imagen(){
        return id_imagen;
    }
    
    public void setid_imagen(int id){
        this.id_imagen = id;
    }
    
    public static String getruta(){
        return ruta;
    }
    
    public void setruta(String ruta){
        this.ruta = ruta;
    }
     // +=======================================================================
    // Esta linea de codigo en adelante representa la creacion de los objetos para
    // Interactuar con la base de datos
    // ========================================================================
        
    static PreparedStatement consulta;
    static Statement estado;
    static ResultSet cmd;
    static Connection cn;
    static DefaultTableModel tabla;
    static int resultado;
    static String  variable;


    // ============================================================================
    // Esta linea de codigo en adelante represetan el metodo registrar para insertar
    // los datos en la base de datos dbflores
    // ============================================================================
    
    public static int ingresar() throws ClassNotFoundException, SQLException {
         try{
        resultado = 0;
        
        //variable = Corregirruta.obtener(getruta(), "//", "////");
        String query = "insert into imagen(ruta) values (?)";
        cn = Conexion.conectar();
        consulta = cn.prepareStatement(query);
        consulta.setString(1, getruta());
        JOptionPane.showMessageDialog(null, getruta());
        resultado = consulta.executeUpdate();
        consulta.close();
        }catch(SQLException sql){
            JOptionPane.showMessageDialog(null, "Error sql :"+sql.getMessage());
        }   
         
         return resultado;
    }
    
}
