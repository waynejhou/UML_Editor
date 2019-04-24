package uml_editor.views;

import java.awt.BorderLayout;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import uml_editor.Program;

public class EditNameDialog extends JDialog {
	
	JTextArea _contextArea = null;
	
	public EditNameDialog(String defaultContext) {
		super(Program.MainWin, Program.MainWin.getTitle(), true);
		this.setSize(200, 200);
		
		this.add(new JPanel() {{
			setLayout(new BorderLayout());
			add( new JScrollPane(_contextArea=new JTextArea()), BorderLayout.CENTER);
			if(defaultContext!=null)
				_contextArea.setText(defaultContext);
			add(new JPanel() {{
				add(new JButton("Confirm") {{
					addActionListener(e->{
						_isSuccess = true;
						((EditNameDialog)((JButton)e.getSource())
								.getParent().getParent().getParent().getParent().getParent().getParent())
						.setVisible(false);
					});
					
				}});
				add(new JButton("Cancel") {{
					addActionListener(e->{
						_isSuccess = false;
						((EditNameDialog)((JButton)e.getSource())
								.getParent().getParent().getParent().getParent().getParent().getParent())
						.setVisible(false);
					});
				}});
			}}, BorderLayout.SOUTH);
		}});
	}
	
	private boolean _isSuccess = false;
	
	
	public static boolean ShowDialog(EditNameDialogResult outResult, String defaultContext) {
		var dlg = new EditNameDialog(defaultContext);
		dlg.setVisible(true);
		{
			if(!dlg._contextArea.getText().isEmpty()) {
				outResult.setIsSuccess(dlg._isSuccess);
				outResult.setName(dlg._contextArea.getText());
			}
		}
		return outResult.getIsSuccess();
	}
}

class EditNameDialogResult {
	private boolean _isSuccess = false;
	private String _name = "";

	public boolean getIsSuccess() {
		return _isSuccess;
	}

	public String getName() {
		return _name;
	}

	public void setIsSuccess(boolean value) {
		_isSuccess = value;
	}

	public void setName(String value) {
		_name = value;
	}
}
