package layouter;

import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;

import utility.GraphFunctions;
import utility.GraphFunctions.BipartitParts;

public class L_Bipartit implements Layouter {

	private Point leftCorner;
	private Point rightCorner;
	
	public L_Bipartit(Point leftCorner, Point rightCorner) {
		super();
		this.leftCorner = leftCorner;
		this.rightCorner = rightCorner;
	}

	@Override
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph next = new Graph(graph);
		BipartitParts parts = GraphFunctions.getBipartitParts(next);
		
		if(parts != null) {
			int y = leftCorner.y;
			int x = leftCorner.x;
			int sizeA = Math.max(parts.partA.size() - 1, 1);
			int step = (rightCorner.x - leftCorner.x) / sizeA;
			
			for(Vertex v : parts.partA) {
				Point location = new Point(x, y);
				v.setLocation(location);
				x += step;
			}
			
			y = rightCorner.y;
			x = leftCorner.x;
			int sizeB = Math.max(parts.partB.size() - 1, 1);
			step = (rightCorner.x - leftCorner.x) / sizeB;
			
			for(Vertex v : parts.partB) {
				Point location = new Point(x, y);
				v.setLocation(location);
				x += step;
			}
			
			return next;
		}	
		return graph;	
	}

	@Override
	public Graph getCompleteLayoutedGraph(Graph graph) {
		return getNextLayoutedGraph(graph);
	}

}
