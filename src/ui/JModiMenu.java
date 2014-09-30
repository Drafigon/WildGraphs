package ui;

import java.awt.event.KeyEvent;

import javax.swing.JMenu;
import javax.swing.KeyStroke;

import modi.M_Create;
import modi.M_Move;
import modi.M_Paint;
import operations.O_ChangeModus;

public class JModiMenu extends JMenu {

	JModiMenu() {
		super("Modi");		
		
		JOperationMenuItem item = new JOperationMenuItem("Create");
		item.setOperation(new O_ChangeModus(M_Create.getInstance()));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F1, 0));
		item.setMnemonic(KeyEvent.VK_F1);
		this.add(item);
		
		item = new JOperationMenuItem("Paint");
		item.setOperation(new O_ChangeModus(M_Paint.getInstance()));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F2, 0));
		item.setMnemonic(KeyEvent.VK_F2);
		this.add(item);
		
		item = new JOperationMenuItem("Move");
		item.setOperation(new O_ChangeModus(M_Move.getInstance()));
		item.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_F3, 0));
		item.setMnemonic(KeyEvent.VK_F3);
		this.add(item);
	}
}
