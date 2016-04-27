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
package flowchart.algorithm;

import core.data.exception.FlowchartException;
import flowchart.utils.Base64;
import flowchart.utils.FileUtils;
import flowchart.utils.UserName;
import flowchart.utils.image.ImageUtils;
import i18n.EditorI18N;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import ui.FLog;
import ui.FProperties;

/**
 * Created on 13/dez/2015, 17:58:43
 *
 * @author zulu - computer
 */
public class Problem implements Serializable {

    public static String FILE_EXTENSION = EditorI18N.get("PROBLEM_EDITOR.problem.file.extension");
    public static String FILE_DEFAULT_NAME = EditorI18N.get("PROBLEM_EDITOR.problem.file.default");

    private UserName creator = UserName.createUser("UNKOWN");
    private String fileName = FILE_DEFAULT_NAME + FILE_EXTENSION;
    private String title = "NO TILE";
    private String description = "";
    private String input = "";
    private String output = "";
    private byte[] image = {};

    public String serialize() {
        try {
            return Base64.encodeObject(this, Base64.GZIP);
        } catch (IOException ex) {
            FLog.printLn("User diditalSignature() " + ex.getMessage());
            return "NOT SIGNED";
        }
    }

    public static Problem createProblem(String problem) {
        try {
            return (Problem) Base64.decodeObject(problem);
        } catch (Exception ex) {
            FLog.printLn("Problem createProblem " + ex.getMessage());
            return new Problem();
        }
    }

    public void save(String fileName) {
        try {
            FileUtils.writeGzipStringToFile(fileName, serialize());
        } catch (FlowchartException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void save() {
        save(fileName);
    }

    public static Problem load(String fileName) {
        try {
            return createProblem(FileUtils.readGZipStringFromFile(fileName));
        } catch (FlowchartException ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return the input
     */
    public String getInput() {
        return input;
    }

    /**
     * @param input the input to set
     */
    public void setInput(String input) {
        this.input = input;
    }

    /**
     * @return the output
     */
    public String getOutput() {
        return output;
    }

    /**
     * @param output the output to set
     */
    public void setOutput(String output) {
        this.output = output;
    }

    /**
     * @return the image
     */
    public ImageIcon getImage() {
        return ImageUtils.getByteArrayJpeg(image);
    }

    /**
     * @param image the image to set
     */
    public void setImage(ImageIcon img) {
        this.image = ImageUtils.getJpegByteArray(img);
    }

    /**
     * @return the creator
     */
    public UserName getCreator() {
        return creator;
    }

    /**
     * @param creator the creator to set
     */
    public void setCreator(UserName creator) {
        this.creator = creator;
    }

    public static void main(String[] args) {
        try {
            Problem prob = new Problem();
            prob.setTitle("OLA");
            prob.setCreator(FProperties.getUser());
            prob.setImage(new ImageIcon(ImageIO.read(new File("test.png"))));
            prob.save("problem.txt");

            Problem pr2 = Problem.load("problem.txt");
            System.out.println(pr2.getTitle());

        } catch (Exception ex) {
            Logger.getLogger(Problem.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static long serialVersionUID = 201512131758L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

    /**
     * @return the fileName
     */
    public String getFileName() {
        return fileName;
    }

    /**
     * @param newName the fileName to set
     */
    public void setFileName(String newName) {
        if (!newName.endsWith(FILE_EXTENSION)) {
            newName += FILE_EXTENSION;
        }
        this.fileName = newName;
    }

}
