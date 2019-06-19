package uml_editor.views.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class JointPoint extends BaseShape {

    public JointPoint(CanBeJointedShape owner) {
        setPt1(0, 0);
        setPt2(10, 10);
        setWidth(10);
        setHeight(10);
        setMaxHeight(10);
        setMinHeight(10);
        setMaxWidth(10);
        setMinWidth(10);
        setOwner(owner);
    }

    private CanBeJointedShape _owner;
    
    public void setOwner(CanBeJointedShape value) {
        _owner = value;
        super.setOwner(value);
    }
    public CanBeJointedShape getOwner() {
        return _owner;
    }
    
    
    public Point getCenterPoint() {
        if (getOwner() == null)
            return new Point(getX() + getWidth() / 2, getY() + getHeight() / 2);
        return new Point(getOwner().getX() + getX() + getWidth() / 2, getOwner().getY() + getY() + getHeight() / 2);
    }

    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        if (getIsHovering())
            g.setColor(applyHovergroundColor(g.getColor()));
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }
        if (getIsHovering() && getIsSelected()) {
            g.setColor(new Color(255, 200, 200));
        }
        g.fillRect(x, y, w, h);
    }

    @Override
    public boolean contains(int x, int y) {
        return super.contains(x - getOwner().getX(), y - getOwner().getY());
    }
    
    @Override
    protected void HowToDrawStr(Graphics2D g, int x, int y, int w, int h) {
        //super.HowToDrawStr(g,x,y,w,h);
    }
}
