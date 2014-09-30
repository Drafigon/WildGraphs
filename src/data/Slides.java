package data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class Slides<E> {
	private List<E> list = new ArrayList<E>();
	private int selectionIndex = -1;
	
	public Slides() {};
	
	public Slides(Slides<E> slides) {
		this.selectionIndex = slides.selectionIndex;
		
		for(E element : slides.list) {
			this.list.add(element);
		}
	}
	
	public void addAfterSelectedAndUpdateIndex(E object) {
		if(object != null) {
			list.add(selectionIndex + 1, object);
			selectNext();
		}
	}
	
	public void removeSelectedAndUpdateIndex() {
		if(!isEmpty()) {
			list.remove(selectionIndex);		
			selectPrevious();
		}
	}
	
	public void setSelectedObjectTo(E object) {
		if(!isEmpty()) {
			list.set(selectionIndex, object);
		}
	}
	
	public E getSelectedObject() {
		if(isEmpty()) {
			return null;
		}
		else {
			return list.get(selectionIndex);
		}
	}

	public void setAllObjects(Collection<E> collection) {
		if(collection != null) {			
			list.clear();
			
			for(E object : collection) {
				list.add(object);
			}
						
			selectIndex(0);
		}
	}
	
	public List<E> getAllObjects() {
		List<E> array = new ArrayList<E>();
		for (E object : list) {
			array.add(object);
		}
		return array;
	}
	
	
	public int getSelectedIndex() {
		return selectionIndex;
	}
	
	public void moveSelectedBackwards() {
		if(!isEmpty() && selectionIndex > 0) {
			E previous = list.get(selectionIndex - 1);
			E current = list.get(selectionIndex);
			list.set(selectionIndex - 1, current);
			list.set(selectionIndex, previous);
			
			selectPrevious();
		}
	}
	
	public void moveSelectedForwards() {
		if(!isEmpty() && selectionIndex < list.size() - 1) {
			E next = list.get(selectionIndex + 1);
			E current = list.get(selectionIndex);
			list.set(selectionIndex + 1, current);
			list.set(selectionIndex, next);
			
			selectNext();
		}
	}
	
	public void moveSelectedTo(int index) {			
		index = getIndexInRange(index);
		
		if(!isEmpty() && selectionIndex != index) {
			E current = list.get(selectionIndex);
			list.remove(current);
			list.add(index, current);
			
			selectIndex(index);
		}
	}

	public void selectObject(E object) {
		int index = list.indexOf(object);
		
		// Not found == -1
		if(index != -1)
			selectIndex(list.indexOf(object));	
	}

	public void selectNext() {
		selectIndex(selectionIndex + 1);
	}
	
	public void selectPrevious() {
		selectIndex(selectionIndex - 1);
	}
	
	public void selectFirst() {
		selectIndex(0);
	}
	
	public void selectLast() {
		selectIndex(list.size() - 1);
	}
	
	public void selectIndex(int index) {
		if(isEmpty()) {
			setIndexToEmpty();
		}
		else {
			selectionIndex = getIndexInRange(index);
		}
	}
	
	private void setIndexToEmpty() {
		selectionIndex = -1;
	}
	
	private int getIndexInRange(int index) {
		// min: 0, max: size - 1
		if(isUnderRange(index)) {
			return 0;
		}
		else if(isOverRange(index)) {
			return list.size() - 1;
		}
		else {
			return index;
		}
	}
	

	private boolean isUnderRange(int index) {
		return index < 0;
	}
	
	private boolean isOverRange(int index) {
		return index >= list.size();
	}
	
	public boolean isEmpty() {
		return list.isEmpty();
	}	
	
	public int getObjectCount() {
		return list.size();
	}

	@Override
	public String toString() {
		String text = "Slides: \n";
		int count = 1;
		for(E object : list) {
			text += "[" + count + "] " + object.toString() + "\n";
			count ++;
		}
		return text;
	}	
}
