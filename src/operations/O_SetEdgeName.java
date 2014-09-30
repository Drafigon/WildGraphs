package operations;

import graph.implementation.Edge;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import data.Collector;

public class O_SetEdgeName implements EdgeOperation {

	private Edge edge;

	public O_SetEdgeName(Edge edge) {
		super();
		this.edge = edge;

	}
	
	@Override
	public void run() {
		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		
		JLabel lblIfTheName = new JLabel("If the name is empty, the weight is shown.");
		lblIfTheName.setAlignmentX(Component.CENTER_ALIGNMENT);
		myPanel.add(lblIfTheName);

		JPanel panel = new JPanel();		
		JLabel label = new JLabel("Name:");
		panel.add(label);
		JTextField name = new JTextField(5);
		panel.add(name);
		myPanel.add(panel);

		Object[] options = { "OK", "Cancel" }; 
		int result = JOptionPane.showOptionDialog(
				null, myPanel, "Enter a name", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				name);
		if (result == JOptionPane.OK_OPTION) {  
			Collector.getSlides().addUndo();
			edge.setName(name.getText());     
		}		
	}
	
	@Override
	public void setEdge(Edge edge) {
		this.edge = edge;
	}
}
