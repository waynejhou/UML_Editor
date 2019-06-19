package uml_editor.views.shapes;

import java.awt.BasicStroke;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;

import uml_editor.views.shapes.creators.ICanBeJointedShapeCreator;

public class UseCaseShape extends CanBeJointedShape{
    
    public final static ICanBeJointedShapeCreator Creator = new ICanBeJointedShapeCreator() {
        @Override
        public CanBeJointedShape cr8Shape() {
            return new UseCaseShape();
        }
    };
    public UseCaseShape() {
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
        j.setY(getHeight() / 2 - 5);
        j.init();
        _jpts.add(j = new JointPoint(this));
        j.setX(getWidth() - 5);
        j.setY(getHeight() / 2 - 5);
        j.init();

        super.init();
    }
    
    @Override
    protected void HowToDrawBG(Graphics2D g, int x, int y, int w, int h) {
        g.fillArc(x, y, w, h, 0, 360);
    }
    
    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        if (getIsHovering())
            g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        if (getIsSelected())
            g.setColor(applyActingroundColor(g.getColor()));
        g.drawArc(x, y, w, h, 0, 360);
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
        // super.HowToDrawStr(g, x, y, w, h);
    }


}
