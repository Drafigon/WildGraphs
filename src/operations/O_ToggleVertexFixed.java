package operations;

import data.Collector;
import graph.implementation.Vertex;

public class O_ToggleVertexFixed implements VertexOperation {

	private Vertex vertex;
	
	public O_ToggleVertexFixed(Vertex vertex) {
		super();
		this.vertex = vertex;
	}

	@Override
	public void run() {
		boolean isFixed = vertex.isFixed();
		Collector.getSlides().addUndo();
		vertex.setFixed(!isFixed);
	}

	@Override
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;
	}
}
