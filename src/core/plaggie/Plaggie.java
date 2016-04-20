/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.plaggie;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import plag.parser.CodeTokenizer;
import plag.parser.SimpleSubmissionSimilarityChecker;
import plag.parser.SimpleTokenSimilarityChecker;
import plag.parser.SingleFileSubmission;
import plag.parser.Stats;
import plag.parser.StatsException;
import plag.parser.Submission;
import plag.parser.SubmissionDetectionResult;
import plag.parser.SubmissionSimilarityChecker;
import plag.parser.TokenSimilarityChecker;
import plag.parser.plaggie.Configuration;

/**
 *
 * @author Luis
 */
public class Plaggie {
      public static double[][] compare2( String[]label, String[] code) throws FileNotFoundException, IOException, NullPointerException {
        try{ 
            
            int dim = label.length;
            
            //desenha primeira linha
            for (int i = 0; i < dim; i++) {
                if(i==0)
                    System.out.print("\n\t| ");
                else
                    System.out.print(" | ");
                System.out.print(label[i]);
            }
            System.out.print(" |");
            for (int i = 0; i < dim; i++) {
                for (int j = 0; j < dim; j++) {
                    if(j==0)
                        System.out.print("\n|"+label[i]+"\t| ");
                    else
                        System.out.print(" ");
                    System.out.print(compare2( code[i], code[j])+" |");
                }
            }        
            System.out.println("\n");
        }catch (Exception ex) {
            Logger.getLogger(core.plaggie.Plaggie.class.getName()).log(Level.SEVERE, null, ex);
        } 
                     
        return null;
        
    }
     
    public static double compare2( String file1, String file2){
        try {
            
            Submission subA = new SingleFileSubmission(new File(file1));
            Submission subB = new SingleFileSubmission(new File(file2));
            
            TokenSimilarityChecker tokenChecker = new SimpleTokenSimilarityChecker(3);
            
            // -- Create the code tokenizer object for parsing the source code files
            Configuration config = new PlaggieConfiguration();
                       
	    CodeTokenizer codeTokenizer = (CodeTokenizer)Class.forName(config.codeTokenizer).newInstance();
            SubmissionSimilarityChecker check = new SimpleSubmissionSimilarityChecker(tokenChecker, codeTokenizer);
            
            SubmissionDetectionResult detResult = 
		    new SubmissionDetectionResult(subA, subB, check, 0.5); 
            //return (int)Math.round(detResult.getMaxFileSimilarityProduct());
            //return Math.round(detResult.getMaxFileSimilarityProduct());
            return detResult.getMaxFileSimilarityProduct();
            
        } catch (Exception ex) {
            Logger.getLogger(core.plaggie.Plaggie.class.getName()).log(Level.SEVERE, null, ex);
        }
        return 0;
    }
    
    public static void main(String[] args) throws FileNotFoundException, IOException, NullPointerException {
        try {
            Stats.newCounter("submissions");
            Stats.newCounter("parsed_files");
            Stats.newCounter("files_to_parse");
            Stats.newCounter("parse_failures");
            Stats.newCounter("failed_file_comparisons");
            Stats.newCounter("file_comparisons");
            Stats.newCounter("similarity_over_threshold");
            Stats.newCounter("similarity_comparisons");
            Stats.newCounter("blacklisted_detection_results");
            
            Stats.newDistribution("files_in_submission");
            Stats.newDistribution("submission_similarities");
            Stats.newDistribution("submission_similarities_a");
            Stats.newDistribution("submission_similarities_b");
            Stats.newDistribution("maximum_file_similarities");
            
        } catch (StatsException ex) {
            Logger.getLogger(Plaggie.class.getName()).log(Level.SEVERE, null, ex);
        }
        
//        String alunos[]={"Ana","Beta","Carla","Dora","Filipa","Joana","Maria"};
//        String ficheiros[]={
//            "D:/Submissions/Ana/Codigo.java",
//            "D:/Submissions/Beta/Codigo.java",
//            "D:/Submissions/Carla/Codigo.java",
//            "D:/Submissions/Dora/Codigo.java",
//            "D:/Submissions/Filipa/Codigo.java",  
//            "D:/Submissions/Joana/Codigo.java",
//            "D:/Submissions/Maria/Codigo.java"};  
        String alunos[]={"N0","N1","N2","N3","N4","N5","N6"};
        String ficheiros[]={
            "D:/Submissions/N0/Codigo.java",
            "D:/Submissions/N1/Codigo.java",
            "D:/Submissions/N2/Codigo.java",
            "D:/Submissions/N3/Codigo.java",
            "D:/Submissions/N4/Codigo.java",  
            "D:/Submissions/N5/Codigo.java",
            "D:/Submissions/N6/Codigo.java"}; 
        try{
            compare2( alunos, ficheiros);
        }catch (IOException | NullPointerException ex) {
            Logger.getLogger(core.plaggie.Plaggie.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }  
}
