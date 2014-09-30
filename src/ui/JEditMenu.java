package ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

import operations.O_CenterAllGraphs;
import operations.O_CenterGraph;
import operations.O_Redo;
import operations.O_SetBackgroundColor;
import operations.O_Undo;

public class JEditMenu extends JMenu {

	JEditMenu() {
		super("Edit");
		
		JOperationMenuItem item = new JOperationMenuItem("Undo");
		item.setOperation(new O_Undo());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Z, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_Z);
		this.add(item);
		
		item = new JOperationMenuItem("Redo");
		item.setOperation(new O_Redo());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Y, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_Y);
		this.add(item);
		
		this.addSeparator();
		
		item = new JOperationMenuItem("Center graph");
		item.setOperation(new O_CenterGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, 0));
		item.setMnemonic(KeyEvent.VK_SPACE);
		this.add(item);
		
		item = new JOperationMenuItem("Center all slides");
		item.setOperation(new O_CenterAllGraphs());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_SPACE, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_SPACE);
		this.add(item);		
		
		this.addSeparator();
		
		item = new JOperationMenuItem("Backgroundcolor...");
		item.setOperation(new O_SetBackgroundColor());
		this.add(item);
		
		
		
	}
}
