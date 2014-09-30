package ui;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import java.awt.FlowLayout;


public class JTools extends JPanel {

	private JComboBox<JToolPanel> dropdown = new JComboBox<JToolPanel>();
	private JToolPanel special = new JToolPanel("Empty");
	
	public JTools() {
		this.setLayout(new BorderLayout());
		this.setFocusable(false);
		
		JPanel border = new JPanel();
		border.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		border.add(dropdown);
		this.add(border, BorderLayout.WEST);
		
		dropdown.setFocusable(false);
		dropdown.addActionListener(new ListListener());
	}
	
	public void addBasicToolbar(JToolBar toolbar) {
		this.add(toolbar, BorderLayout.NORTH);
	}
	
	public void addSpecialPanel(JToolPanel panel) {
		dropdown.addItem(panel);
		dropdown.setSelectedItem(panel);
		special = panel;
		this.add(panel, BorderLayout.CENTER);
		revalidate();
		repaint();
	}
	
	private void setSpecialPanel(JToolPanel next) {
		if(next != special) {
			this.remove(special);
			special = next;
			this.add(next, BorderLayout.CENTER);	
			revalidate();
			repaint();
		}
	}
	
	private class ListListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JToolPanel panel = (JToolPanel) dropdown.getSelectedItem();		
			setSpecialPanel(panel);		
		}
	}
}
