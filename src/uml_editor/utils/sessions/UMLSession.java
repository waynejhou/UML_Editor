package uml_editor.utils.sessions;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import uml_editor.views.components.UMLPanel;
import uml_editor.views.shapes.ClassShape;
import uml_editor.views.shapes.UseCaseShape;
import uml_editor.views.shapes.lines.AssociationLineShape;
import uml_editor.views.shapes.lines.CompositionLineShape;
import uml_editor.views.shapes.lines.GeneralizationLineShape;

public abstract class UMLSession implements MouseListener, MouseMotionListener, MouseWheelListener {
    protected UMLSession() {
    }

    public static SelectSession SelectSession = new SelectSession();
    public static CreateShapeSession ClassSession = new CreateShapeSession(ClassShape.Creator);
    public static CreateShapeSession UseCaseSession = new CreateShapeSession(UseCaseShape.Creator);
    public static CreateLineShapeSession AssociationLineSession = new CreateLineShapeSession(AssociationLineShape.Creator);
    public static CreateLineShapeSession GeneralizationLineShapeSession = new CreateLineShapeSession(GeneralizationLineShape.Creator);
    public static CreateLineShapeSession CompositionLineSession = new CreateLineShapeSession(CompositionLineShape.Creator);
    
    protected UMLPanel H;

    public void setHost(UMLPanel value) {
        H = value;
    }

    public UMLPanel getHost() {
        return H;
    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }


}
