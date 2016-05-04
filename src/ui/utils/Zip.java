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
package ui.utils;

import java.io.ByteArrayOutputStream;
import java.util.zip.Deflater;
import java.util.zip.Inflater;
import ui.FLog;

/**
 * Created on 13/dez/2015, 23:53:51
 *
 * @author zulu - computer
 */
public class Zip {

    public static byte[] compress(byte[] data) {
        if (data.length == 0) { // empty data
            return data;
        }
        try {
            Deflater deflater = new Deflater();
            deflater.setInput(data);
            ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
            deflater.finish();
            byte[] buffer = new byte[1024];
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                out.write(buffer, 0, count);
            }
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (Exception ex) {
            FLog.printLn("ZIP compress " + ex.getMessage());
            return data;
        }
    }
    /**
     * expands a ziped data
     * @param data data
     * @return 
     */  
    public static byte[] expand(byte[] data) {
        if (data.length == 0) { // empty data
            return data;
        }
        try {
            Inflater inflater = new Inflater();
            inflater.setInput(data);
            ByteArrayOutputStream out = new ByteArrayOutputStream(data.length);
            byte[] buffer = new byte[1024];
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                out.write(buffer, 0, count);
            }
            out.flush();
            out.close();
            return out.toByteArray();
        } catch (Exception ex) {
            FLog.printLn("ZIP decompress " + ex.getMessage());
            return data;
        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512132353L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
