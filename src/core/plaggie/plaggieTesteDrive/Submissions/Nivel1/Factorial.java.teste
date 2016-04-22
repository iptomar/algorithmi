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
public class FactorialN1 {
    
    // calcula o factorial de um numero inteiro
    // de forma iterativa
    public static int fact(int num){
        int nf=1;
        for(int j = num; j>1; j--){
            nf=nf*j;	
        }
        return nf;
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Declara variaveis
        int j1,j2;
        long k;
        
        // Calcula componentes
        j1=2+3*2; 
        j2=j1/3+1; 
         
        // Se primeira componente for menor
        if(j1<j2)
            k=fact(j1);
        else
            k=fact(j2);
        
        // Apresenta resultado
        for(int j = (int) k; j>1; j--){
            System.out.println("\t" + fact(j));	
        }
        
    } 
    
}
