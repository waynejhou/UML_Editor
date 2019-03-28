package uml_editor.views.components.elements.interfaces;


import java.util.List;

import uml_editor.views.components.elements.JointPointElement;

public interface ICanBeJointed {
	public List<JointPointElement> getJointPts();
	public JointPointElement getTopJointPt();
	public JointPointElement getBottomJointPt();
	public JointPointElement getLeftJointPt();
	public JointPointElement getRightJointPt();
}
