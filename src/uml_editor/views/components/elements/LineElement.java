package uml_editor.views.components.elements;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.geom.Line2D;
import java.util.Comparator;

public class LineElement extends Element {

	private JointElement _from_joint = null;
	public JointElement _to_joint = null;

	public JointElement getFromJoint() {
		return _from_joint;
	}

	public JointElement getToJoint() {
		return _to_joint;
	}

	

	public void setFromJoint(JointElement value) {
		_from_joint = value;
	}

	public void setToJoint(JointElement value) {
		_to_joint = value;
	}

	@Override
	public Point getPt1() {
		if (_from_joint == null)
			return super.getPt1();
		return _from_joint.getCenterPoint();
	}

	@Override
	public Point getPt2() {
		if (_to_joint == null)
			return super.getPt2();
		return _to_joint.getCenterPoint();
	}

	@Override
	public int getX() {
		return Math.min(getPt1().x, getPt2().x);
	}

	@Override
	public int getY() {
		return Math.min(getPt1().y, getPt2().y);
	}

	@Override
	public int getWidth() {
		return Math.abs(getPt1().x - getPt2().x);
	}

	@Override
	public int getHeight() {
		return Math.abs(getPt1().y - getPt2().y);
	}

	
	@Override
	public boolean isPointIn(int x, int y) {
		var dist = Line2D.ptLineDist(
				getPt1().x, getPt1().y,
				getPt2().x, getPt2().y,
				x, y);
		return dist<3;
	}

	@Override
	protected void HowToDraw(Graphics2D g, Point o) {
		//super.HowToDraw(g,o);
		g.setColor(Color.black);
		g.setStroke(new BasicStroke(1, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		if (getIsMouseOver()) {
			g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}
		if (getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
		}
		if (getIsMouseOver() && getIsSelected()) {
			g.setColor(new Color(255, 0, 0));
			g.setStroke(new BasicStroke(3, BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
		}
		g.drawLine(getPt1().x + o.x, getPt1().y + o.y, getPt2().x + o.x, getPt2().y + o.y);
	}

}
