package operations;

import data.Collector;
import graph.implementation.Graph;

public class O_AddGraph implements Operation {

	private Graph graph;
	
	public O_AddGraph(Graph g) {
		graph = g;
	}
	
	@Override
	public void run() {		
		Collector.getSlides().addGraph(graph);
		Collector.getModi().active = true;	
		Collector.getWindow().updateAnimationImages();
		Collector.getWindow().repaintBoard();
		
	}
}
