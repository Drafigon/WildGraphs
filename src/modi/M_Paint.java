package modi;

import java.awt.Point;

import graph.drawing.EdgeDrawOption;
import graph.drawing.VertexDrawOption;
import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import javax.swing.JPanel;

import utility.GraphFunctions;
import data.Collector;
import modi.indicators.I_Nothing;
import modi.indicators.Indicator;
import modi.ui.JCreate;

public class M_Paint extends Modus {

	private static final M_Paint INSTANCE = new M_Paint();
	
	// Basic
	private Indicator indicator = new I_Nothing();
	private JCreate option = (JCreate) M_Create.getInstance().getOptionPanel();
	
	// Paint Modus
	private VertexDrawOption vdo;
	private EdgeDrawOption edo;
	
	private M_Paint() {
		vdo = option.getVDO();
		edo = option.getEDO();
	}
	
	@Override
	public void runPressedOperation(Point mouse) {

		Point start = mouse;
		
		Graph g = Collector.getGraph();
		Vertex vertex = GraphFunctions.getHitVertex(g, start);
		Edge edge = GraphFunctions.getHitEdge(g, mouse);
		
		// vertex hit		
		if(vertex != null) {
			Collector.getSlides().addUndo();
			vertex.setDrawOption(new VertexDrawOption(vdo));		
		}
		else
		if(edge != null) {			
				Collector.getSlides().addUndo();
				edge.setDrawOption(new EdgeDrawOption(edo));	
		}
		
		Collector.getWindow().repaint();
	}
	
	@Override
	public void runMovedOperation(Point mouse) {
		Graph g = Collector.getGraph();
		
		for(Vertex v : g.getVertices()) {
			boolean isInside = GraphFunctions.isInsideVertex(v, mouse);
			if(isInside) {
				v.setMouseover(true);
			}
			else{
				v.setMouseover(false);
			}
		}
		
		for(Edge e : g.getEdges()) {
			boolean isInside = GraphFunctions.isInsideEdge(e, mouse);
			if(isInside) {
				e.setMouseOver(true);
			}
			else {
				e.setMouseOver(false);
			}
		}

		Collector.getWindow().repaint();
	}

	@Override
	public void updateValuesFromPanel() {
		vdo = option.getVDO();
		edo = option.getEDO();
	}

	@Override
	public JPanel getOptionPanel() {
		return option;
	}

	@Override
	public Indicator getIndicator() {
		return indicator;
	}

	@Override
	public String getDescription() {
		return "Modus Paint";
	}

	public static M_Paint getInstance() {
		return INSTANCE;
	}
}
