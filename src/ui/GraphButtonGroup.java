package ui;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import operations.O_CloseGraphTab;
import operations.O_SetSlides;
import operations.Operation;

public class GraphButtonGroup {
	
	private ArrayList<JGraphButton> group = new ArrayList<JGraphButton>();
	private LeftClickListener leftclick = new LeftClickListener();
	private CloseListener closer = new CloseListener();
	private int count = 0;
	
	public GraphButtonGroup() {
		
	}
	
	void addButton(JGraphButton button) {
		group.add(button);
		count++;
		button.addActionListener(leftclick);
		button.addMouseListener(closer);
		setFocusTo(button);
	}
	
	JGraphButton getSelectedButton() {
		for(JGraphButton b : group) {
			if(b.isSelected()) {
				return b;
			}
		}
		
		return null;
	}
	
	void removeButton(JGraphButton button) {
		if(count > 0) {
			group.remove(button);
			count--;
			setFocusToLast();
		}
	}
	
	int getCount() {
		return count;
	}
	
	private void setFocusTo(JGraphButton button) {
		for(JGraphButton b : group) {
			b.setSelected(false);
			
			if(b == button) {
				b.setSelected(true);
			}
		}
	}
	
	private void setFocusToLast() {
		if(!group.isEmpty()) {
			for(JGraphButton b : group) {
				b.setSelected(false);
			}
			
			JGraphButton button = group.get(group.size() - 1);
			button.setSelected(true);
			
			// To update the slides
			ActionEvent event = new ActionEvent(button, ActionEvent.ACTION_FIRST, "");
			leftclick.actionPerformed(event);
		}
	}
	
	private class LeftClickListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {	
			JGraphButton button = (JGraphButton) e.getSource();
			new O_SetSlides(button.SLIDES).run();
			setFocusTo(button);
		}		
	}
	
	private class CloseListener extends MouseAdapter {

		private Operation operation = new O_CloseGraphTab();
		
		@Override
		public void mousePressed(MouseEvent m) {
			if(m.getButton() == MouseEvent.BUTTON2) {
				JGraphButton button = (JGraphButton) m.getSource();
				setFocusTo(button);
				operation.run();			
			}
		}		
	}
}
