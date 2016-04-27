//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
//::                                                                         ::
//::     Antonio Manuel Rodrigues Manso                                      ::
//::                                                                         ::
//::     Biosystems & Integrative Sciences Institute                         ::
//::     Faculty of Sciences University of Lisboa                            ::
//::     http://www.fc.ul.pt/en/unidade/bioisi                               ::
//::                                                                         ::
//::                                                                         ::
//::     I N S T I T U T O    P O L I T E C N I C O   D E   T O M A R        ::
//::     Escola Superior de Tecnologia de Tomar                              ::
//::     e-mail: manso@ipt.pt                                                ::
//::     url   : http://orion.ipt.pt/~manso                                  ::
//::                                                                         ::
//::     This software was build with the purpose of investigate and         ::
//::     learning.                                                           ::
//::                                                                         ::
//::                                                               (c)2016   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package i18n;

import flowchart.utils.AlgorithmSystem;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.TreeSet;
import javax.swing.DefaultComboBoxModel;
import javax.swing.DefaultListModel;

/**
 * Created on 26/abr/2016, 14:04:09
 *
 * @author zulu - computer
 */
public class LanguagesOfSystem {

    public static List<LanguageCode> getLanguages(){
        String regex = "(.*)flowchart(.*).properties";
        List<File> props = AlgorithmSystem.getResources(regex);
        TreeSet<LanguageCode> langs = new TreeSet<>();
        for(File f: props){
            langs.add( new LanguageCode(f.getAbsolutePath()));
        }
        return new ArrayList<>(langs);        
    }
    
    public static DefaultListModel<LanguageCode> getLanguagesModel(){
        List<LanguageCode> l = getLanguages();
        DefaultListModel<LanguageCode> model = new DefaultListModel<>();
        for (LanguageCode languageCode : l) {
            l.add(languageCode);
        }
        return model;
    }
    
     public static DefaultComboBoxModel getLanguagesComboModel(){
        List<LanguageCode> l = getLanguages();
        DefaultComboBoxModel model = new DefaultComboBoxModel<>();
         for (int i = 0; i < l.size(); i++) {
             model.addElement(l.get(i));
             
         }
        return model;
    }
    
   

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201604261404L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2016  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
