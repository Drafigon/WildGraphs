package layouter;

import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;
import java.util.ArrayList;

/**
 * Places the graph vertices on a circle.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 03.04.2014
 *
 */
public class L_Circle implements Layouter {

	private Point center;
	private double radius;
	
	public L_Circle(Point center, double radius) {
		this.center = center;
		this.radius = radius;
	}
	
	@Override
	public Graph getCompleteLayoutedGraph(Graph graph) {
		return getNextLayoutedGraph(graph);
	}


	@Override
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph next = new Graph(graph);
		ArrayList<Vertex> vertices = next.getVertices();
		
		// subtract the number of fixed vertices
		int fixedVertices = 0;
		for(Vertex v : vertices) {
			if(v.isFixed()) {
				fixedVertices++;
			}
		}
		
		int freeVertices = vertices.size() - fixedVertices;
		
		// the angle between two vertices on the circle
		double angleStep = 2 * Math.PI / freeVertices;
		double alpha = - 0.5 * Math.PI;

		for(Vertex v : vertices) {
			if(!v.isFixed()) {

				int addX = (int) (Math.cos(alpha) * radius);
				int addY = (int) (Math.sin(alpha) * radius);
				
				alpha += angleStep;
				
				// location on circle for next vertex
				int posX = center.x + addX;
				int posY = center.y + addY;
				
				v.setLocation(new Point(posX, posY));
			}
		}
		
		return next;
	}
	
	@Override
	public String toString() {
		return "Layout Circle";
	}
}
