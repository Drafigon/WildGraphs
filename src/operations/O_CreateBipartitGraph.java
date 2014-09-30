package operations;

import graph.implementation.Graph;
import graphcreator.GC_Bipartit;
import graphcreator.GraphCreator;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Point;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;

import layouter.L_Bipartit;
import layouter.Layouter;
import data.Collector;

public class O_CreateBipartitGraph implements Operation {

	private JPanel panel;
	private JSpinner countField;
	private JSlider chanceField;
	private JCheckBox check_directed;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton radioGraph;
	
	public O_CreateBipartitGraph() {
		panel = createPanel();
	}
	
	@Override
	public void run() {
		
		Object[] options = { "Create", "Cancel" }; 
		
		int optionsResult = JOptionPane.showOptionDialog(
				null, 
				panel, 
				"Create bipartit graph", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				null);
		
		if (optionsResult == JOptionPane.OK_OPTION) {
			boolean directed = check_directed.isSelected();
			GraphCreator creator = new GC_Bipartit((int) countField.getValue(), chanceField.getValue(), directed);
			Graph graph = creator.createGraph();
				
			int width = Collector.getWindow().getDrawer().getWidth();
			int height = Collector.getWindow().getDrawer().getHeight();
			int border = 100;
			
			Point leftCorner = new Point(border,border);
			Point rightCorner = new Point(width - border, height - border);
			
			Layouter layouter = new L_Bipartit(leftCorner, rightCorner);
			Graph next = layouter.getCompleteLayoutedGraph(graph);
			
			new O_RelaxAllEdges(next).run();
			
			
			boolean graphTab = radioGraph.isSelected();
			
			if(graphTab) {
				new O_AddGraphTab(next).run();
			}
			else {
				new O_AddGraph(next).run();
			}	
		}
	}
		
	private JPanel createPanel() {
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(321, 216));
		panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
		
		radioGraph = new JRadioButton("New graph tab");
		radioGraph.setSelected(true);
		radioGraph.setAlignmentX(Component.CENTER_ALIGNMENT);
		radioGraph.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(radioGraph);
		panel.add(radioGraph);
		
		JRadioButton radioSlide = new JRadioButton("New slide");
		radioSlide.setAlignmentX(Component.CENTER_ALIGNMENT);
		radioSlide.setHorizontalAlignment(SwingConstants.CENTER);
		buttonGroup.add(radioSlide);
		panel.add(radioSlide);
		
		JSeparator separator = new JSeparator();
		panel.add(separator);
		
		check_directed = new JCheckBox("Directed");
		check_directed.setAlignmentX(Component.CENTER_ALIGNMENT);
		check_directed.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(check_directed);
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		JLabel label = new JLabel("Number of Vertices");
		panel_1.add(label);
		countField = new JSpinner(new SpinnerNumberModel(new Integer(5), new Integer(1), null, new Integer(1)));
		countField.setPreferredSize(new Dimension(50, 30));
		panel_1.add(countField);
		countField.setValue(5);
		JLabel label_1 = new JLabel("Chance of Edge (%)");
		label_1.setAlignmentX(Component.CENTER_ALIGNMENT);
		label_1.setHorizontalAlignment(SwingConstants.CENTER);
		panel.add(label_1);
		chanceField = new JSlider(0, 100);
		chanceField.setPaintLabels(true);
		chanceField.setMajorTickSpacing(10);
		chanceField.setPaintTicks(true);
		panel.add(chanceField);
		return panel;
	}
}
