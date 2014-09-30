package data;

import static org.junit.Assert.assertTrue;
import graph.implementation.Graph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Vector;

import org.junit.Test;

public class SlidesTest extends Slides<Graph> {
	
	@Test
	public void testIndexFunctionsOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		
		// right constructor?
		assertTrue(empty.isEmpty());
		
		
		empty.selectFirst();
		assertTrue(empty.isEmpty());
		
		empty.selectLast();
		assertTrue(empty.isEmpty());
		
		empty.selectNext();
		assertTrue(empty.isEmpty());
		
		empty.selectPrevious();
		assertTrue(empty.isEmpty());
		
		empty.selectIndex(-3);
		assertTrue(empty.isEmpty());
		
		empty.selectIndex(0);
		assertTrue(empty.isEmpty());
		
		empty.selectIndex(3);
		assertTrue(empty.isEmpty());
		
	}
	
	@Test
	public void testIndexFunctionsOnSingle() {
		Slides<Graph> single = new Slides<Graph>();
		Graph graph = new Graph();
		single.addAfterSelectedAndUpdateIndex(graph);
		
		// right constructor?
		assertTrue(single.getSelectedObject() == graph);
		
		
		single.selectFirst();
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectLast();
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectNext();
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectPrevious();
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectIndex(-3);
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectIndex(0);
		assertTrue(single.getSelectedObject() == graph);
		
		single.selectIndex(3);
		assertTrue(single.getSelectedObject() == graph);
	}
	
	@Test
	public void testIndexFunctionsOnMulti() {
		Slides<Graph> multi = new Slides<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		Graph g3 = new Graph();
		multi.addAfterSelectedAndUpdateIndex(g1);
		multi.addAfterSelectedAndUpdateIndex(g2);
		multi.addAfterSelectedAndUpdateIndex(g3);
		
		// right constructor?
		assertTrue(multi.getSelectedObject() == g3);
		
		
		multi.selectFirst();
		assertTrue(multi.getSelectedObject() == g1);
		
		multi.selectNext();
		assertTrue(multi.getSelectedObject() == g2);
		
		multi.selectLast();
		assertTrue(multi.getSelectedObject() == g3);
			
		multi.selectPrevious();
		assertTrue(multi.getSelectedObject() == g2);
		
		multi.selectIndex(-3);
		assertTrue(multi.getSelectedObject() == g1);
		
		multi.selectIndex(0);
		assertTrue(multi.getSelectedObject() == g1);
		
		multi.selectIndex(3);
		assertTrue(multi.getSelectedObject() == g3);
	}
	
		
	@Test
	public void testGraphAddNull() {
		Slides<Graph> slides = new Slides<Graph>();
		
		// null will be ignored
		slides.addAfterSelectedAndUpdateIndex(null);
		assertTrue(slides.isEmpty());
	}
	
	@Test
	public void testGraphAddFunctionsOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		Graph g1 = new Graph();
		
		empty.addAfterSelectedAndUpdateIndex(g1);
		assertTrue(empty.getSelectedObject() == g1);		
	}
	
	@Test
	public void testGraphAddFunctionsOnSingle() {
		Slides<Graph> single = new Slides<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		
		single.addAfterSelectedAndUpdateIndex(g1);		
		single.addAfterSelectedAndUpdateIndex(g2);
		
		assertTrue(single.getSelectedObject() == g2);		
	}

	@Test
	public void testSetAllGraphsOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		Collection<Graph> collection = new ArrayList<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		collection.add(g1);
		collection.add(g2);
		
		// create error
		empty.setAllObjects(null);
		assertTrue(empty.isEmpty());

		empty.setAllObjects(new ArrayList<Graph>());
		assertTrue(empty.isEmpty());
		
		empty.setAllObjects(new Vector<Graph>());
		assertTrue(empty.isEmpty());
		
		// normal
		empty.setAllObjects(collection);
		List<Graph> graphs = empty.getAllObjects();
		
		assertTrue(collection.containsAll(graphs));
		assertTrue(graphs.containsAll(collection));	
	}

	@Test
	public void testRemoveSelectedGraphOnEmpty() {
		Slides<Graph> slides = new Slides<Graph>();
		
		// nothing should happen
		slides.removeSelectedAndUpdateIndex();
		assertTrue(slides.isEmpty());
	}
	
	@Test
	public void testRemoveSelectedGraphOnSingle() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g = new Graph();
		slides.addAfterSelectedAndUpdateIndex(g);
		
		slides.removeSelectedAndUpdateIndex();
		assertTrue(slides.isEmpty());
	}
	
	@Test
	public void testRemoveSelectedGraphOnMulti() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		slides.addAfterSelectedAndUpdateIndex(g1);
		slides.addAfterSelectedAndUpdateIndex(g2);
		
		assertTrue(slides.getSelectedObject() == g2);
		// Removes
		slides.removeSelectedAndUpdateIndex();
		assertTrue(slides.getSelectedObject() == g1);
		assertTrue(slides.getObjectCount() == 1);
	}

	@Test
	public void testSetGraphOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		Graph g = new Graph();
		empty.setSelectedObjectTo(g);
		assertTrue(empty.isEmpty());
	}
	
	@Test
	public void testSetGraphOnSingle() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g = new Graph();
		slides.addAfterSelectedAndUpdateIndex(g);
		
		Graph g2 = new Graph();
		slides.setSelectedObjectTo(g2);
		assertTrue(slides.getSelectedObject() == g2);
		assertTrue(slides.getObjectCount() == 1);
	}	

	@Test
	public void testGetAllGraphsOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		List<Graph> list = empty.getAllObjects();
		assertTrue(list.isEmpty());
	}
	
	@Test
	public void testGetAllGraphsOnMulti() {
		List<Graph> graphs = new ArrayList<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		Graph g3 = new Graph();
		graphs.add(g1);
		graphs.add(g2);
		graphs.add(g3);
		
		Slides<Graph> slides = new Slides<Graph>();
		slides.setAllObjects(graphs);
		
		List<Graph> currentList = slides.getAllObjects();
		assertTrue(graphs != currentList);	
		
		for(int i = 0; i < currentList.size(); i++) {
			assertTrue(currentList.get(i) == currentList.get(i));
		}
	}

	@Test
	public void testGetSelectedGraphOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		Graph g = empty.getSelectedObject();
		assertTrue(g == null);
	}
	
	@Test
	public void testGetSelectedGraphOnSingle() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g = new Graph();
		slides.addAfterSelectedAndUpdateIndex(g);
		
		Graph g2 = slides.getSelectedObject();
		assertTrue(g == g2);
	}

	
	@Test
	public void testSelectOnEmpty() {
		Slides<Graph> empty = new Slides<Graph>();
		Graph g1 = new Graph();
		
		empty.selectObject(g1);
		assertTrue(empty.getSelectedObject() == null);
		
	}
	
	@Test
	public void testSelectOnMulti() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g1 = new Graph();
		Graph g2 = new Graph();
		slides.addAfterSelectedAndUpdateIndex(g1);
		slides.addAfterSelectedAndUpdateIndex(g2);
		
		assertTrue(slides.getSelectedObject() == g2);
		
		slides.selectObject(g1);
		assertTrue(slides.getSelectedObject() == g1);
		
		
		Graph g3 = new Graph();
		slides.selectObject(g2);
		slides.selectObject(g3);
		assertTrue(slides.getSelectedObject() == g2);
		
	}

	
	@Test
	public void testAddFunction() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g1 = new Graph();
		g1.setName("G1");
		Graph g2 = new Graph();
		g2.setName("G2");
		slides.addAfterSelectedAndUpdateIndex(g1);
		slides.addAfterSelectedAndUpdateIndex(g2);
		
		
		slides.selectFirst();
		assertTrue(slides.getSelectedObject() == g1);
		slides.selectLast();
		assertTrue(slides.getSelectedObject() == g2);
	}
	
	@Test
	public void testMoveBackwards() {
		Slides<Graph> slides = new Slides<Graph>();
		for(int i = 0; i < 4; i++) {
			Graph g = new Graph();
			g.setName("G_" + i);
			slides.addAfterSelectedAndUpdateIndex(g);
		}

		slides.selectPrevious();
		assertTrue(slides.getSelectedIndex() == 2);
		assertTrue(slides.getSelectedObject().getName().equals("G_2"));
		
		slides.moveSelectedBackwards();
		assertTrue(slides.getSelectedIndex() == 1);
		assertTrue(slides.getSelectedObject().getName().equals("G_2"));
		
		slides.selectNext();
		assertTrue(slides.getSelectedIndex() == 2);
		assertTrue(slides.getSelectedObject().getName().equals("G_1"));
		
		slides.selectFirst();
		slides.moveSelectedBackwards();
		assertTrue(slides.getSelectedIndex() == 0);
		assertTrue(slides.getSelectedObject().getName().equals("G_0"));
	}
	
	@Test
	public void testMoveForwards() {
		Slides<Graph> slides = new Slides<Graph>();
		for(int i = 0; i < 4; i++) {
			Graph g = new Graph();
			g.setName("G_" + i);
			slides.addAfterSelectedAndUpdateIndex(g);
		}
		
		assertTrue(slides.getSelectedIndex() == slides.getObjectCount()-1);
		slides.moveSelectedForwards();
		assertTrue(slides.getSelectedIndex() == slides.getObjectCount()-1);
		
		slides.selectPrevious();
		assertTrue(slides.getSelectedIndex() == 2);
		assertTrue(slides.getSelectedObject().getName().equals("G_2"));
		
		slides.moveSelectedForwards();
		assertTrue(slides.getSelectedIndex() == 3);
		assertTrue(slides.getSelectedObject().getName().equals("G_2"));
		
		slides.selectPrevious();
		assertTrue(slides.getSelectedIndex() == 2);
		assertTrue(slides.getSelectedObject().getName().equals("G_3"));
		
	}
	
	@Test
	public void testMovingTo() {
		Slides<Graph> slides = new Slides<Graph>();
		for(int i = 0; i < 4; i++) {
			Graph g = new Graph();
			g.setName("G_" + i);
			slides.addAfterSelectedAndUpdateIndex(g);
		}
		
		assertTrue(slides.getSelectedIndex() == 3);
		slides.moveSelectedTo(0);
		assertTrue(slides.getSelectedIndex() == 0);
		assertTrue(slides.getSelectedObject().getName().equals("G_3"));
		System.out.println(slides);
		
		slides.moveSelectedTo(-2);
		assertTrue(slides.getSelectedIndex() == 0);
		assertTrue(slides.getSelectedObject().getName().equals("G_3"));
		
		slides.moveSelectedTo(10);
		assertTrue(slides.getSelectedIndex() == 3);
		assertTrue(slides.getSelectedObject().getName().equals("G_3"));
	}
	
	@Test
	public void testToString() {
		Slides<Graph> slides = new Slides<Graph>();
		Graph g1 = new Graph();
		g1.setName("G1");
		Graph g2 = new Graph();
		g2.setName("G2");
		slides.addAfterSelectedAndUpdateIndex(g1);
		slides.addAfterSelectedAndUpdateIndex(g2);
	}
}
