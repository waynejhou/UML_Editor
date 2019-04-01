package uml_editor.views.components.elements;

import java.awt.Graphics2D;
import java.awt.Point;

public class LineElement extends Element{
	
	private JointElement _from_joint = null;
	public JointElement _to_joint = null;
	
	public JointElement getFromJoint(){return _from_joint;}
	public JointElement getToJoint(){return _to_joint;}
	
	public void setFromJoint(JointElement value) {
		_from_joint = value;
	}

	public void setToJoint(JointElement value) {
		_to_joint = value;
	}
	
	@Override
	public Point getPt1() {
		if(_from_joint==null)
			return super.getPt1();
		return _from_joint.getCenterPoint();
	}

	@Override
	public Point getPt2() {
		if(_to_joint==null)
			return super.getPt2();
		return _to_joint.getCenterPoint();
	}



	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		g.drawLine(getPt1().x+o.x, getPt1().y+o.y, getPt2().x+o.x, getPt2().y+o.y);
	}
	
}
