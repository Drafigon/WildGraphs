package ui;

import java.awt.Color;

import modi.M_BipartitLayout;
import modi.M_CircleLayout;
import modi.M_ForceLayout;
import modi.M_RandomLayout;
import modi.M_TreeLayout;
import operations.O_ChangeModus;


public class JLayoutToolPanel extends JToolPanel {
	
	JLayoutToolPanel() {
		super("Layout");
		init();
	}
	
	private void init() {
		this.setFocusable(false);
		// Normal Buttons
		JOperationButton button = new JOperationButton("Random Layout");
		button.setOperation(new O_ChangeModus(M_RandomLayout.getInstance()));
		setParam(button, "t_randomlayout.png", "Moves vertices at random places");
		this.add(button);
		
		button = new JOperationButton("Circle Layout");
		button.setOperation(new O_ChangeModus(M_CircleLayout.getInstance()));
		setParam(button, "t_circlelayout.png", "Places vertices on a circle");
		this.add(button);
		
		button = new JOperationButton("Force Layout");
		button.setOperation(new O_ChangeModus(M_ForceLayout.getInstance()));
		setParam(button, "t_forcelayout.png", "Calculate the vertices positions using the force model");
		this.add(button);	
		
		button = new JOperationButton("Bipartit Layout");
		button.setOperation(new O_ChangeModus(M_BipartitLayout.getInstance()));
		setParam(button, "t_forcelayout.png", "Two horizontal rows for bipartit graphs.");
		this.add(button);
		
		button = new JOperationButton("Tree Layout");
		button.setOperation(new O_ChangeModus(M_TreeLayout.getInstance()));
		setParam(button, "t_forcelayout.png", "Layout as Tree from top to bottom.");
		this.add(button);
	}
	
	private void setParam(JOperationButton button,String icon, String tooltip) {
		button.setIcons(icon,icon);
		button.setToolTipText(tooltip);
	}

	@Override
	public void setBackground(Color color) {
		int r = (color.getRed() - 30) % 256;
		int g = (color.getGreen() - 30) % 256;
		int b = (color.getBlue() - 30) % 256;
		
		Color bg = new Color(r,g,b);
		super.setBackground(bg);
	}	
	
	
}
