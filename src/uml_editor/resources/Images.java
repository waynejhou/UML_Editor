package uml_editor.resources;

import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;

public final class Images {
    private Images() {}
    public static Image Select, UseCase, GeneralizationLine, Class, CompositionLine, AssociationLine, InheritanceLine, ImplementLine, AssociationArrowLine;
    static {
        try {
            Select = ImageIO.read(Images.class.getResource("images/Select.png"));
            UseCase = ImageIO.read(Images.class.getResource("images/Use Case.png"));
            GeneralizationLine = ImageIO.read(Images.class.getResource("images/Generalization Line.png"));
            Class = ImageIO.read(Images.class.getResource("images/Class.png"));
            CompositionLine = ImageIO.read(Images.class.getResource("images/Composition Line.png"));
            AssociationLine = ImageIO.read(Images.class.getResource("images/Association Line.png"));
            InheritanceLine = ImageIO.read(Images.class.getResource("images/Inheritance Line.png"));
            ImplementLine = ImageIO.read(Images.class.getResource("images/Implement Line.png"));
            AssociationArrowLine = ImageIO.read(Images.class.getResource("images/Association Arrow Line.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
