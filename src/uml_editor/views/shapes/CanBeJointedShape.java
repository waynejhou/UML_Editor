package uml_editor.views.shapes;

import java.awt.Color;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class CanBeJointedShape extends BaseShape {

    public CanBeJointedShape() {
        setActingroundSetter(c->Color.RED);
    }
    
    protected ArrayList<JointPoint> _jpts;

    public List<JointPoint> getAllJointPoints() {
        return _jpts;
    }
    
    @Override
    public void setIsSelected(boolean value) {
        super.setIsSelected(value);
        if(_jpts==null)
            return;
        for (var j : _jpts) {
            j.setIsVisible(value);
        }
    }
}
