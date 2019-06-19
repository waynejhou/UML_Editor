package uml_editor.views.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.stream.Collectors;

import uml_editor.views.shapes.creators.ICanBeJointedShapeCreator;

public class ClassShape extends CanBeJointedShape {

    public final static ICanBeJointedShapeCreator Creator = new ICanBeJointedShapeCreator() {
        @Override
        public CanBeJointedShape cr8Shape() {
            return new ClassShape();
        }
    };
    
    public ClassShape() {
        setMinWidth(120);
        setMinHeight(50);
    }

    @Override
    public void init() {
        _jpts = new ArrayList<JointPoint>();
        JointPoint j = null;

        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() / 2 - 5);
        j.setY(-5);
        j.init();

        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() / 2 - 5);
        j.setY(getHeight() - 5);
        j.init();

        _jpts.add(j = new JointPoint(this));
        j.setX(-5);
        j.setY(20);
        j.init();
        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() - 5);
        j.setY(20);
        j.init();

        _jpts.add(j = new JointPoint(this));
        j.setX(-5);
        j.setY((getHeight() - 50) / 4 + 45);
        j.init();
        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() - 5);
        j.setY((getHeight() - 50) / 4 + 45);
        j.init();

        _jpts.add(j = new JointPoint(this));
        j.setX(-5);
        j.setY((getHeight() - 50) / 4 * 3 + 45);
        j.init();
        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() - 5);
        j.setY((getHeight() - 50) / 4 * 3 + 45);
        j.init();
        super.init();
    }

    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        if (getIsHovering())
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsSelected())
            g.setColor(applyActingroundColor(g.getColor()));
        g.drawRect(x, y, w, h);
        int x0 = x, y0 = y + 50, x1 = x + w;
        g.drawLine(x0, y0, x1, y0);
        y0 += (getHeight() - 50) / 2;
        g.drawLine(x0, y0, x1, y0);
        g.setColor(applyFontColor(g.getColor()));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsIinted()) {
            for (var j : _jpts) {
                j.StartToDraw(g, new Point(x, y));
            }
        }
    }

    @Override
    protected void HowToDrawStr(Graphics2D g, int x, int y, int w, int h) {
        String strs="";
        if (getContext() != null && !(strs=getContext().toString()).isEmpty()) {
            var ctx = strs.split("=====", 3);
            int c = 0;
            for (var str : ctx) {
                var ctxLines = str.lines().collect(Collectors.toList());
                
                if (c == 0) {
                    var old_f = g.getFont();
                    g.setFont(new Font(old_f.getFontName(),old_f.getStyle(),18));
                    int x0 = x, y0 = y + 18, counter = 0;
                    for (var l : ctxLines) {
                        if (counter < ctxLines.size()) {
                            g.drawString(l, x0 + 5, y0);
                        }
                        y0 += 12;
                        counter++;
                    }
                    g.setFont(old_f);
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
