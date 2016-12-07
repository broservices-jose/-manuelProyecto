/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import static Controles.Imagen.getruta;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class producto {
    
    public producto(int id){
        this.id_producto = id;
    }
    
    public producto(){
        
    }
    
    // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================
    
    private static int id_producto;
    
    private static String producto;
    
    private static Date fecha_entrada;
    
    private static String descripcion;
    
    private static double precio;
    
    private static String categoria;
    
    public static String getproducto(){
        return producto;
    }
    
    private static int stock;
    
    
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================
    
    public static int getid_producto(){
        return id_producto;
    }
    
    public void setid_producto(int id){
        this.id_producto = id;
    }
    
    public void setproducto(String producto){
        this.producto = producto;
    }
    
    public static Date getfecha(){
        return fecha_entrada;
    }
    
    public static String getdescripcion(){
        return descripcion;
    }
    
    public void setdescripcion(String descripcion){
        this.descripcion = descripcion;
    }
    
    public void setfecha(Date fecha){
        this.fecha_entrada = fecha;
    }
    
    public static double getprecio(){
        return precio;
    }
    
    public void setprecio(double precio){
        this.precio = precio;
    }
    
    public static String getcategoria(){
        return categoria;
    }
    
    public void setcategoria(String categoria){
        this.categoria = categoria;
    }
    
    public static int getstock(){
        return stock;
    }
    public void setstock(int stock){
        this.stock = stock;
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
    
    public static int guardar() throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "insert into producto (producto, fecha_entrada, descripcion, stock, precio, estado) values (?, ?, ?, ?, ?, ?)";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            cmd.setString(1, getproducto());
            cmd.setDate(2, (new java.sql.Date(getfecha().getTime())));
            cmd.setString(3, getdescripcion());
            cmd.setInt(4, getstock());
            cmd.setDouble(5, getprecio());
            cmd.setString(6, "habilitado");
            resultado = cmd.executeUpdate();
            
            cmd.close();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return resultado;
    }

    // =============================================================================================
    // Esta linea de codigo en adelante representa el metodo consulta que nos van a permitir traer
    // Todos los datos almacenados en la base de datos dbflors
    
    public static void consuta(JTable tabla, DefaultTableModel modelo) throws ClassNotFoundException, SQLException{
        try{
            String query = "select p.id_producto, p.producto, c.categoria, p.fecha_entrada, p.descripcion, p.stock, p.precio from producto as p inner join categoria as c on p.id_categoria = c.id_categoria where p.estado = 'habilitado'";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            consulta = cmd.executeQuery();
            
            while(consulta.next()){
                Object[] objecto = new Object[7];
                objecto[0] = consulta.getString("id_producto");
                objecto[1] = consulta.getString("producto");
                objecto[2] = consulta.getString("categoria");
                objecto[3] = consulta.getDate("fecha_entrada");
                objecto[4] = consulta.getString("descripcion");
                objecto[5] = consulta.getString("stock");
                objecto[6] = consulta.getDouble("precio");
                

                modelo.addRow(objecto);
            }
            tabla.updateUI();
            
        }catch(ClassNotFoundException | SQLException ex){
            ex.printStackTrace();
        }
    }
   
    // ============================================================================
    // Esta linea de codigo representa el metodo guardar2  el cual nos va a permitir
    // introducir los datos en la tabla categoria de la base de datos dbflores 
    // ============================================================================
    
    public static int guardar2() throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "insert into categoria (categoria) values (?)";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            cmd.setString(1, getcategoria());
            
            resultado = cmd.executeUpdate();
            
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        
        return resultado;
     }
    
    
    // ====================================================================================================
    // Esta linea de codigo representa el metodo actualizar el cual nos va a permitir modificar los datos
    // almacenados en la tabla empleado de la base de datos dbflores
    // ====================================================================================================
    
//    private static int modificar (int id) throws ClassNotFoundException, SQLException{
//        try{
//            resultado = 0;
//            String sql = "update producto p inner join imagen i on p.id_imagen = i.id_imagen set p.producto = ?, "
//                    + "c.categoria = ?, p.fecha_entrada = ?, p.descripcion = ?, p.stock = ?, p.precio = ?, i.ruta = ?  where p.id_producto ="+id;
//            cn = Conexion.conectar();
//            cmd = cn.prepareStatement(sql);
//            cmd.setString(1, getproducto());
//            cmd.setString(2, getcategoria());
//            cmd.setString(3, getfecha());
//            cmd.setString(4, getdescripcion());
//            cmd.setInt(5, getstock());
//            cmd.setDouble(6, getprecio());
//            cmd.setString(7, getruta());
//            
//            resultado = cmd.executeUpdate();
//        }catch(ClassNotFoundException | SQLException ex){
//            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
//        }
//        return resultado;
//    }
    
    // ====================================================================================================
    // Esta linea de codigo representa el metodo actualizar el cual nos va a permitir modificar los datos
    // almacenados en la tabla producto de la base de datos dbflores
    // ====================================================================================================

    public static int modificar (int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "update producto p inner join imagen i on p.id_imagen = i.id_imagen inner join categoria c on c.id_categoria = p.id_categoria"
                         + " set p.producto = ?, c.categoria = ?, p.fecha_entrada = ?, p.descripcion = ?, p.stock = ?, p.precio = ?, i.ruta = ? where p.id_producto ="+id;
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            cmd.setString(1, getproducto());
            cmd.setString(2, getcategoria());
            cmd.setDate(3, (new java.sql.Date( getfecha().getTime())));
            cmd.setString(4, getdescripcion());
            cmd.setInt(5, getstock());
            cmd.setDouble(6, getprecio());
            cmd.setString(7, getruta());
            
            resultado = cmd.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
        return resultado;
    }
    
    // ==========================================================================================================
    // Esta linea de codigo representa el metodo eliminar el cual nos va a permitir eliminar los datos no deseados
    // ver la visualizacion de estos en el formulario del producto    
    // ==========================================================================================================
  
    public static int eliminar (int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String sql = "update producto p inner join imagen i on p.id_imagen = i.id_imagen inner join categoria c on p.id_categoria = c.id_categoria set p.estado = ? where p.id_producto ="+id;
            cn = Conexion.conectar();
            cmd.setString(1, "desabiltado");
            
            resultado = cmd.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error es aqui :"+ex.getMessage());
        }
        return resultado;
    }
}
