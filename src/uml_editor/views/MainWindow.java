package uml_editor.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.border.BevelBorder;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

import uml_editor.Program;
import uml_editor.enums.EditorMode;
import uml_editor.views.components.TagJButton;
import uml_editor.views.panels.ElementPanel;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class MainWindow extends JFrame {

    public MainWindow() {
        this.setTitle("Wayne UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(800, 600);
        this.add(new JPanel() {
            {
                setLayout(new BorderLayout());
                add(panel_westJPanel, BorderLayout.WEST);
                add(new ElementPanel(), BorderLayout.CENTER);
            }
        });
        setMode(EditorMode._class);
    }
    
    List<TagJButton> modeBtns =
    		Arrays.asList(EditorMode.values()).stream()
    		.map(x->new TagJButton(x.toString().substring(1), x))
    		.collect(Collectors.toList());
    
    JPanel panel_westJPanel = new JPanel() {{
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        for(var btn: modeBtns ){
            add(btn);
            btn.addActionListener(e->{
                setMode((EditorMode) btn.getTag());
            });
        }
    }};
    
    EditorMode _mode = null;

    public EditorMode getMode(){return _mode;}
    void setMode(EditorMode value){
        _mode = value;
        for(var btn: modeBtns) {
        	if(btn.getTag()!=_mode) {
        		btn.setText(btn.getTag().toString().substring(1));
        	}
        	else {
        		btn.setText("[[["+ btn.getTag().toString().substring(1) +"]]]");
			}
        }
    }
}