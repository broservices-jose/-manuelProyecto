/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controles;

import java.util.StringTokenizer;

/**
 *
 * @author JOSE
 */
public class Corregirruta {
    
    // ====================================================================================
    // Esta linea de codigo en adelante representa la declaracion de las vaiables con la
    // cual vas a trabajar para corregir la ruta extraida del formulario
    // ========================================================================================
    
    //static String ruta, seprador , nuevo;
    
    // ===========================================================================================
    // Esta linea de codigo representa el metodo constructor de esta la clase el cual va iniciarlizar
    // el valor de las variables declaradas en la primera linea de codigo
    // ===========================================================================================
    
    
//    public  Corregirruta(String ruta, String separador, String nuevo){
//        this.ruta = ruta;
//        this.seprador = separador;
//        this.nuevo = nuevo;
//    }
    
    // ===========================================================================================
    // Esta linea de codigo representa el botener el cual nos va a permitir 
    // el valor de las variables declaradas en la primera linea de codigo
    // ===========================================================================================
    
    public static String obtener(String ruta, String separador,String nuevo){
        StringTokenizer tomador = new StringTokenizer (ruta, separador);
        String corregida = new String ();
        // Contar los tomados (en este caso las carpetas, contada tambien
        // el nombre del archivo seleccionado
        int numero = tomador.countTokens();
        
        int i = 1;
        
        do {
            // agregar el nuevo separador
            corregida += tomador.countTokens()+nuevo;
            i++;
          
        }while(i < numero);
        // Agregar a la ruta corregida el ultimo tomador (nombre del archivo)
        // Mostrar la ruta corregida por consola
        System.out.println(corregida+"\n"+numero+"tomados");
        return corregida;
        
    }
}
