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
public class Basicos {
    public void numero4(int a, int b, int c, int d)
    {
        int temp1, temp2;
        temp1 = c;
        temp2 = b;
        c = a;
        a = d;
        b = temp1;
        d = temp2;

        System.out.println("Los valores Son A ="+a+"B ="+b+"C ="+c+"D ="+d);
    }
    
    public void numero7 (int c){
       // System.out.println(c ?:> 0 );
       if (c % 2 == 0){
           System.out.println("Es par");
       }
       else{
           System.out.println("Es impar");
       }
    }
}
