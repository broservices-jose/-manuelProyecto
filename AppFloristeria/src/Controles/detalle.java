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
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class detalle {
    
    // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================    
    
    private static int num_detalle;
    
    private static double precio;
    
    private static int cantidad;
    
    private static double descuento;
    
    private static double totaldesc;
    
    private static double subtotal;
    
    private static int id_producto;
    
    private static int id_factura;
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================
        
    
    public static int getnum_detalle(){
        return num_detalle;
    }
    
    public void setnum_detalle(int num){
        this.num_detalle = num;
    }
    
    public static double getprecio(){
        return precio;
    }
    
    public void setprecio(double precio){
        this.precio = precio;
    }
    
    public static int getcantidad(){
        return cantidad;
    }
    
    public void setcantidad(int cantidad){
        this.cantidad = cantidad;
    }
    
    public static double getdescuento(){
        return descuento;
    }
    
    public void setdescuento(double descuento){
        this.descuento = descuento;
    }
    
    public static double getsubtotal(){
        return subtotal;
    }
    
    public void setsubtotal(double subtotal){
        this.subtotal = subtotal;
    }
    
    public static int getid_producto(){
        return id_producto;
    }
    public void setid_producto(int id){
        this.id_producto = id;
    }
    
    public static int getid_factura(){
        return id_factura;
    }
    
    public void setid_factura(int id){
        this.id_factura = id;
    }
    
    
    public static double gettotaldesc(){
        return totaldesc;
    }
    
    public void settotaldesc(double total){
        this.totaldesc = total;
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

    public static int registrar() throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "insert into detalle (cantidad, precio, descuento, totaldesc, subtotal,"
                         + " id_producto) values (?, ?, ?, ?, ?, ?)";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            cmd.setInt(1, getcantidad());
            cmd.setDouble(2, getprecio());
            cmd.setDouble(3, getdescuento());
            cmd.setDouble(4, gettotaldesc());
            cmd.setDouble(5, getsubtotal());
            cmd.setInt(6, getid_producto());
            JOptionPane.showMessageDialog(null, gettotaldesc());
            
            resultado = cmd.executeUpdate();
        }catch (ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
        return resultado;
    }
    
    // =============================================================================================
    // Esta linea de codigo en adelante representa el metodo consulta que nos van a permitir traer
    // Todos los datos almacenados en la base de datos dbflors
  
    public static void consultad(JTable tabla, DefaultTableModel modelo) throws ClassNotFoundException, SQLException{
        try{
            String query = "select d.id_factura, d.num_detalle, p.producto, c.categoria, d.precio, d.cantidad, d.descuento, d.totaldesc, d.subtotal from detalle d inner join producto p on d.id_producto = p.id_producto inner join categoria c on c.id_categoria = p.id_categoria";
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(query);
            consulta = cmd.executeQuery();
            
            while(consulta.next()){
                Object[] obj = new Object[9];
                obj[0] = consulta.getInt("id_factura");
                obj[1] = consulta.getInt("num_detalle");
                obj[2] = consulta.getString("producto");
                obj[3] = consulta.getString("categoria");
                obj[4] = consulta.getDouble("precio");
                obj[5] = consulta.getInt("cantidad");
                obj[6] = consulta.getDouble("descuento");
                obj[7] = consulta.getDouble("totaldesc");                
                obj[8] = consulta.getDouble("subtotal");
                
                modelo.addRow(obj);
            }
            tabla.updateUI();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
    }
    
    // ====================================================================================================
    // Esta linea de codigo representa el metodo actualizar el cual nos va a permitir modificar los datos
    // almacenados en la tabla detalle de la base de datos dbflores
    // ====================================================================================================

    public static int modificar(int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String sql = "update detalle d inner join producto p on p.id_producto = d.id_producto inner join"
                    + " factura f on d.id_factura = f.id_factura set d.num_detalle = ?, d.precio = ?, d.cantidad = ?, "
                    + "d.descuento = ?, d.totaldesc = ?, d.subtotal = ?, d.id_producto = ? where d.num_detalle = "+id;
            cn = Conexion.conectar();
            cmd = cn.prepareStatement(sql);
            cmd.setInt(1, getnum_detalle());
            cmd.setDouble(2, getprecio());
            cmd.setInt(3, getcantidad());
            cmd.setDouble(4, getdescuento());
            cmd.setDouble(5, gettotaldesc());
            cmd.setDouble(7, getsubtotal());
            cmd.setInt(8, getid_producto());
            
            resultado = cmd.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error : "+ex.getMessage());
        }
        return resultado;
    }
    
    
    // ==========================================================================================================
    // Esta linea de codigo representa el metodo eliminar el cual nos va a permitir eliminar los datos no deseados
    // ver la visualizacion de estos en el formulario del facturacion  
    // ==========================================================================================================
 
    public static int eliminar(int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String query = "update detalle d inner join producto p on p.id_producto = d.id_producto inner join factura f "
                    + "on d.id_factura = f.id_factura set d.estado = ? where d.num_detalle ="+id;
            cn = Conexion.conectar();
            cmd.setString(1, "desabilitado");
            
           resultado = cmd.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error aqui ;"+ex.getMessage());
        }
        return resultado;
    }
    
}
