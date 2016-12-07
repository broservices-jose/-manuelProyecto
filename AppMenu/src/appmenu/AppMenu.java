/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package appmenu;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/**
 *
 * @author JOSE
 */
public class AppMenu {

    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        
        
        // Instanciacion de los objetos de la aplicacion
        Basicos b = new Basicos();
        Secuencial s = new Secuencial();
        Condicional c = new Condicional();
        Repetitiva r = new Repetitiva();
        Generales g = new Generales();
        
        InputStreamReader br = new InputStreamReader(System.in);
        BufferedReader leer = new BufferedReader(br);
        
        try{
            boolean entrada = true, campo1 = true, campo2 = true, campo3 = true, campo4 = true, campo5 = true;
            
            while(entrada){

                System.out.println("======================================================================================================================");
                System.out.println("|| 1] Basicos       2] Estruct Secuenciasles      3] Condicionales    4] Repetitivas       5] Generales     6] Salir ||");
                System.out.println("======================================================================================================================");

                int selector = Integer.parseInt(leer.readLine());
                switch(selector){
                    case 1:
                        
                        while(campo1){
                             System.out.println("1] Ejercicio numero 4");
                             System.out.println("2] Ejercicio numero 5");
                             System.out.println("3] Salir");
                        int variable = Integer.parseInt(leer.readLine());
                        switch(variable){
                            case 1:
                                    System.out.println("Introdusca un numero");
                                    int valor1 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca un otro numero");
                                    int valor2 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca un otro numero");
                                    int valor3 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca un otro numero");
                                    int valor4 = Integer.parseInt(leer.readLine());

                                    b.numero4(valor1, valor2, valor3, valor4);
                                    break;
                            case 2:
                                System.out.println("Introdusca un numero");
                                int numero = Integer.parseInt(leer.readLine());
                                b.numero7(numero);
                                break; 
                            case 3:
                                campo1 = false;
                                break;
                        }
                        
                       
                        }
                       break;
                    case 2:
                             while(campo2){
                             System.out.println("1] Ejercicio numero 3");
                             System.out.println("2] Ejercicio numero 4");
                             System.out.println("3] Salir");
                        int variable = Integer.parseInt(leer.readLine());
                        switch(variable){
                            case 1:
                                System.out.println("Introdusca un numero");
                                int valor1 = Integer.parseInt(leer.readLine());
                                    
                                s.numero3(valor1);
                                break;
                            case 2:
                                System.out.println("Introdusca un numero");
                                int valor2 = Integer.parseInt(leer.readLine());
                                s.numero4(valor2);
                                break; 
                            case 3:
                                campo2 = false;
                                break;
                        }
                        
                       
                        }
                       break;
                    case 3:   
                             while(campo3){
                             System.out.println("1] Ejercicio numero 5");
                             System.out.println("2] Ejercicio numero 8");
                             System.out.println("3] Salir");
                        int variable = Integer.parseInt(leer.readLine());
                        switch(variable){
                            case 1:
                                    System.out.println("Introdusca un Letra");
                                    char primero = leer.readLine().charAt(0);
                                    System.out.println("Introdusca  otra Letra");
                                    char segundo = leer.readLine().charAt(0);
                                    
                                    c.comparar(primero, segundo);
                                    break;
                            case 2:
                                    System.out.println("Introdusca un numero");
                                     int valor1 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca  otro numero");
                                     int valor2 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca  otro numero");
                                    int valor3 = Integer.parseInt(leer.readLine());
                                    c.calcular(valor1, valor2, valor3);
                                break; 
                            case 3:
                                campo3 = false;
                                break;
                        }
                        
                       
                        }
                       break;
                        
                    case 4:
                             while(campo4){
                             System.out.println("1] Ejercicio numero 2");
                             System.out.println("2] Ejercicio numero 6");
                             System.out.println("3] Salir");
                        int variable = Integer.parseInt(leer.readLine());
                        switch(variable){
                            case 1:
                                    r.primero();
                                    

                                    
                                    break;
                            case 2:
                                r.segundo();
                                break; 
                            case 3:
                                campo4 = false;
                                break;
                        }
                        
                       
                        }
                       break;
                    case 5:
                             while(campo5){
                             System.out.println("1] Ejercicio Numeros Amigos");
                             System.out.println("2] Ejercicio Numero Perfecto");
                             System.out.println("3] Salir");
                        int variable = Integer.parseInt(leer.readLine());
                        switch(variable){
                            case 1:
                                    int total1 =1, total2=1;
                                    System.out.println("Introdusca un numero");
                                    int valor1 = Integer.parseInt(leer.readLine());
                                    System.out.println("Introdusca otro numero");
                                    int valor2 = Integer.parseInt(leer.readLine());
                                    
                                            total1= g.primero(valor1,total1);
                                            total2 = g.primero(valor2, total2);
                                            if((total1 == valor2)&&(total2 == valor1)){
                                                System.out.println("los numeros "+
                                                valor1+" y "+valor2+" Si son numeros amigos");
                                            }else{
                                                System.out.println("los numeros "+
                                                valor1+" y "+valor2+" No son numeros amigos");
        }
                                    g.primero(valor1, valor2);
                                    break;
                            case 2:
                                    System.out.println("Introdusca un numero");
                                    valor1 = Integer.parseInt(leer.readLine());
                                    
                                    g.segundo(valor1);
                                break; 
                            case 3:
                                campo5 = false;
                                break;
                        }
                        
                       
                        }
                       break;

                        
                }
            }
       }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
}
