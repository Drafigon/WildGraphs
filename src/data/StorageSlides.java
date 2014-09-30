package data;

import graph.implementation.Graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class StorageSlides extends Slides<GraphStorage> {
	
	private static int count = 1;
	private final int number;
	
	public final int CAPACITY;
	private Color backgroundColor = Color.white;
	
	
	public StorageSlides(int capacity) {
		super();
		CAPACITY = getValidCapacity(capacity);
		number = count;
		count++;
	}

	public StorageSlides(StorageSlides slides) {
		super(slides);
		CAPACITY = slides.CAPACITY;
		number = count;
		count++;
	}
	
	private int getValidCapacity(int capacity) {
		return Math.max(1, capacity);
	}
	
	public void addUndo() {
		if(!isEmpty()) {
			GraphStorage gs = this.getSelectedObject();
			gs.addUndo();
		}
	}

	public void undo() {
		if(!isEmpty()) {
			GraphStorage gs = this.getSelectedObject();
			gs.undo();
		}
	}

	public void redo() {
		if(!isEmpty()) {
			GraphStorage gs = this.getSelectedObject();
			gs.redo();
		}
	}

	public void setSelectedGraph(Graph graph) {
		if(!isEmpty()) {
			GraphStorage s = this.getSelectedObject();
			s.setOriginalGraph(graph);
		}		
	}

	public void setAllGraphs(Collection<Graph> graphs) {
		if(graphs != null) {
			List<GraphStorage> list = new ArrayList<GraphStorage>();
			for(Graph g : graphs) {
				GraphStorage s = GraphStorage.createFor(g, CAPACITY);
				list.add(s);
			}
			
			this.setAllObjects(list);
		}
	}
	
	public Graph getSelectedGraph() {
		if(isEmpty()) {
			return null;
		}
		else {
			GraphStorage s = this.getSelectedObject();
			return s.getOriginalGraph();
		}	
	}

	public List<Graph> getAllGraphs() {
		List<Graph> list = new ArrayList<Graph>();
		
		for(GraphStorage gs : getAllObjects()) {
			list.add(gs.getOriginalGraph());
		}
		
		return list;
	}
	
	public void addGraph(Graph graph) {
		if(graph != null) {
			GraphStorage storage = GraphStorage.createFor(graph, CAPACITY);
			this.addAfterSelectedAndUpdateIndex(storage);
		}
	}
	
	
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		if(backgroundColor != null) 
			this.backgroundColor = backgroundColor;	
	}

	@Override
	public String toString() {
		String text = "<StorageSlides ["+number+"]>: ";
		text += getObjectCount() + "/" + CAPACITY + "\n";
		for(GraphStorage gs : getAllObjects()) {
			text += gs;
		}
		
		return text;
	}
}
