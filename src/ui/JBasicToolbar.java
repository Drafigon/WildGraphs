package ui;

import java.awt.Dimension;

import javax.swing.JPanel;
import javax.swing.JToolBar;

import modi.M_Create;
import modi.M_Move;
import modi.M_Paint;
import operations.O_AddEmptyGraphTab;
import operations.O_CenterAllGraphs;
import operations.O_CenterGraph;
import operations.O_ChangeModus;
import operations.O_ClearGraph;
import operations.O_CloseGraphTab;
import operations.O_LoadGraph;
import operations.O_SaveGraph;
import operations.O_SaveGraphImage;
import data.Collector;

public class JBasicToolbar extends JToolBar {
	
	JBasicToolbar() {
		init();
	}
	
	private void init() {
		this.setFocusable(false);
		this.setSize(new Dimension(30,70));

		// Normal Buttons
		JOperationButton addEmptyGraph = new JOperationButton("New");
		addEmptyGraph.setOperation(new O_AddEmptyGraphTab());
		setParam(addEmptyGraph, "t_new.png", "Creates a new empty board");
		this.add(addEmptyGraph);
		JOperationButton loadGraph = new JOperationButton("Load");
		loadGraph.setOperation(new O_LoadGraph());
		setParam(loadGraph, "t_open.png", "Load graph file");
		this.add(loadGraph);
		JOperationButton saveGraph = new JOperationButton("Save");
		saveGraph.setOperation(new O_SaveGraph());
		setParam(saveGraph, "t_save.png", "Save graph file");
		this.add(saveGraph);
		
		JOperationButton button3 = new JOperationButton("Save as image");
		button3.setOperation(new O_SaveGraphImage());
		setParam(button3, "button_image.png", "Save one or all graphs as image(s).");
		this.add(button3);
		
		JOperationButton closeGraph = new JOperationButton("Close");
		closeGraph.setOperation(new O_CloseGraphTab());
		setParam(closeGraph, "t_close.png", "Close selected board");
		this.add(closeGraph);
		
		/*
		 * <---  Hier eintragen
		 */
		
		
		
		
		this.addSeparator();
		
		JOperationButton buttonA = new JOperationButton("Create");
		buttonA.setOperation(new O_ChangeModus(M_Create.getInstance()));
		setParam(buttonA, "g_create.png", "Switch to modus Create");
		this.add(buttonA);
		
		JOperationButton buttonP = new JOperationButton("Paint");
		buttonP.setOperation(new O_ChangeModus(M_Paint.getInstance()));
		setParam(buttonP, "icon_modus_options.png", "Switch to modus Paint");
		this.add(buttonP);
		
		JOperationButton buttonB = new JOperationButton("Move");
		buttonB.setOperation(new O_ChangeModus(M_Move.getInstance()));
		setParam(buttonB, "g_move.png", "Switch to modus Move");
		this.add(buttonB);
		
		this.addSeparator();
		
		JOperationButton button4 = new JOperationButton("Center");
		button4.setOperation(new O_CenterGraph());
		setParam(button4, "g_center.png", "Moves the graph to the middle of the board");
		this.add(button4);
		
		JOperationButton button5 = new JOperationButton("Center All");
		button5.setOperation(new O_CenterAllGraphs());
		setParam(button5, "g_center.png", "Moves the graphs to the middle of the board");
		this.add(button5);
		
		
		JOperationButton clearGraph = new JOperationButton("Clear Graph");
		clearGraph.setOperation(new O_ClearGraph());
		setParam(clearGraph, "button_clear.png", "Deletes all vertices and edges");
		this.add(clearGraph);
		
		
		this.addSeparator();
		
		// Snap to Grid
		JPanel panel = Collector.getModi().optimizer.getOptionPanel();
		this.add(panel);
		
		this.addSeparator();
		
		this.add(new JUndoRedo());
		
		this.revalidate();
	}
	
	private void setParam(JOperationButton button,String icon, String tooltip) {
		button.setIcons(icon,icon);
		button.setToolTipText(tooltip);
		button.setText("");
	}
}
