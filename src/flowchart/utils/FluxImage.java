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

import flowchart.algorithm.FunctionGraph;
import flowchart.algorithm.Program;
import flowchart.shape.Fshape;
import flowchart.utils.image.ImageUtils;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.filechooser.FileNameExtensionFilter;
import ui.FProperties;

/**
 * Created on 12/out/2015, 6:16:25
 *
 * @author zulu - computer
 */
public class FluxImage {

    public static void copyToClipBoard(Program prog) {
        BufferedImage image = getImage(prog);
        //------------------------------------------- copy to clipboard
        ImageTransferable trans = new ImageTransferable(image);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, null);
    }

    public static void copyToClipBoard(JPanel panel, UserName user) {
        BufferedImage image = getImage(panel, user);
        //------------------------------------------- copy to clipboard
        ImageTransferable trans = new ImageTransferable(image);
        Clipboard c = Toolkit.getDefaultToolkit().getSystemClipboard();
        c.setContents(trans, null);
    }

    public static void saveTofile(Program prog) {
        BufferedImage image = getImage(prog);
        try {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileFilter(new FileNameExtensionFilter("PNG Image files", "png"));
            int returnValue = chooser.showSaveDialog(null);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getPath();
                if (filename == null) {
                    return;
                }
                if (!filename.endsWith(".png")) {
                    filename = filename + ".png";
                }
                ImageIO.write(image, "png", new File(filename));
            }
        } catch (Exception e) {
        }
    }

    public static void saveTofile(JPanel panel, UserName user) {
        BufferedImage image = getImage(panel, user);
        try {
            JFileChooser chooser = new JFileChooser(System.getProperty("user.dir"));
            chooser.setFileFilter(new FileNameExtensionFilter("PNG Image files", "png"));
            int returnValue = chooser.showSaveDialog(panel);
            if (returnValue == JFileChooser.APPROVE_OPTION) {
                String filename = chooser.getSelectedFile().getPath();
                if (filename == null) {
                    return;
                }
                if (!filename.endsWith(".png")) {
                    filename = filename + ".png";
                }
                ImageIO.write(image, "png", new File(filename));
            }
        } catch (Exception e) {
        }
    }

    /**
     * Make color transparent
     *
     * @param im
     * @param color
     * @return
     */
    public static void makeColorTransparent(BufferedImage im, final Color color) {
        int rgb = color.getRGB();
        int backR = (rgb & 0xFF0000) >> 16;
        int backG = (rgb & 0xFF00) >> 8;
        int backB = rgb & 0xFF;

        for (int y = 0; y < im.getHeight(); y++) {
            for (int x = 0; x < im.getWidth(); x++) {
                rgb = im.getRGB(x, y);
                int pixelR = (rgb & 0xFF0000) >> 16;
                int pixelG = (rgb & 0xFF00) >> 8;
                int pixelB = rgb & 0xFF;
                if (backR == pixelR && backG == pixelG && backB == pixelB) {
                    im.setRGB(x, y, (new Color(0, 0, 0, 0)).getRGB());
                }
            }

        }
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    static class ImageTransferable implements Transferable, ClipboardOwner {

        java.awt.Image image;

        public ImageTransferable(java.awt.Image img) {
            this.image = img;
        }

        public DataFlavor[] getTransferDataFlavors() {
            return new DataFlavor[]{DataFlavor.imageFlavor};
        }

        public boolean isDataFlavorSupported(DataFlavor flavor) {
            return DataFlavor.imageFlavor.equals(flavor);
        }

        public Object getTransferData(DataFlavor flavor) throws UnsupportedFlavorException, IOException {
            return image;
        }

        //empty ClipBoardOwner implementation
        public void lostOwnership(Clipboard clipboard, Transferable contents) {
        }
    }

    public static BufferedImage getImage(JPanel panel, UserName user) {
        // dimensions
        int minX = Integer.MAX_VALUE;
        int minY = Integer.MAX_VALUE;
        int maxX = Integer.MIN_VALUE;
        int maxY = Integer.MIN_VALUE;

        for (Component comp : panel.getComponents()) {
            if (comp.getLocation().x < minX) {
                minX = comp.getX();
            }
            if (comp.getLocation().y < minY) {
                minY = comp.getY();
            }
            if (comp.getLocation().x + comp.getWidth() > maxX) {
                maxX = comp.getLocation().x + comp.getWidth();
            }
            if (comp.getLocation().y + comp.getHeight() > maxY) {
                maxY = comp.getLocation().y + comp.getHeight();
            }
        }
        // center image
        maxX += minX; // add right border equal to left border
        maxY += minY;

        maxY += 0; //32; // user avatar;

        /////////////////////////
        BufferedImage image = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        // paint jpanel to image
        panel.paint(g2d);

        //-------------------------------------------- translate image ---
//        AffineTransform tx = new AffineTransform();
//        tx.translate(-minX/2, -minY/2);
//        AffineTransformOp op = new AffineTransformOp(tx,
//                AffineTransformOp.TYPE_NEAREST_NEIGHBOR);
//        image = op.filter(image, null);
        //------------------------------------------- make image transparent -------
        makeColorTransparent(image, panel.getBackground());
        drawCopyright(image, user);
        g2d.dispose();
        return image;
    }

    public static void drawCopyright(BufferedImage image, UserName user) {
//        ImageIcon img = ImageUtils.resizeProportional(user.getAvatar(), 32, 32);
//        int roundCorner = image.getWidth() / 20;
//        Font font = new Font(Font.DIALOG, Font.BOLD, 8);
//        String txt = "Algorithmi (c) M@nso 2015";
//        Dimension d = UtilsFlowchart.getTextDimension(txt, font);
//        Graphics2D g2d = (Graphics2D) image.getGraphics();
//        g2d.setColor(Color.DARK_GRAY);
//        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//        g2d.setFont(font);
//
//        g2d.setColor(Color.DARK_GRAY);
//        if (image.getWidth() > 3 * d.width) {
//            g2d.drawString(txt, image.getWidth() - roundCorner - (int) d.getWidth(), image.getHeight() - d.height / 2);
//        }
//
//        g2d.drawImage(img.getImage(), roundCorner, image.getHeight() - 40, null);
//        g2d.drawString(user.getName(),
//                roundCorner + img.getIconWidth() + 10, image.getHeight() - img.getIconHeight());
//
//        g2d.drawString(user.getCode() + " " + user.getFullName(),
//                roundCorner + img.getIconWidth() + 10, image.getHeight() - img.getIconHeight() + d.height);
//
//        g2d.drawString(UtilsFlowchart.getCurrentTimeStamp(),
//                roundCorner + img.getIconWidth() + 10, image.getHeight() - img.getIconHeight() + 2 * d.height);
//
//        g2d.setColor(Color.lightGray);
//        g2d.drawRoundRect(1, 1, image.getWidth() - 4, image.getHeight() - 4, roundCorner, roundCorner);
    }

    public static BufferedImage getImage(Program prog) {
        UserName user = prog.myProblem.user;
        ArrayList<BufferedImage> algs = new ArrayList<>();
        BufferedImage img;
        int maxX = 0;
        int maxY = 0;
        if (prog.getGlobalMem() != null) {
            img = getImage(prog.getGlobalMem().graph, user);
            algs.add(img);
            maxX = img.getWidth();
            maxY = img.getHeight();
            maxY += Fshape.getStaticZoom() * 2;
        }
        img = getImage(prog.getMain().graph, user);
        algs.add(img);
        maxX = img.getWidth() > maxX ? img.getWidth() : maxX;
        maxY += img.getHeight();
        maxY += Fshape.getStaticZoom() * 2;

        for (FunctionGraph func : prog.getFunctions()) {
            img = getImage(func.graph, user);
            algs.add(img);
            maxX = img.getWidth() > maxX ? img.getWidth() : maxX;
            maxY += img.getHeight();
            maxY += Fshape.getStaticZoom() * 2;
        }

        BufferedImage image = new BufferedImage(maxX, maxY - Fshape.getStaticZoom() * 2, BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2d = image.createGraphics();
        maxY = 0;
        int medX = maxX / 2;
        for (BufferedImage alg : algs) {
            g2d.drawImage(alg, null, medX - alg.getWidth() / 2, maxY);
            maxY += alg.getHeight();
            maxY += Fshape.getStaticZoom() * 2;
        }
        return image;
    }

//::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::
    private static final long serialVersionUID = 201510120616L;
    //:::::::::::::::::::::::::::  Copyright(c) M@nso  2015  :::::::::::::::::::
    ///////////////////////////////////////////////////////////////////////////
}
