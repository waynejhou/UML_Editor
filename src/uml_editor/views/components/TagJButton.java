package uml_editor.views.components;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.JButton;
import javax.swing.border.LineBorder;

import uml_editor.interfaces.ITag;

public class TagJButton extends JButton implements ITag{

    private static final long serialVersionUID = -7626870643707996108L;

    public TagJButton(String text, Object tag) {
        super(text);
        _tag = tag;
    }

    Object _tag = null;
    @Override
    public Object getTag() {
        return _tag;
    }

    @Override
    public void setTag(Object value) {
        _tag = value;
    }

}