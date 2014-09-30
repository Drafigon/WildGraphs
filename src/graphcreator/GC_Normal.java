package graphcreator;

import graph.implementation.*;

import java.util.Random;


/**
 * Use to create a standard graph without nooses.
 * 
 * @author J. Wilde
 * @version 1.0
 * @since 02.04.2014
 */
public class GC_Normal implements GraphCreator {

	private Random dice = new Random();;
	private int chanceOfEdge;
	private int vertexCount;
	private boolean directed;
	
	public GC_Normal(int vertexCount, int chance, boolean directed){
		this.vertexCount = vertexCount;
		this.chanceOfEdge = chance%101; // 0 - 100
		this.directed = directed;
	}

	@Override
	public Graph createGraph() {
		Graph graph = new Graph();
		
		// create vertices
		for(int i = 0; i < vertexCount; i++){
			Vertex vertex = new Vertex("V"+(i+1));
			graph.addVertex(vertex);
		}
		
		// create edges
		for(Vertex vA : graph.getVertices()) {
			for(Vertex vB : graph.getVertices()) {
				// no noose
				if(vA != vB) {
					// roll
					int result = dice.nextInt(100) + 1; // 1 - 100
					
					// add a edge, if the chance is in range
					if(result <= chanceOfEdge){ 
						Edge edge = new Edge(vA, vB, directed);
						graph.addEdge(edge);
					}
				}
			}
		}
		
		return graph;
	}

	@Override
	public String toString() {
		return "GraphCreator Normal: " + chanceOfEdge + " % chance of edge, " + directed;
	}	
}
