package ui;

import javax.swing.JPanel;

import operations.O_Redo;
import operations.O_Undo;
import utility.LookAndFeel;

public class JUndoRedo extends JPanel {
	public JUndoRedo() {	
		//this.setBackground(LookAndFeel.Background1);
		
		
		JOperationButton undo = new JOperationButton("Undo");
		undo.setOperation(new O_Undo());
		setParam(undo, "button_undo.png", "Undo");
		add(undo);
		
		JOperationButton redo = new JOperationButton("Redo");
		redo.setOperation(new O_Redo());
		setParam(redo, "button_redo.png", "Redo");
		add(redo);
	}
	
	private void setParam(JOperationButton button,String icon, String tooltip) {
		button.setIcons(icon,icon);
		button.setToolTipText(tooltip);
		button.setText("");
	}
}
