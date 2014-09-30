package operations;

import data.Collector;
import graph.implementation.Vertex;

public class O_DeleteVertex implements VertexOperation {

	private Vertex vertex;
	
	public O_DeleteVertex(Vertex v) {
		this.vertex = v;
	}
	
	@Override
	public void run() {
		Collector.getSlides().addUndo();
		Collector.getGraph().removeVertex(vertex);
	}

	@Override
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;		
	}
}
