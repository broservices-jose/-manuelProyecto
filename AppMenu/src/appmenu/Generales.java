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
public class Generales {
    int cantidad =1, total =0;
    public int primero(int num1, int num2){
        for(int i=2;i<num1;i++){
            if(num1 % i==0){
            num2+=i;
            }
        }
        return num2;
    }
    
    public void segundo(int valor){
        while(cantidad < valor){
            
            if (valor % cantidad == 0){
                total += cantidad ;
            }
            cantidad++;
        }
        if (total == valor){
            System.out.println("El numero introducido es Perfecto: "+total);
        }
        else{
            System.out.println("El numero introducido NO es Perfecto: "+total);
        }
    }
}