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
package flowchart.utils.image;

import core.data.exception.FlowchartException;
import flowchart.utils.Base64;
import flowchart.utils.FileUtils;
import i18n.Fi18N;
import static i18n.Fi18N.IMG_LOCATION;
import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.IIOImage;
import javax.imageio.ImageIO;
import javax.imageio.ImageWriteParam;
import javax.imageio.ImageWriter;
import javax.imageio.stream.ImageOutputStream;
import javax.imageio.stream.MemoryCacheImageOutputStream;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.filechooser.FileNameExtensionFilter;
import ui.FLog;

/**
 * Created on 9/dez/2015, 11:19:45
 *
 * @author zulu - computer
 */
public class ImageUtils {

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon loadResourceIcon(String imageFile, int height) {
        try {
            ImageIcon iconImage = new ImageIcon(Fi18N.class.getResource(imageFile));
            Image newimg = null;
            if (height > 0) {
                //calcular width
                double ratio = (double) iconImage.getIconWidth() / (double) iconImage.getIconHeight();
                int width = (int) (ratio * height);
                //resize icon
                newimg = iconImage.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            } else {
                newimg = iconImage.getImage();
            }
            iconImage.setImage(newimg);
            return iconImage;
        } catch (Exception e) {
            FLog.printLn("Fi18n loadIcon " + imageFile + " height " + height + " ERROR " + IMG_LOCATION + imageFile);
            return null;
        }
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon loadPathIcon(String imageFile, int height) {
        try {
            ImageIcon iconImage = new ImageIcon(imageFile);
            Image newimg = null;
            if (height > 0) {
                //calcular width
                double ratio = (double) iconImage.getIconWidth() / (double) iconImage.getIconHeight();
                int width = (int) (ratio * height);
                //resize icon
                newimg = iconImage.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH);
            } else {
                newimg = iconImage.getImage();
            }
            iconImage.setImage(newimg);
            return iconImage;
        } catch (Exception e) {
            FLog.printLn("Fi18n loadIcon " + imageFile + " height " + height + " ERROR " + IMG_LOCATION + imageFile);
            return null;
        }
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param width width of the icon
     * @param height height of the icon
     * @return icon with size
     */
    public static ImageIcon loadPathIcon(String imageFile, int width, int height) {
        ImageIcon iconImage = new ImageIcon(imageFile);
        return resize(iconImage, width, height);
    }

    public static ImageIcon resize(ImageIcon image, int width, int height) {
        return new ImageIcon(image.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }

    public static ImageIcon resizeProportional(ImageIcon image, int width, int height) {
        if (image.getIconHeight() <= height && image.getIconWidth() <= width) {
            return image;
        }
        double pWidth = (double) width / (double) image.getIconWidth();
        double pHeight = (double) height / (double) image.getIconHeight();

        if (pWidth < pHeight) {
            height = (int) (image.getIconHeight() * pWidth);
            width = (int) (image.getIconWidth() * pWidth);
        } else {
            height = (int) (image.getIconHeight() * pHeight);
            width = (int) (image.getIconWidth() * pHeight);
        }
        return new ImageIcon(image.getImage().getScaledInstance(width, height, java.awt.Image.SCALE_SMOOTH));
    }

    public static void saveImage(Icon icon, String filename) {
        try {
            Image img = ((ImageIcon) icon).getImage();
            BufferedImage bi = new BufferedImage(img.getWidth(null), img.getHeight(null), BufferedImage.TYPE_INT_ARGB);
            Graphics2D g2 = bi.createGraphics();
            g2.drawImage(img, 0, 0, null);
            g2.dispose();
            ImageIO.write(bi, "png", new File(filename));
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public static void saveBase64(ImageIcon icon, String filename) {
        try {
            String txt = Base64.encodeObject(icon);
            PrintWriter stream = new PrintWriter(filename);
            stream.write(txt);
            stream.flush();
            stream.close();
        } catch (Exception e) {
            FLog.printLn("saveBase64 " + filename + " " + e.getMessage());
        }
    }

    public static ImageIcon loadBase64(String filename) {
        try {
            try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
                StringBuilder sb = new StringBuilder();
                String line = br.readLine();
                while (line != null) {
                    sb.append(line);
                    sb.append(System.lineSeparator());
                    line = br.readLine();
                }
                return (ImageIcon) Base64.decodeToObject(sb.toString());
            }
        } catch (Exception e) {
            FLog.printLn("loadBase64 " + filename + " " + e.getMessage());
        }
        return null;
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon getIconBase64(String image, int height) {
        try {
            return (ImageIcon) Base64.decodeToObject(image.toString());
        } catch (Exception e) {
            FLog.printLn("getIconBase64 load image  " + image);
            return Fi18N.loadKeyIcon("PROPERTIES.userAvatar.icon", 96);
        }
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static String getIconBase64(ImageIcon image) {
        try {
            return Base64.encodeObject(image);
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Selects an image
     *
     * @return ImageIcon
     */
    public static ImageIcon selectImageFromFile() {
        JFileChooser fc = new JFileChooser();
        fc.setCurrentDirectory(new File(System.getProperty("user.dir")));
        fc.setFileFilter(new FileNameExtensionFilter("Image files", ImageIO.getReaderFileSuffixes()));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("JPG Images", "jpg"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("PNG Images", "png"));
        fc.addChoosableFileFilter(new FileNameExtensionFilter("GIF Images", "gif"));
        int returnVal = fc.showDialog(null, Fi18N.get("PROPERTIES.userAvatar.message"));
        try {
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                ImageIcon img = new ImageIcon(ImageIO.read(fc.getSelectedFile()));
                return img;
                //return resize(img, 96, 96);
            }
        } catch (IOException e) {
        }
        return null;
    }

    public static void writeJPG(BufferedImage bufferedImage, OutputStream outputStream, float quality) throws IOException {
        Iterator<ImageWriter> iterator = ImageIO.getImageWritersByFormatName("jpg");
        ImageWriter imageWriter = iterator.next();
        ImageWriteParam imageWriteParam = imageWriter.getDefaultWriteParam();
        imageWriteParam.setCompressionMode(ImageWriteParam.MODE_EXPLICIT);
        imageWriteParam.setCompressionQuality(quality);
        ImageOutputStream imageOutputStream = new MemoryCacheImageOutputStream(outputStream);
        imageWriter.setOutput(imageOutputStream);
        IIOImage iioimage = new IIOImage(bufferedImage, null, null);
        imageWriter.write(null, iioimage, imageWriteParam);
        imageOutputStream.flush();
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static String getJpegBase64(ImageIcon image) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            writeJPG(convertToBufferedImage(image), out, 0.95f);
            return Base64.encodeBytes(out.toByteArray());
        } catch (Exception e) {
            return "ERROR";
        }
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static byte[] getJpegByteArray(ImageIcon image) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            writeJPG(convertToBufferedImage(image), out, 0.99f);
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }
    
    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static byte[] getJpegByteArray(Icon image) {
        try {
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            writeJPG(convertToBufferedImage(image), out, 0.99f);
            return out.toByteArray();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon getBase64Jpeg(String jpeg) {
        try {
            byte[] data = Base64.decode(jpeg);
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BufferedImage img = ImageIO.read(in);
            return new ImageIcon(img);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon getByteArrayJpeg(byte[] data) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BufferedImage img = ImageIO.read(in);
            return new ImageIcon(img);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Load a icon in the path
     *
     * @param imageFile name of the file
     * @param height height of the icon [ 0 ] = not scalling
     * @return icon with size
     */
    public static ImageIcon getByteArrayJpeg(byte[] data, int size) {
        try {
            ByteArrayInputStream in = new ByteArrayInputStream(data);
            BufferedImage img = ImageIO.read(in);
            Image newimg = img.getScaledInstance(size, size, java.awt.Image.SCALE_SMOOTH);
            return new ImageIcon(newimg);
        } catch (IOException ex) {
            Logger.getLogger(ImageUtils.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static BufferedImage convertToBufferedImage(Icon icon) {
        BufferedImage bi = new BufferedImage(
                icon.getIconWidth(),
                icon.getIconHeight(),
                BufferedImage.TYPE_INT_RGB);
        Graphics g = bi.createGraphics();
        // paint the Icon to the BufferedImage.
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return bi;
    }

    //::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201512091119L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////

}
