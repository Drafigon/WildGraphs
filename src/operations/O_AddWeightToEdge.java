package operations;

import data.Collector;
import graph.implementation.Edge;

public class O_AddWeightToEdge implements EdgeOperation {

	private Edge edge;
	private int weight;
	
	public O_AddWeightToEdge(Edge edge, int weight) {
		super();
		this.edge = edge;
		this.weight = weight;
	}

	@Override
	public void run() {
		Collector.getSlides().addUndo();
		int currentWeight = edge.getWeight();
		edge.setWeight(currentWeight + weight);
	}

	@Override
	public void setEdge(Edge edge) {
		this.edge = edge;
	}
}
