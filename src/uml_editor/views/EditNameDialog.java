package uml_editor.views;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;

import uml_editor.Program;

public class EditNameDialog extends JDialog {
	
	JTextArea _contextArea = null;
	
	public EditNameDialog() {
		super(Program.MainWin, Program.MainWin.getTitle(), true);
		this.setSize(200, 80);
		this.add(_contextArea=new JTextArea());
	}

	public static boolean ShowDialog(EditNameDialogResult outResult) {
		var dlg = new EditNameDialog();
		dlg.setVisible(true);
		{
			if(!dlg._contextArea.getText().isEmpty()) {
				outResult.setIsSuccess(true);
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
