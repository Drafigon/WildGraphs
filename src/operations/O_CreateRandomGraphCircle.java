package operations;

import graph.implementation.Graph;
import graphcreator.GC_Normal;
import graphcreator.GraphCreator;

import java.awt.Point;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;
import javax.swing.JCheckBox;
import javax.swing.BoxLayout;
import javax.swing.SwingConstants;

import java.awt.Component;
import java.awt.Dimension;

import layouter.L_Circle;
import layouter.Layouter;

import javax.swing.JRadioButton;
import javax.swing.ButtonGroup;
import javax.swing.JSeparator;

import data.Collector;

public class O_CreateRandomGraphCircle implements Operation {

	private JPanel panel;
	private JSpinner countField;
	private JSlider chanceField;
	private JCheckBox check_directed;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JRadioButton radioGraph;
	
	public O_CreateRandomGraphCircle() {
		panel = createPanel();
	}
	
	@Override
	public void run() {
		Object[] options = { "Create", "Cancel" }; 
		
		int optionsResult = JOptionPane.showOptionDialog(
				null, 
				panel, 
				"Create normal graph", 
				JOptionPane.OK_CANCEL_OPTION, 
				JOptionPane.QUESTION_MESSAGE, 
				null, 
				options, 
				null);
		
		if (optionsResult == JOptionPane.OK_OPTION) {
			int count = (int) countField.getValue();
			int chance = chanceField.getValue();
			boolean directed = check_directed.isSelected();
			
			GraphCreator creator = new GC_Normal(count, chance, directed);
			Graph graph = creator.createGraph();
			int midX = Collector.getWindow().getDrawer().getWidth()/2;
			int midY = Collector.getWindow().getDrawer().getHeight()/2;
			Layouter layouter = new L_Circle(new Point(midX, midY), midY-50);
			Graph next = layouter.getCompleteLayoutedGraph(graph);
			new O_RelaxAllEdges(next).run();
			
			if(radioGraph.isSelected()) {
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
		panel_1.add(countField);
		countField.setValue(5);
		countField.setPreferredSize(new Dimension(50,30));
		
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
