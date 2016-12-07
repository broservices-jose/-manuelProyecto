/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;


/**
 *
 * @author JOSE
 */
public class prueba{
   
     // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================
    
    private String empleado;
    
    private int id_empleado;
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================
    
    public prueba(int id, String nombre){
        this.id_empleado = id;
        this.empleado = nombre;
    }
    
   public String getempleado(){
       return empleado;
   }
   
   public void setempleado(String empleado){
       this.empleado = empleado;
   }
    
    public int getid_empleado(){
        return id_empleado;
    }
    
    public void setid_empleado(int id){
        this.id_empleado = id;
    }
    
    @Override
  public String toString() {
    return id_empleado + " " + empleado;
  } 
    
    // =========================================================================
    // Esta linea de codigo en adelante representa la creacion de los objetos 
    // que nos van a permitir interactuar con la baase de dato
    /// =======================================================================
  
}
