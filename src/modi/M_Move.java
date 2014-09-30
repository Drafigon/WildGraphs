package modi;

import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;
import java.util.List;

import javax.swing.JPanel;

import modi.indicators.I_Nothing;
import modi.indicators.Indicator;
import modi.ui.JMove;
import utility.GraphFunctions;
import data.Collector;

public class M_Move extends Modus {

	private static final M_Move INSTANCE = new M_Move();	
	
	private Vertex vertex;
	private Edge edge;
	private Point start;
	private JMove option;
	private boolean dragVertex;
	private boolean dragEdgePoint;
	private boolean dragGraph;
	
	private M_Move() {
		option = new JMove();
		vertex = null;
		edge = null;
		dragVertex = false;
		dragEdgePoint = false;
		dragGraph = false;
		start = new Point(0,0);
	}
	
	
	@Override
	public void runPressedOperation(Point mouse) {
		start = mouse;
		
		Graph g = Collector.getGraph();
		vertex = GraphFunctions.getHitVertex(g, start);
		edge = GraphFunctions.getHitEdge(g, start);
		
		Collector.getSlides().addUndo();
		
		if(vertex != null) {
			if(!vertex.isFixed()) {
				dragVertex = true;
			}
		}	
		
		else if(edge != null) {
			dragEdgePoint = true;
		}	
		
		else {
			dragGraph = true;
		}
	}

	
	
	@Override
	public void runDraggedOperation(Point mouse) {
		Optimizer optimizer = Collector.getModi().optimizer;	
		
		Point newLocation = optimizer.getOptimizedPoint(mouse);

		if(dragVertex) {
			vertex.setLocation(newLocation);
			
			if(option.isRelax()) {
				// Relax all Edges of the vertex
				for(Edge e : vertex.getNeighbours().keySet()) {
					GraphFunctions.relaxEdge(e);
				}
			}
		}
		if(dragEdgePoint) {
			edge.setCurvePoint(newLocation);
		}
		if(dragGraph) {		
			int diffX = mouse.x - start.x;
			int diffY = mouse.y - start.y;
			Point moveBy =  new Point(diffX, diffY);
			
			if(option.isMoveAllSlides()) {
				List<Graph> graphlist = Collector.getSlides().getAllGraphs();
				for(Graph g : graphlist) {
					GraphFunctions.moveGraphBy(g, moveBy);
				}
			}
			else {
				Graph graph = Collector.getGraph();
				GraphFunctions.moveGraphBy(graph, moveBy);
			}
			
			start = mouse;
		}
		
		Collector.getWindow().repaint();
	}

	@Override
	public void runReleasedOperation(Point mouse) {
		dragVertex = false;
		dragEdgePoint = false;
		dragGraph = false;
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
	}

	@Override
	public JPanel getOptionPanel() {
		return option;
	}

	@Override
	public Indicator getIndicator() {
		return new I_Nothing();
	}

	@Override
	public String getDescription() {
		return "Modus Move";
	}

	public static M_Move getInstance() {
		return INSTANCE;
	}
}
