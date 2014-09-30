package modi.ui;

import graph.drawing.EDO_Normal;
import graph.drawing.EdgeDrawOption;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import ui.JIconButton;

public class JEDOSelection extends JPanel {

	private JEDOSlides pictures;
	private JCreate parent;
	private JIconButton button_add;
	private JIconButton button_duplicate;
	private JIconButton button_delete;
	
	public JEDOSelection(JCreate parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout(0, 0));
		
		pictures = new JEDOSlides(parent);
		JScrollPane scrollEdge = new JScrollPane(pictures);
		scrollEdge.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollEdge.getVerticalScrollBar().setUnitIncrement(16);
		this.add(scrollEdge);
			
		JPanel panel = createButtonBar();
		this.add(panel, BorderLayout.NORTH);
		
		addListener();
	}
	
	private JPanel createButtonBar() {
		JPanel panel = new JPanel();
		
		button_add = createButton("button_add.png", "button_add_hover.png");
		button_add.setToolTipText("Add new");
		panel.add(button_add);
		
		button_duplicate = createButton("button_duplicate.png", "button_duplicate_hover.png");	
		button_duplicate.setToolTipText("Duplicate selected");
		panel.add(button_duplicate);
		
		button_delete = createButton("button_remove.png", "button_remove_hover.png");
		button_delete.setToolTipText("Delete selected");
		panel.add(button_delete);
		
		return panel;
	}	
	
	private JIconButton createButton(String iconpath, String iconhoverpath) {
		JIconButton button = new JIconButton("");		
		button.setIcons(iconpath, iconhoverpath);
		button.setSizeToIconSize(5);
		return button;	
	}
	
	private void addListener() {
		ButtonListener buttonListener = new ButtonListener();
		button_add.addActionListener(buttonListener);
		button_delete.addActionListener(buttonListener);
		button_duplicate.addActionListener(buttonListener);
	}
	
	
	private class ButtonListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			
			if(button == button_add) {				
				add(new EDO_Normal());
			}
			
			if(button == button_duplicate) {
				duplicate();
			}
			
			if(button == button_delete) {
				remove();
			}			
		}
	}
	
	void add(EdgeDrawOption vdo) {
		pictures.addAfterSelected(vdo);		
		parent.updateSelection();
	}
	
	void duplicate() {
		pictures.duplicateSelected();
		parent.updateSelection();
	}
	
	void remove() {
		pictures.removeSelected();
		parent.updateSelection();	
	}
	
	EdgeDrawOption getSelected() {
		return pictures.getSelectedEDO();
	}
}
