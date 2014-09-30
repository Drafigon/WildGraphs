package operations;



import data.Collector;
import graph.implementation.Edge;
import utility.GraphFunctions;
/**
 * Set the curve point to an ideal position.
 * If source and destination are equal, it would be a line.
 * If not, it is a noose on top of the vertex.
 */
public class O_RelaxEdge implements EdgeOperation {

	private Edge edge;
	
	public O_RelaxEdge(Edge edge) {
		super();
		this.edge = edge;
	}

	@Override
	public void run() {
		Collector.getSlides().addUndo();
		GraphFunctions.relaxEdge(edge);	
	}

	@Override
	public void setEdge(Edge edge) {
		this.edge = edge;
	}
}
