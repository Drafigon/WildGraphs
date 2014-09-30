package operations;

import graph.implementation.Graph;

public class O_AddEmptyGraph implements Operation {

	@Override
	public void run() {
		Graph graph = new Graph();
		new O_AddGraph(graph).run();
	}
}
