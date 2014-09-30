package ui;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;

import operations.O_AddEmptyGraphTab;

public class JGraphBar extends JPanel {

	private GraphButtonGroup group;
	
	JGraphBar() {
		setBorder(new EmptyBorder(5, 5, 0, 0));
		FlowLayout layout = new FlowLayout();
		this.setLayout(layout);
		layout.setVgap(0);
		layout.setHgap(0);
		layout.setAlignment(FlowLayout.LEADING);
		
		group = new GraphButtonGroup();
		
		
		JIconButton newTab = new JIconButton("");
		newTab.setIcons("button_add.png", "button_add_hover.png");
		newTab.setSizeToIconSize(10);
		newTab.setToolTipText("Add new");
	
		newTab.addActionListener(new ActionListener() {		
			@Override
			public void actionPerformed(ActionEvent e) {
				new O_AddEmptyGraphTab().run();	
			}
		});
		
		this.add(newTab);
	}
	
	public void addGraphButton(JGraphButton button) {
		this.add(button);
		group.addButton(button);
		
	}
	
	public void removeSelectedGraphButton() {
		if(group.getCount() > 0) {
			JGraphButton button = group.getSelectedButton();
			group.removeButton(button);
			this.remove(button);
			this.revalidate();
		}
	}
	
	public void setTextOfSelectedButton(String name) {
		JToggleButton button = group.getSelectedButton();
		button.setText(name);
		this.repaint();
	}
	
	public boolean isEmpty() {
		return group.getCount() == 0;
	}
}
