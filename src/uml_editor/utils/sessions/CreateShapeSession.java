package uml_editor.utils.sessions;

import java.awt.Cursor;
import java.awt.event.MouseEvent;

import uml_editor.views.components.UMLPanel;
import uml_editor.views.shapes.CanBeJointedShape;
import uml_editor.views.shapes.creators.ICanBeJointedShapeCreator;

public class CreateShapeSession extends UMLSession {

    public CreateShapeSession(ICanBeJointedShapeCreator creator) {
        _creator = creator;
    }

    private ICanBeJointedShapeCreator _creator;

    private CanBeJointedShape _previewShape;

    @Override
    public void setHost(UMLPanel value) {
        if(value==null) {
            H.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            super.setHost(value);
        }
        else{
            super.setHost(value);
            H.setCursor(new Cursor(Cursor.CROSSHAIR_CURSOR));
        }
    }
    
    @Override
    public void mousePressed(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1)
            return;
        var o = H.getOrigin();
        var mpt = e.getPoint();
        _previewShape = _creator.cr8Shape();
        _previewShape.setPt1(mpt.x - o.x, mpt.y - o.y);
        _previewShape.setIsVisible(true);
        H.PreviewedShapes.add(_previewShape);
        H.requestUpdateDynamicground();
        H.repaint();
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        var o = H.getOrigin();
        var mpt = e.getPoint();
        if (_previewShape == null)
            return;
        _previewShape.setPt2(mpt.x - o.x, mpt.y - o.y);
        H.requestUpdateDynamicground();
        H.repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (e.getButton() != MouseEvent.BUTTON1)
            return;
        if (_previewShape == null)
            return;
        var o = H.getOrigin();
        var mpt = e.getPoint();
        _previewShape.setPt2(mpt.x - o.x, mpt.y - o.y);
        H.PreviewedShapes.remove(_previewShape);
        H.CanBeJointedShapeCollection.addShapes(_previewShape);
        _previewShape.init();
        H.requestUpdateStaticground();
        H.requestUpdateDynamicground();
        H.repaint();
    }

}
