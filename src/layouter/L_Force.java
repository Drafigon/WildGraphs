package layouter;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Point;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

/**
 * Places the graph vertices by using the force layout.
 * second Version
 * 
 * @author J. Wilde
 * @version 2.0
 * @since 14.05.2014
 *
 */
public class L_Force implements Layouter {
	
	private double springlength;
	private double damping;
	private int maxRounds;
	
	public L_Force(double springlength, double damping, int rounds) {
		this.springlength = springlength;	
		this.damping = damping;	
		this.maxRounds = rounds;
	}
	
	public Graph getCompleteLayoutedGraph(Graph graph) {
		// deep copy
		Graph oldGraph = new Graph(graph);	
		Graph result = new Graph(graph);
		
		for(int i = 0; i < maxRounds; i++) {			
			
			// run through all vertices
			Graph nextGraph = getNextLayoutedGraph(oldGraph);
			
			// if the graph does not change anymore	
			if(haveSameVertexPositions(oldGraph, nextGraph)){
				System.out.println("F2 Abbruch: " + i);
				i = maxRounds;  // break
			}
						
			oldGraph = nextGraph;
			result = new Graph(nextGraph);
		}				
		
		return result;
	}
	
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph nextGraph = new Graph(graph);
		
		// create empty list
		HashMap<Vertex, Point> forces = new HashMap<Vertex, Point>();
		
		// calculate vector
		for(Vertex currentVertex : nextGraph.getVertices()){
			// if it is not fixed
			if(!currentVertex.isFixed()) {
				Point forceVector = calculateForceVector(currentVertex, nextGraph.getVertices());
				forces.put(currentVertex, forceVector);
			}			
		}
		
		// place vertex
		for(Vertex vertex : forces.keySet()) {
			Point location = vertex.getLocation();
			Point forceVector = forces.get(vertex);
			location.x += forceVector.x * damping;  // Damping
			location.y += forceVector.y * damping;
			vertex.setLocation(location);
		}		
				
		return nextGraph;
	}
	
	private Point calculateForceVector(Vertex currentVertex, ArrayList<Vertex> otherVertices) {	
		// get neighbours
		Collection<Vertex> neigboursOfVertex = currentVertex.getNeighbours().values();
		ArrayList<Vertex> neighbours = new ArrayList<Vertex>();
		for(Vertex n : neigboursOfVertex) {
			neighbours.add(n);
		}
		
		// sum of attraction force
		Point sumAttractionForce = calculateSumOfAttractionForce(currentVertex, neighbours);
		// sum of repulsion force
		Point sumRepulsionForce = calculateSumOfRepulsionForce(currentVertex, otherVertices);
		
		// create result
		Point forceVector = new Point(0,0);
		forceVector.x += sumAttractionForce.x;
		forceVector.x += sumRepulsionForce.x;
		forceVector.y += sumAttractionForce.y;
		forceVector.y += sumRepulsionForce.y;
			
		return forceVector;
	}
	
	
	private Point calculateSumOfAttractionForce(Vertex currentVertex, ArrayList<Vertex> neigbours) {
		int positiveForceX = 0;
		int positiveForceY = 0;
		
		for(Vertex attractor : neigbours) {
			if(currentVertex != attractor) {
				Point positiveForce = calculateAttractionForce(currentVertex, attractor);
				positiveForceX += positiveForce.x;
				positiveForceY += positiveForce.y;
			}
		}
		
		return new Point(positiveForceX, positiveForceY);
	}
	
	private Point calculateSumOfRepulsionForce(Vertex currentVertex, ArrayList<Vertex> otherVertices) {
		int negativeForceX = 0;
		int negativeForceY = 0;
		
		for(Vertex repulsor : otherVertices) {
			if(currentVertex != repulsor) {
				Point negativeForce = calculateRepulsionForce(currentVertex, repulsor);
				negativeForceX += negativeForce.x;
				negativeForceY += negativeForce.y;
			}
		}
		
		return new Point(negativeForceX, negativeForceY);
	}
	
	
	private Point calculateAttractionForce(Vertex currentVertex, Vertex otherVertex) {	
		// Euclidean distance
		double deltaX = otherVertex.getLocation().x - currentVertex.getLocation().x;
		double deltaY = otherVertex.getLocation().y - currentVertex.getLocation().y;
		double distance = Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		
		if(distance > springlength) {			
			double force = distance - springlength;
			force *= 0.5;
			
			// X
			double cosX = deltaX / distance;
			double attractionForceX = force * cosX;
			
			// Y
			double cosY = deltaY / distance;
			double attractionForceY = force * cosY;
						
			return new Point((int) attractionForceX, (int) attractionForceY);
		}
		else {
			return new Point(0,0);
		}
		
	}
	
	private Point calculateRepulsionForce(Vertex currentVertex, Vertex otherVertex) {
		// Euclidean distance
		double deltaX = otherVertex.getLocation().x - currentVertex.getLocation().x;
		double deltaY = otherVertex.getLocation().y - currentVertex.getLocation().y;
		double distance =Math.sqrt(deltaX * deltaX + deltaY * deltaY);
		
		if(distance < springlength) {
			
			double force = springlength - distance;
			force *= 0.5;
			
			// X
			double cosX = deltaX / distance;
			double repulsionForceX = - force * cosX;
			
			// Y
			double cosY = deltaY / distance;
			double repulsionForceY = - force * cosY;
						
			return new Point((int) repulsionForceX, (int) repulsionForceY);
		}
		
		return new Point(0,0);
	}
	
	private boolean haveSameVertexPositions(Graph graphA, Graph graphB) {
		// they have the same vertex count
		if(graphA.getVertices().size() == graphB.getVertices().size()) {
			// run through A
			for(Vertex vA : graphA.getVertices()) {
				Point vAPosition = vA.getLocation();
				
				// is the position of vertex A equal to position of vertex B	
				boolean AeqB = false;
				
				// run through B
				for(Vertex vB : graphB.getVertices()) {
					Point vBPosition = vB.getLocation();
					
					if(vAPosition.x == vBPosition.x && vAPosition.y == vBPosition.y) {
						AeqB = true;
					}
				}
				
				// no vertex B for vertex A found
				if(AeqB == false) {
					return false;
				}
			}
			
			return true;
		}
		else {
			return false;
		}
	}
	
	@Override
	public String toString() {
		return "Layout Force";
	}
}
