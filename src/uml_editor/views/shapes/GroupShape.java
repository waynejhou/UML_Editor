package uml_editor.views.shapes;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.Comparator;
import java.util.List;

public class GroupShape extends BaseShape {

    public GroupShape() {
        // TODO Auto-generated constructor stub
    }

    private List<BaseShape> _groupedShapes = null;

    public List<BaseShape> getGroupedShapes() {
        return _groupedShapes;
    }


    public void setGroupedShapes(List<BaseShape> value) {
        if(value!=null) {
            for (var shape : value) {
                shape.setOwner(this);
                shape.setIsVisible(getIsVisible());
            }
        }else{
            for (var shape : _groupedShapes) {
                shape.setOwner(null);
            }
        }
        _groupedShapes = value;

    }

    public boolean getIsIncluded(BaseShape shape) {
        int ex = shape.getX();
        int ey = shape.getY();
        int ew = shape.getWidth();
        int eh = shape.getHeight();
        int gw = getWidth();
        int gh = getHeight();
        int gx = getX();
        int gy = getY();
        if (gx < ex && gy < ey && gw > ew + (ex - gx) && gh > eh + (ey - gy))
            return true;
        return false;
    }
    public boolean getIsIncluded(JointPoint point) {
        int ex = point.getX() + point.getOwner().getX();
        int ey = point.getY()+ point.getOwner().getY();
        int ew = point.getWidth();
        int eh = point.getHeight();
        int gw = getWidth();
        int gh = getHeight();
        int gx = getX();
        int gy = getY();
        if (gx < ex && gy < ey && gw > ew + (ex - gx) && gh > eh + (ey - gy))
            return true;
        return false;
    }

    @Override
    public Point getPt1() {
        if (_groupedShapes == null)
            return super.getPt1();
        return new Point(getX(), getY());
    }

    @Override
    public Point getPt2() {
        if (_groupedShapes == null)
            return super.getPt2();
        return new Point(getX() + getWidth(), getY() + getHeight());
    }

    @Override
    public int getX() {
        if (_groupedShapes == null)
            return super.getX();
        // if (_isGetX_Needed_ToUpdate) {
        // _isGetX_Needed_ToUpdate = false;
        return /* _x = */ _groupedShapes.stream().map(x -> x.getX()).min(Comparator.comparing(Integer::valueOf)).get()
                - 5;
        // }

        // return _x;
    }

    @Override
    public int getY() {
        if (_groupedShapes == null)
            return super.getY();
        // if (_isGetY_Needed_ToUpdate) {
        // _isGetY_Needed_ToUpdate = false;
        return /* _y = */ _groupedShapes.stream().map(x -> x.getY()).min(Comparator.comparing(Integer::valueOf)).get()
                - 5;
        // }

        // return _y;
    }

    @Override
    public void setX(int value) {
        if (_groupedShapes == null)
            super.setX(value);
        int[] txs = new int[_groupedShapes.size()];
        int c = 0;
        for (var e : _groupedShapes) {
            txs[c++] = value + e.getX() - getX();
        }
        c = 0;
        for (var e : _groupedShapes) {
            e.setX(txs[c++]);
        }
        // _isGetX_Needed_ToUpdate = true;
    }

    @Override
    public void setY(int value) {
        if (_groupedShapes == null)
            super.setY(value);
        int[] tys = new int[_groupedShapes.size()];
        int c = 0;
        for (var e : _groupedShapes) {
            tys[c++] = value + e.getY() - getY();
        }
        c = 0;
        for (var e : _groupedShapes) {
            e.setY(tys[c++]);
        }
        // _isGetY_Needed_ToUpdate = true;
    }

    @Override
    public int getWidth() {
        if (_groupedShapes == null)
            return super.getWidth();
        // if (_isGetW_Needed_ToUpdate) {
        // _isGetW_Needed_ToUpdate = false;
        return /* _w = */ _groupedShapes.stream().map(x -> x.getX() + x.getWidth())
                .max(Comparator.comparing(Integer::valueOf)).get() - getX() + 10;
        // }

        // return _w;
    }

    @Override
    public int getHeight() {
        if (_groupedShapes == null)
            return super.getHeight();
        // if (_isGetH_Needed_ToUpdate) {
        // _isGetH_Needed_ToUpdate = false;
        return /* _h = */_groupedShapes.stream().map(x -> x.getY() + x.getHeight())
                .max(Comparator.comparing(Integer::valueOf)).get() - getY() + 10;
        // }

        // return _h;
    }

    @Override
    public void setIsVisible(boolean value) {
        super.setIsVisible(value);
        if (_groupedShapes != null)
            for (var shape : _groupedShapes)
                shape.setIsVisible(value);
    }

    @Override
    public void StartToDraw(Graphics2D g, Point o) {
        super.StartToDraw(g, o);
        if (_groupedShapes != null)
            for (var shape : _groupedShapes)
                shape.StartToDraw(g, o);
    }

    @Override
    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        if (!getIsIinted()) {
            g.setColor(new Color(150, 150, 150, 150));
            if (getIsHovering()) {
                g.setColor(new Color(0, 0, 0, 150));
            }
            if (getIsSelected()) {
                g.setColor(new Color(255, 0, 0, 150));
            }
            if (getIsHovering() && getIsSelected()) {
                g.setColor(new Color(255, 0, 0));
            }
            g.drawRect(x, y, w, h);
        }else {
            g.setColor(new Color(0, 0, 0));
            g.drawRect(x, y, w, h);
        }
    }

    @Override
    protected void HowToDrawBG(Graphics2D g, int x, int y, int w, int h) {
        // super.HowToDrawBG(g, x, y, w, h);
    }

    @Override
    protected void HowToDrawStr(Graphics2D g, int x, int y, int w, int h) {
        // super.HowToDrawStr(g, x, y, w, h);
    }

    @Override
    public void setIsHovering(Boolean value) {
        super.setIsHovering(value);
        for (var ge : _groupedShapes) {
            ge.setIsHovering(value);
        }

    }

    @Override
    public void setIsSelected(boolean value) {
        super.setIsSelected(value);
        for (var ge : _groupedShapes)
            ge.setIsSelected(value);
    }

}
