package uml_editor.resources;

import java.awt.Color;

public interface ColorSetter {
    public Color Set(Color color);
    public static ColorSetter ThemeColor_Back = (c->new Color(240, 240, 240));
    public static ColorSetter ThemeColor_Fore = (c->new Color(204, 255, 153));
    public static ColorSetter ThemeColor_Hover = (c->ColorAdj(c, -10));
    public static ColorSetter ThemeColor_Action = (c->ColorAdj(c, -20));
    public static ColorSetter ThemeColor_Font = (c->new Color(0, 0, 0));
    static Color ColorAdj(Color origin, int offsetR, int offsetB, int offsetG) {
        return new Color(
                Math.min(Math.max(origin.getRed()+offsetR,0),255),
                Math.min(Math.max(origin.getGreen()+offsetG,0),255),
                Math.min(Math.max(origin.getBlue()+offsetB,0),255));
    }
    static Color ColorAdj(Color origin, int offset) {
        return new Color(
                Math.min(Math.max(origin.getRed()+offset,0),255),
                Math.min(Math.max(origin.getGreen()+offset,0),255),
                Math.min(Math.max(origin.getBlue()+offset,0),255));
    }
}
