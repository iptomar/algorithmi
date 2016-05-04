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

import flowchart.utils.Base64;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;
import ui.FLog;
import ui.FProperties;

/**
 * Created on 6/dez/2015, 21:18:36
 *
 * @author zulu - computer
 */
public class Crypt {

    public static String encrypt(String message) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptAlgorithm);
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(FProperties.copyright().toCharArray()));
            Cipher pbeCipher = Cipher.getInstance(cryptAlgorithm);
            pbeCipher.init(Cipher.ENCRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return Base64.encodeBytes(pbeCipher.doFinal(Zip.compress(message.getBytes("UTF-8"))));
        } catch (Exception ex) {
            FLog.printLn("Crypt.encrypt(" + message + ") " + ex.getMessage());
            return Base64.encodeBytes(Zip.compress(message.getBytes()));
        }

    }

    public static String decrypt(String message) {
        try {
            SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(cryptAlgorithm);
            SecretKey key = keyFactory.generateSecret(new PBEKeySpec(FProperties.copyright().toCharArray()));
            Cipher pbeCipher = Cipher.getInstance(cryptAlgorithm);
            pbeCipher.init(Cipher.DECRYPT_MODE, key, new PBEParameterSpec(SALT, 20));
            return new String(Zip.expand(pbeCipher.doFinal(Base64.decode(message))), "UTF-8");
        } catch (Exception ex) {
            FLog.printLn("Crypt.decrypt(" + message + ") " + ex.getMessage());
            try {
                return new String(Zip.expand(Base64.decode(message)), "UTF-8");
            } catch (Exception ex1) {
                return message;
            }
        }
    }

    public static boolean isEqual(char[] p1, char[] p2) {
        return Arrays.equals(p1, p2);
    }

    public static String toString(char[] p1) {
        return new String(p1);
    }

    public static String getCryptFileName(String fileName) {
        String crypt = encrypt(fileName);
        if (crypt.length() > 16) {
            return crypt.substring(0, 16);
        }
        return crypt;
    }

    private static final byte[] SALT = {
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,
        (byte) 0xde, (byte) 0x33, (byte) 0x10, (byte) 0x12,};

    /**
     *
     * @param message
     * @return
     */
    public static String getHashString(String message) {
        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            byte[] hashedBytes = digest.digest(message.getBytes("UTF-8"));
            return Base64.encodeBytes(hashedBytes);
        } catch (Exception e) {
            return "ERROR toHashString";
        }
    }

    public static boolean isValidHashString(String message, String hash) {
        try {
            MessageDigest digest = MessageDigest.getInstance(hashAlgorithm);
            byte[] msgHash = digest.digest(message.getBytes("UTF-8"));
            byte[] myHash = Base64.decode(hash);
            if (msgHash.length != myHash.length) {
                return false;
            }
            for (int i = 0; i < myHash.length; i++) {
                if (myHash[i] != msgHash[i]) {
                    return false;
                }

            }

        } catch (Exception e) {
            return false;
        }
        return true;
    }
    private static String cryptAlgorithm = "PBEWithMD5AndDES";
    private static String hashAlgorithm = "SHA-256";
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static long serialVersionUID = 201512131758L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
