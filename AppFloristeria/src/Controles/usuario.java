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
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class usuario {

    // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================
 
    private static int id_usuario;
    
    private static String nombre;
    
    private static String clave;
    
    private static int empleado;
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================

    
    public static int getid_usuario(){
        return id_usuario;
    }
    
    public void setid_usuario(int id){
        this.id_usuario = id;
    }
    
    public static String getnombre(){
        return nombre;
    }
    
    public void setnombre(String nombre){
        this.nombre = nombre;
    }
    
    public static String getclave(){
        return clave;
    }
    
    public void setclave(String clave){
        this.clave = clave;
    }
    
    public static int getempleado(){
        return empleado;
    }
    
    public void setempleado(int id){
        this.empleado = id;
    }
    
    static PreparedStatement consulta;
    static Statement estado;
    static ResultSet cmd;
    static Connection cn;
    static DefaultTableModel tabla;
    static int resultado;
    
    
    public static int ingresar() throws ClassNotFoundException, SQLException{
        try{           
        resultado = 0; 
        
        String query = "insert into usuario (nombre, clave, id_empleado, estado) values (?, ?, ?, ?)";
        cn = Conexion.conectar();
        consulta = cn.prepareStatement(query);
        consulta.setString(1, getnombre());
        consulta.setString(2, getclave());
        consulta.setInt(3, getempleado());
        consulta.setString(4, "habilitado");
        resultado = consulta.executeUpdate();
               
        }catch(SQLException e){
            e.printStackTrace();
       }
    

        return resultado;// resultado; 
        
    }
    
    public static void consultau(JTable tabla, DefaultTableModel modelo) throws ClassNotFoundException{
        try{
            
            String query = "select u.id_usuario, u.nombre, u.clave, concat(e.nombre,'', e.apellido) as Empleado, i.ruta as Imagen from usuario as u inner join empleado as e on u.id_empleado = e.id_empleado  inner join imagen as i on e.id_imagen = i.id_imagen where u.estado ='habilitado'"; 
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
            
            while(cmd.next()){
                Object[] obj = new Object[5];
                obj[0] = cmd.getString("id_usuario");
                obj[1] = cmd.getString("nombre");
                obj[2] = cmd.getString("clave");
                obj[3] = cmd.getString("Empleado");
                obj[4] = cmd.getString("Imagen");
                modelo.addRow(obj);
            }
            tabla.updateUI();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el metodo modifcar el cual nos
    // va a permitir actualizar los datos solicitados desde el formulario usuario
    // para actualizarlo en la base de datos 
    //==========================================================================
    
    public static int actualizar (int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            
            String sql = "update usuario u inner join empleado e on i.id_empleado = e.id_empleado set u.nombre = ?, u.clave = ? where u.id_usuario ="+id;
            cn = Conexion.conectar();
            consulta = cn.prepareStatement(sql);
            consulta.setString(1, getnombre());
            consulta.setString(2, getclave());
            
            resultado = consulta.executeUpdate();
        
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
        return resultado;
    }
    // ==========================================================================================================
    // Esta linea de codigo representa el metodo eliminar el cual nos va a permitir eliminar los datos no deseados
    // ver la visualizacion de estos en el formulario del  Usuario    
    // ==========================================================================================================
    public static int eliminar(int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String sql = "update usuario u inner join empleado e on e.id_empleado = u.id_empleado set u.estado = ? where u.id_usuario = "+id;
            cn = Conexion.conectar();
            consulta = cn.prepareStatement(sql);
            consulta.setString(1, "desabilitado");
            
            resultado = consulta.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error : "+ex.getMessage());
        }
        return resultado;
    }
    
}
