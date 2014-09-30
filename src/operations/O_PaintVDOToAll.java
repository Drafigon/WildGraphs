package operations;

import graph.drawing.VertexDrawOption;
import graph.implementation.Graph;
import graph.implementation.Vertex;
import data.Collector;

public class O_PaintVDOToAll implements Operation {

	private VertexDrawOption vdo;
	
	public O_PaintVDOToAll(VertexDrawOption vdo) {
		this.vdo = vdo;
	}
	
	@Override
	public void run() {
		Collector.getSlides().addUndo();
		Graph graph = Collector.getGraph();
		
		for(Vertex v : graph.getVertices()) {
			v.setDrawOption(new VertexDrawOption(vdo));
		}
		
		Collector.getWindow().repaintBoard();

	}

}
