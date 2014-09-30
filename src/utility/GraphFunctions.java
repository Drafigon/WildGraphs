package utility;

import graph.implementation.Edge;
import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

public abstract class GraphFunctions {
	
	private GraphFunctions(){};
	
	public static Edge getHitEdge(Graph graph, Point location) {		
		for(Edge e : graph.getEdges()) {
			boolean isInside = GraphFunctions.isInsideEdge(e, location);
			if(isInside) {
				return e;
			}
		}
		
		return null;
	}

	/**
	 * Returns true if the given point
	 * is inside the (clickable) edge shape.
	 * An edge can be clicked around the curve point.
	 * 
	 * @param mouse : Point
	 * @return true/false
	 */
	public static boolean isInsideEdge(Edge edge, Point mouse) {
		Point curvePoint = edge.getCurvePoint();
		int diffX = Math.abs(mouse.x - curvePoint.x);
		int diffY = Math.abs(mouse.y - curvePoint.y);
		int radius = 30;
		
		if(diffX < radius && diffY < radius) {
			return true;
		}
		
		return false;
	}
	
	public static Vertex getHitVertex(Graph graph, Point location) {		
		for(Vertex v : graph.getVertices()) {
			boolean isInside = GraphFunctions.isInsideVertex(v, location);
			if(isInside) {
				return v;
			}
		}
		
		return null;
	}

	/**
	 * Returns true if the given point
	 * is inside the (clickable) vertex shape.
	 * 
	 * @param mouse : Point
	 * @return true/false
	 */
	public static boolean isInsideVertex(Vertex vertex, Point mouse) {
		Point location = vertex.getLocation();
		int diffX = Math.abs(mouse.x - location.x);
		int diffY = Math.abs(mouse.y - location.y);
		int size = vertex.getDrawOption().getSize();
		
		if(diffX < size * 0.6 && diffY < size * 0.6) {			
			return true;			
		}
		return false;
	}
	
	public static boolean hasVertexWithName(Graph graph, String name) {
		for(Vertex v : graph.getVertices()) {
			if(v.getName().equals(name)) {
				return true;
			}	
		}
		return false;
	}

	/**
	 * Moves the complete graph by the given vector.
	 * 
	 * @param vector : Point
	 */
	public static void moveGraphBy(Graph graph, Point vector) {
		
		for(Vertex v : graph.getVertices()) {
			Point currentPos = v.getLocation();			
			int newX = currentPos.x + vector.x;
			int newY = currentPos.y + vector.y;
			v.setLocation(new Point(newX, newY));
		}	
		
		for(Edge e : graph.getEdges()) {
			Point curvePoint = e.getCurvePoint();
			int newX = curvePoint.x + vector.x;
			int newY = curvePoint.y + vector.y;
			e.setCurvePoint(new Point(newX, newY));
		}
	}
	
	public static void relaxEdge(Edge edge) {
		Vertex source = edge.getSource();
		Vertex destination = edge.getDestination();
		Point start = source.getLocation();
		Point end = destination.getLocation();
		
		Point curvePoint;
		// if source and destination are equal -> noose
		if(source == destination) {
			curvePoint = new Point(end.x, end.y - destination.getDrawOption().getSize() - 20);
		}
		// else line
		else {			
			int deltaX = end.x - start.x;
			int deltaY = end.y - start.y;	
			curvePoint = new Point(start.x + deltaX/2, start.y + deltaY/2);
		}
		edge.setCurvePoint(curvePoint);
	}

	public static boolean isTree(Graph graph) {
		boolean correctEdgeCount = graph.getEdges().size() == (graph.getVertices().size() - 1);
		// For Subgraphs which aren't connected
		boolean isBipartit = getBipartitParts(graph) != null;
		
		boolean noFreeVertices = true;
		for(Vertex v : graph.getVertices()) {
			if(v.getNeighbours().isEmpty()) {
				noFreeVertices = false;
			}
		}
		
		return correctEdgeCount && isBipartit && noFreeVertices;
	}
	
	public static BipartitParts getBipartitParts(Graph graph) {
		// init lists
		HashMap<Vertex, Integer> marker = new HashMap<Vertex, Integer>();
		ArrayList<Vertex> temp = new ArrayList<Vertex>();
		final int UNMARKED = 2;
		final int MARKED_A = 0;
		final int MARKED_B = 1;
		
		for(Vertex v : graph.getVertices()) {
			marker.put(v, UNMARKED);
		}
		
		// algorithm
		for(Vertex firstVertex : marker.keySet()) {
			if(marker.get(firstVertex) == UNMARKED) {
				marker.put(firstVertex, MARKED_A);
				temp.add(firstVertex);
				
				while(!temp.isEmpty()) {
					Vertex currentVertex = temp.get(0);
					temp.remove(0);
					
					Collection<Vertex> neighbours = currentVertex.getNeighbours().values();
					for(Vertex n : neighbours) {
						if(marker.get(n) == UNMARKED) {
							int newMarker = MARKED_B - marker.get(currentVertex);
							marker.put(n, newMarker);
							temp.add(n);
						}
						else if(marker.get(n) == marker.get(currentVertex)) {
							return null;
						}
					}
				}
			}
		}
		
		BipartitParts bipartit = new BipartitParts();
		
		for(Vertex v : marker.keySet()) {
			if(marker.get(v) == MARKED_A) {
				bipartit.partA.add(v);
			}
			if(marker.get(v) == MARKED_B) {
				bipartit.partB.add(v);
			}
		}
		
		return bipartit;
	}
	
