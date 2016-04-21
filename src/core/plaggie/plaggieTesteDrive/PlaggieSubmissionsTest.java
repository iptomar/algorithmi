/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package core.plaggie.plaggieTesteDrive;

import static core.plaggie.Plaggie.compare2;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import plag.parser.Stats;
import plag.parser.StatsException;

/**
 *
 * @author Luis
 */
public class PlaggieSubmissionsTest {
    
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
            Logger.getLogger(PlaggieSubmissionsTest.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String alunos[]={"Nivel0","Nivel1","Nivel2","Nivel3","Nivel4","Nivel5","Nivel6","Nivel7","Nivel8"};

        String ficheiros[]={
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel0/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel1/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel2/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel3/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel4/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel5/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel6/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel7/Factorial.java.teste",
            "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel8/Factorial.java.teste"};         
        try{
            //compare2( alunos, ficheiros);
            //System.out.println("Similarities: "+Stats.getPercentage(compare2( "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel0/Factorial.java.teste", "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel1/Factorial.java.teste")));
            System.out.println("Similarities: "+Stats.getPercentage(compare2( "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel0/Factorial.java.teste", "/myGitHub/LEI2016PSI/Algorithmi-Core-Plaggie-Git/algorithmi-code/src/core/plaggie/plaggieTesteDrive/Submissions/Nivel1/Factorial.java.teste")));

        }catch (NullPointerException ex) {
            Logger.getLogger(core.plaggie.plaggieTesteDrive.PlaggieSubmissionsTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }  
}
