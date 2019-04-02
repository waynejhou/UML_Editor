package uml_editor;

import javax.swing.UIManager;
import uml_editor.views.MainWindow;

public class Program {

	public static MainWindow MainWin = null;
	public static void main(String[] args) throws Exception {
		UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		MainWin = new MainWindow();
		MainWin.setVisible(true);
	}

}
