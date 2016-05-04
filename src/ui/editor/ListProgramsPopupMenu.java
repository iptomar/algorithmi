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
//::                                                               (c)2015   ::
//:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
//////////////////////////////////////////////////////////////////////////////
package ui.editor;

import flowchart.utils.FileUtils;
import i18n.EditorI18N;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;
import ui.dialogs.FMessages;
import ui.flowchart.dialogs.Fdialog;

/**
 * Created on 8/dez/2015, 16:57:45
 *
 * @author zulu - computer
 */
public class ListProgramsPopupMenu {

    static int ICON_SIZE = 24;
    final JPopupMenu popupMenu = new JPopupMenu();

    String fileName = "UNKNOW";

    public ListProgramsPopupMenu() {

        //----------------------------------------------------------------------
        //:::::::::::::::::::::::::::: OPEN ::::::::::::::::::::::::::::::::::::
        //----------------------------------------------------------------------
        JMenuItem delete = new JMenuItem(
                EditorI18N.get("POPUP.listFiles.delete.title"),
                EditorI18N.loadIcon("POPUP.listFiles.delete.icon", ICON_SIZE)
        );
        delete.setToolTipText(EditorI18N.get("POPUP.listFiles.delete.help"));
        delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String msg = EditorI18N.get("POPUP.listFiles.delete.message").replace("%1", fileName);
                int resp = FMessages.dialogYesNo("question", msg);
                if (resp == 0) {
                    File file = new File(fileName);
                    try {
                        Files.deleteIfExists(file.toPath());
                    } catch (IOException ex) {
                        Fdialog.showKeyMessage("POPUP.listFiles.deleteError.message", new String[]{fileName});
                    }
                }
            }
        });

        //----------------------------------------------------------------------
        //:::::::::::::::::::::::::::: OPEN ::::::::::::::::::::::::::::::::::::
        //----------------------------------------------------------------------
        JMenuItem rename = new JMenuItem(
                EditorI18N.get("POPUP.listFiles.rename.title"),
                EditorI18N.loadIcon("POPUP.listFiles.rename.icon", ICON_SIZE)
        );
        rename.setToolTipText(EditorI18N.get("POPUP.listFiles.rename.help"));
        rename.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                String msg = EditorI18N.get("POPUP.listFiles.rename.message").replace("%1", fileName);
                String onlyFileName = FileUtils.getFileWithoutExtension(fileName);
                String newName = FMessages.inputDialog("POPUP.listFiles.rename",fileName);
                if (!onlyFileName.equalsIgnoreCase(newName)) {
                    File file = new File(fileName);
                    File file2 = new File(FileUtils.getPath(fileName) + newName + "." + FileUtils.FILTER_PROG_EXT);
                    if (!file.renameTo(file2)) {
                        Fdialog.showKeyMessage("POPUP.listFiles.renameError.message", new String[]{onlyFileName, newName});
                    }
                }
            }
        });

        popupMenu.add(delete);
        popupMenu.add(new JPopupMenu.Separator());
        popupMenu.add(rename);
    }

    public void show(int x, int y, String file) {
        this.fileName = file;
        popupMenu.show(null, x, y);
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512081657L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
