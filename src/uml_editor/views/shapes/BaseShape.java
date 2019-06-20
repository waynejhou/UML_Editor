package uml_editor.views.shapes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.Serializable;

import uml_editor.resources.ColorSetter;

public class BaseShape {
    private ColorSetter _ForegroundSetter = (e->Color.black);

    public ColorSetter getForegroundSetter() {
        return _ForegroundSetter;
    }
    public void setForegroundSetter(ColorSetter value) {
        _ForegroundSetter = value;
    }
    public Color getForegroundColor() {
        return getForegroundSetter().Set(new Color(0));
    }
    public Color applyForegroundColor(Color color) {
        return getForegroundSetter().Set(color);
    }
 
    private ColorSetter _BackgroundSetter = (e->Color.white);
    public ColorSetter getBackgroundSetter() {
        return _BackgroundSetter;
    }

    public void setBackgroundSetter(ColorSetter value) {
        _BackgroundSetter = value;
    }

    public Color getBackgroundColor() {
        return getBackgroundSetter().Set(new Color(0));
    }
    public Color applyBackgroundColor(Color color) {
        return getBackgroundSetter().Set(color);
    }
    
    
    private ColorSetter _HovergroundSetter = (e->Color.lightGray);

    public ColorSetter getHovergroundSetter() {
        return _HovergroundSetter;
    }

    public void setHovergroundSetter(ColorSetter value) {
        _HovergroundSetter = value;
    }
    
    public Color getHovergroundColor() {
        return getHovergroundSetter().Set(new Color(0));
    }
    public Color applyHovergroundColor(Color color) {
        return getHovergroundSetter().Set(color);
    }
    
    private ColorSetter _ActingroundSetter = (e->Color.gray);

    public ColorSetter getActingroundSetter() {
        return _ActingroundSetter;
    }

    public void setActingroundSetter(ColorSetter value) {
        _ActingroundSetter = value;
    }

    public Color getActingroundColor() {
        return getActingroundSetter().Set(new Color(0));
    }
    public Color applyActingroundColor(Color color) {
        return getActingroundSetter().Set(color);
    }
    
    private ColorSetter _FontColorSetter = (e->Color.black);

    public ColorSetter getFontColorSetter() {
        return _FontColorSetter;
    }

    public void setFontColorSetter(ColorSetter value) {
        _FontColorSetter = value;
    }

    public Color getFontColor() {
        return getFontColorSetter().Set(new Color(0));
    }
    public Color applyFontColor(Color color) {
        return getFontColorSetter().Set(color);
    }

    private Boolean _IsHovering = false;

    public Boolean getIsHovering() {
        return _IsHovering;
    }

    public void setIsHovering(Boolean value) {
        _IsHovering = value;
    }

    private Boolean _IsActing = false;

    public Boolean getIsActing() {
        return _IsActing;
    }

    public void setIsActing(Boolean value) {
        _IsActing = value;
    }
    
    private Object _DataContext = null;

    public Object getDataContext() {
        return _DataContext;
    }

    public void setDataContext(Object value) {
        _DataContext = value;
    }
    
    
    private boolean _isIinted = false;

    public final boolean getIsIinted() {
        return _isIinted;
    }

    public void init() {
        _isIinted = true;
    }

    private Object _Context = null;

    public Object getContext() {
        return _Context;
    }

    public void setContext(Object value) {
        _Context = value;
    }
    
    private BaseShape _owner = null;

    public BaseShape getOwner() {
        return _owner;
    }

    public void setOwner(BaseShape value) {
        _owner = value;
    }

    private int _depth = 0;

    public int getDepth() {
        return _depth;
    }

    public void setDepth(int value) {
        _depth = value;
    }

    public void incDepth() {
        _depth += 1;
    }

    private Point _pt1 = null;
    private Point _pt2 = null;

    public Point getPt1() {
        return _pt1;
    }

    public void setPt1(Point value) {
        if (getPt1() != null && _pt1.equals(value))
            return;
        if (getPt2() == null) {
            _pt1 = value;
            return;
        }

        _pt1 = restrictSize(value, getPt2());
    }

    public void setPt1(int x, int y) {
        setPt1(new Point(x, y));
    }

    public Point getPt2() {
        return _pt2;
    }

    public void setPt2(Point value) {
        if (getPt2() != null && _pt2.equals(value))
            return;
        if (getPt1() == null) {
            _pt2 = value;
            return;
        }
        _pt2 = restrictSize(value, getPt1());
    }

    public void setPt2(int x, int y) {
        setPt2(new Point(x, y));
    }

    private Point restrictSize(Point value, Point ptAnother) {
        var offsetW = value.x - ptAnother.x;
        var signW = Math.min(1, Math.max(-1, offsetW));
        if(signW==0)signW=1;
        Point ret = value;
        if (Math.abs(offsetW) < getMinWidth()) {
            ret = new Point(value.x + (signW * getMinWidth() - offsetW), value.y);
        }
        if (Math.abs(offsetW) > getMaxWidth()) {
            int x = value.x;
            if (ret != null)
                x = ret.x;
            ret = new Point(x + (Math.min(1, Math.max(-1, offsetW)) * getMaxWidth() - offsetW), value.y);
        }
        var offsetH = value.y - ptAnother.y;
        var signH = Math.min(1, Math.max(-1, offsetH));
        if(signH==0)signH=1;
        if (Math.abs(offsetH) < getMinHeight()) {
            int x = value.x;
            if (ret != null)
                x = ret.x;
            ret = new Point(x, value.y + (signH * getMinHeight() - offsetH));
        }
        if (Math.abs(offsetH) > getMaxHeight()) {
            int x = value.x;
            if (ret != null)
                x = ret.x;
            ret = new Point(x, value.y + (Math.min(1, Math.max(-1, offsetH)) * getMaxHeight() - offsetH));
        }
        return ret;
    }

