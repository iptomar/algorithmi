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
package flowchart.utils;

import i18n.EditorI18N;
import ui.dialogs.FMessages;
import core.data.exception.FlowchartException;
import flowchart.algorithm.AlgorithmGraph;
import flowchart.algorithm.Program;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import javax.imageio.ImageIO;
import javax.swing.JFileChooser;
import javax.swing.filechooser.FileNameExtensionFilter;
import ui.FLog;
import ui.flowchart.dialogs.Fdialog;

/**
 * Created on 12/out/2015, 19:51:21
 *
 * @author zulu - computer
 */
public class FileUtils {

    //default path to sava programs
    public static String PROGRAMS_PATH = System.getProperty("user.dir") + File.separator + "code" + File.separator;

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    public final static String FILTER_PROG_TITLE = EditorI18N.get("FILES.prog.filter.title");
    public final static String FILTER_PROG_EXT = EditorI18N.get("FILES.prog.filter.ext");

    public final static String FILTER_FLUX_TITLE = EditorI18N.get("FILES.flux.filter.title");
    public final static String FILTER_FLUX_EXT = EditorI18N.get("FILES.flux.filter.ext");

    public final static String FILTER_CODE_TITLE = EditorI18N.get("FILES.code.filter.title");

    public final static String FILTER_CODE_ALG_TITLE = EditorI18N.get("FILES.alg.filter.name");
    public final static String FILTER_CODE_ALG = EditorI18N.get("FILES.alg.filter.ext");

    public final static String FILTER_CODE_JAVA_TITLE = EditorI18N.get("FILES.java.filter.name");
    public final static String FILTER_CODE_JAVA = EditorI18N.get("FILES.java.filter.ext");

    public final static String FILTER_CODE_C_TITLE = EditorI18N.get("FILES.c.filter.name");
    public final static String FILTER_CODE_C = EditorI18N.get("FILES.c.filter.ext");

    static JFileChooser fileProgram = new JFileChooser(System.getProperty(PROGRAMS_PATH));
    static JFileChooser fileFluxogram = new JFileChooser(System.getProperty(PROGRAMS_PATH));
    static JFileChooser fileCode = new JFileChooser(System.getProperty(PROGRAMS_PATH));

    static {

        fileProgram.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileProgram.setFileFilter(new FileNameExtensionFilter(FILTER_PROG_TITLE, FILTER_PROG_EXT));

        fileFluxogram.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileFluxogram.setFileFilter(new FileNameExtensionFilter(FILTER_FLUX_TITLE, FILTER_FLUX_EXT));

        fileCode.setFileSelectionMode(JFileChooser.FILES_ONLY);
        fileCode.setFileFilter(new FileNameExtensionFilter(FILTER_PROG_EXT, FILTER_CODE_ALG, FILTER_CODE_JAVA, FILTER_CODE_C));
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //-------------------------------------------------------------------------- complete program
    /**
     * save complete program
     *
     * @param program filename
     * @return
     */
    public static String saveProgramAs(Program program) throws IOException {
        fileProgram.setSelectedFile(new File(program.getFileName()));
        int returnValue = fileProgram.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileProgram.getSelectedFile().getAbsolutePath();
            if (!filename.endsWith("." + FILTER_PROG_EXT)) {
                filename = filename + "." + FILTER_PROG_EXT;
            }
            program.fileName = filename;
            saveProgram(program, filename);
            return filename;
        }
        return program.getFileName();
    }

    public static void saveProgram(Program program, String filename) throws IOException {
        createPathOfFile(filename);
        saveTokens(program, filename);
    }

