/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package flowchart.utils;

import java.io.File;
import java.io.FileInputStream;
import java.security.CodeSource;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import javax.swing.JOptionPane;

/**
 *
 * @author zulu
 */
public class AlgorithmSystem {

    public static ArrayList<String> resources = new ArrayList<>();
    public static String mugaPath = ".";

    public static List<File> getResources(String pattern) {
        try {
            //get running source code
            CodeSource src = AlgorithmSystem.class.getProtectionDomain().getCodeSource();
            //running into jar file
            if (src.getLocation().getFile().endsWith(".jar")) {
                return loadJarResources(src, pattern);
            } else { //running in class file
                return getPathClasses(src, pattern);
            }
        } catch (Exception e) {
        }
        return null;

    }

    /**
     * load classes from jar file
     *
     * @param src source jar file
     * @throws Exception
     */
    private static ArrayList<File> loadJarResources(CodeSource src, String pattern) throws Exception {
        ArrayList<File> resources = new ArrayList<>();
        File f = new File(src.getLocation().getFile());
        if (f.exists()) {
            try {
                JarInputStream jarFile = new JarInputStream(
                        new FileInputStream(src.getLocation().getFile()));
                JarEntry jarEntry;
                //process all files in Jar file
                while (true) {
                    jarEntry = jarFile.getNextJarEntry();
                    //end of jar file
                    if (jarEntry == null) {
                        break;
                    }
                    if (!jarEntry.isDirectory()  && jarEntry.getName().matches(pattern)) {
                     //   System.out.println(jarEntry.getName());
                        resources.add(new File(jarEntry.getName()));
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return resources;
    }

    /**
     * load classes from fileSystem
     *
     * @param src executable class
     * @throws Exception
     */
    private static ArrayList<File> getPathClasses(CodeSource src, String pattern) throws Exception {
        ArrayList<File> lst = new ArrayList<>();
        getPathClasses(lst, new File(src.getLocation().getPath()), new File(src.getLocation().getFile()), pattern);
        return lst;
    }

    /**
     * load classes from fileSystem
     *
     * @param path path of file system
     * @param root root file
     * @throws Exception
     */
    private static void getPathClasses(ArrayList<File> lst, File path, File root, String pattern) throws Exception {
        File[] files = root.listFiles();
        for (File file : files) {
            if (file.isFile()) {
                if (file.getName().matches(pattern)) {
                  //  System.out.println(file.getAbsolutePath());
                    lst.add(file);
                }
            } else {
                getPathClasses(lst, path, file, pattern);
            }
        }
    }
 
}
