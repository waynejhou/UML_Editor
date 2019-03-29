package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;

import uml_editor.views.components.enums.ElementState;


public class Element {

	private int _x=0;
	private int _y=0;
	public Point getPosition() { return new Point(_x,_y);}
	public int getX() { return _x;}
	public int getY() { return _y;}
	public void setPosition(Point value) { _x=value.x; _y=value.y;}
	public void setPosition(int x, int y) { _x=x; _y=y;}
	public void setX(int value) { _x=value;}
	public void setY(int value) { _y=value;}
	
	/*
	 * Size Relative Properties
	 */
	private int _width=50;
	private int _height=50;
	private int _half_width=25;
	private int _half_height=25;
	private int _minWidth=0;
	private int _minHeight=0;
	public Dimension getSize() { return new Dimension(_width,_height);}
	public int getWidth() { return _width;}
	public int getHeight() { return _height;}
	protected int getHalfWidth() { return _half_width;}
	protected int getHalfHeight() { return _half_height;}
	public void setSize(Dimension value) {
		if(value.width>=_minWidth)
			_width=value.width;
		if(value.height>=_minHeight)
			_height=value.height;
		_half_width=_width/2;_half_height=_height/2;}
	public void setSize(int w, int h) {
		if(w>=_minWidth)
			_width=w;
		if(h>=_minHeight)
			_height=h;
		_half_width=_width/2;_half_height=_height/2;}
	public void setWidth(int value) {
		if(value>=_minWidth)
			_width=value;
		_half_width=_width/2;}
	public void setHeight(int value) {
		if(value>=_minHeight)
			_height=value;
		_half_height=_width/2;}
	public Dimension getMinSize() { return new Dimension(_minWidth,_minHeight);}
	public int getMinWidth() { return _minWidth;}
	public int getMinHeight() { return _minHeight;}
	public void setMinSize(Dimension value) {
		_minWidth=(value.width>=0)?value.width:0;
		_minHeight=(value.height>=0)?value.height:0;}
	public void setMinSize(int w, int h) {
		_minWidth=(w>=0)?w:0;
		_minHeight=(h>=0)?h:0;}
	public void setMinWidth(int value) {_minWidth=(value>=0)?value:0;}
	public void setMinHeight(int value) {_minHeight=(value>=0)?value:0;}
	
	private boolean _isMouseOver = false;
	public boolean getIsMouseOver() { return _isMouseOver;}
	public void setIsMouseOver(boolean value) { _isMouseOver=value;}

	private boolean _isSelected = false;
	public boolean getIsSelected() { return _isSelected;}
	public void setIsSelected(boolean value) { _isSelected=value;}
	
	private boolean _isIinted = false;
	public boolean getIsIinted() { return _isIinted;}
	
	private int _depth = 0;
	public int getDepth() { return _depth;}
	public void setDepth(int value) { _depth=value;}
	public void incDepth() { _depth+=1;}
	
	public void Draw(Graphics2D g, Point Origin) {
		var x = Origin.x+_x;var y = Origin.y+_y;
		g.setColor(new Color(0, 0, 0, 5));
		g.fillRect(x - _half_width, y - _half_height, _width, _height);
		g.setColor(new Color(100, 100, 100));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(_isSelected) {
			g.setColor(new Color(255, 0, 0));
		}
		if(_isMouseOver) {
			g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}

		g.drawRect(x - _half_width, y - _half_height, _width, _height);
		g.drawLine(x - _half_width, y - _half_height, x+_half_width, y+_half_height);
		g.drawLine(x - _half_width, y+_half_height, x+_half_width, y - _half_height);
	}
	
	public void DrawInfo(Graphics2D g, Point Origin) {
		var x = Origin.x+_x;var y = Origin.y+_y;
		g.drawString("[width: "+getWidth(), x+_half_width, y - _half_height);
		g.drawString(" height: "+getHeight(), x+_half_width, y - _half_height+15);
		g.drawString(" x: "+getX(), x+_half_width, y - _half_height+30);
		g.drawString(" y: "+getY(), x+_half_width, y - _half_height+45);
		g.drawString(" depth: "+getDepth(), x+_half_width, y - _half_height+60);
		g.drawString(" isMouseOver: "+getIsMouseOver(), x+_half_width, y - _half_height+75);
		g.drawString(" ]", x+_half_width, y - _half_height+90);
	}
	
	public void init() {
		_isIinted = true;
	}
	
	public boolean isPointIn(Point pt) {
		return isPointIn(pt.x, pt.y);
	}
	public boolean isPointIn(int x, int y) {
		return (Math.abs(x-_x) <= _half_width) && (Math.abs(y-_y) <= _half_height);
	}
}
