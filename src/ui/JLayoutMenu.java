package ui;


import javax.swing.JMenu;

import modi.M_BipartitLayout;
import modi.M_CircleLayout;
import modi.M_ForceLayout;
import modi.M_RandomLayout;
import modi.M_TreeLayout;
import operations.O_ChangeModus;

public class JLayoutMenu extends JMenu {

	JLayoutMenu() {
		super("Layouter");
		
		JOperationMenuItem item = new JOperationMenuItem("Random");
		item.setOperation(new O_ChangeModus(M_RandomLayout.getInstance()));
		this.add(item);
		
		item = new JOperationMenuItem("Circle");
		item.setOperation(new O_ChangeModus(M_CircleLayout.getInstance()));
		this.add(item);
		
		item = new JOperationMenuItem("Force");
		item.setOperation(new O_ChangeModus(M_ForceLayout.getInstance()));
		this.add(item);

		this.addSeparator();
		
		item = new JOperationMenuItem("Bipartit");
		item.setOperation(new O_ChangeModus(M_BipartitLayout.getInstance()));
		this.add(item);	

		item = new JOperationMenuItem("Baum");
		item.setOperation(new O_ChangeModus(M_TreeLayout.getInstance()));
		this.add(item);
	}
}
