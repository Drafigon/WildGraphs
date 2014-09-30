package graph.implementation;

import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.util.ArrayList;
import java.util.HashMap;

import javax.swing.JOptionPane;



public class Graph implements Drawable {
	
	private static int count = 1;
	private int id;
	
	private String name;
	private ArrayList<Vertex> vertices;
	private ArrayList<Edge> edges;
	private boolean directed;
	
	// ******************
	// 	  CONSTRUCTORS
	// ******************
	
	public Graph() {
		init("Graph", false);
	}
	
	public Graph(Graph graph) {
		// Name
		init(graph.name, graph.directed);
		// temp map
		HashMap<Vertex, Vertex> cloneMap = new HashMap<Vertex, Vertex>();
		
		// First add all edges and their source and destination vertices
		// run through all edges
		for(Edge e : graph.edges) {
			Vertex source = e.getSource();
			Vertex destination = e.getDestination();
			
			// if source not in map yet, create new vertex
			if(!cloneMap.containsKey(source)) {
				Vertex a = new Vertex(source);
				cloneMap.put(source, a);
			}
			
			if(!cloneMap.containsKey(destination)) {
				Vertex b = new Vertex(destination);
				cloneMap.put(destination, b);
			}
			
			// create new edge
			Edge edge = new Edge(cloneMap.get(source), cloneMap.get(destination), e.isDirected(), e.getDrawOption());
			edge.setWeight(e.getWeight());
			edge.setCurvePoint(e.getCurvePoint());
			this.addEdge(edge);
		}
		
		// Second add vertices with no edges
		for(Vertex v : graph.vertices) {
			if(!cloneMap.containsKey(v)) {
				Vertex vertex = new Vertex(v);	
				cloneMap.put(v, vertex);
				this.addVertex(vertex);
			}
		}
	}
	
	private void init(String name, boolean directed) {
		count++;
		this.id = count;
		this.name = name;
		this.directed = directed;
		vertices = new ArrayList<Vertex>();
		edges = new ArrayList<Edge>();
		
	}

	//*******************
	//	 DRAW FUNCTION
	//*******************
	@Override
	public void draw(Graphics2D g2) {
		// Anti-Aliasing
		g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		
		for(Edge e : edges) {
			Edge ed = (Edge) e;
			ed.draw(g2);
		}
		
		for(Vertex v : vertices) {
			Vertex vd = (Vertex) v;
			vd.draw(g2);
		}
	}
	
	// ******************
	// 		ADDER
	// ******************
	
	public void addVertexAll(ArrayList<Vertex> vertices) {
		for(Vertex vertex : vertices) {
			addVertex(vertex);
		}
	}
	
	public void addVertex(Vertex vertex) {
		if(!vertices.contains(vertex)) {
			vertices.add(vertex);
		}
	}
	
	public void addEdgeAll(ArrayList<Edge> edges) {
		for(Edge edge : edges) {
			addEdge(edge);
		}
	}
	
	public void addEdge(Edge edge) {		
		// If this edge is the first one
		if(edges.isEmpty()) {
			this.directed = edge.isDirected();
		}
				
		boolean isInside = false;	
		for(Edge e : edges) {
			// Directed
			// if an edge should be added again
			if(edge.getSource() == e.getSource() &&
				edge.getDestination() == e.getDestination()) {
				isInside = true;
			}
			
			if(!directed) {
				// Undirected
				if(edge.getSource() == e.getDestination() &&
					edge.getDestination() == e.getSource()) {
					isInside = true;
				}			
			}
		}
		
		if(!isInside) {		
			// if the graph is (un)directed and the edge isn't 
			if(edge.isDirected() == this.directed) {
				addThisNewEdge((Edge) edge);
			}
			else {
				JOptionPane.showMessageDialog(null, "A graph can only be directed or undirected.");
			}
		}
	}
	
	private void addThisNewEdge(Edge edge) {
		// Add Edge to Vertices
		Vertex source = edge.getSource();
		Vertex destination = edge.getDestination();

		if(edge.isDirected()) {
			source.addSuccessor(edge, destination);
			destination.addPredecessor(edge, source);
		}
		else {
			source.addNeighbour(edge, destination);
			destination.addNeighbour(edge, source);
		}
		
		// Add Vertices if they are not in the list	
		addVertex(source);
		addVertex(destination);
			
		edges.add(edge);		
	}
	
	// ******************
	// 		REMOVER
	// ******************
	
	public void removeVertex(Vertex vertex) {
		removeAllEdgesInSuccessors(vertex);
		removeAllEdgesInPredecessors(vertex);
		vertices.remove(vertex);
	}
	
	private void removeAllEdgesInSuccessors(Vertex vertex) {
		// can't run through a Map, so i had to do it like this
		for(Edge e : vertex.getSuccessors().keySet()) {
			Vertex destination = e.getDestination();
			destination.removePredecessor(e);
			System.out.println("Delete " + e);
			edges.remove(e);
		}
	}

	private void removeAllEdgesInPredecessors(Vertex vertex) {
		// can't run through a Map, so i had to do it like this
		for(Edge e : vertex.getPredecessors().keySet()) {
			Vertex source = e.getSource();
			source.removeSuccessor(e);
			System.out.println("Delete " + e);
			edges.remove(e);
		}
	}

	public void removeEdge(Edge edge) {
		Vertex source = edge.getSource();
		Vertex destination = edge.getDestination();
		
		if(edge.isDirected()) {
			source.removeSuccessor(edge);
			destination.removePredecessor(edge);
		}
		else {
			source.removeNeighbour(edge);
			destination.removeNeighbour(edge);
		}
		
		edges.remove(edge);
	}
	
	public void removeAll() {
		vertices.clear();
		edges.clear();
	}
	
		
	
	//*******************
	//		SETTER
	//*******************

	
	public void setName(String name) {
		this.name = name;
	}
	
	// ******************
	// 		GETTER
	// ******************
	
	public ArrayList<Vertex> getVertices() {
		ArrayList<Vertex> list = new ArrayList<Vertex>();
		for(Vertex v: vertices) {
			list.add(v);
		}	
		return list;
	}
	
	public ArrayList<Edge> getEdges() {
		ArrayList<Edge> list = new ArrayList<Edge>();
		for(Edge v: edges) {
			list.add(v);
		}
		return list;
	}
	
	public String getName() {
		return name;
	}
	
	public boolean isDirected() {
		return directed;
	}
	
		
	@Override
	public String toString() {
		String t = "";
		t += "Graph <" + name + ">: " + id + "\n";
		
		t += "Directed: " + directed + "\n";
		t += "Vertices: ";
		
		// Vertices
		for(Vertex v : vertices) {
			t += v.getName() + "(" + v.getLocation().x + "," + v.getLocation().y + ")["+v.id+"], ";
		}
		
		t += "\nEdges: ";
		
		// Edges
		for(Edge e : edges) {
			t += "(" + e.getSource().getName() + ", " + e.getDestination().getName() + ")[" + e.getWeight() + "], ";
		}
		
		t += "\n";
		
		
		return t;
	}	
}
