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
public class FactorialTesteDrive {
    
    // calcula o factorial de um numero inteiro
    // de forma iterativa
    public static int fact(int n){
        
        int nfact=1;
 
        for(int i = n; i>1; i--){
            nfact=nfact*i;	
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
        i1=2+3*2;   // 8
        i2=i1/3+1;  // 3,6666666666666666666666666666667
         
        // Se primeira componente for menor
        if(i1<i2)
            f=fact(i1);
        else
            f=fact(i2);
        
        // Apresenta resultado
        for(int i = (int) f; i>1; i--){
            System.out.println("\t" + fact(i));	
        }
        
//    // Teste #1    
//    // calcula o factorial de um numero inteiro
//    // de forma recursiva   
//    public static int fact(int n){
//        if(n<3)
//            return n;
//        return n*fact(n-1);
//    }        
        
//        // Teste #2
//        // 10! = 3628800
//        System.out.println("\t" + fact(10));
        
//        // Teste #3
//        // Troca de ciclo: for <=> while
//        int i = (int) f;
//        while(i>1){
//            System.out.println("\t" + fact(i));
//            i--;
//        }
        
    }

/////[ OUTPUT ]/////////////////////////////
//    
// run:
//	 720
//	 120
//	 24
//	 6
//	 2
// BUILD SUCCESSFUL (total time: 0 seconds)    
////////////////////////////////////////////    
    
}
