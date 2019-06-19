package uml_editor.views.shapes.lines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import uml_editor.views.shapes.creators.ILineShapeCreator;

public class ImplementLine extends LineShape {

    public final static ILineShapeCreator Creator = new ILineShapeCreator() {
        @Override
        public LineShape cr8Shape() {
            return new ImplementLine();
        }
    };
    
    private double cos = Math.cos(30 * Math.PI / 180);
    private double sin = Math.sin(30 * Math.PI / 180);
    public ImplementLine() {
        // TODO Auto-generated constructor stub
    }
    
    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] { 5f }, 0));
        if (getIsHovering()) {
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] { 5f }, 0));
        }
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }
        if (getIsHovering() && getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND, 1f, new float[] { 5f }, 0));
        }
        Point pt1 = getPt1()!=null?getPt1():new Point(0,0);
        Point pt2 = getPt2()!=null?getPt2():new Point(0,0);
        int ox = (x-getX()), oy = (y-getY());
        
        double d = Math.sqrt((Math.pow((pt1.x - pt2.x), 2) + Math.pow((pt1.y - pt2.y), 2)));
        double mx = (pt1.x - pt2.x) / d * 15;
        double my = (pt1.y - pt2.y) / d * 15;
        int lx = ((int) (mx * cos - my * sin)) + pt2.x;
        int ly = ((int) (mx * sin + my * cos)) + pt2.y;
        int rx = ((int) (mx * cos + my * sin)) + pt2.x;
        int ry = ((int) (-mx * sin + my * cos)) + pt2.y;
        int mmx = (int) (mx * 0.8 + pt2.x);
        int mmy = (int) (my * 0.8 + pt2.y);
        g.drawLine(pt1.x+ox, pt1.y+oy, mmx+ox, mmy+oy);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        g.drawPolygon(new int[] { pt2.x + ox, lx + ox, rx + ox },
                new int[] { pt2.y + oy, ly + oy, ry + oy }, 3);
    }

}
