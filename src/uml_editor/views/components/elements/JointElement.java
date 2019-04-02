package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class JointElement extends Element {

	public JointElement(Element owner) {
		setPt1(0, 0);
		setPt2(10, 10);
		setOwner(owner);
	}

	public Point getCenterPoint() {
		if(getOwner()==null)
			return new Point(getX()+getWidth()/2,getY()+getHeight()/2 );
		return new Point(
				getOwner().getX()+getX()+getWidth()/2,
				getOwner().getY()+getY()+getHeight()/2 );
	}
	
	@Override
	public boolean isPointIn(int x, int y) {
		return super.isPointIn(x-getOwner().getX(), y-getOwner().getY());
	}

	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		int w = getWidth();
		int h = getHeight();
		int x = getX() + o.x /*- w / 2*/;
		int y = getY() + o.y /*- h / 2*/;
		g.setColor(Color.black);
		if(getIsMouseOver())
			g.setColor(new Color(200, 200, 200));
		if(getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
		}
		if(getIsMouseOver() && getIsSelected()) {
			g.setColor(new Color(255, 200, 200));
		}

		g.fillRect(x, y, w, h);
	}
}
