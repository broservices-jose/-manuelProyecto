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
import java.sql.Statement;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author FCC III
 */
public class empleado {
        // *=================================================================================*
    // Esta linea de codigo representa la declaracion de las variables que interactuan
    // con el sistema
    // *=================================================================================*
    
    private static int id_empleado;
    
    private static String nombre;
    
    private static String apellido;
    
    private static String correo;
    
    private static String telefon;
    
    private static  String direccion;
    
    private static Date fecha_nac;
    
    private static String sexo;
    
    private static Date fecha_contracto;
    
    private static double sueldo;
    
    private static String observacion;
    

    // =========================================================================
    // esta linea de codigo representa la encapsulaciones de las variables que 
    // esta private para que sean visible pero no se puedan modificar 
    //==========================================================================
    
    public static int getid_empleado(){
        return id_empleado;
    }
    
    public void setid_empleado(int id){
        this.id_empleado = id;
    }
    public static double getsueldo(){
        return sueldo;
    }
    public void setsueldo(double sueldo){
        this.sueldo = sueldo;
    }
    
    /**
     * @return the nombre
     */
    public static String getnombre(){
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setnombre(String nombre) {
        this.nombre = nombre;
    }
    
    public static String getapellido(){
        return apellido;
    }
    public void setapellido(String apellido){
        this.apellido = apellido;
    }
    
    public static String getcorreo(){
        return correo;
    }
    
    public void setcorreo(String correo){
        this.correo = correo;
    }
    
    public static String gettelefono(){
        return telefon;
    }
    
    public void settelefono(String telefono){
        this.telefon = telefono;
    }
    
    public static String getdireccion(){
        return direccion;
    }
    
    public void setdireccion(String direccion){
        this.direccion = direccion;
    }
    
    public static Date getfecha(){
        return fecha_nac;
    }
    
    public void setfecha(Date fecha){
        this.fecha_nac = fecha;
    }
    
    public static String getsexo(){
        return sexo;
    }
    
    public void setsexo(String sexo){
        this.sexo = sexo;
    }
    
    public static Date getfecha_contracto(){
        return fecha_contracto;
    }
    
    public void setfecha_contracto(Date fecha_cont){
        this.fecha_contracto = fecha_cont;
    }
    
    public static String getobservacion(){
        return observacion;
    }
    
    public void setobservacion(String obsrvacion){
        this.observacion = obsrvacion;
    }
    
    
    static PreparedStatement consulta;
    static Statement estado;
    static ResultSet cmd;
    static Connection cn;
    static DefaultTableModel tabla;
    static int resultado;
    
    // =================================================================================================
    // Esta linea de codigo representa el metodo registra el cual nos va a permitir registrar los datos
    // solicitados en la tabla empleado de la base de datos dbflores
    // =================================================================================================
    
    public static int registrar() throws ClassNotFoundException, SQLException{
        try{           
        resultado = 0; 
        
        String query = "insert into empleado (nombre, apellido, correo, telefono, direccion, fecha_nac, sexo, fecha_contracto, sueldo, observacion, estado) "
                + "values (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        cn = Conexion.conectar();
        consulta = cn.prepareStatement(query);
        consulta.setString(1, getnombre());
        consulta.setString(2, getapellido());
        consulta.setString(3, getcorreo());
        consulta.setString(4, gettelefono());
        consulta.setString(5, getdireccion());
        consulta.setDate(6,  new java.sql.Date( getfecha().getTime()));
        consulta.setString(7, getsexo());
        consulta.setDate(8,  new java.sql.Date( getfecha_contracto().getTime()));
        consulta.setDouble(9, getsueldo());
        consulta.setString(10, getobservacion());
        consulta.setString(11, "habilitado");
        resultado = consulta.executeUpdate();
               
        }catch(SQLException e){
            e.printStackTrace();
          }finally{
    
        
    
    }

        return resultado;// resultado; 
        
    }
    
    public static void consulta(JTable tabla, DefaultTableModel modelo) throws ClassNotFoundException{
        try{
            
            String query = "select e.id_empleado, e.nombre, e.apellido, e.correo, e.telefono, e.direccion, e.fecha_nac, e.sexo, e.fecha_contracto, e.sueldo, e.observacion, i.ruta as ruta from empleado as e inner join imagen as i on e.id_imagen = i.id_imagen where e.estado = 'habilitado'"; 
            cn = Conexion.conectar();
            estado = cn.createStatement();
            cmd = estado.executeQuery(query);
            
            while(cmd.next()){
                //System.out.println(cmd.getString("nombre"));
                Object[] obj = new Object[12];
                obj[0] = cmd.getInt("id_empleado");
                obj[1] = cmd.getString("nombre");
                obj[2] = cmd.getString("apellido");
                obj[3] = cmd.getString("correo");
                obj[4] = cmd.getString("telefono");
                obj[5] = cmd.getString("direccion");
                obj[6] = cmd.getDate("fecha_nac");
                obj[7] = cmd.getString("sexo");
                obj[8] = cmd.getDate("fecha_contracto");
                obj[9] = cmd.getDouble("sueldo");
                obj[10] = cmd.getString("observacion");
                obj[11] = cmd.getString("ruta");
                modelo.addRow(obj);
            }
            tabla.updateUI();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
    }
    
    // ====================================================================================================
    // Esta linea de codigo representa el metodo actualizar el cual nos va a permitir modificar los datos
    // almacenados en la tabla empleado de la base de datos dbflores
    // ====================================================================================================

    /**
     *
     * @param id
     * @return
     * @throws ClassNotFoundException
     * @throws SQLException
     */
    
    public static int actualizar(int id) throws ClassNotFoundException, SQLException{

        try{
         resultado = 0;
        
        String sql = "update empleado e inner join imagen i on e.id_imagen = i.id_imagen set e.nombre = ?, "
                + "e.apellido = ?, e.correo = ?, e.telefono = ?, e.direccion = ?, e.fecha_nac = ?, e.sexo = ? ,"
                + "e.fecha_contracto = ?, i.ruta = ? where e.id_empleado ="+id;
        cn = Conexion.conectar();
        consulta = cn.prepareStatement(sql);
        consulta.setString(1, getnombre());
        consulta.setString(2, getapellido());
        consulta.setString(3, getcorreo());
        consulta.setString(4, gettelefono());
        consulta.setString(5, getdireccion());
        consulta.setDate(6,  new java.sql.Date( getfecha().getTime()));
        consulta.setString(7, getsexo());
        consulta.setDate(8,  new java.sql.Date( getfecha_contracto().getTime()));
        consulta.setString(9, getruta());
        
        
        resultado = consulta.executeUpdate();
        }catch(ClassNotFoundException | SQLException ex){
           JOptionPane.showMessageDialog(null, "Error : "+ex.getMessage());
        }
        return resultado;
    }
    
    // ==========================================================================================================
    // Esta linea de codigo representa el metodo eliminar el cual nos va a permitir eliminar los datos no deseados
    // ver la visualizacion de estos en el formulario del empleado     
    // ==========================================================================================================
    public static int eliminar (int id) throws ClassNotFoundException, SQLException{
        try{
            resultado = 0;
            String sql = "update empleado e inner join imagen i on e.id_imagen = i.id_imagen set e.estado = ? where e.id_empleado ="+id;
            cn = Conexion.conectar();
            consulta = cn.prepareStatement(sql);
            consulta.setString(1, "desabilitado");
            
            resultado = consulta.executeUpdate();
            
        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error :"+ex.getMessage());
        }
        return resultado;
    }
    
        // ==========================================================================================================
    // Esta linea de codigo representa el metodo eliminar el cual nos va a permitir eliminar los datos no deseados
    // ver la visualizacion de estos en el formulario del empleado     
    // ==========================================================================================================
  
    public static void filtrar(Object filtro, JTable tabla, DefaultTableModel modelo) throws SQLException, ClassNotFoundException{
        try{
             String query = "select e.id_empleado, e.nombre, e.apellido, e.correo, e.telefono, e.direccion, e.fecha_nac, "
                     + "e.sexo, e.fecha_contracto, e.sueldo, e.observacion, i.ruta as ruta from empleado as e inner join "
                     + "imagen as i on e.id_imagen = i.id_imagen where e.estado = 'habilitado' and e.nombre like"+filtro;
             cn = Conexion.conectar();
             estado = cn.createStatement();
             cmd = estado.executeQuery(query);

             while(cmd.next()){
                Object [] obj = new Object[12];
                obj[0] = cmd.getInt("id_empleado");
                obj[1] = cmd.getString("nombre");
                obj[2] = cmd.getString("apellido");
                obj[3] = cmd.getString("correo");
                obj[4] = cmd.getString("telefono");
                obj[5] = cmd.getString("direccion");
                obj[6] = cmd.getDate("fecha_nac");
                obj[7] = cmd.getString("sexo");
                obj[8] = cmd.getDate("fecha_contracto");
                obj[9] = cmd.getDouble("sueldo");
                obj[10] = cmd.getString("observacion");
                obj[11] = cmd.getString("ruta");
                
               modelo.addRow(obj);
            }
             tabla.updateUI();


        }catch(ClassNotFoundException | SQLException ex){
            JOptionPane.showMessageDialog(null, "Error en el filtro");
        }
    }
    /*  try {
            // TODO add your handling code here:
            
            if (validar() == 1){
                this.dispose();
                JOptionPane.showMessageDialog(null, "Bienvenido\n Has ingresado "
                + "satisfactoriamente al sistema mas completo del mercado", "Mensaje de bienvenida",
                JOptionPane.INFORMATION_MESSAGE);
                
                Formulario frm = new Formulario();
                frm.setVisible(true);
                
            }
            else{
                JOptionPane.showMessageDialog(null, "Error: Usuario o Contrasenia incorrecto");
            }
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Login.class.getName()).log(Level.SEVERE, null, ex);
        }*/
}
