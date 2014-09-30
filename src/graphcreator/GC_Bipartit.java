package graphcreator;

import graph.implementation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GC_Bipartit implements GraphCreator {

	private Random dice = new Random();
	private int vertexCount;
	
	private boolean directed;
	private int chanceOfEdge;
	
	public GC_Bipartit(int vertexCount, int chanceOfEdge, boolean directed) {
		super();
		this.vertexCount = vertexCount;
		this.directed = directed;
		this.chanceOfEdge = chanceOfEdge;
	}

	@Override
	public Graph createGraph() {
		Graph graph = new Graph();
		
		int vertexCountA = vertexCount / 2;
		int vertexCountB = vertexCount - vertexCountA;
	
		// create vertices
		List<Vertex> groupA = new ArrayList<Vertex>();
		List<Vertex> groupB = new ArrayList<Vertex>();	
		int count = 0;
		for(int i = 0; i < vertexCountA; i++){
			Vertex vertex = new Vertex("V"+(i+1));
			graph.addVertex(vertex);
			groupA.add(vertex);
			count++;
		}
		
		for(int i = 0; i < vertexCountB; i++){
			Vertex vertex = new Vertex("V"+(count+i+1));
			graph.addVertex(vertex);
			groupB.add(vertex);
		}
		
		addEdges(groupA, groupB, graph);
		
		if(directed) {
			addEdges(groupB, groupA, graph);
		}
		
		return graph;
	}

	
	private void addEdges(List<Vertex> groupA, List<Vertex> groupB, Graph graph) {
		//edges
		for(Vertex vertexA : groupA) {
			for(Vertex vertexB : groupB) {
				// roll
				int result = dice.nextInt(100) + 1; // 1 - 100
				
				// add a edge, if the chance is in range
				if(result <= chanceOfEdge){ 
					Edge edge = new Edge(vertexA, vertexB, directed);
					graph.addEdge(edge);
				}				
			}
		}
	}
}
