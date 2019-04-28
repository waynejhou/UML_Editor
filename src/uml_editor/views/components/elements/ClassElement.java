package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uml_editor.views.components.elements.interfaces.ICanBeJointed;

public class ClassElement extends Element implements ICanBeJointed {

    ArrayList<JointElement> _joints = null;

    @Override
    public void init() {
        JointElement j = null;
        _joints = new ArrayList<JointElement>();
        _joints.add(j = new JointElement(this));
        j.setX(getWidth() / 2 - 5);
        j.setY(-5);
        j.init();
        _joints.add(j = new JointElement(this));
        j.setX(getWidth() / 2 - 5);
        j.setY(getHeight() - 5);
        j.init();
        _joints.add(j = new JointElement(this));
        j.setX(-5);
        j.setY(getHeight() / 2 - 5);
        j.init();
        _joints.add(j = new JointElement(this));
        j.setX(getWidth() - 5);
        j.setY(getHeight() / 2 - 5);
        j.init();
        super.init();
    }

    @Override
    public List<JointElement> getAllJointElements() {
        return _joints;
    }

    @Override
    public void setIsSelected(boolean value) {
        for (var j : _joints) {
            j.setIsVisible(value);
        }
        super.setIsSelected(value);
    }

    @Override
    protected void HowToDraw(Graphics2D g, Point o) {
        int w = getWidth();
        int h = getHeight();
        int x = getX() + o.x /*- w / 2*/;
        int y = getY() + o.y /*- h / 2*/;
        g.setColor(Color.white);
        g.fillRect(x, y, w, h);

        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));

        if (getIsMouseOver())
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }

        g.drawRect(x, y, w, h);

        {
            int x0 = x, y0 = y + 50, x1 = x + w;
            g.drawLine(x0, y0, x1, y0);
            y0 += (getHeight() - 50) / 2;
            g.drawLine(x0, y0, x1, y0);
        }

        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsIinted()) {
            for (var j : _joints) {
                j.StartToDraw(g, new Point(x, y));
            }
        }

        if (getContext() != null && !getContext().isEmpty()) {
            var ctx = getContext().split("=====", 3);
            int c = 0;
            for (var str : ctx) {
                var ctxLines = str.lines().collect(Collectors.toList());
                if (c == 0) {
                    int x0 = x, y0 = y + 12, counter = 0;
                    for (var l : ctxLines) {
                        if (counter < ctxLines.size()) {
                            g.drawString(l, x0 + 5, y0);
                        }
                        y0 += 12;
                        counter++;
                    }
                } else if (c == 1) {
                    int x0 = x, y0 = y + 50, counter = 0;
                    for (var l : ctxLines) {
                        if (counter < ctxLines.size()) {
                            g.drawString(l, x0 + 5, y0);
                        }
                        y0 += 12;
                        counter++;
                    }
                } else if (c == 2) {
                    int x0 = x, y0 = y + 50 + ((getHeight() - 50) / 2), counter = 0;
                    for (var l : ctxLines) {
                        if (counter < ctxLines.size()) {
                            g.drawString(l, x0 + 5, y0);
                        }
                        y0 += 12;
                        counter++;
                    }
                }

                c++;
            }

        }
    }

}
