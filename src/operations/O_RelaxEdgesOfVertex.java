package operations;

import utility.GraphFunctions;
import graph.implementation.Edge;
import graph.implementation.Vertex;
/**
 * Relaxes all edges to or from the vertex.
 * @param vertex : Vertex
 */
public class O_RelaxEdgesOfVertex implements Operation {

	private Vertex vertex;
	
	public O_RelaxEdgesOfVertex(Vertex vertex) {
		super();
		this.vertex = vertex;
	}

	@Override
	public void run() {
		for(Edge e : vertex.getNeighbours().keySet()) {
			GraphFunctions.relaxEdge(e);
		}
	}
}