	public static class BipartitParts {
		public ArrayList<Vertex> partA = new ArrayList<Vertex>();
		public ArrayList<Vertex> partB = new ArrayList<Vertex>();
	}
	
	public static Image getSizedGraphImage(Graph graph, int width, int height, Color background) {
		// create basic image
		BufferedImage image = getGraphImage(graph, background);
				
		final double IMAGE_WIDTH = image.getWidth();
		final double IMAGE_HEIGHT = image.getHeight();
		final double MAX_IMAGE_WIDTH = width;
		final double MAX_IMAGE_HEIGHT = height;
		
		// if necessary reduce image
		if(IMAGE_WIDTH > MAX_IMAGE_WIDTH || IMAGE_HEIGHT > MAX_IMAGE_HEIGHT) {
			
			// factors
			int littleWidth;
			int littleHeight;
				
			
			if(MAX_IMAGE_WIDTH / IMAGE_WIDTH <= MAX_IMAGE_HEIGHT / IMAGE_HEIGHT) {
				final double factor = IMAGE_HEIGHT / IMAGE_WIDTH;
				
				littleWidth = (int) MAX_IMAGE_WIDTH;
				littleHeight =  (int) (MAX_IMAGE_WIDTH * factor);
			}
			else {
				double factor = IMAGE_WIDTH / IMAGE_HEIGHT;
				
				littleWidth = (int) (MAX_IMAGE_HEIGHT * factor);
				littleHeight = (int) MAX_IMAGE_HEIGHT;		
			}
					
			// scale image
			Image little = image.getScaledInstance(littleWidth, littleHeight, BufferedImage.SCALE_SMOOTH);
	
			return little;
		}
		else {
			return image;
		}
	}
	
 	public static BufferedImage getGraphImage(Graph graph, Color background) {		
		// get borders
		Bounds bounds = getGraphBounds(graph);	
		int minX = bounds.x;
		int minY = bounds.y;
		int moveBackX = 0;
		int moveBackY = 0;
		
		// correction: move graph in positive value range
		if(minX < 0) {
			moveGraphBy(graph, new Point(-minX,0));
			moveBackX = minX;
			minX = 0;
			
		}
		if(minY < 0) {
			moveGraphBy(graph, new Point(0,-minY));
			moveBackY = minY;
			minY = 0;
		}
			
		int maxX = minX + bounds.weight;
		int maxY = minY + bounds.height;
		
		
		// Creating image
		BufferedImage image = new BufferedImage(maxX, maxY, BufferedImage.TYPE_INT_RGB);
		Graphics2D g2 = (Graphics2D) image.getGraphics();
		g2.setBackground(background);
		g2.clearRect(0, 0, maxX, maxY);
		graph.draw(g2);
		g2.dispose();
		
		BufferedImage little = image.getSubimage(minX, minY, maxX - minX, maxY - minY);
		
		moveGraphBy(graph, new Point(moveBackX, moveBackY));
		
		return little;
		
	}

	public static Bounds getGraphBounds(Graph graph) {	
		int minX = Integer.MAX_VALUE;		
		int maxX = Integer.MIN_VALUE;
		int minY = Integer.MAX_VALUE;
		int maxY = Integer.MIN_VALUE;
		
		if(graph.getVertices().isEmpty()) {
			return new Bounds(0,0,1,1);
		}
		
		else {
			for(Vertex v : graph.getVertices()) {
				Point pos = v.getLocation();
				int size = v.getDrawOption().getSize();
				if(pos.x - size < minX) {
					minX = pos.x - size;
				}
				if(pos.x + size > maxX) {
					maxX = pos.x + size;
				}
				if(pos.y - size < minY) {
					minY = pos.y - size;
				}
				if(pos.y + size > maxY) {
					maxY = pos.y + size;
				}
			}	
			for(Edge e : graph.getEdges()) {
				Point pos = e.getCurvePoint();
				int size = 50;
				if(pos.x - size < minX) {
					minX = pos.x - size;
				}
				if(pos.x + size > maxX) {
					maxX = pos.x + size;
				}
				if(pos.y - size < minY) {
					minY = pos.y - size;
				}
				if(pos.y + size > maxY) {
					maxY = pos.y + size;
				}
			}
		}		
		
		int width = maxX - minX;
		int height = maxY - minY;
		
		Bounds bounds = new Bounds(minX, minY, width, height);
 	
		return bounds;
	}
	
	public static class Bounds {
		public final int x;
		public final int y;
		public final int weight;
		public final int height;
		
		public Bounds(int x, int y, int weight, int height) {
			this.x = x;
			this.y = y;
			this.weight = weight;
			this.height = height;
		}
	}
}
