package data;

import graph.implementation.Graph;

public final class GraphStorage {
	public final int CAPACITY;
	private Graph original;
	private Queue<Graph> undo;
	private Queue<Graph> redo;
	
	static GraphStorage createFor(Graph original, int capacity) {
		if(original != null) {
			return new GraphStorage(original, capacity);
		}
		else 
			return null;
	}
	
	private GraphStorage(Graph original, int capacity) {
		this.original = original;
		this.undo = new Queue<Graph>(capacity);
		this.redo = new Queue<Graph>(capacity);
		this.CAPACITY = undo.CAPACITY;
	}

	public void undo() {
		if(!undo.isEmpty()) {
			// Copy original in redo
			Graph redoGraph = new Graph(original);
			redo.addFirstElement(redoGraph);
			
			// Copy first graph of undo to original
			original = undo.getFirstElement();
			
			// delete reference (pop)
			undo.removeFirstElement();
		}
	}
	
	public void redo() {
		if(!redo.isEmpty()) {
			// Copy original in undo
			Graph undoGraph = new Graph(original);
			undo.addFirstElement(undoGraph);
			
			original = redo.getFirstElement();
			redo.removeFirstElement();
		}
	}
	
	// run before the original will change
	public void addUndo() {
		Graph graph = new Graph(original);
		if(graph != null) {
			undo.addFirstElement(graph);
			redo.clear();
		}
	}

	public Graph getOriginalGraph() {
		return original;
	}
	
	public void setOriginalGraph(Graph graph) {
		if(graph != null) 
			original = graph;
	}

	@Override
	public String toString() {
		String t = "";
		
		t += "GraphStorage : " + original + "\n";
		t += "Undo: " + undo + "\n";
		t += "Redo: " + redo + "\n";
		
		return t;
	}
	
	
}