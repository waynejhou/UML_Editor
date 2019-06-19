package uml_editor.resources;

import java.awt.Color;

public interface ColorSetter {
    public Color Set(Color color);
    public static ColorSetter ThemeColor_Back = (c->new Color(240, 240, 240));
    public static ColorSetter ThemeColor_Fore = (c->new Color(204, 255, 153));
    public static ColorSetter ThemeColor_Hover = ColorAdj(-10);
    public static ColorSetter ThemeColor_Action = ColorAdj(-20);
    public static ColorSetter ThemeColor_Font = (c->new Color(0, 0, 0));
    
    static ColorSetter ColorAdj(int offsetR, int offsetB, int offsetG) {
        return c->{
                Math.min(Math.max(c.getRed()+offsetR,0),255);
                Math.min(Math.max(c.getGreen()+offsetG,0),255);
                Math.min(Math.max(c.getBlue()+offsetB,0),255);
                return c;};
    }
    static ColorSetter ColorAdj(int offset) {
        return c->{
                Math.min(Math.max(c.getRed()+offset,0),255);
                Math.min(Math.max(c.getGreen()+offset,0),255);
                Math.min(Math.max(c.getBlue()+offset,0),255);
                return c;};
    }
}
