package data;

import java.util.ArrayList;
import java.util.List;

public final class Queue<E> {
	public final int CAPACITY;
	private List<E> list = new ArrayList<E>();
	
	public Queue(int capacity) {
		super();
		this.CAPACITY = getValidCapacity(capacity);
	}
	
	private int getValidCapacity(int capacity) {
		if(capacity <= 0) 
			return 1;
		else 
			return capacity;
	}
	
	public E getFirstElement() {
		if(list.isEmpty()) 
			return null;
		else
			return list.get(0);
	}
	
	public void removeFirstElement() {
		if(!list.isEmpty()) {
			list.remove(0);
		}
	}
	
	public void addFirstElement(E element) {
		if(element != null) {
			list.add(0, element);
		}
		
		if(list.size() > CAPACITY) {
			list.remove(list.size() - 1);
		}			
	}
	
	public int getSize() {
		return list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}
	
	public void clear() {
		list.clear();
	}

	@Override
	public String toString() {
		String text = "";
		
		text += "Queue: " + list.size() + "/" +CAPACITY + "\n";
		for(E e : list) {
			text += e + ",";
		}
		
		text += "\n";
		
		return text;
	}	
}