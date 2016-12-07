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
public class Repetitiva {
    private int contador =0;

    public void primero(){
        do {
            contador++;
            System.out.println(contador);
        }while(contador != 100);
    }
    
    public void segundo(){
        for (int i = 100; i !=0; i-- ){
            System.out.println(i);
        }
    }
}
