package operations;

import graph.implementation.Graph;
import layouter.Layouter;
import data.Collector;

public class O_LayoutAnimation implements Operation {

	private Layouter layouter;
	
	public O_LayoutAnimation(Layouter layouter) {
		this.layouter = layouter;
	}
	
	@Override
	public void run() {
		Graph graph = Collector.getGraph();

	}

}
