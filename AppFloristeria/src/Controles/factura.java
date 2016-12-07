/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class factura {
    
    // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================    
 
    
    private static int id_factura;
    
    private static Date fecha;
    
    private static int id_usuario;
    
    private static int id_detalle;
    
    private static double total;
    
    private static double totaldesc;
    
    public static int getid_factura(){
        return id_factura;
    }
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================

    
    public void setid_factura(int id){
        this.id_factura = id;
    }
    
    public static Date getfecha(){
        return fecha;
    }
    
    public void setfecha(Date fecha){
        this.fecha = fecha;
    }
    
    public static int getid_usuario(){
        return id_usuario;
    }
    
    public void setid_usuario(int id){
        this.id_usuario = id;
    }
    
    public static int getid_detalle(){
        return id_detalle;
    }
    
    public void setid_detalle(int id){
        this.id_detalle = id;
    }
    
    public static double gettotaldesc(){
        return totaldesc;
    }
    public void settotaldesc(double totald){
        this.totaldesc = totald;
    }
    
    public static double gettotal(){
        return total;
    }
    
    public void settotal(double total){
        this.total = total;
    }
    
    // ==========================================================
    // Esta linea de codigo representa la creacion de los objetos 
    // para interactuar con la base de datos 
    // ==========================================================
    
    
    static PreparedStatement cmd;
    static ResultSet consulta;
    static DefaultTableModel modelo;
    static Connection cn;
    static int resultado = 0;
    
    // ===========================================================================
    // Esta linea de codigo en adelante representa el metodo guardar el cual se 
    // encargarar de registrar los datos solicitados en la tabla producto 
    // =========================================================================

    public static int guardarf() throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "insert into factura (total, totaldesc) values (?, ?)";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            cmd.setDouble(1, gettotal());
            cmd.setDouble(2, gettotaldesc());
            
            
            resultado = cmd.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Aqui en ingresar factura"+ex.getMessage());
        }
        return resultado;
    }
    
     // =============================================================================================
    // Esta linea de codigo en adelante representa el metodo consulta que nos van a permitir traer
    // Todos los datos almacenados en la base de datos dbflors
 
    public static void consultaf(JTable tabla, DefaultTableModel modelo) throws ClassNotFoundException, SQLException{
        try{
            String query = "select distinct f.id_factura as codigo, f.totaldesc, f.total, f.fecha from factura f inner join detalle d on d.id_factura = f.id_factura";
            cn = Conexion.conectar();
            cmd = cn.prepareCall(query);
            
            consulta = cmd.executeQuery();
            
            while(consulta.next()){
                Object [] obj = new Object[4];
                obj[0] = consulta.getInt("codigo");
                obj[1] = consulta.getDouble("totaldesc");
                obj[2] = consulta.getDouble("total");
                obj[3] = consulta.getString("fecha");
                modelo.addRow(obj);
            }
            tabla.updateUI();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error aqui en factura"+ex.getMessage());
        }
    }
    
    // No borrar esto por favor
    
    // =========================================================================================
    // Esta linea de codigo representa el metodo tomauser el cual nos va a permitir llenar el label
    // con el nombre del empleado y su cuenta de usuario
    // ========================================================================================

//    private void tomauser(){
//            try{
//            
//            String sql = "select u.id_usuario, u.nombre, u.clave, concat(e.nombre,'', e.apellido) as Empleado, i.ruta as Imagen from usuario as u inner join empleado as e on u.id_empleado = e.id_empleado  inner join imagen as i on e.id_imagen = i.id_imagen where u.id_usuario='"+id+"'";
//            cn = Conexion.conectar();
//            estado = cn.createStatement();
//            cmd = estado.executeQuery(sql);
//            
//            if(cmd.next()){
//                lblusuario.setText(cmd.getString("nombre"));
//                lblempleado.setText(cmd.getString("Empleado"));
//
//               
//                
//            }
//        }catch(ClassNotFoundException | SQLException ex){
//            JOptionPane.showMessageDialog(null, "Error"+ex.getMessage());
//        }
//    
//    }
    
        
}

