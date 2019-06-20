package uml_editor.views;

import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.Component;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import static uml_editor.functions.ComponentFunctions.*;

import uml_editor.Program;
import uml_editor.utils.Pair;
import uml_editor.utils.Setter;

public class EditNameDialog extends JDialog {

    JTextArea _contextArea = null;

    public EditNameDialog(String defaultContext) {
        super(Program.MainWin, Program.MainWin.getTitle(), true);
        this.setSize(200, 200);
        var jsp = new JScrollPane(_contextArea = new JTextArea());
        if (defaultContext != null)
            _contextArea.setText(defaultContext);
        this.add(cr8BorderPanel(
                new Pair<String, Component>(BorderLayout.CENTER, jsp),
                new Pair<String, Component>(BorderLayout.SOUTH,
                        cr8Panel(
                        cr8Comp(Button.class,null,
                                new Setter("Label","Confirm"),
                                new Setter("ActionListener",(ActionListener)(e->{
                                    _isSuccess = true;
                                    ((EditNameDialog) ((Button) e.getSource()).getParent().getParent().getParent()
                                            .getParent().getParent().getParent()).setVisible(false);
                                }))),
                        cr8Comp(Button.class,null,
                                new Setter("Label","Cancel"),
                                new Setter("ActionListener",(ActionListener)(e->{
                                    _isSuccess = false;
                                    ((EditNameDialog) ((Button) e.getSource()).getParent().getParent().getParent()
                                            .getParent().getParent().getParent()).setVisible(false);
                                })))
                        ))
                ));
        
        /*
        this.add(new JPanel() {
            {
                setLayout(new BorderLayout());
                add(new JScrollPane(_contextArea = new JTextArea()), BorderLayout.CENTER);
                if (defaultContext != null)
                    _contextArea.setText(defaultContext);
                add(new JPanel() {
                    {
                        add(new JButton("Confirm") {
                            {
                                addActionListener(e -> {
                                    _isSuccess = true;
                                    ((EditNameDialog) ((JButton) e.getSource()).getParent().getParent().getParent()
                                            .getParent().getParent().getParent()).setVisible(false);
                                });

                            }
                        });
                        add(new JButton("Cancel") {
                            {
                                addActionListener(e -> {
                                    _isSuccess = false;
                                    ((EditNameDialog) ((JButton) e.getSource()).getParent().getParent().getParent()
                                            .getParent().getParent().getParent()).setVisible(false);
                                });
                            }
                        });
                    }
                }, BorderLayout.SOUTH);
            }
        });*/
    }

    private boolean _isSuccess = false;

    public static boolean ShowDialog(EditNameDialogResult outResult, String defaultContext) {
        var dlg = new EditNameDialog(defaultContext);
        dlg.setVisible(true);
        {
            if (!dlg._contextArea.getText().isEmpty()) {
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
