package data;

import static org.junit.Assert.assertTrue;
import graph.implementation.Graph;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StorageSlidesTest {

	
	
	private final int CAPACITY = 5;
	private StorageSlides empty = new StorageSlides(CAPACITY);
	private StorageSlides one = new StorageSlides(CAPACITY);
	private StorageSlides many = new StorageSlides(CAPACITY);
	private List<Graph> graphs = new ArrayList<Graph>();
	
	@Before
	public void init() {
		for (int i = 0; i < CAPACITY; i++) {
			Graph g = new Graph();
			g.setName("G " + i);
			graphs.add(g);
		}
		GraphStorage storage = GraphStorage.createFor(graphs.get(0), CAPACITY);
		one.addAfterSelectedAndUpdateIndex(storage);
		
		for(Graph g : graphs) {
			GraphStorage s = GraphStorage.createFor(g, CAPACITY);
			many.addAfterSelectedAndUpdateIndex(s);
		}
	}
	
	@Test
	public void testStorageSlidesInt() {
		StorageSlides s = new StorageSlides(-5);
		assertTrue(s.CAPACITY == 1);
		
		s = new StorageSlides(0);
		assertTrue(s.CAPACITY == 1);
		
		s = new StorageSlides(5);
		assertTrue(s.CAPACITY == 5);
	}

	@Test
	public void testStorageSlidesSlidesOfGraphStorageInt() {
		StorageSlides slides = new StorageSlides(CAPACITY*2);
		
		StorageSlides storage = new StorageSlides(slides);
		assertTrue(storage.getObjectCount() == 0);
		
		for(Graph g : graphs) {
			GraphStorage gs = GraphStorage.createFor(g, CAPACITY*2);
			slides.addAfterSelectedAndUpdateIndex(gs);
		}
		
		// Deep copy?
		assertTrue(storage.getObjectCount() == 0);
		
		storage = new StorageSlides(slides);
		assertTrue(storage.CAPACITY == storage.getSelectedObject().CAPACITY);
	}

	@Test
	public void testSetAllGraphs() {
		empty.setAllGraphs(null);
		setAllGraphsTo(empty);
		setAllGraphsTo(one);
		setAllGraphsTo(many);		
	}

	private void setAllGraphsTo(StorageSlides slides) {
		slides.setAllGraphs(graphs);
		assertTrue(slides.getObjectCount() == CAPACITY);
		List<Graph> list = slides.getAllGraphs();
		
		int index = 0;
		for(Graph g : list) {
			assertTrue(g == graphs.get(index));
			index++;
		}
	}
	
	@Test
	public void testGetAllGraphs() {
		List<Graph> list = empty.getAllGraphs();
		assertTrue(list.isEmpty());
		
		list = one.getAllGraphs();
		assertTrue(list.size() == 1);
		
		list = many.getAllGraphs();
		assertTrue(list.size() == CAPACITY);
		
	}

	@Test
	public void testGetSelectedGraph() {
		Graph g;
		g = empty.getSelectedGraph();
		assertTrue(g == null);
		
		g = one.getSelectedGraph();	
		assertTrue(g == graphs.get(0));
		
		g = many.getSelectedGraph();
		assertTrue(g == graphs.get(CAPACITY - 1));
	}

	@Test
	public void testSetSelectedGraph() {
		Graph g = graphs.get(2);
		empty.setSelectedGraph(g);
		assertTrue(empty.isEmpty());
		
		one.setSelectedGraph(g);
		assertTrue(one.getSelectedGraph() == g);
		
		many.setSelectedGraph(g);
		assertTrue(many.getSelectedGraph() == g);
	}

	@Test
	public void testAddGraph() {
		// Add null -> nothing
		empty.addGraph(null);
		assertTrue(empty.isEmpty());
		
		// Add one graph
		empty.addGraph(graphs.get(0));
		assertTrue(empty.getObjectCount() == 1);
		assertTrue(empty.getSelectedObject().getOriginalGraph() == graphs.get(0));
	}

	@Test
	public void testUndo() {
		empty.undo();
		one.undo();
		many.undo();
	}

	@Test
	public void testRedo() {
		empty.redo();
		one.redo();
		many.redo();
	}

	@Test
	public void testAddUndo() {
		empty.addUndo(); // Nothing should happen
	}

	public void testSetBackgroundColor() {
		assertTrue(empty.getBackgroundColor() == Color.white);
		empty.setBackgroundColor(Color.green);
		assertTrue(empty.getBackgroundColor() == Color.green);
		empty.setBackgroundColor(null);
		assertTrue(empty.getBackgroundColor() == Color.green);
	}
	
	@Test
	public void testToString() {
		StorageSlides s = new StorageSlides(20);
		s.addGraph(new Graph());
		System.out.println(s);
	}
}
