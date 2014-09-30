package modi;

import java.awt.Point;

import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import javax.swing.JPanel;

import data.Collector;
import operations.Operation;
import utility.GraphFunctions;
import modi.indicators.I_EdgeMenu;
import modi.indicators.I_GeneralMenu;
import modi.indicators.I_Menu;
import modi.indicators.I_VertexMenu;
import modi.indicators.Indicator;

public class M_Menu extends Modus {

	private static final M_Menu INSTANCE = new M_Menu();
	
	private I_Menu currentMenu;
	private I_VertexMenu vertexMenu = new I_VertexMenu();
	private I_EdgeMenu edgeMenu = new I_EdgeMenu();
	private I_Menu generalMenu = new I_GeneralMenu();
	
	private Vertex vertex;
	private Edge edge;
	
	private M_Menu() {
		currentMenu = generalMenu;
	}
	
	@Override
	public void runPressedOperation(Point mouse) {		
		// were something hit?
		Graph g = Collector.getGraph();
		vertex = GraphFunctions.getHitVertex(g, mouse);
		edge = GraphFunctions.getHitEdge(g, mouse);
		
		if(vertex != null) {
			vertexMenu.setVertex(vertex);
			currentMenu = vertexMenu;
		}
		else if(edge != null) {
			edgeMenu.setEdge(edge);
			currentMenu = edgeMenu;
		}
		else {
			generalMenu.setLocation(mouse);
			currentMenu = generalMenu;
		}
		
		currentMenu.setVisible(true);
		Collector.getWindow().getDrawer().setContextmenu(currentMenu);
	}
	
	@Override
	public void runDraggedOperation(Point mouse) {
		currentMenu.updateMouseOver(mouse);
		Collector.getWindow().getDrawer().setContextmenu(currentMenu);	
	}
	
	
	@Override
	public void runReleasedOperation(Point mouse) {		
		Operation op = currentMenu.getHitOperation(mouse);	
		
		currentMenu.setVisible(false);
		Collector.getWindow().getDrawer().setContextmenu(currentMenu);
		
		if(op != null) {
			op.run();
		}
	}
	
	@Override
	public void updateValuesFromPanel() {
	}

	@Override
	public JPanel getOptionPanel() {
		return new JPanel();
	}

	@Override
	public Indicator getIndicator() {
		return currentMenu;
	}

	@Override
	public String getDescription() {
		return "Context menu";
	}

	
	public static M_Menu getInstance() {
		return INSTANCE;
	}
}
