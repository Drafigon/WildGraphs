package ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

import operations.O_AddEmptyGraphTab;
import operations.O_CloseGraphTab;
import operations.O_ExitProgramm;
import operations.O_LoadGraph;
import operations.O_SaveGraph;
import operations.O_SaveGraphImage;

public class JFileMenu extends JMenu {

	JFileMenu() {
		super("File");
		
		JOperationMenuItem item = new JOperationMenuItem("New file");
		item.setOperation(new O_AddEmptyGraphTab());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_N);
		this.add(item);
		
		item = new JOperationMenuItem("Open file...");
		item.setOperation(new O_LoadGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_O);
		this.add(item);
		
		this.addSeparator();
		
		item = new JOperationMenuItem("Save file...");
		item.setOperation(new O_SaveGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_S);
		this.add(item);
		
		item = new JOperationMenuItem("Save as image...");
		item.setOperation(new O_SaveGraphImage());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK+ActionEvent.SHIFT_MASK));
		item.setMnemonic(KeyEvent.VK_S);
		this.add(item);
		
		this.addSeparator();
		
		item = new JOperationMenuItem("Close file");
		item.setOperation(new O_CloseGraphTab());
		this.add(item);
		
		item = new JOperationMenuItem("Exit");
		item.setOperation(new O_ExitProgramm());
		this.add(item);
	}
}
