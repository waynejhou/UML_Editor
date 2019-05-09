package wayneUI.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Panel;
import java.awt.Point;

import uml_editor.resources.ColorSetter;
import wayneUI.enums.HorizontalAlignment;
import wayneUI.enums.VerticalAlignment;

public class WComponent extends Panel {

    private ColorSetter _ForegroundSetter = (e->Color.black);

    public ColorSetter getForegroundSetter() {
        return _ForegroundSetter;
    }
    public void setForegroundSetter(ColorSetter value) {
        _ForegroundSetter = value;
    }
    
    private ColorSetter _BackgroundSetter = (e->Color.white);

    public ColorSetter getBackgroundSetter() {
        return _BackgroundSetter;
    }

    public void setBackgroundSetter(ColorSetter value) {
        _BackgroundSetter = value;
    }
    
    private ColorSetter _HovergroundSetter = (e->Color.lightGray);

    public ColorSetter getHovergroundSetter() {
        return _HovergroundSetter;
    }

    public void setHovergroundSetter(ColorSetter value) {
        _HovergroundSetter = value;
    }
    
    private ColorSetter _ActingroundSetter = (e->Color.gray);

    public ColorSetter getActingroundSetter() {
        return _ActingroundSetter;
    }

    public void setActingroundSetter(ColorSetter value) {
        _ActingroundSetter = value;
    }

    private HorizontalAlignment _HorizontalAlignment = HorizontalAlignment.Center;

    public HorizontalAlignment getHorizontalAlignment() {
        return _HorizontalAlignment;
    }

    public void setHorizontalAlignment(HorizontalAlignment value) {
        _HorizontalAlignment = value;
    }

    private VerticalAlignment _VerticalAlignment = VerticalAlignment.Center;

    public VerticalAlignment getVerticalAlignment() {
        return _VerticalAlignment;
    }


    protected Point getLabelPositionByAlignments(Dimension stringDimension) {
        int setx = 0, sety = 0;
        switch (getHorizontalAlignment()) {
        case Left:
            setx = 0;
            break;
        case Center:
            setx = (getWidth() / 2 - stringDimension.width / 2);
            break;
        case Right:
            setx = getWidth() - stringDimension.width;
            break;
        }
        switch (getVerticalAlignment()) {
        case Top:
            sety = stringDimension.height;
            break;
        case Center:
            sety = (getHeight() / 2 - stringDimension.height / 2);
            break;
        case Bottom:
            sety = getHeight();
            break;
        }
        return new Point(setx, sety);
    }
    
    
    public void setVerticalAlignment(VerticalAlignment value) {
        _VerticalAlignment = value;
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
}
