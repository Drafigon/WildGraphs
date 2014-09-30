package graphcreator;

import graph.implementation.*;

import java.awt.Point;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GC_Tree implements GraphCreator {
	
	private Random dice = new Random();
	private int vertexCount;
	private int layerCount;
	private boolean directed;
	
	public GC_Tree(int vertexCount, boolean directed) {
		super();
		this.vertexCount = Math.max(vertexCount, 2);
		this.layerCount = Math.max(vertexCount/2, 2);
		this.directed = directed;
	}

	@Override
	public Graph createGraph() {
		Graph graph = new Graph();
		
		for(int i = 0; i < vertexCount; i++) {
			Vertex v = new Vertex("V"+(i+1));
			graph.addVertex(v);
		}
		
		Map<Integer, ArrayList<Vertex>> layer = new HashMap<Integer, ArrayList<Vertex>>();
		ArrayList<Vertex> vertices = graph.getVertices();
		
		
		System.out.println("Layer Count: " + layerCount);
		
		int index = 0;
		// In each layer is one vertex
		while(index < layerCount) {
			Vertex v = vertices.get(index);
			v.setLocation(new Point(index, index));
			ArrayList<Vertex> list = new ArrayList<Vertex>();
			list.add(v);
			layer.put(index, list);
			index++;
		}
		// random layers for the other vertices
		while(index < vertices.size()) {
			Vertex v = vertices.get(index);
			v.setLocation(new Point(index, index));
			int randomLayer = dice.nextInt(layerCount-1)+1;
			List<Vertex> list = layer.get(randomLayer);
			list.add(v);
			index++;
		}
		
		for(int i = layerCount-1; i > 0; i--) {
			// Last Layer
			ArrayList<Vertex> lastLayer = layer.get(i);
			ArrayList<Vertex> parentLayer = layer.get(i-1);
			for(Vertex v : lastLayer) {
				int parentVertex = dice.nextInt(parentLayer.size());
				Vertex parent = parentLayer.get(parentVertex);
				
				Edge edge = new Edge(parent, v, directed);
				graph.addEdge(edge);
			}
		}
		
		return graph;
	}

}
