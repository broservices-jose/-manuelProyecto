/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmenu;

/**
 *
 * @author JOSE
 */
public class Secuencial {
    
    public void numero3(int valor){
        int doble = valor * valor;
        int triple = valor * valor * valor;
        
        System.out.println("El doble de "+valor+"Es : "+doble+" y el tripe ES : "+triple);
    }
    
    public void numero4(int cantidad){
        int f = 32 + ( 9 * cantidad / 5);
        System.out.println("La Cantidad de "+cantidad+"centígrados corresponde a : "+f+"  Fahrenheit");
    }
}
