package uml_editor.views.components.elements;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.Comparator;

public class GroupElement extends Element {

	private ArrayList<Element> _groupedElements = null;

	public ArrayList<Element> getGroupedElements() {
		return _groupedElements;
	}

	private boolean _isGetX_Needed_ToUpdate = true;
	private boolean _isGetY_Needed_ToUpdate = true;
	private boolean _isGetW_Needed_ToUpdate = true;
	private boolean _isGetH_Needed_ToUpdate = true;

	public void setGroupedElements(ArrayList<Element> value) {
		_groupedElements = value;
		_isGetX_Needed_ToUpdate = true;
		_isGetY_Needed_ToUpdate = true;
		_isGetW_Needed_ToUpdate = true;
		_isGetH_Needed_ToUpdate = true;
	}

	@Override
	public boolean isPointIn(int x, int y) {
		if (_groupedElements == null)
			return super.isPointIn(x, y);
		return (x > getX() - 2 && x < getX() + getWidth() + 2 && y > getY() - 2 && y < getY() + getHeight() + 2) &&
				(x < getX() + 2 && x > getX() + getWidth() - 2 && y < getY() + 2 && y > getY() + getHeight() - 2);
	}

	private int _x = 0;
	private int _y = 0;
	private int _w = 0;
	private int _h = 0;

	@Override
	public int getX() {
		if (_groupedElements == null)
			return super.getX();
		if (_isGetX_Needed_ToUpdate)
			return _x = _groupedElements.stream().map(x -> x.getX()).min(Comparator.comparing(Integer::valueOf)).get();
		return _x;
	}

	@Override
	public int getY() {
		if (_groupedElements == null)
			return super.getY();
		if (_isGetY_Needed_ToUpdate)
			return _y = _groupedElements.stream().map(x -> x.getY()).min(Comparator.comparing(Integer::valueOf)).get();
		return _y;
	}

	@Override
	public int getWidth() {
		if (_groupedElements == null)
			return super.getWidth();
		if (_isGetW_Needed_ToUpdate)
			return _w = _groupedElements.stream().map(x -> x.getX() + x.getWidth())
					.max(Comparator.comparing(Integer::valueOf)).get() - getX();
		return _w;
	}

	@Override
	public int getHeight() {
		if (_groupedElements == null)
			return super.getHeight();
		if (_isGetH_Needed_ToUpdate)
			return _h = _groupedElements.stream().map(x -> x.getY() + x.getHeight())
					.max(Comparator.comparing(Integer::valueOf)).get() - getY();
		return _h;
	}

	public boolean getIsIncluded(Element ele) {
		int ex = ele.getX();
		int ey = ele.getY();
		int ew = ele.getWidth();
		int eh = ele.getHeight();
		int gw = getWidth();
		int gh = getHeight();
		int gx = getX();
		int gy = getY();
		if (gx < ex && gy < ey && gw > ew + (ex - gx) && gh > eh + (ey - gy))
			return true;
		return false;
	}

	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		int w = getWidth() + 10;
		int h = getHeight() + 10;
		int x = getX() + o.x - 5 /*- w / 2*/;
		int y = getY() + o.y - 5/*- h / 2*/;
		g.setColor(new Color(150, 150, 150, 150));
		if(getIsMouseOver()) {
			g.setColor(new Color(255, 0, 0, 150));
		}
		if(getIsSelected()) {
			g.setColor(new Color(0, 0, 0, 150));
		}
		if(getIsMouseOver()&&getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
		}
		g.drawRect(x, y, w, h);
	}

}
