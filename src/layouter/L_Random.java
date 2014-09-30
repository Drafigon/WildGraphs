package layouter;

import graph.implementation.*;

import java.awt.Point;
import java.util.Random;


/**
 * Places the graph vertices inside the given area
 * at random places.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 03.04.2014
 *
 */
public class L_Random implements Layouter {

	private Point leftCorner;
	private int width;
	private int height;
	private Random dice = new Random();	
	
	public L_Random(Point leftCorner, Point rightCorner) {
		super();
		this.leftCorner = leftCorner;
		
		this.width = rightCorner.x - leftCorner.x;
		this.height = rightCorner.y - leftCorner.y;
	}

	
	public void layoutThisGraph(Graph graph) {
		getNextLayoutedGraph(graph);		
	}

	@Override
	public Graph getCompleteLayoutedGraph(Graph graph) {
		return getNextLayoutedGraph(graph);
	}

	@Override
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph next = new Graph(graph);
		// run through vertices
		for(Vertex v : next.getVertices()) {
			// only move unfixed vertices
			if(!v.isFixed()) {
				Point pos = new Point(0,0);	
				pos.x = dice.nextInt(width) + leftCorner.x;
				pos.y = dice.nextInt(height) + leftCorner.y;
				v.setLocation(pos);
			}
		}
		
		return next;
	}

	@Override
	public String toString() {
		return "Layout Random";
	}	
}
