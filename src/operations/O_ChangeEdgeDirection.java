package operations;

import data.Collector;
import graph.drawing.EdgeDrawOption;
import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

public class O_ChangeEdgeDirection implements EdgeOperation {

	private Edge edge;
	
	public O_ChangeEdgeDirection(Edge edge) {
		super();
		this.edge = edge;
	}

	@Override
	public void run() {
		if(edge.isDirected()){
			Collector.getSlides().addUndo();
			
			Vertex start = edge.getSource();
			Vertex end = edge.getDestination();
			
			Graph graph = Collector.getGraph();
			graph.removeEdge(edge);
			
			Edge newEdge = new Edge(end, start, edge.isDirected());
			newEdge.setCurvePoint(edge.getCurvePoint());
			newEdge.setDrawOption(new EdgeDrawOption(edge.getDrawOption()));
			newEdge.setName(edge.getName());
			newEdge.setWeight(edge.getWeight());
			graph.addEdge(newEdge);
	
			Collector.getWindow().repaintBoard();
		}
	}

	@Override
	public void setEdge(Edge edge) {
		this.edge = edge;		
	}
}
