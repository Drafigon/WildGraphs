package operations;

import graph.implementation.Graph;

public class O_AddEmptyGraphTab implements Operation {

	private int count;
	
	public O_AddEmptyGraphTab() {
		count = 0;
	}
	
	@Override
	public void run() {
		count++;
		Graph graph = new Graph();
		graph.setName("New " + count);
		new O_AddGraphTab(graph).run();

	}
}
