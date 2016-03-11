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

import flowchart.utils.image.ImageUtils;
import i18n.Fi18N;
import java.io.IOException;
import java.io.Serializable;
import javax.swing.ImageIcon;
import ui.FLog;
import ui.utils.Crypt;

/**
 * Created on 11/dez/2015, 11:15:26
 *
 * @author zulu - computer
 */
public class UserName implements Serializable {

    public static int AVATAR_SIZE = 96; // size of avatar

    private String name; // username
    private String fullName; // full name
    private String code; // code
    private byte[] avatar; // avatar
    private String password; // encrypted password

    public UserName() {
        name = Fi18N.get("LAUNCHER.newName");
        fullName = Fi18N.get("LAUNCHER.newFullName");
        code = Fi18N.get("LAUNCHER.newNumber");
        try {
            avatar = ImageUtils.getJpegByteArray(Fi18N.loadKeyIcon("PROPERTIES.userDefaultAvatar.icon", AVATAR_SIZE));
        } catch (Exception ex) {
            FLog.printLn("User UserName() " + ex.getMessage());
            avatar = null;
        }
        setPassword("");
    }

    public UserName(String name, String fullName, String code, ImageIcon avatar, String password) {
        this.name = name.trim();
        this.fullName = fullName.trim();
        this.code = code.trim();
        this.avatar = ImageUtils.getJpegByteArray(avatar);
        this.password = password;
    }

    public String toString() {
        return name;
    }

    public static UserName createUser(String digitalSignature) {
        try {
            return (UserName) Base64.decodeObject(Crypt.decrypt(digitalSignature));
        } catch (Exception ex) {
            FLog.printLn("UserName createUser" + ex.getMessage());
            UserName user = new UserName();
            user.avatar = ImageUtils.getJpegByteArray(Fi18N.loadKeyIcon("PROPERTIES.userErrorAvatar.icon", AVATAR_SIZE));
            return user;
        }
    }

    public String digitalSignature() {
        try {
            return Crypt.encrypt(Base64.encodeObject(this));
        } catch (IOException ex) {
            FLog.printLn("User diditalSignature() " + ex.getMessage());
            return "NOT SIGNED";
        }
    }

    public boolean passwordMatch(char[] pass) {
        return Crypt.isEqual(pass, Crypt.decrypt(password).toCharArray());
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the fullName
     */
    public String getFullName() {
        return fullName;
    }

    /**
     * @param fullName the fullName to set
     */
    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return code;
    }

    /**
     * @param code the code to set
     */
    public void setCode(String code) {
        this.code = code;
    }

    /**
     * @return the avatar
     */
    public ImageIcon getAvatar() {
        return ImageUtils.getByteArrayJpeg(avatar);
    }

    /**
     * @param avatar the avatar to set
     */
    public void setAvatar(ImageIcon avatar) {
        try {
            this.avatar = ImageUtils.getJpegByteArray(
                    ImageUtils.resizeProportional(avatar, AVATAR_SIZE, AVATAR_SIZE));
        } catch (Exception e) {
        }
    }

    /**
     * @return the password (decrypted)
     */
    public String getPassword() {
        return Crypt.decrypt(password);
    }

    /**
     * @param password the password to set
     */
    public void setPassword(String password) {
        this.password = Crypt.encrypt(password);
    }

    /**
     * @param password the password to set
     */
    public void setPassword(char[] password) {
        this.password = Crypt.encrypt(new String(password));
    }
    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512111115L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
