package data;

import static org.junit.Assert.assertTrue;

import org.junit.Test;

public class QueueTest {

	@Test
	public void testQueueConstructor() {
		int negative = -10;
		int zero = 0;
		int one = 1;
		int positive = 20;
		
		Queue<Integer> n = new Queue<Integer>(negative);
		Queue<Integer> z = new Queue<Integer>(zero);
		Queue<Integer> o = new Queue<Integer>(one);
		Queue<Integer> p = new Queue<Integer>(positive);
		
		assertTrue(n.CAPACITY == 1);
		assertTrue(z.CAPACITY == 1);
		assertTrue(o.CAPACITY == 1);
		assertTrue(p.CAPACITY == positive);
		
		assertTrue(n.isEmpty());
		assertTrue(z.isEmpty());
		assertTrue(o.isEmpty());
		assertTrue(p.isEmpty());

	}
	
	@Test
	public void testAddElementsUnderCapacity() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);
		
		assertTrue(stack.isEmpty());
		
		int begin = 1;
		int end = 2;
		
		fill(begin, end, stack);
		
		assertTrue(stack.getSize() == 2);
	}
	
	@Test
	public void testAddElementsOverCapacity() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);

		int begin = 1;
		int end = 20;
		
		fill(begin, end, stack);
		
		assertTrue(stack.getSize() == capacity);
	}
	
	@Test
	public void testLastElements() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);	
		assertTrue(stack.getSize() == 0);
		
		int begin = 1;
		int end = 10;
		
		// Fill stack over
		// Add more elements => remove last ones
		fill(begin, end, stack);
		
		assertTrue(stack.getSize() == capacity);

		
		for(int i = end; i > end-capacity; i--) {
			int element = stack.getFirstElement();
			stack.removeFirstElement();
			assertTrue(element == i);
		}
		
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testAddNull() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);	
		stack.addFirstElement(null);
		assertTrue(stack.isEmpty());
	}
	
	@Test
	public void testRemoveFirstElement() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);
		
		fill(0, 4, stack);
		
		int firstElement;
		firstElement = stack.getFirstElement();
		assertTrue(firstElement == 4);
		
		stack.removeFirstElement();
		firstElement = stack.getFirstElement();
		assertTrue(firstElement == 3);
	}
	
	@Test
	public void testRemoveEmpty() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);		
		assertTrue(stack.isEmpty());		
		stack.removeFirstElement();
	}
	
	@Test
	public void testClear() {
		int capacity = 5;
		Queue<Integer> stack = new Queue<Integer>(capacity);
		assertTrue(stack.isEmpty());
		
		fill(0, 4, stack);
		assertTrue(stack.getSize() == capacity);
		
		stack.clear();
		assertTrue(stack.isEmpty());
	}
	
	private void fill(int begin, int end, Queue<Integer> stack) {
		// Fill stack
		for(int i = begin; i <= end; i++) {
			stack.addFirstElement(i);
		}
	}

}
