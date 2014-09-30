package operations;

import data.Collector;
import graph.implementation.Graph;

public class O_DuplicateSelectedGraph implements Operation {

	@Override
	public void run() {
		Graph original = Collector.getSlides().getSelectedGraph();
		Graph copy = new Graph(original);
		new O_AddGraph(copy).run();
	}

}
