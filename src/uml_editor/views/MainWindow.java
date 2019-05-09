package uml_editor.views;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.lang.reflect.InvocationTargetException;

import javax.swing.BoxLayout;
import javax.swing.JRadioButton;

import uml_editor.Pair;
import uml_editor.listeners.SelectSessionMouseListener;
import uml_editor.resources.ColorSetter;
import uml_editor.resources.Images;

import static uml_editor.interfaces.ComponentFactory.*;
import static uml_editor.interfaces.MenuFactory.*;
import uml_editor.views.panels.ElementPanel;
import wayneUI.Setter;
import wayneUI.Style;
import wayneUI.components.WRadioButton;

public class MainWindow extends Frame {

    ElementPanel _ElePanel;

    @SuppressWarnings("unchecked")
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
                newMenuBar(
                        newMenu("File"),
                        newMenu("Edit",
                                newMenuItem("Group Elements", setAGroup),
                                newMenuItem("Edit Name", showEditNameDialog),
                                newMenuItem("UnGroup Elements", unsetAGroup))
                        ));
        this.add(newBorderPanel(
                new Pair(BorderLayout.WEST, newBoxPanel(BoxLayout.Y_AXIS,
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.Select),
                                new Setter("DataContext", new SelectSessionMouseListener())),
                        
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.AssociationLine)),
                        
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.GeneralizationLine)),
                        
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.CompositionLine)),
                        
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.Class)),
                        
                        newComponent(WRadioButton.class,leftButtonStyle,
                                new Setter("Label", Images.UseCase))
                        )),
                new Pair(BorderLayout.CENTER, (_ElePanel = new ElementPanel()))));
    }

    public ActionListener setAGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            _ElePanel.setAGroup();
        }
    };
    public ActionListener unsetAGroup = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            _ElePanel.unsetAGroup();
        }
    };
    public ActionListener showEditNameDialog = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var result = new EditNameDialogResult();
            if (EditNameDialog.ShowDialog(result, _ElePanel.getAName())) {
                _ElePanel.setAName(result.getName());
            }
        }
    };
    public ActionListener changeMode = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var btn = (WRadioButton)e.getSource();
            _ElePanel.addMouseListener((MouseListener)btn.getDataContext());
            _ElePanel.addMouseMotionListener((MouseMotionListener)btn.getDataContext());
            _ElePanel.addMouseWheelListener((MouseWheelListener)btn.getDataContext());
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
