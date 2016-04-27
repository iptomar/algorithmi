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
public class FactorialRecursive {
      
    // calcula o factorial de um numero inteiro
    // de forma recursiva   
    public static int fact(int n){
        if(n<3)
            return n;
        return n*fact(n-1);
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
