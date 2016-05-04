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
package flowchart.utils.images;
// RCS Private Heading

/**
 * ***************************************************************************
 * $Name: $ * $Log: SerializedImage.java,v $ Revision 1.2 2003/02/28 18:36:20
 * dtm24 Added to header comments.
 *
 * Revision 1.1 2003/02/28 18:34:09 dtm24 Images now work properly, using
 * SerializedImage to serialize them. Past images are not stored.
 *
 * Revision 1.2 1998/09/22 13:45:29 nobody ServerJ3D shutdown button, and
 * informations area. Tostring in SerializedImage. Javadoc debug ;) File
 * organization.
 *
 * Revision 1.1 1998/09/08 12:53:41 nobody Initial revision
 *
 *
 * $RCSfile: SerializedImage.java,v $ $Revision: 1.2 $ $Date: 2003/02/28
 * 18:36:20 $
 *
 * $Author: dtm24 $
 * **************************************************************************
 */

/*
 * Imported into jinn by David Morgan. Originally from ServicesJ3D:
 * 
 * http://www.ifrance.com/foxworld/servicesj3d/
 *
 * It appears to be free software but has no license.
 */
// Core Java
import java.io.*;
// AWT
import java.awt.image.BufferedImage;
import java.awt.Image;
import java.awt.image.PixelGrabber;
import ui.FLog;

/**
 * BufferedImage or Image serializable (<I>Can be use with RMI.</I>).
 * <HR WIDTH=25%>
 * <DL>
 * <DT><B>Public RCS Heading:</B>
 * <DD>$Id: SerializedImage.java,v 1.2 2003/02/28 18:36:20 dtm24 Exp $</DD>
 * </DT>
 * </DL>
 *
 * <DL>
 * <DT><B>Core Java:</B>
 * <DD>java.io.*</DD>
 * </DT>
 * <DT><B>AWT:</B>
 * <DD>java.awt.image.BufferedImage</DD>
 * <DD>java.awt.Image</DD>
 * <DD>java.awt.image.PixelGrabber</DD>
 * </DT>
 * </DL>
 *
 * <HR WIDTH=10%>
 *
 * @version $Revision: 1.2 $
 * @author $Author: dtm24 $
 * @since Jdk 1.2 beta4 and Java3D 1.1 beta 1.
 * @see Image
 * @see BufferedImage
 * @see Serializable
 *
 */
public class SerializedImage implements Serializable {

    /**
     * A BufferedImage to serialized.
     *
     * @serial bimage A BufferedImage to serialized.
     */
    private BufferedImage bimage;

    /**
     * Create a SerializedImage with a BufferedImage (<I>Can be use with
     * RMI</I>).
     *
     * @param bufferedImage Initial BufferedImage.
     */
    public SerializedImage(BufferedImage bufferedImage) {
        bimage = bufferedImage;
    }

    /**
     * Create a SerializedImage (<I>Can be use with RMI</I>).
     *
     * @param image Initial Image.
     * @param width Width of this image.
     * @param Height height of this image.
     */
    public SerializedImage(Image image, int width, int height) {
        boolean errorFlag = false;

        PixelGrabber pg = new PixelGrabber(image, 0, 0, width, height, true);
        try {
            pg.grabPixels();
        } catch (InterruptedException e) {
            FLog.printLn("* Error while creating SerializedImage from Image");
            errorFlag = true;
        }
        bimage = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        if (!errorFlag) {
            bimage.setRGB(0, 0, width, height, (int[]) pg.getPixels(), 0, width);
        }
    }

    /**
     * Get an BufferedImage of this SerializedImage.
     */
    public BufferedImage getBImage() {
        return bimage;
    }

    /**
     * Get the width of this SerializedImage.
     *
     * @return Width in pixels.
     */
    public int getWidth() {
        return bimage.getWidth();
    }

    /**
     * Get the height of this SerializedImage.
     *
     * @return Height in pixels.
     */
    public int getHeight() {
        return bimage.getHeight();
    }

    /**
     * Returns a string representation of the object. Like this :
     * SerializedImage[w=bimageWidth,h=bimageHeight].
     */
    public String toString() {
        return new String("SerializedImage[w=" + bimage.getWidth() + ",h=" + bimage.getHeight() + "]");
    }

    // Serialization of the BufferedImage 
    private void writeObject(ObjectOutputStream s) throws IOException {
        s.writeInt(bimage.getWidth());
        s.writeInt(bimage.getHeight());
        s.writeInt(bimage.getType());

        for (int i = 0; i < bimage.getWidth(); i++) {
            for (int j = 0; j < bimage.getHeight(); j++) {
                s.writeInt(bimage.getRGB(i, j));
            }
        }
        s.flush();
    }

    private void readObject(ObjectInputStream s) throws IOException, ClassNotFoundException {
        bimage = new BufferedImage(s.readInt(), s.readInt(), s.readInt());
        for (int i = 0; i < bimage.getWidth(); i++) {
            for (int j = 0; j < bimage.getHeight(); j++) {
                bimage.setRGB(i, j, s.readInt());
            }
        }

    }

   


}
