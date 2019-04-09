package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;

public class Element {

	private boolean _isIinted = false;
	
	public boolean getIsIinted() {
		return _isIinted;
	}

	public void init() {
		_isIinted = true;
	}

	private Element _owner = null;

	public Element getOwner() {
		return _owner;
	}

	public void setOwner(Element value) {
		_owner = value;
	}
	
	private int _depth = 0;

	public int getDepth() {
		return _depth;
	}

	public void setDepth(int value) {
		_depth = value;
	}

	public void incDepth() {
		_depth += 1;
	}

	private boolean _isVisible = false;

	public boolean getIsVisible() {
		return _isVisible;
	}

	public void setIsVisible(boolean value) {
		_isVisible = value;
	}

	private Point _pt1 = new Point(0, 0);
	private Point _pt2 = new Point(0, 0);

	public Point getPt1() {
		return _pt1;
	}

	public void setPt1(Point value) {
		_pt1.setLocation(value);
	}

	public void setPt1(int x, int y) {
		_pt1.setLocation(x, y);
	}

	public Point getPt2() {
		return _pt2;
	}

	public void setPt2(Point value) {
		_pt2.setLocation(value);
	}

	public void setPt2(int x, int y) {
		_pt2.setLocation(x, y);
	}

	public void move(int x, int y) {
		setPt1(_pt1.x + x, _pt1.y + y);
		setPt2(_pt2.x + x, _pt2.y + y);
	}
	
	public int getX() {
		return Math.min(_pt1.x, _pt2.x);
	}

	public int getY() {
		return Math.min(_pt1.y, _pt2.y);
	}

	public int getWidth() {
		return Math.abs(_pt1.x - _pt2.x);
	}

	public int getHeight() {
		return Math.abs(_pt1.y - _pt2.y);
	}

	public void setX(int value) {
		int offset = value-getX();
		setPt1(_pt1.x+offset, _pt1.y);
		setPt2(_pt2.x+offset, _pt2.y);
	}

	public void setY(int value) {
		int offset = value-getY();
		setPt1(_pt1.x, _pt1.y+offset);
		setPt2(_pt2.x, _pt2.y+offset);
	}
	
	private boolean _isMouseOver = false;

	public boolean getIsMouseOver() {
		return _isMouseOver;
	}

	public void setIsMouseOver(boolean value) {
		_isMouseOver = value;
	}

	private boolean _isSelected = false;

	public boolean getIsSelected() {
		return _isSelected;
	}

	public void setIsSelected(boolean value) {
		_isSelected = value;
	}

	public void StartToDraw(Graphics2D g, Point o) {
		if (!_isVisible)
			return;
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		HowToDraw(g, o);
	}

	protected void HowToDraw(Graphics2D g, Point o) {
		int w = getWidth();
		int h = getHeight();
		int x = getX() + o.x;
		int y = getY() + o.y;
		g.drawRect(x, y, w, h);
		g.setColor(new Color(0, 0, 0, 5));
		g.fillRect(x, y, w, h);
	}

	public void DrawInfo(Graphics2D g, Point origin) {
		var x0 = getX()+getWidth()+origin.x + 5;
		var y0 = getY()+origin.y;
		for(var str : toString().lines().toArray())
			g.drawString(str.toString(), x0, y0+=14);
		g.setColor(Color.white);
	}

	public boolean isPointIn(int x, int y) {
		int xx1 = Math.min(_pt1.x, _pt2.x);
		int yy1 = Math.min(_pt1.y, _pt2.y);
		int xx2 = Math.max(_pt1.x, _pt2.x);
		int yy2 = Math.max(_pt1.y, _pt2.y);
		if (x < xx1 || y < yy1 || x > xx2 || y > yy2)
			return false;
		return true;
	}
	
	private String _context = null;
	
	public String getContext() {
		return _context;
	}
	
	public void setContext(String value) {
		_context = value;
	}
	
	@Override
	public String toString() {
		return
				String.format("[depth: %d]\n", getDepth()) + 
				String.format("[X: %d]\n", getX()) + 
				String.format("[Y: %d]\n", getY()) +
				String.format("[W: %d]\n", getWidth()) + 
				String.format("[H: %d]\n", getHeight());
	}
	
	

}
