package uml_editor.views.shapes.lines;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;

import uml_editor.views.shapes.BaseShape;
import uml_editor.views.shapes.JointPoint;

public class LineShape extends BaseShape {

    public LineShape() {
        setActingroundSetter(c -> Color.RED);
    }

    private JointPoint _from_joint = null;
    private JointPoint _to_joint = null;

    public JointPoint getFromJoint() {
        return _from_joint;
    }

    public JointPoint getToJoint() {
        return _to_joint;
    }

    public void setFromJoint(JointPoint value) {
        _from_joint = value;
    }

    public void setToJoint(JointPoint value) {
        _to_joint = value;
    }

    @Override
    public Point getPt1() {
        if (_from_joint == null)
            return super.getPt1();
        return _from_joint.getCenterPoint();
    }

    @Override
    public Point getPt2() {
        if (_to_joint == null)
            return super.getPt2();
        return _to_joint.getCenterPoint();
    }

    @Override
    public boolean contains(int x, int y) {
        var dist = Line2D.ptLineDist(getPt1().x, getPt1().y, getPt2().x, getPt2().y, x, y);
        return dist < 3;
    }

    @Override
    protected void HowToDrawBG(Graphics2D g, int x, int y, int w, int h) {
        // super.HowToDrawBG(g, x, y, w, h);
    }

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
        g.drawLine(pt1.x + ox, pt1.y + oy, pt2.x + ox, pt2.y + oy);
    }

    @Override
    protected void HowToDrawStr(Graphics2D g, int x, int y, int w, int h) {
        String strs = "";
        if (getContext() != null && !(strs = getContext().toString()).isEmpty()) {
            int mx = x + w / 2;
            int my = y + h / 2;
            var fm = g.getFontMetrics();
            int hei = fm.getHeight();
            int wid = (int) fm.getStringBounds(strs, g).getWidth();
            g.setColor(Color.WHITE);
            g.fillRect(mx - wid / 2,my-hei,wid,hei);
            g.setColor(getFontColor());
            g.drawString(strs, mx - wid / 2, my);
        }
    }
}
