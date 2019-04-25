package uml_editor.views;

import java.awt.BorderLayout;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.border.BevelBorder;

import uml_editor.views.panels.ElementPanel;
import uml_editor.views.panels.enums.EditorMode;

public class MainWindow extends JFrame {

	// Panel With Elements
	ElementPanel _ElePanel;
    public MainWindow() {
        this.setTitle("Wayne UML Editor");
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLocationByPlatform(true);
        this.setSize(800, 600);
        this.setJMenuBar(new JMenuBar() {{
        	add(new JMenu("File"));
        	add(new JMenu("Edit") {{
        		add(new JMenuItem("Group Elements") {{
        			addActionListener(e->{
        				_ElePanel.setAGroup();
        			});
        		}});
        		add(new JMenuItem("Edit Name") {{
        			addActionListener(e->{
        				var result = new EditNameDialogResult();
        				if(EditNameDialog.ShowDialog(result, _ElePanel.getAName())) {
        					_ElePanel.setAName(result.getName());
        				}
        			});
        		}});
        		add(new JMenuItem("UnGroup Elements") {{
        			addActionListener(e->{
        				_ElePanel.unsetAGroup();
        			});
        		}});
        	}});
        }});
        this.add(new JPanel() {
            {
                setLayout(new BorderLayout());
                add(panel_westJPanel, BorderLayout.WEST);
				add(_ElePanel = new ElementPanel(), BorderLayout.CENTER);
            }
        });
        setMode(EditorMode._select);
    }
    
    List<JButton> modeBtns =
    		Arrays.asList(EditorMode.values()).stream()
    		.map(x->{
    			var btn = new JButton(x.toString().substring(1));
    			btn.setName(x.toString());
    			return btn;
    		})
    		.collect(Collectors.toList());
    
    JPanel panel_westJPanel = new JPanel() {{
    	setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBorder(BorderFactory.createBevelBorder(BevelBorder.LOWERED));
        for(var btn: modeBtns ){
            add(btn);
            btn.addActionListener(e->{
                setMode(EditorMode.valueOf(btn.getName()));
            });
        }
    }};

    public EditorMode getMode(){return _ElePanel.getMode();}
    void setMode(EditorMode value){
    	_ElePanel.setMode(value);
        for(var btn: modeBtns) {
        	if(EditorMode.valueOf(btn.getName())!=getMode()) {
        		btn.setText(btn.getName().substring(1));
        	}
        	else {
        		btn.setText("[[["+ btn.getName().substring(1) +"]]]");
			}
        }
    }
}