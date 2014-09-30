package operations;

import graph.implementation.Graph;
import graph.implementation.Vertex;

import java.awt.Component;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import utility.GraphFunctions;
import data.Collector;

public class O_SetVertexName implements VertexOperation {

	private Vertex vertex;
	
	public O_SetVertexName(Vertex vertex) {
		super();
		this.vertex = vertex;
	}

	@Override
	public void run() {

		JPanel myPanel = new JPanel();
		myPanel.setLayout(new BoxLayout(myPanel, BoxLayout.Y_AXIS));

		JLabel lblIfTheName = new JLabel("The name cannot be empty.");
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
			Graph graph = Collector.getGraph();
			String text = name.getText().replace(" ", "");
			boolean valid = !text.equals("");
			valid = valid && !GraphFunctions.hasVertexWithName(graph, name.getText());
			
			if(valid) {
				Collector.getSlides().addUndo();
				vertex.setName(name.getText()); 
			}
		}	
	}

	@Override
	public void setVertex(Vertex vertex) {
		this.vertex = vertex;		
	}
}
