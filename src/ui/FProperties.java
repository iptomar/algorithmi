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
package ui;

import flowchart.shape.Fshape;
import flowchart.utils.UserName;
import i18n.EditorI18N;
import i18n.Fi18N;
import i18n.FkeyWord;
import java.awt.Color;
import java.awt.Font;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Properties;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;
import javax.swing.plaf.ColorUIResource;
import ui.utils.Crypt;

/**
 * Created on 19/out/2015, 17:08:26
 *
 * @author zulu - computer
 */
public class FProperties {

    public static void init() {
        FkeyWord.init();
        Fi18N.init();
        FkeyWord.init();
    }

    //path to save properties file
    public static File PROPERTIES_PATH = new File(System.getProperty("user.dir") + File.separator + "users");
    //extension of properties file
    public static String PROPERTIES_USER_EXTENSION = "user";

    private static Properties props = new Properties();

    //--------------------------------------------------------------------------Language
    public static String languageKey = "language";
    public static String language = "pt";
    public static String countryKey = "country";
    public static String country = "PT";
    //-------------------------------------------------------------------------- SIZE OF SHAPES    
    public static String MIN_BORDER_SIZE_Key = "MIN_BORDER_SIZE"; // minimum border of shapes
    public static int BORDER_SIZE = 2;
    public static String ARROW_LENGHT_RATIO_key = "ARROW_LENGHT_RATIO"; // distance Between  Shapes
    public static double ARROW_LENGHT_RATIO = 0.5; // factor to multiply zoom

    public static final String SPACE_BETWEEN_LEVELS_key = "SPACE_BETWEEN_LEVELS";
    public static int SPACE_BETWEEN_LEVELS = 0; // space betwen levels in the flowchart design

    //--------------------------------------------------------------------------colors of the shapes
    public static String arrowColorkey = "arrowColor";
    public static Color arrowColor = Color.WHITE;
    public static String terminatorColorkey = "terminatorColor";
    public static Color terminatorColor = new Color(178, 178, 255);
    public static String writeColorkey = "writeColor";
    public static Color writeColor = new Color(210, 255, 255);
    public static String returnColorkey = "returnColor";
    public static Color returnColor = FProperties.terminatorColor;
    public static String readColorkey = "readColor";
    public static Color readColor = new Color(255, 153, 255);
    public static String executeColorkey = "executeColor";
    public static Color executeColor = new Color(255, 255, 210);
    public static String defineColorkey = "defineColor";
    public static Color defineColor = new Color(255, 210, 210);
    public static String whileDoColorkey = "whileDoColor";
    public static Color whileDoColor = Color.PINK;
    public static String decisionColorkey = "ifElseColor";
    public static Color decisionColor = new Color(194, 255, 133);
    public static String iterationColorkey = "forNextColor";
    public static Color iterationColor = Color.YELLOW;

    public static String doWhileColorkey = "doWhileColor";
    public static Color doWhileColor = Color.MAGENTA;
    public static String connectorColorkey = "connectorColor";
    public static Color connectorColor = new Color(255, 194, 102);

    public static String jumpColorkey = "jumpColor";
    public static Color jumpColor = connectorColor;
    //-------------------------------------------------------------------------- font to the shapes
    public static String fontNamekey = "fontName";
    public static String fontNameValue = "courier new";
    public static String fontStyleKey = "fontType";
    public static int fontTypeValue = Font.BOLD;
    public static String fontSizekey = "fontSize";
    public static int fontSizeValue = 16;
    public static Font font = new Font(fontNameValue, fontTypeValue, fontSizeValue);
    //-------------------------------------------------------------------------- EXECUTION COLORS
    public static String selectedColorKey = "selectedColor";
    public static Color selectedColor = new Color(100, 255, 100); // color of selected
    public static String runColorKey = "executedColor";
    public static Color runColor = new Color(200, 255, 200); // color of executed
    public static String errorColorKey = "errorColor";
    public static Color errorColor = new Color(255, 100, 100); // color of the runError
    public static String lineColorKey = "lineColor";
    public static Color lineColor = Color.BLACK; // color of the lines
    public static String executionColorKey = "sintaxColorText";
    public static Color executionColor = new Color(255, 133, 102); // color of execution
    //-------------------------------------------------------------------------- SINTAX HIGHLIGHT
    public static String keySintaxBackground = "keySintaxBackground";
    public static Color colorSintaxBackGround = new Color(255, 255, 255); // background color

