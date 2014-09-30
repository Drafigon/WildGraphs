package operations;

import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;
import data.Collector;

public class O_DeselectAllVerticesAndEdges implements Operation {

	@Override
	public void run() {
		Graph graph = Collector.getGraph();
		
		for(Vertex v : graph.getVertices()) {
			v.setMouseover(false);
		}
		
		for(Edge e : graph.getEdges()) {
			e.setMouseOver(false);
		}
		
		Collector.getWindow().repaintBoard();
	}

}
