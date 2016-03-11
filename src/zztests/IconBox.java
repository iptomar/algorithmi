///****************************************************************************/
///****************************************************************************/
///****     Copyright (C) 2015                                             ****/
///****     Antonio Manuel Rodrigues Manso                                 ****/
///****     e-mail: manso@ipt.pt                                           ****/
///****     url   : http://orion.ipt.pt/~manso    manso@ipt.pt             ****/
///****     Instituto Politecnico de Tomar                                 ****/
///****     Escola Superior de Tecnologia de Tomar                         ****/
///****                                                                    ****/
///****     This software was build with the purpose of investigate and    ****/
///****     learning.                                                      ****/
///****                                                                    ****/
///****************************************************************************/
///****************************************************************************/

package zzTests;

import java.net.URL;
import javax.swing.ImageIcon;

/**
 * Created on 6/set/2015, 11:48:03 
 * @author Antonio M@nso <manso@ipt.pt>
 */
public class IconBox {
    public final static ImageIcon minTabClose = loadIcon("tab-close.png");
    public final static ImageIcon actList     = loadIcon("act-list.png");
    public final static ImageIcon act         = loadIcon("act.png");
    public final static ImageIcon up          = loadIcon("up.png");
    public final static ImageIcon down        = loadIcon("down.png");
    public final static ImageIcon search      = loadIcon("search.png");
    public final static ImageIcon quit        = loadIcon("quit.png");
    public final static ImageIcon print       = loadIcon("print.png");
    public final static ImageIcon warning     = loadIcon("warning.png");
    public final static ImageIcon fileSave    = loadIcon("filesave.png");
    public final static ImageIcon fileOpen    = loadIcon("fileopen.png");
    public final static ImageIcon open        = loadIcon("open.png");
    public final static ImageIcon copy        = loadIcon("copy.png");
    public final static ImageIcon cut         = loadIcon("cut.png");
    public final static ImageIcon paste       = loadIcon("paste.png");
    public final static ImageIcon add         = loadIcon("list-add.png");
    public final static ImageIcon remove      = loadIcon("list-remove.png");


    private static ImageIcon loadIcon(final String fileName) {
        URL url;
        Class daClass;
        ClassLoader classLoader;

        classLoader = IconBox.class.getClassLoader();
        if (classLoader == null) {
            throw new RuntimeException("Icon '"+fileName+"' not found ! (ClassLoader)");
        }

        url = classLoader.getResource(fileName);
        if (url == null) {
            throw new RuntimeException("Icon '"+fileName+"' not found ! (Resource)");
        }

        return new ImageIcon(url);
    }
}