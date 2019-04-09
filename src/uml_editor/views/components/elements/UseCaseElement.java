package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import uml_editor.views.components.elements.interfaces.ICanBeJointed;

public class UseCaseElement extends Element implements ICanBeJointed {
	
	ArrayList<JointElement> _joints = null;
	
	@Override
	public void init() {
		JointElement j = null;
		_joints = new ArrayList<JointElement>();
		_joints.add(j=new JointElement(this));
		j.setX(getWidth()/2-5);
		j.setY(-5);
		j.init();
		_joints.add(j=new JointElement(this));
		j.setX(getWidth()/2-5);
		j.setY(getHeight()-5);
		j.init();
		_joints.add(j=new JointElement(this));
		j.setX(-5);
		j.setY(getHeight()/2-5);
		j.init();
		_joints.add(j=new JointElement(this));
		j.setX(getWidth()-5);
		j.setY(getHeight()/2-5);
		j.init();
		super.init();
	}

	@Override
	public List<JointElement> getAllJointElements() {
		return _joints;
	}
	
	@Override
	public void setIsSelected(boolean value) {
		for(var j : _joints) {
			j.setIsVisible(value);
		}
		super.setIsSelected(value);
	}
	
	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		int w = getWidth();
		int h = getHeight();
		int x = getX() + o.x /*- w / 2*/;
		int y = getY() + o.y /*- h / 2*/;
		g.setColor(Color.white);
		g.fillArc(x,y,w,h,0,360);
		
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		
		if(getIsMouseOver())
			g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
		}
		
		g.drawArc(x,y,w,h,0,360);
		
		g.setColor(new Color(0, 0, 0));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(getIsIinted()) {
			for(var j : _joints) {
				j.StartToDraw(g, new Point(x,y));
			}
		}
		
		if (getContext() != null && !getContext().isEmpty()) {
			var ctxLines = getContext().lines().collect(Collectors.toList());
			int x0 = x, y0 = y + 50;
			for(var l : ctxLines) {
				g.drawString(l, x0+15, y0-5);
				y0 += 14;
			}
			
		}
	}
}
