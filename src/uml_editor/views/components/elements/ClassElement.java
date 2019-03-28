package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

import uml_editor.views.components.elements.interfaces.ICanBeJointed;


public class ClassElement extends Element implements ICanBeJointed {
	
	private String _class_name = "class_name";
	public String getClassName() {return _class_name;}
	public void setClassName(String value) {_class_name = value;}
	
	public ClassElement() {
		setMinSize(50, 50);
	}
	
	private JointPointElement _topJointPt = null;
	private JointPointElement _bottomJointPt = null;
	private JointPointElement _leftJointPt = null;
	private JointPointElement _rightJointPt = null;
	private List<JointPointElement> _jointPts = null;
	public List<JointPointElement> getJointPts(){return _jointPts;}
	public JointPointElement getTopJointPt() {return _topJointPt;}
	public JointPointElement getBottomJointPt() {return _bottomJointPt;}
	public JointPointElement getLeftJointPt() {return _leftJointPt;}
	public JointPointElement getRightJointPt() {return _rightJointPt;}
	
	@Override
	public void init() {
		_topJointPt = new JointPointElement();
		_topJointPt.setPosition(0, -getHalfHeight());
		_bottomJointPt = new JointPointElement();
		_bottomJointPt.setPosition(0, +getHalfHeight());
		_leftJointPt = new JointPointElement();
		_leftJointPt.setPosition(-getHalfWidth(), 0);
		_rightJointPt = new JointPointElement();
		_rightJointPt.setPosition(+getHalfWidth(), 0);
		_jointPts = new ArrayList<JointPointElement>();
		_jointPts.add(_topJointPt);
		_jointPts.add(_bottomJointPt);
		_jointPts.add(_leftJointPt);
		_jointPts.add(_rightJointPt);
		super.init();
	}
	
	@Override
	public void Draw(Graphics2D g, Point Origin) {
		var x = Origin.x+getX();var y = Origin.y+getY();
		g.setColor(new Color(0, 0, 0, 5));
		g.fillRect(x - getHalfWidth(), y - getHalfHeight(), getWidth(), getHeight());
		g.setColor(new Color(100, 100, 100));
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if(getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
		}
		if(getIsMouseOver()) {
			g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}

		g.drawRect(x - getHalfWidth(), y - getHalfHeight(), getWidth(), getHeight());
		int x0 = x - getHalfWidth(), y0 = y - getHalfHeight() + 50, x1 = x + getHalfWidth();
		g.drawString(_class_name, x0+10, y0-10);
		
		if(getIsIinted()) {
			_topJointPt.setIsSelected(getIsSelected());
			_bottomJointPt.setIsSelected(getIsSelected());
			_leftJointPt.setIsSelected(getIsSelected());
			_rightJointPt.setIsSelected(getIsSelected());
			_topJointPt.Draw(g, new Point(x, y));
			_bottomJointPt.Draw(g, new Point(x, y));
			_leftJointPt.Draw(g, new Point(x, y));
			_rightJointPt.Draw(g, new Point(x, y));
		}
		while(y0-(y - getHalfHeight())<=getHeight()-30) {
			g.drawLine(x0, y0,x1,y0);
			y0+=30;
		}
	}
	
}
