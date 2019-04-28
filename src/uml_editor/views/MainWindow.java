package uml_editor.views;

import java.awt.BorderLayout;
import java.awt.Frame;
import java.awt.Menu;
import java.awt.MenuBar;
import java.awt.MenuItem;
import java.awt.Panel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.Action;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import uml_editor.views.panels.ElementPanel;
import uml_editor.views.panels.enums.EditorMode;
import wayneUI.components.Button;

public class MainWindow extends Frame {

    ElementPanel _ElePanel;

    public MainWindow() {
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
        this.setMenuBar(new MenuBar() {
            {
                add(new Menu("File"));
                add(new Menu("Edit") {
                    {
                        add(new MenuItem("Group Elements") {
                            {
                                addActionListener(setAGroup);
                            }
                        });
                        add(new MenuItem("Edit Name") {
                            {
                                addActionListener(ShowEditNameDialog);
                                add(new MenuItem("UnGroup Elements") {
                                    {
                                        addActionListener(unsetAGroup);
                                    }
                                });
                            }
                        });
                    }
                });
            }
        });
        this.add(new Panel(new BorderLayout()) {
            {
                add(new Panel() {
                    {
                        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
                        add(new Button("Select", 50, 25));
                    }
                }, BorderLayout.WEST);
                add(new Panel(), BorderLayout.CENTER);
            }
        });

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
    public ActionListener ShowEditNameDialog = new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
            var result = new EditNameDialogResult();
            if (EditNameDialog.ShowDialog(result, _ElePanel.getAName())) {
                _ElePanel.setAName(result.getName());
            }
        }
    };

}
