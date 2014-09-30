package data;

import static org.junit.Assert.assertTrue;
import graph.implementation.Graph;

import org.junit.Before;
import org.junit.Test;

public class GraphStorageTest {

	private Graph original = new Graph();
	private Graph[] graphs = new Graph[5];
	
	@Before
	public void init() {		
		for(int i = 0; i < graphs.length; i++) {
			graphs[i] = new Graph();
		}
	}
	
	@Test
	public void testGraphStorage() {
		GraphStorage s = GraphStorage.createFor(original, 5);
		assertTrue(s.getOriginalGraph() == original);
		
		s = GraphStorage.createFor(null, 5);
		assertTrue(s == null);
	}
	
	@Test
	public void testAddUndoGraph() {
		GraphStorage s = GraphStorage.createFor(original, 5);		
		s.addUndo();
		
		assertTrue(s.getOriginalGraph() == original);
		
		s.undo();
		
		assertTrue(s.getOriginalGraph() != original);	
	}
	
	@Test
	public void testUndo() {
		GraphStorage s = GraphStorage.createFor(original, 5);
		s.undo();  // Nothing should happen
		
		s.addUndo();
		s.addUndo();
		
		assertTrue(s.getOriginalGraph() == original);
		
		s.undo();
		
		Graph next = s.getOriginalGraph();
		
		assertTrue(next != original);
		
		s.undo();
		
		assertTrue(s.getOriginalGraph() != next);
	}

	@Test
	public void testRedo() {
		GraphStorage s = GraphStorage.createFor(original, 5);
		s.redo();  // Nothing should happen
		s.addUndo();
		
		assertTrue(s.getOriginalGraph() == original);
		
		s.undo();
		
		Graph next = s.getOriginalGraph();	
		
		assertTrue(next != original);
		
		s.redo();
		
		assertTrue(s.getOriginalGraph() != next);
	}

	

}
