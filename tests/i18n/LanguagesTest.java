/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package i18n;

import static i18n.LanguagesOfSystem.getLanguages;
import java.util.List;
import junit.framework.TestCase;

/**
 *
 * @author zulu
 */
public class LanguagesTest extends TestCase {

    public LanguagesTest(String testName) {
        super(testName);
    }

    /**
     * Test of getLanguages method, of class LanguagesOfSystem.
     */
    public void testGetLanguagesCodes() {
        String langs[] = {
            "xpto_pt_PT.properties",
            "xpto_en_US.properties",
            "xpto_ar_EG.properties",
            "xpto_el_GR.properties",
            "xpto_zh_CN.properties",};
        for (String lang : langs) {
            LanguageCode l = new LanguageCode(lang);
            System.out.println(l.getLanguageCountry());
        }
    }

    public static void testGetLanguages() {
        List<LanguageCode> l = getLanguages();
        for (LanguageCode languageCode : l) {
            System.out.println(languageCode.getLanguageCountry());
        }
    }

}
