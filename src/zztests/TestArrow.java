/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package zzTests;

import java.awt.BorderLayout;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Polygon;
import java.awt.Shape;
import java.awt.geom.AffineTransform;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author zulu
 */
public class TestArrow {

    public static Shape createArrowShape(Point fromPt, Point toPt) {
        Polygon arrowPolygon = new Polygon();
        arrowPolygon.addPoint(-6, 1);
        arrowPolygon.addPoint(3, 1);
        arrowPolygon.addPoint(3, 3);
        arrowPolygon.addPoint(6, 0);
        arrowPolygon.addPoint(3, -3);
        arrowPolygon.addPoint(3, -1);
        arrowPolygon.addPoint(-6, -1);

        Point midPoint = midpoint(fromPt, toPt);

        double rotate = Math.atan2(toPt.y - fromPt.y, toPt.x - fromPt.x);

        AffineTransform transform = new AffineTransform();
        transform.translate(midPoint.x, midPoint.y);
        double ptDistance = fromPt.distance(toPt);
        double scale = ptDistance / 12.0; // 12 because it's the length of the arrow polygon.
        transform.scale(scale, scale);
        transform.rotate(rotate);

        return transform.createTransformedShape(arrowPolygon);
    }

    private static Point midpoint(Point p1, Point p2) {
        return new Point((int) ((p1.x + p2.x) / 2.0),
                (int) ((p1.y + p2.y) / 2.0));
    }

    /**
     * Draw an arrow line betwwen two point
     *
     * @param g the graphic component
     * @param x1 x-position of first point
     * @param y1 y-position of first point
     * @param x2 x-position of second point
     * @param y2 y-position of second point
     * @param d the width of the arrow
     * @param h the height of the arrow
     */
    private static void drawArrowLine(Graphics g, int x1, int y1, int x2, int y2, int d, int h) {
        int dx = x2 - x1, dy = y2 - y1;
        double D = Math.sqrt(dx * dx + dy * dy);
        double xm = D - d, xn = xm, ym = h, yn = -h, x;
        double sin = dy / D, cos = dx / D;

        x = xm * cos - ym * sin + x1;
        ym = xm * sin + ym * cos + y1;
        xm = x;

        x = xn * cos - yn * sin + x1;
        yn = xn * sin + yn * cos + y1;
        xn = x;

        int[] xpoints = {x2, (int) xm, (int) xn};
        int[] ypoints = {y2, (int) ym, (int) yn};

        g.drawLine(x1, y1, x2, y2);
        g.fillPolygon(xpoints, ypoints, 3);
    }
    
    public static void main(String[] args) {
        JFrame frame = new JFrame("Running Example");
        frame.setLayout(new BorderLayout());
        JPanel pn = new JPanel();        
        Shape s = createArrowShape(new Point(100,200 ), new Point(500,150));
        pn.add(pn);
        
        frame.add(pn);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        frame.setVisible(true);
        
    }

}
