package layouter;

import graph.implementation.Graph;

import java.awt.Point;

import utility.GraphFunctions;
import utility.GraphFunctions.Bounds;

/**
 * Places the graph at the middle of the given area.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 03.04.2014
 *
 */
public class L_InMiddle implements Layouter {

	private Point center;
	
	public L_InMiddle(int width, int height) {
		this.center = new Point(width/2, height/2);
	}

	@Override
	public Graph getCompleteLayoutedGraph(Graph graph) {
		return getNextLayoutedGraph(graph);
	}

	@Override
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph next = new Graph(graph);
		
		Bounds bounds = GraphFunctions.getGraphBounds(next);
		
		int minX = bounds.x;
		int minY = bounds.y;
		
		// calculate values
		int width = Math.abs(bounds.weight);
		int height = Math.abs(bounds.height);
		int graphCenterX = minX + width/2;
		int graphCenterY = minY + height/2;
		
		// vector
		int diffX = center.x - graphCenterX; 
		int diffY = center.y - graphCenterY;
		
		GraphFunctions.moveGraphBy(next, new Point(diffX, diffY));
		
		return next;
	}

	@Override
	public String toString() {
		return "Layout InMiddle";
	}
}
