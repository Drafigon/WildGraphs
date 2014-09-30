package modi.ui;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.ScrollPaneConstants;

import ui.JIconButton;
import external.ImageLoader;
import graph.drawing.VDO_StandardText;
import graph.drawing.VertexDrawOption;

public class JVDOSelection extends JPanel {
	private JVDOSlides pictures;
	private JCreate parent;
	private JIconButton button_add;
	private JIconButton button_duplicate;
	private JIconButton button_delete;
	
	public JVDOSelection(JCreate parent) {
		this.parent = parent;
		
		this.setLayout(new BorderLayout(0, 0));
		
		JPanel panel = createButtonBar();
		this.add(panel, BorderLayout.NORTH);
		
		pictures = new JVDOSlides(parent);		
		JScrollPane scrollVertex = new JScrollPane(pictures);	
		scrollVertex.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
		scrollVertex.setPreferredSize(new Dimension(50, 100));
		scrollVertex.getVerticalScrollBar().setUnitIncrement(16);
		this.add(scrollVertex, BorderLayout.CENTER);
		
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
				add(new VDO_StandardText());
			}
			
			if(button == button_duplicate) {
				duplicate();
			}
			
			if(button == button_delete) {
				remove();
			}			
		}
	}
	
	void add(VertexDrawOption vdo) {
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
	
	VertexDrawOption getSelected() {
		return pictures.getSelectedVDO();
	}
}
