package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import operations.O_Nothing;
import operations.Operation;
import operations.OperationObject;

public class JOperationButton extends JIconButton implements OperationObject {

	private static ButtonListener listener = new ButtonListener();
	private Operation operation = new O_Nothing();
	
	public JOperationButton(String name) {
		super(name);
		operation = new O_Nothing();
		this.setFocusable(false);
		this.addActionListener(listener);
	}

	@Override
	public Operation getOperation() {
		return operation;
	}

	@Override
	public void setOperation(Operation operation) {
		this.operation = operation;
	}
	
	private static class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {	
			OperationObject operationObject = (OperationObject) e.getSource();
			operationObject.getOperation().run();
		}		
	}
}
