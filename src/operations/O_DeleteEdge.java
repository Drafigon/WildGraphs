package operations;

import data.Collector;
import graph.implementation.Edge;


public class O_DeleteEdge implements EdgeOperation {

	private Edge edge;
	
	public O_DeleteEdge(Edge edge) {
		super();
		this.edge = edge;
	}

	@Override
	public void run() {
		Collector.getSlides().addUndo();
		Collector.getGraph().removeEdge(edge);
	}

	@Override
	public void setEdge(Edge edge) {
		this.edge = edge;
	}

}
