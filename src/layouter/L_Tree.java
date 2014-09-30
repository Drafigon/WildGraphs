package layouter;

import graph.implementation.*;

import java.awt.Point;
import java.util.HashMap;
import java.util.Map;

import utility.GraphFunctions;

public class L_Tree implements Layouter {

	private Point rootLocation;
	private int layerHeight;
	private int vertexDistanceX;
	
	public L_Tree(Point rootLocation, int layerHeight, int vertexDistanceX) {
		super();
		this.rootLocation = rootLocation;
		this.layerHeight = layerHeight;
		this.vertexDistanceX = Math.abs(vertexDistanceX);
	}

	@Override
	public Graph getNextLayoutedGraph(Graph graph) {
		Graph next = new Graph(graph);
		
		if(GraphFunctions.isTree(next)) {
			int distance = Integer.MAX_VALUE;
			Vertex root = next.getVertices().get(0);
			for(Vertex v : next.getVertices()) {
				int width = Math.abs(rootLocation.x - v.getLocation().x);
				int height = Math.abs(rootLocation.y - v.getLocation().y);
				if(width + height < distance) {
					distance = width + height;
					root = v;
				}
			}
			
			Map<Vertex, Attribute> marked = new HashMap<Vertex, Attribute>();
			for(Vertex v : next.getVertices()) {
				marked.put(v, new Attribute(0,0));
			}
			
			mark(marked, root, 1);

			placeVertex(marked, root, 0);	
			
			Point vector = new Point(0,0);
			Point currentRoot = root.getLocation();
			vector.x = rootLocation.x - currentRoot.x;
			vector.y = rootLocation.y - currentRoot.y;
			
			GraphFunctions.moveGraphBy(next, vector);
			
			return next;
		}
		
		return graph;
	}
	
	private class Attribute {
		public final int LAYER;
		public final int NEEDED_SPACE;
		public Attribute(int layer, int neededSpace) {
			super();
			LAYER = layer;
			NEEDED_SPACE = neededSpace;
		}	
	}
	
	private void mark(Map<Vertex, Attribute> marked, Vertex vertex, int layer) {
		if(marked.get(vertex).LAYER == 0) {
			int neededSpace = 0;
			// Had to be zero, because this space is added during the for-loop
			marked.put(vertex, new Attribute(layer, neededSpace));
			
			for(Vertex n : vertex.getNeighbours().values()) {
				if(marked.get(n).LAYER < layer) {
					mark(marked, n, layer+1);	
					neededSpace += marked.get(n).NEEDED_SPACE;
				}
			}
		
			neededSpace = Math.max(neededSpace, 1);
				
			marked.put(vertex, new Attribute(layer, neededSpace));
		}
	}
	
	private void placeVertex(final Map<Vertex, Attribute> marked, Vertex vertex, int startX) {
		int layer = marked.get(vertex).LAYER;
		int neededSpace = marked.get(vertex).NEEDED_SPACE - 1;
		int y = layerHeight * layer;
		int x = startX + (vertexDistanceX * neededSpace) / 2;
		
		for(Vertex n : vertex.getNeighbours().values()) {
			if(marked.get(n).LAYER > layer) {
				placeVertex(marked, n, startX);
				startX += (marked.get(n).NEEDED_SPACE) * vertexDistanceX;
			}
		}
		
		vertex.setLocation(new Point(x, y));
	}
	
	@Override
	public Graph getCompleteLayoutedGraph(Graph graph) {
		return getNextLayoutedGraph(graph);
	}

}
