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
public class Condicional {
    
    public void calcular(int num1, int num2, int num3){
        if (num1 > num2 && num1 > num3){
            System.out.println("El mayor de los tres numeos introducidos es : "+ num1);
        
        }
        else if (num2 > num3){
            System.out.println("El mayor de los tres numeros introducidos es :"+num2);
        }
        else {
            System.out.println("El mayor de los tres numeros introducidos es :"+num3);
        }
    }    
    
    public void comparar(char caracter1, char caracter2 ){
       if (Character.isLowerCase(caracter1)){
           if (Character.isLowerCase(caracter2)){
            System.out.println("El primer y el Segundo caracter introducido son letras minusculas : "+caracter1+"  "+caracter2);
            }
           
        else{
            System.out.println("El Primer caracter introducido es una letra minuscula"+caracter1+" Pero el Segundo no:"+caracter2);
        }
       }
       else {
           if (Character.isLowerCase(caracter2)){
               System.out.println("El Primer caracter no es una letra minuscula"+caracter1+" Pero el Segundo si : "+caracter2);
           }
           else{
               System.out.println("Ninguno de los caracter introducidos son letras minusculas");
           }
       }
        
            
    }
}
