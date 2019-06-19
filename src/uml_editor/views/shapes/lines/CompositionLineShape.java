package uml_editor.views.shapes.lines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import uml_editor.views.shapes.creators.ILineShapeCreator;

public class CompositionLineShape extends LineShape {
    public final static ILineShapeCreator Creator = new ILineShapeCreator() {
        @Override
        public LineShape cr8Shape() {
            return new CompositionLineShape();
        }
    };

    public CompositionLineShape() {
        // TODO Auto-generated constructor stub
    }

    private double cos = Math.cos(30 * Math.PI / 180);
    private double sin = Math.sin(30 * Math.PI / 180);

    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        if (getIsHovering()) {
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }
        if (getIsHovering() && getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        Point pt1 = getPt1() != null ? getPt1() : new Point(0, 0);
        Point pt2 = getPt2() != null ? getPt2() : new Point(0, 0);
        int ox = (x - getX()), oy = (y - getY());
        double d = Math.sqrt((Math.pow((pt1.x - pt2.x), 2) + Math.pow((pt1.y - pt2.y), 2)));
        double mx1 = (pt2.x - pt1.x) / d * 15;
        double my1 = (pt2.y - pt1.y) / d * 15;
        int lx1 = ((int) (mx1 * cos - my1 * sin)) + pt1.x;
        int ly1 = ((int) (mx1 * sin + my1 * cos)) + pt1.y;
        int rx1 = ((int) (mx1 * cos + my1 * sin)) + pt1.x;
        int ry1 = ((int) (-mx1 * sin + my1 * cos)) + pt1.y;
        int mm1x = (int) (mx1 * 2 + pt1.x);
        int mm1y = (int) (my1 * 2 + pt1.y);
        g.drawLine(pt2.x + ox, pt2.y + oy, mm1x + ox, mm1y + oy);
        g.drawPolygon(new int[] { pt1.x + ox, lx1 + ox, mm1x + ox, rx1 + ox },
                new int[] { pt1.y + oy, ly1 + oy, mm1y + oy, ry1 + oy }, 4);
        double mx2 = (pt1.x - pt2.x) / d * 15;
        double my2 = (pt1.y - pt2.y) / d * 15;
        int lx2 = ((int) (mx2 * cos - my2 * sin)) + pt2.x;
        int ly2 = ((int) (mx2 * sin + my2 * cos)) + pt2.y;
        
        int rx2 = ((int) (mx2 * cos + my2 * sin)) + pt2.x;
        int ry2 = ((int) (-mx2 * sin + my2 * cos)) + pt2.y;

        g.drawLine(pt2.x + ox, pt2.y + oy, lx2 + ox, ly2 + oy);
        g.drawLine(pt2.x + ox, pt2.y + oy, rx2 + ox, ry2 + oy);

    }
}
