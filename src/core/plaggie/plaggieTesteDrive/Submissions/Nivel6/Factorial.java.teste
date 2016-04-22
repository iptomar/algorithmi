/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.plaggie.plaggieTesteDrive.Factorial;

/**
 *
 * @author Luis 
 */
public class FactorialN6 {
    
    // calcula o factorial de um numero inteiro
    // de forma iterativa
    public static int fact(int n){
        int nfact=1;
        int i = n;
        while(i>1){
            nfact=nfact*i;
            i--;
        }        
        return nfact;  
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Declara variaveis
        int i1,i2;
        long f;
        
        // Calcula componentes
        i1=2+3*2; 
        i2=i1/3+1; 
         
        // Se primeira componente for menor
        if(i1<i2)
            f=fact(i1);
        else
            f=fact(i2);
        
        // Apresenta resultado
        int i = (int) f;
        while(i>1){
            System.out.println("\t" + fact(i));
            i--;
        }
        
    } 
    
}
