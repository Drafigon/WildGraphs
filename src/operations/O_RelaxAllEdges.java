package operations;

import utility.GraphFunctions;
import graph.implementation.Edge;
import graph.implementation.Graph;
/**
 * Relaxes all edges of the graph.
 * 
 */
public class O_RelaxAllEdges implements Operation {

	private Graph graph;

	public O_RelaxAllEdges(Graph graph) {
		super();
		this.graph = graph;
	}

	@Override
	public void run() {
		for(Edge e : graph.getEdges()) {
			GraphFunctions.relaxEdge(e);
		}
	}
}
