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

	private Element _owner = null;

	public Element getOwner() {
		return _owner;
	}

	public void setOwner(Element value) {
		_owner = value;
	}

	public Point getCenterPoint() {
		if(_owner!=null)
			return new Point(getX()+getWidth()/2,getY()+getHeight()/2 );
		return new Point(
				_owner.getX()+getX()+getWidth()/2,
				_owner.getY()+getY()+getHeight()/2 );
	}
	
	@Override
	public boolean isPointIn(int x, int y) {
		return super.isPointIn(x-_owner.getX(), y-_owner.getY());
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
