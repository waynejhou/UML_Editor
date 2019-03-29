package uml_editor.views.panels;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import javax.swing.JPanel;
import javax.swing.event.MenuDragMouseListener;

import uml_editor.Program;
import uml_editor.views.MainWindow;
import uml_editor.views.components.elements.ClassElement;
import uml_editor.views.components.elements.Element;
import uml_editor.views.components.elements.JointPointElement;
import uml_editor.views.components.elements.LineElement;
import uml_editor.views.components.enums.ElementState;
import uml_editor.enums.EditorMode;


public class ElementPanel extends JPanel implements MouseListener, MouseMotionListener{
	public ArrayList<Element> Elements = new ArrayList<Element>();
	
	Element _PreviewElement = null;
	
	public ElementPanel() {
		super();
		addMouseMotionListener(this);
		addMouseListener(this);
		var ele = new Element();
		ele.init();
		ele.setDepth(0);
		Elements.add(ele);
		
		var ele2 = new ClassElement();
		ele2.setPosition(-100, 0);
		ele2.init();
		ele2.setDepth(1);
		Elements.add(ele2);
		
		var ele3 = new ClassElement();
		ele3.setPosition(100, 0);
		ele3.init();
		ele3.setDepth(2);
		Elements.add(ele3);
		
		var ele4 = new LineElement();
		ele4.setPosition(0, 100);
		ele4.init();
		ele4.setDepth(3);
		Elements.add(ele4);
		
		
	}
	
	@Override
	public void paint(Graphics g) {
		// TODO Auto-generated method stub
		super.paint(g);
		cls(g);
		var origin = getOrigin();
		drawBase(g);
		for (var e : Elements) {
			e.Draw((Graphics2D)g, origin);
			e.DrawInfo((Graphics2D)g, origin);
		}
		if(_PreviewElement!=null)
			_PreviewElement.Draw((Graphics2D)g, origin);
	}
	
	void cls(Graphics g) {
		g.setColor(Color.white);
		g.fillRect(0, 0, getWidth(), getHeight());
	}

	Point getOrigin() {
		return new Point(getWidth()/2, getHeight()/2);
	}
	
	void drawBase(Graphics g) {
		var origin = getOrigin();
		g.setColor(new Color(165, 165, 165));
		g.drawRect(5, 5, getWidth()-6, getHeight()-6);
		g.drawLine(origin.x, 0, origin.x, getHeight());
		g.drawLine(0, origin.y, getWidth(),origin.y );
	}

	@Override
	public void mouseClicked(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	int _dragTempPtX = 0;
	int _dragTempPtY = 0;
	int _dragEleTempPtX = 0;
	int _dragEleTempPtY = 0;
	boolean _dragingElementStart = false;
	Element _mouseOveringElement = null;
	Element _selectedElement = null;
	
	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		var origin = getOrigin();
		switch (Program.MainWin.getMode()) {
		case _class:
			_PreviewElement = new ClassElement();
			_dragTempPtX = e.getX();
			_dragTempPtY = e.getY();
			break;
		case _select:
			for(var ele: Elements)
				ele.setIsSelected(false);
			if(_mouseOveringElement!=null) {
				_mouseOveringElement.setIsSelected(true);
				_selectedElement = _mouseOveringElement;
				if(_selectedElement.isPointIn(e.getX()-origin.x, e.getY()-origin.y)) {
					_dragTempPtX = e.getX();
					_dragTempPtY = e.getY();
					_dragEleTempPtX = _selectedElement.getX();
					_dragEleTempPtY = _selectedElement.getY();
					_dragingElementStart = true;
				}
			}else {
				if(_selectedElement!=null) {
					_selectedElement.setIsSelected(false);
					_selectedElement = null;
				}
			}
			break;
		default:
			break;
		}
		updatePanel();
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		switch (Program.MainWin.getMode()) {
		case _class:
			for(var ele: Elements) {
				ele.incDepth();
			}
			_PreviewElement.init();
			Elements.add(_PreviewElement);
			Collections.sort(Elements, (l,r)->l.getDepth()-r.getDepth());
			_PreviewElement = null;
			break;
		case _select:
			_dragingElementStart = false;
			break;
		default:
			break;
		}

	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouseDragged(MouseEvent e) {
		// TODO Auto-generated method stub
		var origin = getOrigin();
		switch (Program.MainWin.getMode()) {
		case _class:
			if(_PreviewElement !=null) {
				var wid = e.getX()-_dragTempPtX;
				var hei = e.getY()-_dragTempPtY;
				_PreviewElement.setPosition(
						e.getX()-origin.x-wid/2,
						e.getY()-origin.y-hei/2);
				_PreviewElement.setSize(Math.abs(e.getX()-_dragTempPtX), Math.abs(e.getY()-_dragTempPtY));
			}
			updatePanel();
			break;
		case _select:
			var wid = e.getX()-_dragTempPtX;
			var hei = e.getY()-_dragTempPtY;
			if(_selectedElement!=null&&_dragingElementStart) {
				_selectedElement.setPosition(
						_dragEleTempPtX +( e.getX()-_dragTempPtX),
						_dragEleTempPtY +( e.getY()-_dragTempPtY));
			}
			updatePanel();
			break;
		default:
			break;
		}
	}

	@Override
	public void mouseMoved(MouseEvent e) {
		var origin = getOrigin();
		switch (Program.MainWin.getMode()) {
		case _select:
			boolean hovered = false;
			for (var ele : Elements ) {
				if(!hovered && ele.isPointIn(e.getX()-origin.x, e.getY()-origin.y)) {
					ele.setIsMouseOver(true);
					hovered = true;
					_mouseOveringElement = ele;
				}
				else if(ele.getIsMouseOver()){
					ele.setIsMouseOver(false);
				}
			}
			if(!hovered)
				_mouseOveringElement = null;
			updatePanel();
			break;
		default:
			break;
		}
	}
	
	long _lastFrameTime = java.lang.System.currentTimeMillis();
	void updatePanel() {
		if(java.lang.System.currentTimeMillis()-_lastFrameTime>=60) {
			_lastFrameTime = java.lang.System.currentTimeMillis();
			update(getGraphics());
		}
	}
}
