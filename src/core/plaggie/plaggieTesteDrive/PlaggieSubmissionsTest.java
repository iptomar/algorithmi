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
            Stats.newCounter("files_to_parse");
            Stats.newCounter("file_comparisons");
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
            System.out.println("\n\t\t==[ Nveis de Detecao de Plgio ]==\n");
            System.out.println("\n\t Nvel 0 - SEM alteraao de cdigo:              "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[0])));
            System.out.println("\n\t Nvel 1 - Alteraao do NOME das variveis:      "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[1])));
            System.out.println("\n\t Nvel 2 - Alteraao da POSIAO das Variveis:   "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[2])));
            System.out.println("\n\t Nvel 3 - Alteraao da POSIAO de Clculo:      "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[3])));
            System.out.println("\n\t Nvel 4 - Alteraao de EXPRESSOES de clculo:   "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[4])));
            System.out.println("\n\t Nvel 5 - Alteraao de IF por ELSE:             "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[5])));
            System.out.println("\n\t Nvel 6 - Alteraao de CICLO:                   "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[6])));
            System.out.println("\n\t Nvel 7 - Alterao para RECURSIVIDADE:         "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[7])));
            System.out.println("\n\t Nvel 8 - Incorpora�ao de FUNAO no MAIN:       "+Stats.getPercentage(compare2( ficheiros[0], ficheiros[8])));
            System.out.println("\n");
        }catch (NullPointerException ex) {
            Logger.getLogger(core.plaggie.plaggieTesteDrive.PlaggieSubmissionsTest.class.getName()).log(Level.SEVERE, null, ex);
        } 
        
    }  
}
