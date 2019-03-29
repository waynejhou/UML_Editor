package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

public class LineElement extends Element{
	
	@Override
	public void Draw(Graphics2D g, Point Origin) {
		// TODO Auto-generated method stub
		//super.Draw(g, Origin);
		var x = Origin.x+getX();var y = Origin.y+getY();
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
			if(getIsMouseOver())
				g.setColor(new Color(255, 100, 100));
		}
		g.drawLine(x - getHalfWidth(), y - getHalfHeight(), x+getWidth(), y+getHeight());
	}
}
