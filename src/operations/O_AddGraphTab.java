package operations;

import ui.JGraphButton;
import data.Collector;
import graph.implementation.Graph;

public class O_AddGraphTab implements Operation {

	private Graph graph;
	
	public O_AddGraphTab(Graph graph) {
		this.graph = graph;
	}
	
	@Override
	public void run() {		
		JGraphButton button = new JGraphButton();
		Collector.getWindow().addGraphTab(button);
		
		Collector.setSlides(button.SLIDES);
		new O_AddGraph(graph).run();
	}
}
