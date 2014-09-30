package ui;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

import operations.O_AddEmptyGraph;
import operations.O_DeleteSelectedGraph;
import operations.O_DuplicateSelectedGraph;
import operations.O_SelectFirstSlide;
import operations.O_SelectLastSlide;
import operations.O_SelectNextSlide;
import operations.O_SelectPreviousSlide;


public class JSlidesMenu extends JMenu {

	JSlidesMenu() {
		super("Slides");
		
		JOperationMenuItem item = new JOperationMenuItem("Add slide");
		item.setOperation(new O_AddEmptyGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_COMMA, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_COMMA);
		this.add(item);
		
		item = new JOperationMenuItem("Duplicate slide");
		item.setOperation(new O_DuplicateSelectedGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_PERIOD, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_PERIOD);
		this.add(item);
		
		item = new JOperationMenuItem("Delete slide");
		item.setOperation(new O_DeleteSelectedGraph());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_MINUS, ActionEvent.CTRL_MASK));
		item.setMnemonic(KeyEvent.VK_MINUS);
		this.add(item);
		
		this.addSeparator();
		
		item = new JOperationMenuItem("First slide");
		item.setOperation(new O_SelectFirstSlide());
		this.add(item);
		
		item = new JOperationMenuItem("Previous slide");
		item.setOperation(new O_SelectPreviousSlide());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_UP, 0));
		item.setMnemonic(KeyEvent.VK_UP);
		this.add(item);
		
		item = new JOperationMenuItem("Next slide");
		item.setOperation(new O_SelectNextSlide());
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_DOWN, 0));
		item.setMnemonic(KeyEvent.VK_DOWN);
		this.add(item);
		
		item = new JOperationMenuItem("Last slide");
		item.setOperation(new O_SelectLastSlide());
		this.add(item);
		
	}
}
