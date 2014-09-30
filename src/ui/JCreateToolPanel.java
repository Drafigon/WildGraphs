package ui;

import operations.O_CreateBipartitGraph;
import operations.O_CreateRandomGraphCircle;
import operations.O_CreateTree;


public class JCreateToolPanel extends JToolPanel {
	
	JCreateToolPanel() {
		super("Create");
		init();
	}
	
	private void init() {
		this.setFocusable(false);
		// Normal Buttons
		JOperationButton button = new JOperationButton("Random graph");
		button.setOperation(new O_CreateRandomGraphCircle());
		setParam(button, "t_randomgraph.png", "Create a random normal graph");
		this.add(button);	
		
		button = new JOperationButton("Bipartit graph");
		button.setOperation(new O_CreateBipartitGraph());
		setParam(button, "t_randomgraph.png", "Create a random bipartit graph");
		this.add(button);
		
		button = new JOperationButton("Tree");
		button.setOperation(new O_CreateTree());
		setParam(button, "t_randomgraph.png", "Create a random tree");
		this.add(button);
	}
	
	private void setParam(JOperationButton button,String icon, String tooltip) {
		button.setIcons(icon,icon);
		button.setToolTipText(tooltip);
	}
}