    public static String keySintaxNormal = "keySintaxNormal";
    public static Color colorSintaxNormal = new Color(0, 0, 0); // normal color

    public static String keySintaxKeyword = "keySintaxKeyword";
    public static Color colorSintaxKeyword = new Color(0, 0, 255); // reserved word

    public static String keySintaxString = "keySintaxString";
    public static Color colorSintaxString = new Color(200, 0, 200); // String

    public static String keySintaxReal = "keySintaxReal";
    public static Color colorSintaxReal = new Color(153, 0, 0); // real number

    public static String keySintaxInteger = "keySintaxInteger";
    public static Color colorSintaxInteger = new Color(255, 0, 0); // integer Number

    public static String keySintaxLogic = "keySintaxLogic";
    public static Color colorSintaxLogic = new Color(200, 200, 0); // logic value

    public static String keySintaxOperator = "keySintaxOperator";
    public static Color colorSintaxOperator = new Color(0, 0, 255); // operator

    public static String keySintaxFunction = "keySintaxFunction";
    public static Color colorSintaxFunction = new Color(0, 153, 0); // function

    public static String keySintaxComments = "keySintaxComments";
    public static Color colorSintaxComments = new Color(128, 128, 128); // comments

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    //:::::::::::::::::::: USERNAME ::::::::::::::::::::::::::::::::::::::::::::
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    public static String keyDigitalSignature = "keyDigitalSignature";
    private static String digitalSignature = "NOT SIGNED";

    public static String keyLastProgram = "keyLastProgramOpened";
    public static String lastProgram = "noName";
//

    static {
        PROPERTIES_PATH.mkdirs();
        loadDefaults(); // getUser defaults
    }

    public static void reLoad() {
        getUser(FProperties.getUser().getName());
    }

    /**
     * open file of properties
     */
    public static UserName getUser(String fileName) {
        return loadFromFile(PROPERTIES_PATH.getAbsolutePath() + File.separator + fileName);
    }

    public static void loadProperties(UserName user) {
        //load core    
        loadFromFile(PROPERTIES_PATH.getAbsolutePath() + File.separator + user.getName());
//        props = new Properties();
//        language = user.getLanguageCode();
//        country = user.getCountryCode();
//        props.setProperty(languageKey, user.getLanguage());
//        props.setProperty(countryKey, user.getCountry());
        
    }

    public static UserName loadFromFile(String fileName) {
        if (!fileName.endsWith("." + PROPERTIES_USER_EXTENSION)) {
            fileName += "." + PROPERTIES_USER_EXTENSION;
        }
        try {
            props = new Properties();
            FileInputStream file = new FileInputStream(fileName);
            props.load(file); // getUser file                 
            file.close();
        } catch (IOException ex) {
            FLog.printLn("FPROPERTIES LOAD FILE ERROR : " + fileName);
            loadDefaults(); // getUser defaults
        }
        updateSystemProperties();
        return UserName.createUser(digitalSignature);
    }

    public static void loadDefaults() {
        FLog.printLn("FPROPERTIES loadDefaults ");
        props = new Properties();
        props.setProperty(languageKey, "pt");
        props.setProperty(countryKey, "PT");
        //load core
        FkeyWord.load(props.getProperty(languageKey), props.getProperty(countryKey));
        //load language
        Fi18N.load(props.getProperty(languageKey), props.getProperty(countryKey));
        //load editor
        EditorI18N.load(props.getProperty(languageKey), props.getProperty(countryKey));

        setFont(new Font(fontNameValue, fontTypeValue, fontSizeValue));

        setColor(terminatorColorkey, terminatorColor);
        setColor(writeColorkey, writeColor);
        setColor(returnColorkey, returnColor);
        setColor(readColorkey, readColor);
        setColor(executeColorkey, executeColor);
        setColor(defineColorkey, defineColor);
        setColor(whileDoColorkey, whileDoColor);
        setColor(decisionColorkey, decisionColor);
        setColor(iterationColorkey, iterationColor);
        setColor(doWhileColorkey, doWhileColor);
        setColor(connectorColorkey, connectorColor);
        setColor(arrowColorkey, arrowColor);
        setColor(jumpColorkey, jumpColor);

        setColor(selectedColorKey, selectedColor);
        setColor(runColorKey, runColor);
        setColor(errorColorKey, errorColor);
        setColor(lineColorKey, lineColor);
        setColor(executionColorKey, executionColor);

        props.setProperty(MIN_BORDER_SIZE_Key, BORDER_SIZE + "");
        props.setProperty(ARROW_LENGHT_RATIO_key, ARROW_LENGHT_RATIO + "");
        props.setProperty(SPACE_BETWEEN_LEVELS_key, SPACE_BETWEEN_LEVELS + "");

        setColor(keySintaxBackground, colorSintaxBackGround);
        setColor(keySintaxNormal, colorSintaxNormal);
        setColor(keySintaxKeyword, colorSintaxKeyword);
        setColor(keySintaxString, colorSintaxString);
        setColor(keySintaxReal, colorSintaxReal);
        setColor(keySintaxInteger, colorSintaxInteger);

        setColor(keySintaxLogic, colorSintaxLogic);
        setColor(keySintaxOperator, colorSintaxOperator);
        setColor(keySintaxFunction, colorSintaxFunction);
        setColor(keySintaxComments, colorSintaxComments);

        props.setProperty(keyDigitalSignature, new UserName().digitalSignature());
    }