    public int getX() {
        if(getPt1()==null|getPt2()==null)
            return 0;
        return Math.min(getPt1().x, getPt2().x);
    }

    public int getY() {
        if(getPt1()==null|getPt2()==null)
            return 0;
        return Math.min(getPt1().y, getPt2().y);
    }

    public int getWidth() {
        if(getPt1()==null|getPt2()==null)
            return 0;
        return Math.abs(getPt1().x - getPt2().x);
    }

    public int getHeight() {
        if(getPt1()==null|getPt2()==null)
            return 0;
        return Math.abs(getPt1().y - getPt2().y);
    }

    public void setX(int value) {
        int offset = value - getX();
        _pt1 = new Point(getPt1().x + offset, getPt1().y);
        _pt2 = new Point(getPt2().x + offset, getPt2().y);
    }

    public void setY(int value) {
        int offset = value - getY();
        _pt1 = new Point(getPt1().x, getPt1().y + offset);
        _pt2 = new Point(getPt2().x, getPt2().y + offset);
    }

    public void setWidth(int value) {
        var offset = value - getWidth();
        if (getPt1().x < getPt2().x) {
            setPt2(getPt2().x + offset, getPt2().y);
        } else {
            setPt1(getPt1().x + offset, getPt1().y);
        }
    }

    public void setHeight(int value) {
        var offset = value - getHeight();
        if (getPt1().y < getPt2().y) {
            setPt2(getPt2().x, getPt2().y + offset);
        } else {
            setPt1(getPt1().x, getPt1().y + offset);
        }
    }

    private int _MinWidth = 0;

    public int getMinWidth() {
        return _MinWidth;
    }

    public void setMinWidth(int value) {
        _MinWidth = value;
    }

    private int _MaxWidth = Integer.MAX_VALUE;

    public int getMaxWidth() {
        return _MaxWidth;
    }

    public void setMaxWidth(int value) {
        _MaxWidth = value;
    }

    private int _MinHeight = 0;

    public int getMinHeight() {
        return _MinHeight;
    }

    public void setMinHeight(int value) {
        _MinHeight = value;
    }

    private int _MaxHeight = Integer.MAX_VALUE;

    public int getMaxHeight() {
        return _MaxHeight;
    }

    public void setMaxHeight(int value) {
        _MaxHeight = value;
    }

    public Rectangle getBounds() {
        return new Rectangle(getX(), getY(), getWidth(), getHeight());
    }
    
    public boolean contains(int x, int y) {
        return getBounds().contains(x, y);
    }

    public boolean contains(Point point) {
        return getBounds().contains(point);
    }

    private boolean _isSelected = false;

    public boolean getIsSelected() {
        return _isSelected;
    }

    public void setIsSelected(boolean value) {
        _isSelected = value;
    }

    private boolean _isVisible = false;

    public boolean getIsVisible() {
        return _isVisible;
    }

    public void setIsVisible(boolean value) {
        _isVisible = value;
    }

    public Rectangle getActualBound(Point o) {
        int w = getWidth();
        int h = getHeight();
        int x = getX() + o.x;
        int y = getY() + o.y;
        return new Rectangle(x,y,w,h);
    }
    
    public void StartToDraw(Graphics2D g, Point o) {
        if (!_isVisible)
            return;
        g.setColor(new Color(0, 0, 0));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        int w = getWidth();
        int h = getHeight();
        int x = getX() + o.x;
        int y = getY() + o.y;
        g.setColor(applyBackgroundColor(g.getColor()));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        HowToDrawBG(g, x, y, w, h);
        g.setColor(applyForegroundColor(g.getColor()));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        HowToDrawFG(g, x, y, w, h);
        g.setColor(applyFontColor(g.getColor()));
        g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
        HowToDrawStr(g, x, y, w, h);
    }

    protected void HowToDrawBG(Graphics2D g, int x, int y, int w, int h) {
        g.fillRect(x, y, w, h);
    }

    protected void HowToDrawFG(Graphics2D g, int x, int y, int w, int h) {
        g.drawRect(x, y, w, h);
        g.drawLine(x, y, x + w, y + h);
        g.drawLine(x + w, y, x, y + h);
    }

    protected void HowToDrawStr(Graphics2D g, int x, int y, int w, int h) {
        var x0 = x + w + 5;
        var y0 = y;
        for (var str : toString().lines().toArray())
            g.drawString(str.toString(), x0, y0 += 14);
    }

    @Override
    public String toString() {
        return String.format("[Pt1: %s]\n", getPt1().toString()) + String.format("[Pt2: %s]\n", getPt2().toString())
                + String.format("[depth: %d]\n", getDepth()) + String.format("[X: %d]\n", getX())
                + String.format("[Y: %d]\n", getY()) + String.format("[W: %d]\n", getWidth())
                + String.format("[H: %d]\n", getHeight());
    }


}
