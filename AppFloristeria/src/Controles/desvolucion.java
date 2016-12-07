
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import java.sql.Date;

/**
 *
 * @author FCC III
 */
public class desvolucion {
    
    // =========================================================================
    // Esta linea de codigo en adelante representa todas variables declaradas con
    // un nivel de acceso privaado para que no se puedan alterar o modificar estas 
    // propiedades
    //==========================================================================
 
    
    private static int id_desvolucion;
    
    private static String motivo;
    
    private static Date fecha;
    
    private static int cantidad;
    
    private static int id_factura;
    
    //=========================================================================
    // Esta linea de codigo en adelante representa el encapsulamiento de las variables  con
    // para que no se puedan alterar o modificar estas 
    //==========================================================================

    /**
     * @return the id_desvolucion
     */
    public static int getId_desvolucion() {
        return id_desvolucion;
    }

    /**
     * @param id_desvolucion the id_desvolucion to set
     */
    public void setId_desvolucion(int id_desvolucion) {
        this.id_desvolucion = id_desvolucion;
    }

    /**
     * @return the motivo
     */
    public static String getMotivo() {
        return motivo;
    }

    /**
     * @param motivo the motivo to set
     */
    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    /**
     * @return the fecha
     */
    public static Date getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    /**
     * @return the cantidad
     */
    public static int getCantidad() {
        return cantidad;
    }

    /**
     * @param cantidad the cantidad to set
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }

    /**
     * @return the id_factura
     */
    public static int getId_factura() {
        return id_factura;
    }

    /**
     * @param id_factura the id_factura to set
     */
    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

}