    public static Program chooseProgramFromFile(String defaultFile) {
        try {
            //set default path
            fileProgram.setSelectedFile(new File(defaultFile));
            int returnValue = fileProgram.showOpenDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                Program prog = Program.loadProgram(fileProgram.getSelectedFile().getAbsolutePath());
                return prog;
            }
        } catch (Exception e) {
            FMessages.flowchartException(new FlowchartException(e));
            Fdialog.compileException(e);
        }
        return null;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //-------------------------------------------------------------------------- complete program    
    //-------------------------------------------------------------------------- complete program
    public static void saveFluxogram(AlgorithmGraph flux) {
        String path = System.getProperty("user.dir");
        if (flux.myProgram.fileName != null) {
            path = (new File(flux.myProgram.fileName)).getParent();
        }
        fileFluxogram.setSelectedFile(new File(path + File.separator + flux.getFunctionName() + "." + FILTER_FLUX_EXT));
        int returnValue = fileFluxogram.showSaveDialog(null);

        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileFluxogram.getSelectedFile().getAbsolutePath();
            if (filename == null) {
                return;
            }
            createPathOfFile(filename);
            if (!filename.endsWith("." + FILTER_FLUX_EXT)) {
                filename = filename + "." + FILTER_FLUX_EXT;
            }
            try {
                writeGzipStringToFile(filename, flux.getPseudoTokens());
                FMessages.dialog("ok", EditorI18N.get("FILES.flux.success.message") + "\n\n" + filename);
            } catch (Exception ex) {
                FMessages.flowchartException(new FlowchartException(ex));
            }
        }
    }

    public static AlgorithmGraph LoadFluxogram(Program prog) throws FlowchartException {
        String path = System.getProperty("user.dir");
        if (prog.fileName != null) {
            path = (new File(prog.fileName)).getParent();
        }
        fileFluxogram.setSelectedFile(new File(path + File.separator + "*." + FILTER_FLUX_EXT));
        int returnValue = fileFluxogram.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileFluxogram.getSelectedFile().getAbsolutePath();
            return FunctionFile.loadFromFile(filename, prog);
        }
        return null;
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //-------------------------------------------------------------------------- complete program    
    //-------------------------------------------------------------------------- complete program
    public static void saveCode(String code, final String type, String defaultFile) {
        fileCode.setSelectedFile(new File(defaultFile));
        int returnValue = fileCode.showSaveDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            String filename = fileCode.getSelectedFile().getAbsolutePath();
            //------------------------------------------------------------------ no file selected 
            if (filename == null) {
                return;
            }
            createPathOfFile(filename);
            //----------------------------------------------------------------------- normalize file name
            if (type.equalsIgnoreCase(FILTER_CODE_ALG_TITLE) && !filename.endsWith("." + FILTER_CODE_ALG)) {
                filename = filename + "." + FILTER_CODE_ALG;
            } else if (type.equalsIgnoreCase(FILTER_CODE_JAVA_TITLE) && !filename.endsWith("." + FILTER_CODE_JAVA)) {
                filename = filename + "." + FILTER_CODE_JAVA;
            } else if (type.equalsIgnoreCase(FILTER_CODE_C_TITLE) && !filename.endsWith("." + FILTER_CODE_C)) {
                filename = filename + "." + FILTER_CODE_C;
            }

            try {
                PrintStream out = new PrintStream(filename);
                out.println(code);
                out.close();
                FMessages.dialog("ok", EditorI18N.get("FILES.code.success.message") + "\n\n" + filename);
            } catch (Exception ex) {
                FMessages.flowchartException(new FlowchartException(ex));
            }
        }
    }

    //:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::: 
    //-------------------------------------------------------------------------- complete program    
    //-------------------------------------------------------------------------- complete program
    public static void saveTokens(Program prog, String defaultFile) {
        try {
            //change extension to token
            String filename = changeExtension(defaultFile, FILTER_PROG_EXT);
            //write file
            FileUtils.writeGzipStringToFile(filename, prog.getTokens());
        } catch (Exception ex) {
            FMessages.flowchartException(new FlowchartException(ex));
        }
    }

    public static String getPath(String fileName) {
        try {
            File f = new File(fileName);
            f = new File(f.getAbsolutePath()); //full path
            if (f.isDirectory()) {
                return f.getAbsolutePath() + File.separator;
            } else {
                return f.getParent() + File.separator;
            }
        } catch (Exception e) {
        }
        return "";
    }

    public static String getFileWithoutExtension(String fileName) {
        try {
            File f = new File(fileName);
            String file = fileName.substring(f.getParent().length() + 1);
            if (file.contains(".")) {
                return file.substring(0, file.lastIndexOf("."));
            }
            return file;
        } catch (Exception e) {
        }
        return fileName;
    }

    public static String getFileFromPath(String fileName) {
        File f = new File(fileName); // current filename
        fileName = f.getAbsolutePath(); // full path
        f = new File(fileName); // file to full path
        if (f.isDirectory()) {
            return null;
        }
        return fileName.substring(f.getParent().length() + 1);
    }

    public static String updateFileName(String oldPath, String newName) {
        if (!newName.endsWith("." + FILTER_PROG_EXT)) {
            newName += "." + FILTER_PROG_EXT;
        }
        try {
            File f = new File(oldPath);
            return f.getParent() + File.separator + newName;
        } catch (Exception e) {
        }
        return newName;
    }

    public static String correctFileName(String fileName) {
        return System.getProperty("user.dir") + File.separator + getFileFromPath(fileName);

    }

    /**
     * gets the program files (*.prog) from the path of file name
     *
     * @param filename name of one file or path
     * @return array of files
     */
    public static File[] getProgramsInPath(String filename) {
        File dir = new File(FileUtils.getPath(filename));
        return dir.listFiles(new FilenameFilter() {
            public boolean accept(File dir, String name) {
                return name.toLowerCase().endsWith("." + FILTER_PROG_EXT);
            }
        });
    }

    public static void writeGzipStringToFile(String fileName, String txt) throws FlowchartException {
        try {
            createPathOfFile(fileName);
            FileOutputStream output = new FileOutputStream(fileName);
            //Writer writer = new OutputStreamWriter(new GZIPOutputStream(output));
            Writer writer = new OutputStreamWriter(output);
            writer.write(txt);
            writer.flush();
            writer.close();
            output.close();
        } catch (Exception e) {
            throw new FlowchartException(e);
        }

    }

    public static String readGZipStringFromFile(String fileName) throws FlowchartException {
        try {
            //gziped input stream
            FileInputStream input = new FileInputStream(fileName);
            //InputStream reader = new GZIPInputStream(input);
            InputStream reader = input;

            //read bytes to memory
            ByteArrayOutputStream buffer = new ByteArrayOutputStream();
            int nRead;
            byte[] data = new byte[1024];
            while ((nRead = reader.read(data, 0, data.length)) != -1) {
                buffer.write(data, 0, nRead);
            }
            buffer.flush();
            reader.close();
            input.close();
            //convert buffer to string
            return new String(buffer.toByteArray());
//            StringBuffer buffer = new StringBuffer();
//            FileInputStream fis = new FileInputStream("test.txt");
//            InputStreamReader isr = new InputStreamReader(fis, "UTF_16");
//            Reader in = new BufferedReader(isr);
//            int ch;
//            while ((ch = in.read()) > -1) {
//                buffer.append((char) ch);
//            }
//            in.close();
//            return buffer.toString();

        } catch (Exception e) {
            //something wrong happens
            throw new FlowchartException(e);
        }
    }

    public static String readStringFromFile(String fileName) {
        try {
            byte[] encoded = Files.readAllBytes(Paths.get(fileName));
            return new String(encoded);
        } catch (IOException ex) {
            FLog.printLn("readStringFromFile (" + fileName + ") : " + ex.getMessage());
            Logger.getLogger(FileUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void writeImageToFile(String fileName, Image img) {
        try {
            createPathOfFile(fileName);
            BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_4BYTE_ABGR);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi, "jpg", new File("img.jpg"));

        } catch (IOException ex) {
            Logger.getLogger(FileUtils.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * ~replace the extension of the file name
     *
     * @param filename name of the file
     * @param newExtension new extension
     * @return
     */
    public static String changeExtension(String filename, String newExtension) {
        //get path
        String path = "";
        int lastDir = filename.lastIndexOf("\\");
        if (lastDir < 0) {
            lastDir = filename.lastIndexOf("/");
        }
        if (lastDir < 0) {
            lastDir = filename.lastIndexOf(File.pathSeparator);
        }
        if (lastDir > 0) {
            path = filename.substring(0, lastDir + 1);
            filename = filename.substring(lastDir + 1);
        }
        //remove old extension
        int lastPoint = filename.lastIndexOf(".");
        if (lastPoint > 0) {
            filename = filename.substring(0, lastPoint);
        }
        //add new  extension
        return path + filename + "." + newExtension;
    }

    public static void createPathOfFile(String fileName) {
        File file = new File(fileName);
        file = new File(file.getAbsolutePath()); // full path
        if (!file.getParentFile().exists()) {
            file.getParentFile().mkdir();
        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510121951L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////


}
