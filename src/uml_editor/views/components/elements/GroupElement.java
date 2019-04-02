package uml_editor.views.components.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class GroupElement extends Element {

	public boolean getIsIncluded(Element ele) {
		int ex = ele.getX();
		int ey = ele.getY();
		int ew = ele.getWidth();
		int eh = ele.getHeight();
		int gw = getWidth();
		int gh = getHeight();
		int gx = getX();
		int gy = getY();
		if(gx<ex&&gy<ey&&gw>ew+(ex-gx)&&gh>eh+(ey-gy))
			return true;
		return false;
	}
	
	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		int w = getWidth();
		int h = getHeight();
		int x = getX() + o.x /*- w / 2*/;
		int y = getY() + o.y /*- h / 2*/;
		g.setColor(new Color(150,150,150,150));
		g.drawRect(x,y,w,h);
	}
	
}
