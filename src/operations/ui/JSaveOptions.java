package operations.ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.border.LineBorder;

public class JSaveOptions extends JPanel {
	public enum Save {
		ONE_GRAPH,
		COMPLETE_ANIMATION
	}
	
	
	private JRadioButton radioGraph;
	private JRadioButton radioAnimation;
		
	private JTextField labelPath;
	private JButton buttonPath;
	
	private JTextField textName;
	private JLabel labelMulti;
	private JLabel labelEnding;
	
	JSaveOptions() {
		this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		final ButtonGroup radioButtonGroup = new ButtonGroup();
		
		radioGraph = new JRadioButton("Only this graph");
		radioGraph.setSelected(true);
		radioButtonGroup.add(radioGraph);
		radioGraph.setAlignmentX(Component.CENTER_ALIGNMENT);
		this.add(radioGraph);
		
		radioAnimation = new JRadioButton("Complete animation");
		radioButtonGroup.add(radioAnimation);
		radioAnimation.setAlignmentX(0.5f);
		this.add(radioAnimation);
		
		SaveOptionListener listener = new SaveOptionListener();
		radioGraph.addActionListener(listener);
		radioAnimation.addActionListener(listener);
		
		
		JPanel panelPath = new JPanel();
		this.add(panelPath);
		
		JLabel lblPath = new JLabel("Path:");
		panelPath.add(lblPath);
		
		labelPath = new JTextField(".");
		labelPath.setPreferredSize(new Dimension(100, 20));
		labelPath.setBorder(new LineBorder(Color.GRAY));
		panelPath.add(labelPath);
		labelPath.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		buttonPath = new JButton("Browse");
		panelPath.add(buttonPath);
		BrowsePathListener browser = new BrowsePathListener();
		buttonPath.addActionListener(browser);
		
		JPanel panelName = new JPanel();
		this.add(panelName);
		
		textName = new JTextField();
		textName.setText("Name");
		textName.setColumns(10);
		panelName.add(textName);
		
		labelMulti = new JLabel("");
		panelName.add(labelMulti);
		
		labelEnding = new JLabel(".png");
		panelName.add(labelEnding);
		
		JSeparator separator_1 = new JSeparator();
		this.add(separator_1);
		
	}
	
	void setEnding(String ending) {
		labelEnding.setText(ending);
	}
	
	Save getGraphOption() {
		if(radioGraph.isSelected()) {
			return Save.ONE_GRAPH;
		}
		
		if(radioAnimation.isSelected()) {
			return Save.COMPLETE_ANIMATION;
		}
		
		return Save.ONE_GRAPH;
	}
	
	String getPath() {
		String path = labelPath.getText();
		if(path.equals("")) {
			return ".";
		}
		else {
			return path;
		}
	}
	
	String getFileName() {
		String name = textName.getText();
		if(name.equals("")) {
			return "MySavedGraph";
		}
		else {
			return name;
		}
	}
	
	String getFileEnding() {
		return labelEnding.getText();
	}
	
	private final class SaveOptionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			JRadioButton radio = (JRadioButton) e.getSource();
			if(radio == radioGraph) {
				labelMulti.setText("");				
			}
			if(radio == radioAnimation) {
				labelMulti.setText("_(Number)");
			}
		}		
	}
	
	private final class BrowsePathListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			String path = getPath();
			JFileChooser chooser = new JFileChooser(path);
			chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
			int result = chooser.showSaveDialog(null);
			
			if(result == JFileChooser.APPROVE_OPTION) {
				String nextPath = chooser.getSelectedFile().getPath();
				labelPath.setText(nextPath);
			}
		}
	}
}
