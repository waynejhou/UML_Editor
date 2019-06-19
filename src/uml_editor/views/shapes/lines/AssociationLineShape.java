package uml_editor.views.shapes.lines;


import uml_editor.views.shapes.creators.ILineShapeCreator;

public class AssociationLineShape extends LineShape {

    public final static ILineShapeCreator Creator = new ILineShapeCreator() {
        @Override
        public LineShape cr8Shape() {
            return new AssociationLineShape();
        }
    };

    public AssociationLineShape() {
    }
    


}
