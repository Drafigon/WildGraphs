package modi;


import graph.drawing.EdgeDrawOption;
import graph.drawing.VertexDrawOption;
import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;

import javax.swing.JPanel;

import data.Collector;
import modi.indicators.I_CreateEdge;
import modi.indicators.Indicator;
import modi.ui.JCreate;
import utility.GraphFunctions;

public class M_Create extends Modus {

	private static final M_Create INSTANCE = new M_Create();
	
	private I_CreateEdge indicatorEdgeCreation;
	private JCreate option;
	
	// Create Modus
	private int Vcount = 0;
	private Vertex source;
	private Vertex destination;
	private boolean createEdge;
	private VertexDrawOption vdo;
	private EdgeDrawOption edo;
		
	private M_Create() {
		createEdge = false;
		indicatorEdgeCreation = new I_CreateEdge();
		option = new JCreate(this);
		
		vdo = option.getVDO();
		edo = option.getEDO();
	}
	
	public void addVDO(VertexDrawOption newVDO){
		option.addVDO(newVDO);
	}
	
	public void addEDO(EdgeDrawOption newEDO) {
		option.addEDO(newEDO);
	}
	
	@Override
	public void runPressedOperation(Point mouse) {
		
		Point start = mouse;
		
		Graph g = Collector.getGraph();
		Vertex vertex = GraphFunctions.getHitVertex(g, start);
		
		// vertex hit		
		if(vertex != null) {		
			source = vertex;
			createEdge = true;
			indicatorEdgeCreation.setStart(start);
			indicatorEdgeCreation.setEnd(start);
			indicatorEdgeCreation.setVisible(createEdge);
		
		}	
		else {		
			// nothing hit
			// create new vertex
			
			// Snap to Grid?
			Optimizer optimizer = Collector.getModi().optimizer;				
			start = optimizer.getOptimizedPoint(mouse);
			
			
			Graph graph = Collector.getGraph();
			Vcount = 0;
			
			String vertexName = "";
			boolean nameExists = true;
			while(nameExists) {
				Vcount++;
				vertexName = "V"+Vcount;
				nameExists = GraphFunctions.hasVertexWithName(graph, vertexName);
			}
			Vertex newVertex = new Vertex(vertexName, start, new VertexDrawOption(vdo));
			Collector.getSlides().addUndo();
			graph.addVertex(newVertex);		
		}
		
		Collector.getWindow().repaint();
	}


	@Override
	public void runDraggedOperation(Point mouse) {
		if(createEdge) {
			indicatorEdgeCreation.setEnd(mouse);
			Collector.getWindow().repaint();
		}
	}

	@Override
	public void runReleasedOperation(Point mouse) {
		if(createEdge) {	
			Graph graph = Collector.getGraph();
			Vertex vertex = GraphFunctions.getHitVertex(graph, mouse);
			
			if(vertex != null) {
				destination = vertex;
				Edge edge = new Edge(source, destination, option.isDirected(), new EdgeDrawOption(edo));
				GraphFunctions.relaxEdge(edge);
				Collector.getSlides().addUndo();
				graph.addEdge(edge);	
			}

			createEdge = false;
			indicatorEdgeCreation.setVisible(createEdge);
			Collector.getWindow().repaint();
		}
		
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
		M_Paint.getInstance().updateValuesFromPanel();
	}


	@Override
	public Indicator getIndicator() {
		return indicatorEdgeCreation;
	}


	@Override
	public JPanel getOptionPanel() {
		return option;
	}

	@Override
	public String getDescription() {
		return "Modus Create";
	}


	public static M_Create getInstance() {
		return INSTANCE;
	}
}
