package uml_editor.views.components.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class GroupElement extends Element {

    private ArrayList<Element> _groupedElements = null;

    public ArrayList<Element> getGroupedElements() {
        return _groupedElements;
    }

    // private boolean _isGetX_Needed_ToUpdate = true;
    // private boolean _isGetY_Needed_ToUpdate = true;
    // private boolean _isGetW_Needed_ToUpdate = true;
    // private boolean _isGetH_Needed_ToUpdate = true;

    public void setGroupedElements(ArrayList<Element> value) {
        _groupedElements = value;
        // _isGetX_Needed_ToUpdate = true;
        // _isGetY_Needed_ToUpdate = true;
        // _isGetW_Needed_ToUpdate = true;
        // _isGetH_Needed_ToUpdate = true;
    }

    @Override
    public boolean isPointIn(int x, int y) {
        if (_groupedElements == null)
            return super.isPointIn(x, y);
        // var inOut = (x > getX() - 3 && x < getX() + getWidth() + 3 && y > getY() - 3
        // && y < getY() + getHeight() + 3);
        // var outIn = (x < getX() + 3 || x > getX() + getWidth() - 3 || y < getY() + 3
        // || y > getY() + getHeight() - 3);
        // System.out.printf("%b, %b\n", inOut, outIn);
        // var re = inOut /*&& outIn*/;
        // System.out.println(re);
        boolean re = false;
        for (var ge : getGroupedElements()) {
            re |= ge.isPointIn(x, y);
        }
        return re;
    }

    // private int _x = 0;
    // private int _y = 0;
    // private int _w = 0;
    // private int _h = 0;

    @Override
    public int getX() {
        if (_groupedElements == null)
            return super.getX();
        // if (_isGetX_Needed_ToUpdate) {
        // _isGetX_Needed_ToUpdate = false;
        return /* _x = */ _groupedElements.stream().map(x -> x.getX()).min(Comparator.comparing(Integer::valueOf)).get()
                - 5;
        // }

        // return _x;
    }

    @Override
    public int getY() {
        if (_groupedElements == null)
            return super.getY();
        // if (_isGetY_Needed_ToUpdate) {
        // _isGetY_Needed_ToUpdate = false;
        return /* _y = */ _groupedElements.stream().map(x -> x.getY()).min(Comparator.comparing(Integer::valueOf)).get()
                - 5;
        // }

        // return _y;
    }

    @Override
    public void setX(int value) {
        if (_groupedElements == null)
            super.setX(value);
        int[] txs = new int[_groupedElements.size()];
        int c = 0;
        for (var e : _groupedElements) {
            txs[c++] = value + e.getX() - getX();
        }
        c = 0;
        for (var e : _groupedElements) {
            e.setX(txs[c++]);
        }
        // _isGetX_Needed_ToUpdate = true;
    }

    @Override
    public void setY(int value) {
        if (_groupedElements == null)
            super.setY(value);
        int[] tys = new int[_groupedElements.size()];
        int c = 0;
        for (var e : _groupedElements) {
            tys[c++] = value + e.getY() - getY();
        }
        c = 0;
        for (var e : _groupedElements) {
            e.setY(tys[c++]);
        }
        // _isGetY_Needed_ToUpdate = true;
    }

    @Override
    public int getWidth() {
        if (_groupedElements == null)
            return super.getWidth();
        // if (_isGetW_Needed_ToUpdate) {
        // _isGetW_Needed_ToUpdate = false;
        return /* _w = */ _groupedElements.stream().map(x -> x.getX() + x.getWidth())
                .max(Comparator.comparing(Integer::valueOf)).get() - getX() + 10;
        // }

        // return _w;
    }

    @Override
    public int getHeight() {
        if (_groupedElements == null)
            return super.getHeight();
        // if (_isGetH_Needed_ToUpdate) {
        // _isGetH_Needed_ToUpdate = false;
        return /* _h = */_groupedElements.stream().map(x -> x.getY() + x.getHeight())
                .max(Comparator.comparing(Integer::valueOf)).get() - getY() + 10;
        // }

        // return _h;
    }

    public boolean getIsIncluded(Element ele) {
        int ex = ele.getX();
        int ey = ele.getY();
        int ew = ele.getWidth();
        int eh = ele.getHeight();
        int gw = getWidth();
        int gh = getHeight();
        int gx = getX();
        int gy = getY();
        if (gx < ex && gy < ey && gw > ew + (ex - gx) && gh > eh + (ey - gy))
            return true;
        return false;
    }

    @Override
    protected void HowToDraw(Graphics2D g, Point o) {
        if (!getIsIinted()) {
            int w = getWidth();
            int h = getHeight();
            int x = getX() + o.x;
            int y = getY() + o.y;
            g.setColor(new Color(150, 150, 150, 150));
            if (getIsMouseOver()) {
                g.setColor(new Color(0, 0, 0, 150));
            }
            if (getIsSelected()) {
                g.setColor(new Color(255, 0, 0, 150));
            }
            if (getIsMouseOver() && getIsSelected()) {
                g.setColor(new Color(255, 0, 0));
            }
            g.drawRect(x, y, w, h);
        }
    }

    @Override
    public void DrawInfo(Graphics2D g, Point o) {
        super.DrawInfo(g, o);
        int w = getWidth();
        int h = getHeight();
        int x = getX() + o.x;
        int y = getY() + o.y;
        g.setColor(new Color(150, 150, 150, 150));
        if (getIsMouseOver()) {
            g.setColor(new Color(0, 0, 0, 150));
        }
        if (getIsSelected()) {
            g.setColor(new Color(255, 0, 0, 150));
        }
        if (getIsMouseOver() && getIsSelected()) {
            g.setColor(new Color(255, 0, 0));
        }
        g.drawRect(x, y, w, h);
    }

    @Override
    public void setIsMouseOver(boolean value) {
        super.setIsMouseOver(value);
        for (var ge : _groupedElements)
            ge.setIsMouseOver(value);
    }

    @Override
    public void setIsSelected(boolean value) {
        super.setIsSelected(value);
        for (var ge : _groupedElements)
            ge.setIsSelected(value);
    }

}
