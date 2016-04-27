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
public class FactorialN8 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        // Declara variaveis
        int i1,i2;
        long f;
        int fact;
        
        // Calcula componentes
        i1=2+3*2; 
        i2=i1/3+1; 
         
        // Se primeira componente for menor
        if(i1<i2){
            int nfact=1;
            for(int i = i1; i>1; i--){
                nfact=nfact*i;	
            }
            f=nfact;
        }   
        else{
            int nfact=1;
            for(int i = i2; i>1; i--){
                nfact=nfact*i;	
            }
            f=nfact;
        }   
        
        // Apresenta resultado
        for(int i = (int) f; i>1; i--){
            int nfact=1;
            for(int j = i; j>1; j--){
                nfact=nfact*j;	
            }
            fact=nfact;
            System.out.println("\t" + fact);
        } 
    } 
    
}
