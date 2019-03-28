package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

import uml_editor.views.components.elements.interfaces.ICanBeJointed;

public class JointPointElement extends Element{
	public JointPointElement() {
		setSize(12, 12);
	}
	
	
	@Override
	public void Draw(Graphics2D g, Point Origin) {
		var x = Origin.x+getX();var y = Origin.y+getY();
		g.setColor(new Color(0, 0, 0));
		if(getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
			if(getIsMouseOver())
				g.setColor(new Color(255, 100, 100));
			g.fillRect(x - getHalfWidth(), y - getHalfHeight(), getWidth(), getHeight());
		}
		
	}
}
