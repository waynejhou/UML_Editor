package uml_editor.views;

import static uml_editor.functions.ComponentFunctions.*;
import static uml_editor.functions.MenuFunctions.*;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.BoxLayout;

import uml_editor.resources.ColorSetter;
import uml_editor.resources.Images;
import uml_editor.utils.Pair;
import uml_editor.utils.Setter;
import uml_editor.utils.Style;
import uml_editor.utils.sessions.CreateLineShapeSession;
import uml_editor.utils.sessions.UMLSession;
import uml_editor.views.components.UMLPanel;
import uml_editor.views.components.WRadioButton;
import uml_editor.views.shapes.lines.AssociationArrowLineShape;
import uml_editor.views.shapes.lines.AssociationLineShape;
import uml_editor.views.shapes.lines.ImplementLine;
import uml_editor.views.shapes.lines.InheritanceLine;

public class MainWindow extends Frame {

    UMLPanel _UmlPanel;

    public MainWindow(){
        this.setTitle("Wayne UML Editor");
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                e.getWindow().setVisible(false);
                e.getWindow().dispose();
            }
        });
        this.setLocationByPlatform(true);
        this.setSize(800, 600);
        this.setMenuBar(
                cr8MenuBar(
                        cr8Menu("File"),
                        cr8Menu("Edit",
                                cr8MenuItem("Group Elements", setAGroup),
                                cr8MenuItem("Edit Name", showEditNameDialog),
                                cr8MenuItem("UnGroup Elements", unsetAGroup))
                        ));
        this.add(cr8BorderPanel(
                new Pair<String, Component>(BorderLayout.WEST, cr8BoxPanel(BoxLayout.Y_AXIS,
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.Select),
                                new Setter("DataContext", UMLSession.SelectSession),
                                new Setter("IsToggle", Boolean.TRUE)),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.AssociationLine),
                                new Setter("DataContext", new CreateLineShapeSession(AssociationLineShape.Creator))),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.AssociationArrowLine),
                                new Setter("DataContext", new CreateLineShapeSession(AssociationArrowLineShape.Creator))),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.InheritanceLine),
                                new Setter("DataContext", new CreateLineShapeSession(InheritanceLine.Creator))),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.ImplementLine),
                                new Setter("DataContext", new CreateLineShapeSession(ImplementLine.Creator))),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.GeneralizationLine),
                                new Setter("DataContext", UMLSession.GeneralizationLineShapeSession)),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.CompositionLine),
                                new Setter("DataContext", UMLSession.CompositionLineSession)),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.Class),
                                new Setter("DataContext", UMLSession.ClassSession)),
                        
                        cr8Comp(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.UseCase),
                                new Setter("DataContext", UMLSession.UseCaseSession))
                        )),
                new Pair<String, Component>(BorderLayout.CENTER, (_UmlPanel = cr8Comp(UMLPanel.class, null,
                        new Setter("BackgroundSetter", ColorSetter.ThemeColor_Back),
                        new Setter("ForegroundSetter", (ColorSetter)(c->new Color(200,200,200)))
                        )))));
    }

    public ActionListener setAGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(_UmlPanel.getSession()==UMLSession.SelectSession) {
                UMLSession.SelectSession.setAGroup();
            }
        }
    };
    public ActionListener unsetAGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(_UmlPanel.getSession()==UMLSession.SelectSession) {
                UMLSession.SelectSession.unsetAGroup();
            }
        }
    };
    public ActionListener showEditNameDialog = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var result = new EditNameDialogResult();
            if(_UmlPanel.getSession()==UMLSession.SelectSession) {
                if (EditNameDialog.ShowDialog(result, UMLSession.SelectSession.getAName())) {
                    UMLSession.SelectSession.setAName(result.getName());
                }
            }
        }
    };
    public ActionListener changeMode = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            if(_UmlPanel==null)
                return;
            var btn = (WRadioButton)e.getSource();
            if(btn.getIsToggle()!=true)
                return;
            _UmlPanel.setSession((UMLSession)btn.getDataContext());
        }
    };
    private Style leftButtonStyle = new Style(
            new Setter("BackgroundSetter", ColorSetter.ThemeColor_Back),
            new Setter("ToggroundSetter", ColorSetter.ThemeColor_Fore),
            new Setter("HovergroundSetter", ColorSetter.ThemeColor_Hover),
            new Setter("ActingroundSetter", ColorSetter.ThemeColor_Action),
            new Setter("MaximumSize", new Dimension(50, 50)),
            new Setter("MinimumSize", new Dimension(50, 50)),
            new Setter("PreferredSize", new Dimension(50, 50)),
            new Setter("ActionListener", changeMode));
}
