package operations;

import graph.implementation.Graph;
import data.Collector;

public class O_ClearGraph implements Operation {

	@Override
	public void run() {
		Collector.getSlides().addUndo();
		
		Graph graph = Collector.getGraph();
		
		graph.removeAll();

		Collector.getWindow().repaintBoard();
	}
}
