package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class GeneralizationLineElement extends LineElement {

    private double cos = Math.cos(15 * Math.PI / 180);
    private double sin = Math.sin(15 * Math.PI / 180);

    @Override
    protected void HowToDraw(Graphics2D g, Point o) {
        g.setColor(Color.black);
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsMouseOver()) {
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }
        if (getIsMouseOver() && getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        }
        double d = Math.sqrt((Math.pow((getPt1().x - getPt2().x), 2) + Math.pow((getPt1().y - getPt2().y), 2)));
        double mx = (getPt1().x - getPt2().x) / d * 30;
        double my = (getPt1().y - getPt2().y) / d * 30;
        int lx = ((int) (mx * cos - my * sin)) + getPt2().x;
        int ly = ((int) (mx * sin + my * cos)) + getPt2().y;
        int rx = ((int) (mx * cos + my * sin)) + getPt2().x;
        int ry = ((int) (-mx * sin + my * cos)) + getPt2().y;
        g.drawLine(getPt1().x + o.x, getPt1().y + o.y, getPt2().x + o.x, getPt2().y + o.y);
        g.fillPolygon(new int[] { getPt2().x + o.x, lx + o.x, rx + o.x },
                new int[] { getPt2().y + o.y, ly + o.y, ry + o.y }, 3);

    }

}
