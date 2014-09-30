package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JMenuItem;

import operations.O_Nothing;
import operations.Operation;
import operations.OperationObject;

public class JOperationMenuItem extends JMenuItem implements OperationObject {

	private static ItemListener listener = new ItemListener();
	private Operation operation = new O_Nothing();
	
	
	public JOperationMenuItem(String name) {
		super(name);
		this.addActionListener(listener);
	}
	
	@Override
	public Operation getOperation() {
		return operation;
	}

	@Override
	public void setOperation(Operation a) {
		operation = a;
	}
	
	private static class ItemListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			OperationObject object = (OperationObject) e.getSource();
			Operation op = object.getOperation();
			op.run();
		}		
	}
}
