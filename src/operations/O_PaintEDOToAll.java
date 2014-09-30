package operations;

import graph.drawing.EdgeDrawOption;
import graph.implementation.Edge;
import graph.implementation.Graph;
import data.Collector;

public class O_PaintEDOToAll implements Operation {

	private EdgeDrawOption edo;
	
	public O_PaintEDOToAll(EdgeDrawOption vdo) {
		this.edo = vdo;
	}
	
	@Override
	public void run() {
		Collector.getSlides().addUndo();
		Graph graph = Collector.getGraph();
		
		for(Edge e : graph.getEdges()) {
			e.setDrawOption(new EdgeDrawOption(edo));
		}
		
		Collector.getWindow().repaintBoard();
	}
}
