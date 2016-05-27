/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.autoEvaluate;

/**
 *
 * @author Luis
 */
public class StringCompare {
    
    /**
    * Compara a semelhança entre duas Strings:
    * retorna uma percentagem [um numero entre 0..1] 
    * @param str1
    * @param str2
    * @return (longerLength - myLevenshteinDistance(longerString, shorterString)) / (double) longerLength
    */
    public double mySimilarity(String str1, String str2) {
        
        // duas strings: uma maior, outra menor
	String longerString = str1, shorterString = str2;
        
	// verifica qual a String maior
	if (str1.length() < str2.length()) { 
            longerString = str2; shorterString = str1;
        }
        int longerLength = longerString.length();
	// caso string vazia
        if (longerLength == 0) { 
            return 1.0;
	} 
	// caso contenha caracteres
	else{	
            return (longerLength - myLevenshteinDistance(longerString, shorterString)) / (double) longerLength;
	}
    }
    
    // Implementaç?o do algoritmo de Levenshtein { Edit Distance }
    // Modelo de implementaçao n?o recursivo e mais rápido
    // Fonte: https://en.wikibooks.org/wiki/Algorithm_Implementation/Strings/Levenshtein_distance#Java
    public int myLevenshteinDistance (CharSequence lhs, CharSequence rhs) {                          
        
        int len0 = lhs.length() + 1;                                                     
        int len1 = rhs.length() + 1;                                                     

        // the array of distances                                                       
        int[] cost = new int[len0];                                                     
        int[] newcost = new int[len0];                                                  

        // initial cost of skipping prefix in String s0                                 
        for (int i = 0; i < len0; i++) cost[i] = i;                                     

        // dynamically computing the array of distances                                  

        // transformation cost for each letter in s1                                    
        for (int j = 1; j < len1; j++) {                                                
            // initial cost of skipping prefix in String s1                             
            newcost[0] = j;                                                             

            // transformation cost for each letter in s0                                
            for(int i = 1; i < len0; i++) {                                             
                // matching current letters in both strings                             
                int match = (lhs.charAt(i - 1) == rhs.charAt(j - 1)) ? 0 : 1;             

                // computing cost for each transformation                               
                int cost_replace = cost[i - 1] + match;                                 
                int cost_insert  = cost[i] + 1;                                         
                int cost_delete  = newcost[i - 1] + 1;                                  

                // keep minimum cost                                                    
                newcost[i] = Math.min(Math.min(cost_insert, cost_delete), cost_replace);
            }                                                                           

            // swap cost/newcost arrays                                                 
            int[] swap = cost; cost = newcost; newcost = swap;                          
        }                                                                               

        // the distance is the cost for transforming all letters in both strings        
        return cost[len0 - 1];                                                          
    }

    public String showMySimilarity(String str_a, String str_b) {
        return  (String.format("\n\t%.0f é a percentagem de semelhança entre \"%s\" e \"%s\"", 
                mySimilarity(str_a, str_b)*100, str_a, str_b));
    }    
    
}