    /**
     * save file of properties
     */
    public static void save() {
        try {
            FileOutputStream file = new FileOutputStream(
                    PROPERTIES_PATH.getAbsolutePath() + File.separator
                    + UserName.createUser(digitalSignature).getName() + "." + PROPERTIES_USER_EXTENSION);
            props.store(file, EditorI18N.get("APPLICATION.title"));

        } catch (IOException ex) {
            FLog.printLn("FPROPERTIES save " + ex.getMessage());
            Logger.getLogger(FProperties.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * open file of properties
     */
    public static void save(String fileName) {
        if (!fileName.endsWith("." + PROPERTIES_USER_EXTENSION)) {
            fileName += "." + PROPERTIES_USER_EXTENSION;
        }
        try {
            FileOutputStream file = new FileOutputStream(
                    PROPERTIES_PATH.getAbsolutePath() + File.separator + fileName);
            props.store(file, EditorI18N.get("APPLICATION.title"));
            file.close();

        } catch (IOException ex) {
            FLog.printLn("FPROPERTIES save " + ex.getMessage());
            Logger.getLogger(FProperties.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
    }

//    /**
//     * open file of properties
//     */
//    public static void saveCrypt(String userName) {
//        userName = Crypt.getCryptFileName(userName.trim());
//        userName += PROPERTIES_USER_EXTENSION;
//        save(userName);
//    }
    public static void updateSystemProperties() {
        try {

            // language = props.getProperty(languageKey);
            // country = props.getProperty(countryKey);
            //load core
            FkeyWord.load(language, country);
            //load language
            Fi18N.load(language, country);
            //load editor
            EditorI18N.load(language, country);

            font = getFont();
            Fshape.myFONT = font;

            terminatorColor = getColor(terminatorColorkey);
            writeColor = getColor(writeColorkey);
            returnColor = getColor(returnColorkey);
            readColor = getColor(readColorkey);
            executeColor = getColor(executeColorkey);
            defineColor = getColor(defineColorkey);
            whileDoColor = getColor(whileDoColorkey);
            decisionColor = getColor(decisionColorkey);
            iterationColor = getColor(iterationColorkey);
            doWhileColor = getColor(doWhileColorkey);
            connectorColor = getColor(connectorColorkey);
            arrowColor = getColor(arrowColorkey);
            jumpColor = getColor(jumpColorkey);

            selectedColor = getColor(selectedColorKey);
            runColor = getColor(runColorKey);
            errorColor = getColor(errorColorKey);
            lineColor = getColor(lineColorKey);
            executionColor = getColor(executionColorKey);
            try {
                BORDER_SIZE = Integer.parseInt(props.getProperty(MIN_BORDER_SIZE_Key));
            } catch (Exception e) {
                FLog.runError("updateStaticVars() BORDER_SIZE" + e.getMessage());
            }
            try {
                ARROW_LENGHT_RATIO = Double.parseDouble(props.getProperty(ARROW_LENGHT_RATIO_key));
            } catch (Exception e) {
                FLog.runError("updateStaticVars() ARROW_LENGHT_RATIO" + e.getMessage());
            }
            try {
                SPACE_BETWEEN_LEVELS = Integer.parseInt(props.getProperty(SPACE_BETWEEN_LEVELS_key));
            } catch (Exception e) {
                FLog.runError("updateStaticVars() SPACE_BETWEEN_LEVELS" + e.getMessage());
            }

            colorSintaxBackGround = getColor(keySintaxBackground);
            colorSintaxNormal = getColor(keySintaxNormal);
            colorSintaxKeyword = getColor(keySintaxKeyword);
            colorSintaxString = getColor(keySintaxString);
            colorSintaxReal = getColor(keySintaxReal);
            colorSintaxInteger = getColor(keySintaxInteger);

            colorSintaxLogic = getColor(keySintaxLogic);
            colorSintaxOperator = getColor(keySintaxOperator);
            colorSintaxFunction = getColor(keySintaxFunction);
            colorSintaxComments = getColor(keySintaxComments);

            digitalSignature = get(keyDigitalSignature);
            lastProgram = get("keyLastProgramOpened");

            Fi18N.load(language, country);

            UIManager.put("ComboBox.background", new ColorUIResource(getColor(keySintaxBackground)));

        } catch (Exception e) {
            loadDefaults();
        }

    }

    public static Color getColor(String key) {
        try {
            String R = props.getProperty(key + "_R");
            String G = props.getProperty(key + "_G");
            String B = props.getProperty(key + "_B");
            String A = props.getProperty(key + "_A");
            return new Color(
                    Integer.parseInt(R),
                    Integer.parseInt(G),
                    Integer.parseInt(B),
                    Integer.parseInt(A));
        } catch (Exception e) {
            FLog.runError(" public static Color getColor(String key) " + e.getMessage());
            return Color.GRAY;
        }
    }

    public static void set(String key, String value) {
        props.setProperty(key, value);
    }

    public static String get(String key) {
        return props.getProperty(key);
    }

    public static void setColor(String key, Color color) {
        props.setProperty(key + "_R", color.getRed() + "");
        props.setProperty(key + "_G", color.getGreen() + "");
        props.setProperty(key + "_B", color.getBlue() + "");
        props.setProperty(key + "_A", color.getAlpha() + "");
    }

    public static void setFont(Font f) {
        props.setProperty(fontNamekey, f.getFamily());
        props.setProperty(fontStyleKey, f.getStyle() + "");
        props.setProperty(fontSizekey, f.getSize() + "");
    }

    public static Font getFont() {
        try {
            String name = props.getProperty(fontNamekey);
            int style = Integer.parseInt(props.getProperty(fontStyleKey));
            int size = Integer.parseInt(props.getProperty(fontSizekey));
            return new Font(name, style, size);
        } catch (Exception e) {
            FLog.runError(" public static Font getFont() " + e.getMessage());
            return font;
        }

    }

    public static int getMIN_BORDER() {
        try {
            Integer.parseInt(props.getProperty(MIN_BORDER_SIZE_Key));
        } catch (Exception e) {
            FLog.runError("  public static int getMIN_BORDER() " + e.getMessage());
        }
        return 2;
    }

    public static void setLanguageSufix(String lang_country) {
        language = lang_country.substring(0, lang_country.indexOf("_"));
        country = lang_country.substring(lang_country.indexOf("_") + 1);
        props.setProperty(languageKey, language);
        props.setProperty(countryKey, country);
    }

    public static String getLanguageSufix() {
        return language + "_" + country;
    }

    public static void setSPACE_BETWEEN_LEVELS(int value) {
        props.setProperty(SPACE_BETWEEN_LEVELS_key, value + "");
    }

    public static void setMinBorder(int border) {
        props.setProperty(MIN_BORDER_SIZE_Key, border + "");
    }

    public static void setArrowLength(double ratio) {
        props.setProperty(ARROW_LENGHT_RATIO_key, ratio + "");
    }

    public static void setUser(UserName user) {
        digitalSignature = user.digitalSignature();
        props.setProperty(keyDigitalSignature, digitalSignature);
    }

    public static UserName getUser() {
        return UserName.createUser(digitalSignature);
    }

    public static boolean delete() {
        try {

            String file = PROPERTIES_PATH.getAbsolutePath() + File.separator
                    + FProperties.getUser().getName() + "." + PROPERTIES_USER_EXTENSION;
            File f = new File(file);
            f.deleteOnExit();
            Files.delete(f.toPath());
            FLog.printLn("FProperties deleteFile " + file);
            return true;
        } catch (IOException ex) {
            FLog.printLn("FProperties deleteFile  ERROR" + FProperties.getUser().getName() + " " + ex.getMessage());
        }
        return false;
    }

    public static String copyright() {
        return "M@nso 2015";
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510191708L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
