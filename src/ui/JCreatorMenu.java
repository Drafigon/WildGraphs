package ui;

import javax.swing.JMenu;

import operations.O_CreateBipartitGraph;
import operations.O_CreateRandomGraphCircle;
import operations.O_CreateTree;


public class JCreatorMenu extends JMenu {

	JCreatorMenu() {
		super("Creator");
		
		JOperationMenuItem item = new JOperationMenuItem("Normal graph...");
		item.setOperation(new O_CreateRandomGraphCircle());
		this.add(item);	
		
		item = new JOperationMenuItem("Bipartit graph...");
		item.setOperation(new O_CreateBipartitGraph());
		this.add(item);	
		
		item = new JOperationMenuItem("Tree...");
		item.setOperation(new O_CreateTree());
		this.add(item);
	}
}
