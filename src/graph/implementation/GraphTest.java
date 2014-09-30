package graph.implementation;

import java.awt.Point;

public class GraphTest {

	public static void main(String[] args) {
		Graph graph = new Graph();
		graph.setName("Original");
		Vertex v1 = new Vertex("V1");
		v1.setLocation(new Point(20,500));
		Vertex v2 = new Vertex("V2");
		Vertex v3 = new Vertex("V3");
		Edge e1 = new Edge(v1, v2, true);
		Edge e2 = new Edge(v2, v3, true);
		graph.addEdge(e1);
		graph.addEdge(e2);
		
		Graph copy = new Graph(graph);
		
		System.out.println("Original\n" + graph);
		System.out.println("Kopie\n" + copy);

	}

}
